package homework04;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

/**
 * Report Generator
 *
 * @author Brayden Lopez
 * @version Feb 2, 2014
 * @param <T> the generic type
 */
public class ReportGenerator
{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	/** The foods. */
	static ArrayList<FoodItem> foods;
	
	/** The warehau5es. */
	static ArrayList<Warehouse<FoodItem>> warehau5;
	
	/** The current day. */
	static GregorianCalendar currentDay;
	
	/** The busy day tracker. */
	static ArrayList<Transaction> busyDayTracker;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 *  Is basically the constructor of the program.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		writeLine("Enter a file: ");
		String file = scan.nextLine();
		scan.close();
		
		if(!new File(file).canRead()) {
			writeLine("File cannot be read from");
			return;
		}
		
		foods = new ArrayList<FoodItem>();
		warehau5 = new ArrayList<Warehouse<FoodItem>>();
		currentDay = new GregorianCalendar();
		busyDayTracker = new ArrayList<Transaction>();

		runActivities(file);
		generateReport();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Generates a report.
	 */
	static void generateReport()
	{
		writeLine("Report by Brayden Lopez");
		writeLine();

		writeLine("Unstocked Products:");
		for (FoodItem itm : foods)
		{
			boolean isInAnyWarehouse = false;
			for (Warehouse<FoodItem> hau5 : warehau5)
			{
				if (itemExistsInWarehouse(itm, hau5))
					isInAnyWarehouse = true;
			}
			if (!isInAnyWarehouse)
			{
				writeLine(itm.getUpcCode() + " " + itm.getName());
			}
		}
		writeLine();

		writeLine("Fully-Stocked Products:");
		for (FoodItem itm : foods)
		{
			boolean isInEveryWherehouse = true;
			for (Warehouse<FoodItem> hau5 : warehau5)
			{
				if (!itemExistsInWarehouse(itm, hau5))
					isInEveryWherehouse = false;
			}
			if (isInEveryWherehouse)
			{
				writeLine(itm.getUpcCode() + " " + itm.getName());
			}
		}
		writeLine();

		writeLine("Busiest Days:");
		Collections.sort(busyDayTracker);
		for (Transaction trans : busyDayTracker)
		{
			writeLine(trans.warehouse.getName() + " " + new SimpleDateFormat("MM/dd/yyyy").format(trans.date.getTime()) + " " + trans.transactions);
		}
		writeLine();

	}

	/**
	 * Run activities.
	 *
	 * @param file the file
	 */
	@SuppressWarnings("unchecked")
	static void runActivities(String file)
	{
		ArrayList<Transaction> dailyTransactions = new ArrayList<Transaction>();
		Parser parser;
		try
		{
			parser = new Parser(file);
			ArrayList<AbstractMap.SimpleEntry<String, Object>> data = parser.parse();

			for (int i = 0, len = data.size(); i < len; i++)
			{
				AbstractMap.SimpleEntry<String, Object> line = data.get(i);
				if (line.getKey().equalsIgnoreCase("Food Item"))
				{
					Hashtable<String, Object> foodData = (Hashtable<String, Object>) line.getValue();

					// Create a food item //
					FoodItem item = new FoodItem(foodData.get("Name").toString(), foodData.get("UPC Code").toString(), Integer.parseInt(foodData.get("Shelf Life").toString()));
					foods.add(item);
				}

				if (line.getKey().equalsIgnoreCase("Warehouse"))
				{
					// Create a warehouse //
					Warehouse<FoodItem> hau5 = new Warehouse<FoodItem>(line.getValue().toString());
					
					// Tricky tricky. //
					if (findHouse(hau5.getName()) == null) 
						warehau5.add(hau5);

				}

				if (line.getKey().equalsIgnoreCase("Start date"))
				{
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					Date date = format.parse(line.getValue().toString());

					currentDay.setTime(date);

					for (Warehouse<FoodItem> hau5 : warehau5)
					{
						dailyTransactions.add(new Transaction(hau5, 0, (GregorianCalendar) currentDay.clone()));
						busyDayTracker.add(new Transaction(hau5, 0, (GregorianCalendar) currentDay.clone()));
					}
				}

				if (line.getKey().equalsIgnoreCase("Next Day"))
				{
					currentDay.add(Calendar.DAY_OF_YEAR, 1);
					for (Transaction current : dailyTransactions)
					{
						Transaction total = findTransactionFromWarehouse(current.warehouse);
						if (total.transactions < current.transactions)
						{
							total.transactions = current.transactions;
							total.date = (GregorianCalendar) currentDay.clone();
						}
						current.reset();
					}
				}

				if (line.getKey().equalsIgnoreCase("Receive"))
				{
					String[] split = line.getValue().toString().split(" ");
					FoodItem item = fromUPC(split[0]);
					int quantity = Integer.parseInt(split[1]);
					Warehouse<FoodItem> warehouse = findHouse(join(split, " ", 2));

					warehouse.getInventory().addItem(item, item.getExpirationDate(currentDay), quantity);

					// -- Transaction tracker -- //
					findTransactionFromWarehouse(dailyTransactions, warehouse).increment();
				}

				if (line.getKey().equalsIgnoreCase("Request"))
				{
					String[] split = line.getValue().toString().split(" ");
					FoodItem item = fromUPC(split[0]);
					int quantity = Integer.parseInt(split[1]);
					Warehouse<FoodItem> warehouse = findHouse(join(split, " ", 2));

					warehouse.getInventory().removeItem(item, item.getExpirationDate(currentDay), quantity);

					// -- Transaction tracker -- //
					findTransactionFromWarehouse(dailyTransactions, warehouse).increment();
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/*
	 * -------------------------- 
	 * - Utilities 
	 * --------------------------
	 */

	/**
	 * Writes a line to System.out.
	 *
	 * @param line the line
	 */
	static void writeLine(String line)
	{
		System.out.println(line);
	}

	/**
	 * Writes a line to System.out.
	 */
	static void writeLine()
	{
		System.out.println();
	}

	/** The upc cache. */
	static Map<String, FoodItem> upcCache = new HashMap<String, FoodItem>();

	/**
	 * From upc.
	 *
	 * @param upc the upc
	 * @return the food item
	 */
	static FoodItem fromUPC(String upc)
	{
		if (upcCache.containsKey(upc))
			return upcCache.get(upc);
		for (int i = 0, len = foods.size(); i < len; i++)
		{
			FoodItem item = foods.get(i);
			if (item.getUpcCode().equals(upc))
			{
				upcCache.put(upc, item);
				return item;
			}
		}
		return null;
	}

	/** The house cache. */
	static Map<String, Warehouse<FoodItem>> houseCache = new HashMap<String, Warehouse<FoodItem>>();

	/**
	 * Find house.
	 *
	 * @param location the location
	 * @return the warehouse
	 */
	static Warehouse<FoodItem> findHouse(String location)
	{
		if (houseCache.containsKey(location))
			return houseCache.get(location);
		for (int i = 0, len = warehau5.size(); i < len; i++)
		{
			Warehouse<FoodItem> item = warehau5.get(i);
			if (item.getName().equalsIgnoreCase(location))
			{
				houseCache.put(location, item);
				return item;
			}
		}
		return null;
	}

	/**
	 * Item exists in warehouse.
	 *
	 * @param item the item
	 * @param hau5 the warehouse
	 * @return true, if successful
	 */
	static boolean itemExistsInWarehouse(FoodItem item, Warehouse<FoodItem> hau5)
	{
		for (int i = 0, len = hau5.getInventory().inventory.size(); i < len; i++)
		{
			DatedItem<FoodItem> itm = hau5.getInventory().inventory.get(i);
			if (itm.item.equals(item))
				return true;
		}
		return false;
	}

	/**
	 * Join.
	 *
	 * @param s the array of strings
	 * @param delimiter the delimiter
	 * @param position the position
	 * @return the string
	 */
	static String join(String[] s, String delimiter, int position)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = position, len = s.length; i < len; i++)
		{
			builder.append(s[i] + delimiter);
		}
		return builder.toString().trim();
	}
	

	/**
	 * Find transaction from warehouse.
	 *
	 * @param hau5 the warehouse
	 * @return the transaction
	 */
	static Transaction findTransactionFromWarehouse(Warehouse<FoodItem> hau5)
	{
		return findTransactionFromWarehouse(busyDayTracker, hau5);
	}

	/**
	 * Find transaction from warehouse.
	 *
	 * @param list the list
	 * @param hau5 the warehouse
	 * @return the transaction
	 */
	static Transaction findTransactionFromWarehouse(ArrayList<Transaction> list, Warehouse<FoodItem> hau5)
	{
		for (Transaction trans : list)
			if (trans.warehouse == hau5)
				return trans;
		return null;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	/**
	 * Simple class containing a warehouse, number of transactions and a date.
	 */
	static class Transaction implements Comparable<Transaction>
	{
		
		/** The warehouse. */
		Warehouse<FoodItem> warehouse;
		
		/** The transactions. */
		int transactions;
		
		/** The date. */
		GregorianCalendar date;

		/**
		 * Instantiates a new transaction.
		 *
		 * @param warehouse the warehouse
		 * @param transactions the transactions
		 * @param date the date
		 */
		public Transaction(Warehouse<FoodItem> warehouse, int transactions, GregorianCalendar date)
		{
			this.warehouse = warehouse;
			this.transactions = transactions;
			this.date = date;
		}

		/**
		 * Increment the number of transactions.
		 */
		void increment()
		{
			transactions++;
		}

		/**
		 * Reset the number of transactions.
		 */
		void reset()
		{
			transactions = 0;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Transaction compare)
		{
			return Integer.compare(transactions, compare.transactions) * -1;
		}
	}


}

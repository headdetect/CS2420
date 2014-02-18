package homework04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The parser class for parsing specified files.
 *
 * @author Brayden Lopez
 * @version Feb 2, 2014
 */
public class Parser
{

	// ===========================================================
	// Constants
	// ===========================================================

	/** The Constant TOKEN_FOOD_ITEM. */
	private static final String TOKEN_FOOD_ITEM = "(?<=FoodItem - ).*";
	
	/** The Constant TOKEN_FOOD_ITEM_UPC. */
	private static final String TOKEN_FOOD_ITEM_UPC = "(?<=Code: ).([^\\s]+)";
	
	/** The Constant TOKEN_FOOD_ITEM_SHELF_LIFE. */
	private static final String TOKEN_FOOD_ITEM_SHELF_LIFE = "(?<=Shelf life: ).([^N]+)";
	
	/** The Constant TOKEN_FOOD_ITEM_NAME. */
	private static final String TOKEN_FOOD_ITEM_NAME = "(?<=([Nn]ame: )).*";
	
	/** The Constant TOKEN_WAREHOUSE. */
	private static final String TOKEN_WAREHOUSE = "(?<=[Ww]arehouse - ).*";
	
	/** The Constant TOKEN_START_DATE. */
	private static final String TOKEN_START_DATE = "(?<=[Ss]tart [Dd]ate: ).*";
	
	/** The Constant TOKEN_RECIEVE. */
	private static final String TOKEN_RECIEVE = "(?<=[Rr]eceive: ).*";
	
	/** The Constant TOKEN_REQUEST. */
	private static final String TOKEN_REQUEST = "(?<=[Rr]equest: ).*";
	
	/** The Constant TOKEN_NEXT_DAY. */
	private static final String TOKEN_NEXT_DAY = "[Nn]ext [Dd]ay:";
	
	/** The Constant TOKEN_END. */
	private static final String TOKEN_END = "[Ee]nd";

	// ===========================================================
	// Fields
	// ===========================================================

	/** The m file name. */
	private String mFileName;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new parser.
	 *
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Parser(String fileName) throws IOException
	{
		mFileName = fileName;
		if (!new File(fileName).canRead())
			throw new IOException("Cannot read from " + fileName);
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
	 * Will return an ArrayList of all of the tokens in a key value pair. EX: a key value pair could
	 * be: 
	 * {Request, 0984523912 8 Scottsdale} <br />
	 * {FoodItem, {{UPC Code, 0353264991}, {Shelf life, 2}, {Name, chestnut puree with vanilla}}} <br />
	 * <br />
	 * <b>Order of events is guaranteed.</b>
	 *
	 * @return the array list
	 * @throws FileNotFoundException the file not found exception
	 */
	public ArrayList<AbstractMap.SimpleEntry<String, Object>> parse() throws FileNotFoundException
	{
		ArrayList<AbstractMap.SimpleEntry<String, Object>> data = new ArrayList<AbstractMap.SimpleEntry<String, Object>>();
		Scanner scan = new Scanner(new File(mFileName));
		while (scan.hasNextLine())
		{
			String line = scan.nextLine();

			if (isToken(line, TOKEN_FOOD_ITEM))
			{
				String UPC = getTokenValue(line, TOKEN_FOOD_ITEM_UPC);
				String shelfLife = getTokenValue(line, TOKEN_FOOD_ITEM_SHELF_LIFE);
				String name = getTokenValue(line, TOKEN_FOOD_ITEM_NAME);

				Hashtable<String, Object> itemData = new Hashtable<String, Object>();
				itemData.put("UPC Code", UPC);
				itemData.put("Shelf Life", shelfLife);
				itemData.put("Name", name);

				data.add(new AbstractMap.SimpleEntry<String, Object>("Food Item", itemData));
			}

			if (isToken(line, TOKEN_WAREHOUSE))
			{
				String value = getTokenValue(line, TOKEN_WAREHOUSE);
				data.add(new AbstractMap.SimpleEntry<String, Object>("Warehouse", value));
			}

			if (isToken(line, TOKEN_START_DATE))
			{
				String value = getTokenValue(line, TOKEN_START_DATE);
				data.add(new AbstractMap.SimpleEntry<String, Object>("Start date", value));
			}

			if (isToken(line, TOKEN_RECIEVE))
			{
				String value = getTokenValue(line, TOKEN_RECIEVE);
				data.add(new AbstractMap.SimpleEntry<String, Object>("Receive", value));
			}

			if (isToken(line, TOKEN_REQUEST))
			{
				String value = getTokenValue(line, TOKEN_REQUEST);
				data.add(new AbstractMap.SimpleEntry<String, Object>("Request", value));
			}

			if (isToken(line, TOKEN_NEXT_DAY))
			{
				data.add(new AbstractMap.SimpleEntry<String, Object>("Next Day", ""));
			}

			if (isToken(line, TOKEN_END))
			{
				data.add(new AbstractMap.SimpleEntry<String, Object>("End", ""));
			}

		}

		scan.close();
		return data;
	}

	/**
	 * Gets the token value.
	 *
	 * @param input the input
	 * @param token the token
	 * @return the token value
	 */
	private static String getTokenValue(String input, String token)
	{
		Pattern reg = Pattern.compile(token);
		Matcher match = reg.matcher(input);
		if (!match.find())
			return null;
		return match.group(0).trim();
	}

	/**
	 * Checks if is token.
	 *
	 * @param input the input
	 * @param token the token
	 * @return true, if is token
	 */
	private static boolean isToken(String input, String token)
	{
		return getTokenValue(input, token) != null;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}

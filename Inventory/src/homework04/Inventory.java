package homework04;

import java.text.DateFormat;
import java.util.*;

/**
 * The class represents an inventory of items that each have a quantity and a
 * critical date. The list explicitly keeps track of both the quantity and
 * critical date for every item. No item in the inventory ever has a null date
 * or non-positive quantity. In the inventory, items are identified by both the
 * item object and their date and .equals is used to compare items and dates.
 * (Items that are the same but have different critical dates are kept
 * separately in the inventory.)
 * 
 * This class makes use of a DatedItem generic class (that immediately follows
 * this class in this file).
 * 
 * @author Peter Jensen
 * @version January 25, 2014
 */
public class Inventory<I extends Item>
{

	// Instance variables

	protected List<DatedItem<I>> inventory;

	/* Constructor */

	/**
	 * Builds an empty inventory.
	 */
	public Inventory()
	{
		inventory = new ArrayList<DatedItem<I>>();
	}

	/**
	 * Adds an item to the inventory with the specified critical date and
	 * quantity. The item and date must be non-null and the quantity must be
	 * positive or no action is taken. If the item already exists with the
	 * specified critical date in the inventory, the quantity for that item (and
	 * date) will be increased by the specified quantity.
	 * 
	 * @param item
	 *            an item
	 * @param criticalDate
	 *            a date
	 * @param quantity
	 *            a positive item count
	 * 
	 */
	public void addItem(I item, GregorianCalendar criticalDate, int quantity)
	{
		// Check for invalid parameters.

		if (item == null || criticalDate == null || quantity < 1)
			return;

		// Make a dated item for these parameters.

		DatedItem<I> d = new DatedItem<I>(item, criticalDate, quantity);

		// Try to find it in the inventory.

		for (DatedItem<I> i : inventory)
			if (i.isSameItemAndDate(d))
			{
				// Found it - increase quantity and quit.

				i.quantity += quantity;
				return;
			}

		// Didn't find it - add it to inventory.

		inventory.add(d);
	}

	/**
	 * Reduces the quantity of an item (specified by item object and date) in
	 * the inventory by the specified quantity. If the item's quantity is
	 * reduced to or below 0, the item is removed from the inventory. If the
	 * quantity is not positive, or if the item or date is null, no action is
	 * taken.
	 * 
	 * @param item
	 *            an item
	 * @param criticalDate
	 *            a date
	 * @param quantity
	 *            a positive item count
	 */
	public void removeItem(I item, GregorianCalendar criticalDate, int quantity)
	{
		// Check for invalid parameters.

		if (item == null || criticalDate == null || quantity < 1)
			return;

		// Make a dated item for these parameters.

		DatedItem<I> d = new DatedItem<I>(item, criticalDate, quantity);

		// Try to find it in the inventory.

		for (DatedItem<I> i : inventory)
			if (i.isSameItemAndDate(d))
			{
				// Found it - decrease quantity and remove it if quantity falls
				// below 0.

				i.quantity -= quantity;
				if (i.quantity < 1)
					inventory.remove(i);

				return;
			}
	}

	/**
	 * This method returns the quantity of this item (with the specified
	 * critical date) in the inventory.
	 * 
	 * @param item
	 *            an item
	 * @param criticalDate
	 *            a date
	 * @return the quantity of that item with that date in the inventory
	 */
	public int getQuantity(I item, GregorianCalendar criticalDate)
	{
		// Check for invalid parameters.

		if (item == null || criticalDate == null)
			return 0;

		// Make a dated item for these parameters.

		DatedItem<I> d = new DatedItem<I>(item, criticalDate, 0);

		// Try to find it in the inventory.

		for (DatedItem<I> i : inventory)
			if (i.isSameItemAndDate(d))
				return i.quantity;// Found it - return the quantity

		// Not found.

		return 0;
	}

	/**
	 * This method returns the critical date for the specified item in the
	 * inventory. If multiple matching items are in the inventory with different
	 * critical dates, the oldest critical date for that kind of item is
	 * returned. If no such item is found in the inventory, null is returned.
	 * 
	 * @param item
	 *            an item
	 * @return the oldest critical date for this item
	 */
	public GregorianCalendar getDate(I item)
	{
		GregorianCalendar result = null;

		// Optimization loop on item dates for the specified item

		for (DatedItem<I> i : inventory)
			if (i.item.equals(item) && (result == null || i.criticalDate.before(result)))
				result = i.criticalDate;

		return result;

	}

	/**
	 * This method returns an ArrayList of items whose critical date is before
	 * the target date.
	 * 
	 * @param targetDate
	 *            a date
	 * @return a list of items whose critical date is before the target date
	 */
	public ArrayList<I> getItemsPastDate(GregorianCalendar targetDate)
	{
		ArrayList<I> result = new ArrayList<I>();

		// Find all the past due items.

		for (DatedItem<I> i : inventory)
			if (i.criticalDate.before(targetDate))
				result.add(i.item);

		return result;
	}

	/**
	 * Programmer debugging method - not for general use. Outputs the contents
	 * of this inventory to the screen with a title label.
	 */
	void outputToConsole(String inventoryName)
	{
		DateFormat formatter = DateFormat.getDateInstance();

		System.out.println();
		String s = "Contents of Inventory: " + inventoryName;
		System.out.println(s);
		for (int i = 0; i < s.length(); i++)
			System.out.print('-');
		System.out.println();
		for (DatedItem<I> i : inventory)
			System.out.printf("Item: %-20s  Item Type: %-20s  Critical date: %13s     Quantity: %3s\n", i.item.getName(), i.item.getClass().getSimpleName(),
					formatter.format(i.criticalDate.getTime()), i.quantity);
		System.out.println();
	}
}

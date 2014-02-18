package homework04;

import java.util.GregorianCalendar;

/**
 * An <i>almost</i> immutable wrapper class for item. Contains a quantity count and
 * critical date
 *
 * @author Brayden Lopez
 * @version January 22, 2014
 * @param <T> the generic type
 */
public class DatedItem<T extends Item>
{

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	/** The item. */
	public T item;
	
	/** The quantity. */
	public int quantity;
	
	/** The critical date. */
	public GregorianCalendar criticalDate;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new dated item.
	 *
	 * @param item the item
	 * @param criticalDate the critical date
	 * @param quantity the quantity
	 */
	public DatedItem(T item, GregorianCalendar criticalDate, int quantity)
	{
		this.item = item;
		this.criticalDate = criticalDate;
		this.quantity = quantity;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof DatedItem))
			return false;
		
		@SuppressWarnings("rawtypes")
		DatedItem itm = (DatedItem)o; // Can't override equals and preserve type safety. //
		
		return matches(itm.item, itm.criticalDate) && quantity == itm.quantity;
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Matches.
	 *
	 * @param item the item
	 * @param criticalDate the critical date
	 * @return true, if successful
	 */
	public boolean matches(Item item, GregorianCalendar criticalDate)
	{
		return item.equals(this.item) &&
				criticalDate.equals(this.criticalDate);
	}
	

	/**
     * Returns true if this dated item and the other dated item share the same items and dates.
     * 
     * @param other
     *            some other dated item
     * @return true if they are equivalent items and dates
     */
    public boolean isSameItemAndDate (DatedItem<T> other)
    {
        return item.getName().equals(other.item.getName()) && criticalDate.equals(other.criticalDate);
    }     

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	

	

	

	   
}
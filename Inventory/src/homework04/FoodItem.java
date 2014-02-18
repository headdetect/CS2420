/**
 * 
 */
package homework04;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A food item object is an Item object that has a name, a UPC code, and a shelf life.
 * FoodItem objects can be added to an inventory.
 * 
 * @author Peter Jensen
 * @version January 25, 2014
 */
public class FoodItem extends Item implements Comparable<FoodItem>
{
    // Instance variables.
    
    private String upcCode;  // Keeping the upcCode as a String simplifies the code.
    private int shelfLife;
    
    /**
     * Builds a food item with the specified name, UPC code, and shelf life (in days).
     * 
     * @param name the name of this food item
     * @param upcCode the UPC code printed on the packaging
     * @param shelfLife the number of days this item will stay fresh
     */
    public FoodItem (String name, String upcCode, int shelfLife)
    {
        super(name);
        this.upcCode = upcCode;
        this.shelfLife = shelfLife;
    }
    
    /**
     * Returns the UPC code for this food item.
     * 
     * @return the UPC code for this item
     */
    public String getUpcCode()
    {
        return upcCode;
    }
    
    /**
     * Given a manufacture date, this method will return the corresponding expiration date
     * for this food item.  The item expires on the beginning of the expiration date.
     * This method can be used repeatedly to compute expiration dates for different
     * manufacturing dates.
     * 
     * @param manufactureDate the date of manufacture
     * @return the expiration date
     */
    public GregorianCalendar getExpirationDate(GregorianCalendar manufactureDate)
    {
        GregorianCalendar result = (GregorianCalendar) manufactureDate.clone();
        result.add(Calendar.DAY_OF_MONTH, shelfLife);
        return result;
    }
        
    /**
     * Compares this FoodItem to another FoodItem to determine relative order (for sorting
     * food items).  FoodItems are ordered first by name, then in the case of ties by UPC, and by shelf life last. 
     * Zero is returned if this food item is equal to the other food item.  A negative integer is returned if
     * this food item is less than the other food item.  A positive integer is returned if this food item is
     * greater than the other food item. 
     * 
     * @param other an item to compare this item to
     * @return -1, 0, or 1 if this item is less than, equal to, or greater than the other item
     */
    public int compareTo(FoodItem other)
    {
        int result;
        
        result = name.compareTo(other.name);
        if (result != 0)
            return result;
        
        result = upcCode.compareTo(other.upcCode);
        if (result != 0)
            return result;
        
        if (this.shelfLife < other.shelfLife)
            return -1;
        else if (this.shelfLife > other.shelfLife)
            return 1;
        else
            return 0;
    }
    
    /**
     * Returns true if this FoodItem represents the same food item
     * as the other object, false otherwise.
     * 
     * @return true if these items represent the same food
     */
    public boolean equals (Object other)
    {
        // If they are the same reference, they are the same item.
        
        if (this == other)
            return true;
        
        // If the other object is not a food item, they cannot be equal.
        
        if (!(other instanceof FoodItem))
            return false;
        
        // Use the comparison method - equal if result is 0.
        
        FoodItem food = (FoodItem) other;
        
        return this.compareTo(food) == 0;        
    }
    
    /**
     * Returns a hash code for this food item.  Equal food items are
     * guaranteed to have equal hash codes.
     * 
     * @return this item's hash code
     */
    public int hashCode ()
    {
        return name.hashCode() + upcCode.hashCode() + shelfLife;
    }
    
    
    /**
     * Returns a String that describes this FoodItem object.
     * 
     * @return a String describing this object
     */
    public String toString ()
    {
        return "FoodItem - UPC Code: " + upcCode + "  Shelf life: " + shelfLife + "  Name: " + name;
    }

}

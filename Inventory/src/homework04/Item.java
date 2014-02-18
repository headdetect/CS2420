/**
 * 
 */
package homework04;

/**
 * <p>
 * This is a simple item class for assignment #2.  All items
 * have a name, and all items can be compared for equality.
 * It is intended that for assignment #3 you will inherit from 
 * this class to create some interesting item subclasses.
 * </p>
 * 
 * <p>
 * Items are immutable.
 * </p>
 * 
 * @author Peter Jensen
 * @version January 25, 2014
 */
public class Item
{
    // Instance variables
    
    protected String name;
    
    /* Constructor */
    
    /**
     * Constructs a general purpose item with the
     * given name.
     * 
     * @param name a name for this item
     */
    public Item (String name)
    {
        this.name = name;
    }
    
    /* Accessor method */
    
    /**
     * Returns the name of this item as a String.
     * 
     * @return the name of this item
     */
    public String getName ()
    {
        return name;
    }
    
    /* Equality methods */
    
    /**
     * Returns true if this item and the other object
     * both represent the same item.
     * 
     * @return true if the items are equal
     */
    public boolean equals (Object other)
    {
        // If they are the same reference, they are the same item.
        
        if (this == other)
            return true;
        
        // If the other object is not an item, they cannot be equal.
        
        if (!(other instanceof Item))
            return false;
        
        // For simple items, the items are equal if their names are equal.
        
        Item otherItem = (Item) other;
        
        return this.name.equals(otherItem.name);
    }
    
    /**
     * Returns a hashCode that is guaranteed to be equal for
     * items that are equal.
     * 
     * @return the hash code for this item
     */
    public int hashCode ()
    {
        return name.hashCode();
    }
}

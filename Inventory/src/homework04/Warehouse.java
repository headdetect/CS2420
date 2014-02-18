package homework04;

/**
 * Warehouse wrapper class.
 *
 * @author Brayden Lopez
 * @version Feb 2, 2014
 * @param <T> the generic type
 */
public class Warehouse<T extends Item>
{

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The name. */
	private String mName;
	
	/** The inventory. */
	private Inventory<T> mInventory;
	
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new warehouse.
	 *
	 * @param mName the m name
	 */
	public Warehouse(String mName)
	{
		super();
		this.setName(mName);
		this.setInventory(new Inventory<T>());
	}


	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * Gets the inventory.
	 *
	 * @return the mInventory
	 */
	public Inventory<T> getInventory()
	{
		return mInventory;
	}


	/**
	 * Sets the inventory.
	 *
	 * @param mInventory the mInventory to set
	 */
	public void setInventory(Inventory<T> mInventory)
	{
		this.mInventory = mInventory;
	}



	/**
	 * Gets the name.
	 *
	 * @return the mName
	 */
	public String getName()
	{
		return mName;
	}




	/**
	 * Sets the name.
	 *
	 * @param mName the mName to set
	 */
	public void setName(String mName)
	{
		this.mName = mName;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}

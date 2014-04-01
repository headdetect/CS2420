package homework08;



/**
 * Objects of this class represent a set of sortable values. The set has the following performace characteristics:
 * 
 * - the set is kept in a sorted linked list
 * 
 * - getting the size of the set - theta(1)
 * 
 * - adding, removing, or searching for a random element - theta(n)
 * 
 * - adding, removing, or searching for an element that immediately follows the previously accessed element - theta(1)
 * 
 * In other words, this set performs very well if additions or removals occur with long sequential runs of ordered data values.
 * 
 * Note: This data structure is not threadsafe because instance variables are used to keep track of visit state. An iterator would be a much better idea!
 * 
 * @author your_name_here
 * @version current_date_here
 */
public class SpecialtySet<E extends Comparable<E>>
{
	// Instance variables. Students are allowed
	// only these, do not add or change instance variables.

	private Node root;
	private int size;

	// Instance methods below.

	/**
	 * Constructs an empty set.
	 */
	public SpecialtySet()
	{
	}

	/**
	 * Returns the number of elements in this SpecialtySet.
	 * 
	 * @return a count of the elements in this set
	 */
	public int size()
	{
		// We keep track of the size when doing operations, //
		// So we don't have to iterate through the whole //
		// list just to count how many items we have //
		return size;
	}


	/**
	 * Returns 'true' if the specified data is in the set, false otherwise.
	 * 
	 * @param data
	 *            A data value to search for
	 * @return true iff the data is in the set
	 */
	/* Implementation note:  The postconditions for the
	 * 'locatePosition' function are also guaranteed for
	 * this function.
	 */
	public boolean contains(E data)
	{
		if (size == 0)
			return false;

		return true;
	}

	/**
	 * Adds the specified data to the set. (If the data is already in the set, the data is ignored.)
	 * 
	 * @param data
	 *            a data value to be added to the set
	 */
	/* Implementation note:  If an element is actually added, 
	 *   'current' will refer to the node containing the added
	 *   data after this function, and 'last' will refer
	 *   to the previous node (as appropriate).
	 */
	public void add(E data)
	{
		if (!contains(data))
		{
			
			size++;
		}
	}
	

	/**
	 * Guarantees that the specified data is not in the set. (The data is removed if needed.)
	 * 
	 * @param data
	 *            a data value to be removed from the set
	 */
	/* Implementation note:  If an element is actually removed, 
	 *   'current' will refer to the node following the removed
	 *   node after this function, and 'last' will refer
	 *   to the previous node (as appropriate).
	 */
	public void remove(E data)
	{
		if (!contains(data))
			return;

	}
	
	

	// An example of an inner class (a class within another object).

	/**
	 * A private helper class for the SpecialtySet class. Node objects are used to construct linked lists in a SpecialtySet.
	 * 
	 * Students are not allowed to change this class.
	 * 
	 * @author Peter Jensen
	 * @version 2/22/2014
	 */
	private class Node
	{
		private E data; 			// The data element - cannot be changed after it is assigned
		private Node left, right; 	// Initialized to null when this object is created
		private Node parent; 		// Parent - initialized to null
		private int height; 		// Height of this subtree - initialized to 0
		
		/**
		 * Builds this node to contain the specified data. By default, this node does not point to any other nodes (next is null), although it is expected that 'next' may change.
		 * 
		 * Also note, the data variable is final, the data reference cannot be changed. (This fact is largely irrelevant.)
		 * 
		 * @param data
		 *            the data to store in the node
		 */
		Node(E data)
		{
			this.data = data;
		}

		@Override
		public String toString()
		{
			return data.toString();
		}
	}

}

package homework06;

import java.util.Iterator;

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

	private Node head, last, current;
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
	 * A private helper function that locates the position in the linked list where 'data' exists, or could be inserted. Upon completion, two instance variables are set:
	 * 
	 * The 'last' variable points to the node prior to the position that 'data' should occupy in the list. 'Last' will be null if 'data' should occupy the first position in the
	 * list.
	 * 
	 * The 'current' variable either points to the node containing the data (if the data exists in the list), or the node following the position that the data should occupy (if the
	 * data does not exist in the list). 'Current' will be null only if the data is not in the list and would naturally appear at the end of the list.
	 * 
	 * Finally, if the node at 'last' exists and last.data < data, this function begins the search at 'current'. Otherwise, this function starts the search at the beginning of the
	 * linked list.
	 * 
	 * @param data
	 */
	private void locatePosition(E data)
	{
		if (size == 0)
		{
			current = null;
			last = null;
			return;
		}
		current = head;
		last = null;
		while (current != null)
		{
			int compare = data.compareTo(current.data);

			if (compare == 0)
			{
				return;
			}
			else if (compare > 1)
			{
				// Do nothing //
			}
			else if (compare < 0)
			{
				return;
			}

			last = current;
			current = current.next;

		}
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

		Node tempCurrent = head;
		do
		{
			if (tempCurrent.data.equals(data))
				return true;

			tempCurrent = tempCurrent.next;
		}
		while (tempCurrent != null);

		return false;
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
			if (size == 0)
			{
				head = new Node(data);
				current = head;
			}
			else
			{
				locatePosition(data);
				if (last == null && head != null)
				{

					Node tempHead = head;

					head = new Node(data);
					head.next = tempHead;
				}
				else
				{

					Node tempCurrent = current;

					current = new Node(data);
					current.next = tempCurrent;

					if (last != null)
						last.next = current;
				}

			}
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
		locatePosition(data);
		if (current != null && current.data.equals(data))
		{
			if (last != null)
				last.next = current.next;
			current = last.next;
			size--;
		}
		
	}

	/**
	 * A debugging function (not required) that verifies the element count and element sortedness. My test also printed out the contents of the set.
	 * 
	 * Students may write debugging functions like this one, but they may not write external tests or other internal code that depends on the execution of any internal test
	 * function.
	 * 
	 * @return true iff the set passes an internal test
	 */
	boolean validate()
	{
		return false; // Stub
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
		private final E data; // The data element - cannot be changed after it is assigned
		private Node next; // Initialized to null when this object is created

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
	}

}

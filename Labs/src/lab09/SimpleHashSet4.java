package lab09;

/**
 * This class is a hash table implementation of a set. It is designed to support simple hash table experiments. Each table has a fixed maximum size. Elements can be added, deleted,
 * or queried, but the number of elements in the set is not available to the user. Also there is no mechanism for iterating through the elements in a table. (These restrictions are
 * imposed to prevent inaccurate testing strategies.)
 * 
 * Element equality is determined using the .equals method.
 * 
 * The hash table is implemented using open addressing and linear probing.
 * 
 * In addition to the methods for accessing the set, this implementation keeps a probe count that can be read and reset by the user. Every read of a location in the hash table is
 * considered a probe.
 * 
 * Finally, the user can request a cluster table. This is just a String object that indicates which locations in the hash table are filled.
 * 
 * @author Peter Jensen
 * @version Spring 2014
 */
public class SimpleHashSet4
{
	// Instance variables.

	private Node[] table;
	private int capacity;
	private int probeCount;
	private int size;

	/**
	 * Builds a hash set with the specified maximum size.
	 * 
	 * @param capacity
	 *            the size of the hash table
	 */
	public SimpleHashSet4(int capacity)
	{
		this.capacity = capacity;
		this.table = new Node[capacity];
		this.probeCount = 0;
	}

	/**
	 * Adds the specified element to the set. If the hash table already contains an object equal to the specified object, the specified object will not be added.
	 * 
	 * @param element
	 *            any object
	 * @throws TableFullException
	 *             if the hash table is full
	 */
	public void add(Object element)
	{

		if (element == null)
			return;

		int hash = Math.abs(element.hashCode()) % capacity;

		Node n = table[hash];
		if (n == null)
		{
			n = new Node(element);
			size++;
			table[hash] = n;
		}
		else
		{
			Node q = n;
			Node prev = null;
			while (true)
			{
				probeCount++;
				prev = q;
				q = q.next;
				if (q == null)
				{
					q = prev;
					break;
				}
			}
			if (q != null)
				q.next = new Node(element);
			else
				n.next = new Node(element);
			size++;
		}

	}

	/**
	 * Returns true if this element is in the set. The element is considered to be in the set if it is equal to any object in the set.
	 * 
	 * @param element
	 *            any object
	 * @return true if the element is in the set
	 */
	public boolean contains(Object element)
	{
		if (element == null)
			return false;

		int hash = Math.abs(element.hashCode()) % capacity;

		Node n = table[hash];
		if (n == null)
		{
			return false;
		}
		else
		{
			Node q = n;
			while (q != null)
			{
				probeCount++;
				if (q.value.equals(element))
					return true;
				q = q.next;
			}
			return false;
		}
	}

	/**
	 * Removes the specified element from the set. If the set does not contain an object equal to the element, nothing happens.
	 */
	public void delete(Object element)
	{
		if (element == null)
			return;

		int hash = Math.abs(element.hashCode()) % capacity;

		Node n = table[hash];
		if (n == null)
		{
			return;
		}
		else
		{
			Node q = n;
			while (q != null)
			{
				probeCount++;
				if (q.value.equals(element))
				{
					n.next = q.next;
					size--;
					return;
				}
				q = q.next;
			}
		}
	}

	/**
	 * Returns the number of probes made to the table since the last probe count resent.
	 * 
	 * @return the probe count
	 */
	public int getProbeCount()
	{
		return probeCount;
	}

	/**
	 * Resets the probe count to 0.
	 */
	public void resetProbeCount()
	{
		probeCount = 0;
	}

	public int size()
	{
		return size;
	}

	/**
	 * A debugging method for printing out the table contents.
	 */
	public void debugTable()
	{
		for (int i = 0; i < capacity; i++)
		{
			System.out.print("Position " + i + ": " + table[i]);
			if (table[i] != null)
				System.out.println(" (hashes to location " + (Math.abs(table[i].hashCode()) % capacity) + ")");
			else
				System.out.println();
		}
		System.out.println();
	}

	/**
	 * An exception class for indicating the table is full.
	 */
	static class TableFullException extends RuntimeException
	{
		TableFullException()
		{
			super("The hash table is full.");
		}
	}

	static class Node
	{
		public Node next;
		public Object value;

		public Node(Object value)
		{
			this.value = value;
		}

	}
}

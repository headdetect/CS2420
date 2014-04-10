package lab09;

/**
 * This class is a hash table implementation of a set. It is designed to support
 * simple hash table experiments. Each table has a fixed maximum size. Elements
 * can be added, deleted, or queried, but the number of elements in the set is
 * not available to the user. Also there is no mechanism for iterating through
 * the elements in a table. (These restrictions are imposed to prevent
 * inaccurate testing strategies.)
 * 
 * Element equality is determined using the .equals method.
 * 
 * The hash table is implemented using open addressing and linear probing.
 * 
 * In addition to the methods for accessing the set, this implementation keeps a
 * probe count that can be read and reset by the user. Every read of a location
 * in the hash table is considered a probe.
 * 
 * Finally, the user can request a cluster table. This is just a String object
 * that indicates which locations in the hash table are filled.
 * 
 * @author Peter Jensen
 * @version Spring 2014
 */
public class SimpleHashSet2 {
	// Instance variables.

	private Object[] table;
	private int capacity;
	private int probeCount;

	/**
	 * Builds a hash set with the specified maximum size.
	 * 
	 * @param capacity
	 *            the size of the hash table
	 */
	public SimpleHashSet2(int capacity) {
		this.capacity = capacity;
		this.table = new Object[capacity];
		this.probeCount = 0;
	}

	/**
	 * Adds the specified element to the set. If the hash table already contains
	 * an object equal to the specified object, the specified object will not be
	 * added.
	 * 
	 * @param element
	 *            any object
	 * @throws TableFullException
	 *             if the hash table is full
	 */
	public void add(Object element) {

		if(element == null)
			return;
		
		int hash = Math.abs(element.hashCode()) % capacity;

		for (int retry = 0; retry < capacity; retry++) {
			int pos = (hash + retry) % capacity;
			Object val = table[pos];
			probeCount++;
			if (val == null) {
				table[pos] = element;
				return;
			}
			if (val.equals(element))
				return;
		}

		throw new TableFullException();
	}

	/**
	 * Returns true if this element is in the set. The element is considered to
	 * be in the set if it is equal to any object in the set.
	 * 
	 * @param element
	 *            any object
	 * @return true if the element is in the set
	 */
	public boolean contains(Object element) {
		if (element == null)
			return false;

		int hash = Math.abs(element.hashCode()) % capacity;

		for (int retry = 0; retry < capacity; retry++) {
			int pos = (hash + retry) % capacity;
			Object val = table[pos];
			probeCount++;

			if(val == null)
				return false;
			
			if (val.equals(element))
				return true;
			
		}

		return false;
	}

	/**
	 * Removes the specified element from the set. If the set does not contain
	 * an object equal to the element, nothing happens.
	 */
	public void delete(Object element) {
		// Compute the hash code for the element, confine it to the table size.
		// (A simple division-based hash function.)

		int hash = Math.abs(element.hashCode()) % capacity;

		// Try to locate the element at the hashed position. If it is not
		// there, simply return.

		int retry = 0;
		for (; retry < capacity; retry++) {
			// Determine what is in the hash table by probing it.

			Object probed = table[(hash + retry) % capacity];
			probeCount++;

			// If that location in the table is empty, the element is not in the
			// table.

			if (probed == null)
				return;

			// If that location contains the element, terminate the loop.

			if (probed.equals(element))
				break;
		}

		// If the table was full, but the element was not found, return.

		if (retry == capacity)
			return;

		// Remove the element. This involves possibly moving other elements
		// in the hash table to collapse the cluster.

		int freePosition = (hash + retry) % capacity;

		for (int count = 1; (freePosition + count) % capacity != hash; count++) {
			// Determine what is in the hash table by probing it.

			Object probed = table[(freePosition + count) % capacity];
			probeCount++;

			// If null, we're done collapsing the cluster.

			if (probed == null)
				break;

			// If the probed object could legally occupy the free position, put
			// it in the free position.
			// To do this, determine the retry count that was used to place the
			// probed element
			// in the cache.

			int probedHash = Math.abs(probed.hashCode()) % capacity;
			int probedRetry = ((freePosition + count) - probedHash + capacity)
					% capacity;

			// If the probed retry is greater than or equal to our offset from
			// the free
			// position, move the element into the free position.

			if (probedRetry >= count) {
				table[freePosition] = probed;
				freePosition = (freePosition + count) % capacity;
				count = 0;
			}
		}

		// Clear the free position.

		table[freePosition] = null;
	}

	/**
	 * Returns a string that indicates which elements of the table are used.
	 * 
	 * @return a string that shows table usage.
	 */
	public String getClusterMap() {
		StringBuilder result = new StringBuilder(table.length);
		for (Object o : table)
			result.append(o == null ? '.' : 'X');

		return result.toString();
	}

	/**
	 * Returns the number of probes made to the table since the last probe count
	 * resent.
	 * 
	 * @return the probe count
	 */
	public int getProbeCount() {
		return probeCount;
	}

	/**
	 * Resets the probe count to 0.
	 */
	public void resetProbeCount() {
		probeCount = 0;
	}

	/**
	 * A debugging method for printing out the table contents.
	 */
	public void debugTable() {
		for (int i = 0; i < capacity; i++) {
			System.out.print("Position " + i + ": " + table[i]);
			if (table[i] != null)
				System.out.println(" (hashes to location "
						+ (Math.abs(table[i].hashCode()) % capacity) + ")");
			else
				System.out.println();
		}
		System.out.println();
	}

	/**
	 * An exception class for indicating the table is full.
	 */
	static class TableFullException extends RuntimeException {
		TableFullException() {
			super("The hash table is full.");
		}
	}
}

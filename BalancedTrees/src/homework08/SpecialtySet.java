package homework08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int LEFT = -1;
	private static final int CURRENT = 0;
	private static final int RIGHT = 1;

	// ===========================================================
	// Fields
	// ===========================================================

	// Instance variables. Students are allowed
	// only these, do not add or change instance variables.
	private Node<E> root;
	private int size;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Constructs an empty set.
	 */
	public SpecialtySet()
	{
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

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

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	// Public Operations
	// -----------------------------------------------------------
	// -----------------------------------------------------------

	/**
	 * Returns 'true' if the specified data is in the set, false otherwise.
	 * 
	 * @param data
	 *            A data value to search for
	 * @return true if the data is in the set
	 */
	public boolean contains(E data)
	{
		return contains(root, data);
	}

	/**
	 * Adds the specified data to the set. (If the data is already in the set, the data is ignored.)
	 * 
	 * @param data
	 *            a data value to be added to the set
	 */
	public void add(E data)
	{
		// We don't need a contains because it just ignores if it already exists //
		Node<E> node = new Node<E>(data);
		size++;
		add(root, node);

	}

	/**
	 * Guarantees that the specified data is not in the set. (The data is removed if needed.)
	 * 
	 * @param data
	 *            a data value to be removed from the set
	 */
	public void remove(E data)
	{
		Node<E> node = find(root, data);
		if (node == null)
			return;

		remove(node);
		size--;
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	// Private Drivers
	// -----------------------------------------------------------
	// -----------------------------------------------------------

	private boolean contains(Node<E> node, E data)
	{
		if (root == null || size == 0 || node == null)
		{
			return false;
		}

		return find(node, data) != null;
	}

	private void add(Node<E> node, Node<E> toInsert)
	{
		if (node == null)
		{
			root = toInsert;
		}
		else
		{
			int direction = getDirection(toInsert.data, node.data);

			if (direction == LEFT)
			{
				if (node.left == null)
				{
					node.left = toInsert;
					toInsert.parent = node;
					balance(node);
				}
				else
				{
					add(node.left, toInsert);
				}

			}
			else if (direction == RIGHT)
			{
				if (node.right == null)
				{
					node.right = toInsert;
					toInsert.parent = node;
					balance(node);
				}
				else
				{
					add(node.right, toInsert);
				}
			}
			else
			{
				// Already exists. Ignore //
			}
			
		}
	}

	private Node<E> find(Node<E> start, E data)
	{
		while( start != null ) {
			if( data == start.data ) return start; 
			int direction = getDirection(data, start.data);
			
			if(direction == LEFT) {
				start = start.left;
			} else if (direction == RIGHT) {
				start = start.right;
			} else {
				return start;
			}
		}
		return null;
	}

	private void remove(Node<E> node)
	{
		Node<E> replace;
		if (node.left == null || node.right == null)
		{
			if (node.parent == null)
			{
				this.root = null;
				node = null;
				return;
			}
			replace = node;
		}
		else
		{
			replace = getSuccessor(node);
			node.data = replace.data;
		}

		Node<E> parent;
		if (replace.left != null)
			parent = replace.left;
		else
			parent = replace.right;

		if (parent != null)
			parent.parent = replace.parent;

		if (replace.parent == null)
			this.root = parent;
		else
		{
			if (replace == replace.parent.left)
				replace.parent.left = parent;
			else
				replace.parent.right = parent;
			balance(replace.parent);
		}
		replace = null;

	}

	private void balance(Node<E> node)
	{
		int difference = getNodeHeight(node.right) - getNodeHeight(node.left);
		if (difference <= -2)
		{

			if (getNodeHeight(node.left.left) >= getNodeHeight(node.left.right))
			{
				node = rotateRight(node);
			}
			else
			{
				node.left = rotateLeft(node.left);
				node = rotateRight(node);

			}
		}
		else if (difference >= 2)
		{
			if (getNodeHeight(node.right.right) >= getNodeHeight(node.right.left))
			{
				node = rotateLeft(node);
			}
			else
			{
				node.right = rotateRight(node.right);
				node = rotateLeft(node);
			}
		}

		if (node.parent != null)
			balance(node.parent);
		else {
			root = node;
			System.out.println("------------ Balancing finished ----------------");
		}
	}

	private Node<E> getSuccessor(Node<E> node)
	{
		if (node.right != null)
		{
			Node<E> right = node.right;
			while (right.left != null)
				right = right.left;
			return right;
		}
		else
		{
			Node<E> parent = node.parent;
			while (parent != null && node == parent.right)
			{
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	private Node<E> rotateRight(Node<E> node)
	{
		Node<E> left = node.left;
		left.parent = node.parent;

		node.left = left.right;

		if (node.left != null)
			node.left.parent = node;

		left.right = node;
		node.parent = left;

		if (left.parent != null)
			if (left.parent.right == node)
				left.parent.right = left;
			else if (left.parent.left == node)
				left.parent.left = left;

		return left;
	}

	private Node<E> rotateLeft(Node<E> node)
	{
		Node<E> right = node.right;
		right.parent = node.parent;

		node.right = right.left;

		if (node.right != null)
			node.right.parent = node;

		right.left = node;
		node.parent = right;

		if (right.parent != null)
			if (right.parent.right == node)
				right.parent.right = right;
			else if (right.parent.left == node)
				right.parent.left = right;

		return right;
	}

	private int getNodeHeight(Node<E> node)
	{
		if (node == null)
			return 0;
		if (node.left == null && node.right == null)
			return 1;
		else if (node.left == null)
			return 1 + getNodeHeight(node.right);
		else if (node.right == null)
			return 1 + getNodeHeight(node.left);
		else
			return 1 + Math.max(getNodeHeight(node.left), getNodeHeight(node.right));
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	// Abstract Operations
	// -----------------------------------------------------------
	// -----------------------------------------------------------

	private int getDirection(E toCheck, E data)
	{
		return (int) Math.signum(toCheck.compareTo(data));
	}

	// TODO: Remove for submition //
	public homework7.Data.Node<E> convertNodeTypes()
	{
		return root.asOtherNode(null);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * A private helper class for the SpecialtySet class. Node objects are used to construct linked lists in a SpecialtySet.
	 * 
	 * Students are not allowed to change this class.
	 * 
	 * @author Peter Jensen
	 * @version 2/22/2014
	 */
	class Node<E>
	{
		private E data; // The data element - cannot be changed after it is assigned
		private Node<E> left; // Initialized to null when this object is created
		private Node<E> right;
		private Node<E> parent; // Parent - initialized to null
		private int height; // Height of this subtree - initialized to 0

		/**
		 * Builds this node to contain the specified data. By default, this node does not point to any other nodes (next is null), although it is expected that 'next' may change.
		 * 
		 * Also note, the data variable is final, the data reference cannot be changed. (This fact is largely irrelevant.)
		 * 
		 * @param data
		 *            the data to store in the node
		 */
		public Node(E data)
		{
			this.data = data;
		}

		@Override
		public String toString()
		{
			return data.toString();
		}
		
		public homework7.Data.Node<E> asOtherNode(homework7.Data.Node<E> parent)
		{
			ArrayList<homework7.Data.Node<E>> children = new ArrayList<homework7.Data.Node<E>>(2);
			homework7.Data.Node<E> node = new homework7.Data.Node<E>(data);

			if (left != null)
				children.add(left.asOtherNode(node));

			if (right != null)
				children.add(right.asOtherNode(node));

			if (parent != null)
				node.setParent(parent);

			node.setChildren(children);

			return node;
		}
	}
}

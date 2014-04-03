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
	private Node root;
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

	private boolean contains(Node root, E data)
	{
		if (root == null)
		{
			return false;
		}

		int direction = getDirection(data, root.data);
		switch (direction)
		{
			case CURRENT:
				return true;
			case LEFT:
				return contains(root.left, data);
			case RIGHT:
				return contains(root.right, data);
		}

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
			root = add(root, data);
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

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	// Private Drivers
	// -----------------------------------------------------------
	// -----------------------------------------------------------

	private Node add(Node parent, E data)
	{
		if (parent == null)
		{
			Node node = new Node(data);
			// node.parent = current;
			return node;
		}

		int direction = getDirection(data, parent.data);
		switch (direction)
		{
			case CURRENT:
				return parent;
			case LEFT:
				parent.left = add(parent.left, data);
			case RIGHT:
				parent.right = add(parent.right, data);
		}

		return parent;
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

	public void printTree()
	{
		new BTreePrinter().printNode(root);
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
	class Node
	{
		private E data; // The data element - cannot be changed after it is assigned
		private Node left, right; // Initialized to null when this object is created
		private Node parent; // Parent - initialized to null
		private int height; // Height of this subtree - initialized to 0

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

	class BTreePrinter
	{

		public void printNode(Node root)
		{
			int maxLevel = maxLevel(root);

			printNodeInternal(Collections.singletonList(root), 1, maxLevel);
		}

		private void printNodeInternal(List<Node> nodes, int level, int maxLevel)
		{
			if (nodes.isEmpty() || isAllElementsNull(nodes))
				return;

			int floor = maxLevel - level;
			int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
			int firstSpaces = (int) Math.pow(2, (floor)) - 1;
			int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

			printWhitespaces(firstSpaces);

			List<Node> newNodes = new ArrayList<Node>();
			for (Node node : nodes)
			{
				if (node != null)
				{
					System.out.print(node.data);
					newNodes.add(node.left);
					newNodes.add(node.right);
				}
				else
				{
					newNodes.add(null);
					newNodes.add(null);
					System.out.print(" ");
				}

				printWhitespaces(betweenSpaces);
			}
			System.out.println("");

			for (int i = 1; i <= endgeLines; i++)
			{
				for (int j = 0; j < nodes.size(); j++)
				{
					printWhitespaces(firstSpaces - i);
					if (nodes.get(j) == null)
					{
						printWhitespaces(endgeLines + endgeLines + i + 1);
						continue;
					}

					if (nodes.get(j).left != null)
						System.out.print("/");
					else
						printWhitespaces(1);

					printWhitespaces(i + i - 1);

					if (nodes.get(j).right != null)
						System.out.print("\\");
					else
						printWhitespaces(1);

					printWhitespaces(endgeLines + endgeLines - i);
				}

				System.out.println("");
			}

			printNodeInternal(newNodes, level + 1, maxLevel);
		}

		private void printWhitespaces(int count)
		{
			for (int i = 0; i < count; i++)
				System.out.print(" ");
		}

		private int maxLevel(Node node)
		{
			if (node == null)
				return 0;

			return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
		}

		private boolean isAllElementsNull(List list)
		{
			for (Object object : list)
			{
				if (object != null)
					return false;
			}

			return true;
		}

	}

}

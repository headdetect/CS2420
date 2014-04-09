package homework08;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Objects of this class represent a set of sortable values. The set has the following performance characteristics:
 * 
 * - the set is kept in a sorted linked list
 * 
 * - getting the size of the set - O(1)
 * 
 * - adding, removing, or searching for a random element - O(log n)
 * 
 * 
 * @author Brayden.Lopez
 * @version 4/4/14
 * @param <E>
 *            the element type
 */
public class SpecialtySet<E extends Comparable<E>>
{

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int LEFT = -1;
	private static final int RIGHT = 1;

	// ===========================================================
	// Fields
	// ===========================================================

	// Instance variables. Students are allowed
	// only these, do not add or change instance variables.
	private Node root;

	private int size;

	// public int balanceLoad = 2;

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

	/*
	 * Public Operations
	 * -----------------------------------------------------------
	 * - contains(E)
	 * - add(E)
	 * - remove(E)
	 * -----------------------------------------------------------
	*/

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
		Node node = new Node(data);
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
		Node node = find(root, data);
		if (node == null)
			return;

		remove(node);
	}

	/*
	 * Private Drivers
	 * -----------------------------------------------------------
	 * - contains(Node, E)
	 * - add(Node, Node)
	 * - remove(Node)
	 * -----------------------------------------------------------
	*/

	/**
	 * Checks to see if specified data exists in the set.
	 * 
	 * @param node
	 *            the node
	 * @param data
	 *            the data
	 * @return true, if successful
	 */
	private boolean contains(Node node, E data)
	{
		if (root == null || size == 0 || node == null)
		{
			return false;
		}

		return find(node, data) != null;
	}

	/**
	 * Adds the <i>toInsert</i> node to the <i>node</i> node.
	 * 
	 * @param node
	 *            the node
	 * @param toInsert
	 *            the to insert
	 */
	private void add(Node node, Node toInsert)
	{
		if (node == null)
		{
			root = toInsert;
			size++;
		}
		else
		{
			int direction = toInsert.data.compareTo(node.data);

			if (direction < 0)
			{
				if (node.left == null)
				{
					node.left = toInsert;
					toInsert.parent = node;
					balance(node);
					size++;
				}
				else
				{
					add(node.left, toInsert);
				}

			}
			else if (direction > 0)
			{
				if (node.right == null)
				{
					node.right = toInsert;
					toInsert.parent = node;
					balance(node);
					size++;
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

	/**
	 * Removes the specified node from the set.
	 * 
	 * @param node
	 *            the node
	 */
	private void remove(Node node)
	{
		Node replace;
		if (node.left == null || node.right == null)
		{
			// If is root //
			if (node.parent == null)
			{
				this.root = null;
				node = null;
				size = 0;
				return;
			}
			replace = node;
		}
		else
		{
			replace = getSuccessor(node);
			node.data = replace.data;
		}

		Node parent = replace.left != null ? replace.left : replace.right;

		if (parent != null)
			parent.parent = replace.parent;

		if (replace.parent == null)
			root = parent;
		else
		{
			if (replace == replace.parent.left)
				replace.parent.left = parent;
			else
				replace.parent.right = parent;

			balance(replace.parent);
			size--;
		}
	}

	/*
	 * Balancing Operations
	 * -----------------------------------------------------------
	 * - balance(Node)
	 * - rotateRight(Node)
	 * - rotateLeft(Node)
	 * -----------------------------------------------------------
	 */

	/**
	 * Balance the tree. Meets AVL standard.
	 * 
	 * @param node
	 *            the node
	 */
	private void balance(Node node)
	{
		int difference = getNodeHeight(node.right) - getNodeHeight(node.left);

		// if (difference <= -balanceLoad)
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
		// else if (difference >= balanceLoad)
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
		else
			root = node;
	}

	/**
	 * Rotates the sub-tree one to the right.
	 * 
	 * @param node
	 *            the node
	 * @return the node
	 */
	private Node rotateRight(Node node)
	{
		Node left = node.left;
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

	/**
	 * Rotates the sub-tree one to the left.
	 * 
	 * @param node
	 *            the node
	 * @return the node
	 */
	private Node rotateLeft(Node node)
	{
		Node right = node.right;
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

	/*
	 * Abstract Functions
	 * -----------------------------------------------------------
	 * - find(Node, E)
	 * - getNodeHeight(Node)
	 * - getDirection(E, E)
	 * - getSuccessor(Node)
	 * -----------------------------------------------------------
	 */

	/**
	 * Finds the node that contains the data.
	 * 
	 * @param start
	 *            the start
	 * @param data
	 *            the data
	 * @return the node
	 */
	private Node find(Node start, E data)
	{
		while (start != null)
		{
			if (data == start.data)
				return start;
			int direction = getDirection(data, start.data);

			if (direction == LEFT)
			{
				start = start.left;
			}
			else if (direction == RIGHT)
			{
				start = start.right;
			}
			else
			{
				return start;
			}
		}
		return null;
	}

	/**
	 * Gets the successor to the specified node.
	 * 
	 * @param node
	 *            the node
	 * @return the successor
	 */
	private Node getSuccessor(Node node)
	{
		if (node.right != null)
		{
			Node right = node.right;
			while (right.left != null)
				right = right.left;
			return right;
		}
		else
		{
			Node parent = node.parent;
			while (parent != null && node == parent.right)
			{
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	/**
	 * Recursivly gets the node height.
	 * 
	 * @param node
	 *            the node
	 * @return the node height
	 */
	private int getNodeHeight(Node node)
	{
		if (node == null)
			return 0;
		if (node.left == null && node.right == null)
		{
			node.height = 1;
			return 1;
		}
		else if (node.left == null)
		{
			node.height = getNodeHeight(node.right) + 1;
			return node.height;
		}
		else if (node.right == null)
		{
			node.height = getNodeHeight(node.left) + 1;
			return node.height;
		}
		else
		{
			node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
			return node.height;
		}

	}

	/**
	 * Gets the direction that the data should be placed in.
	 * 
	 * @param toCheck
	 *            the to check
	 * @param data
	 *            the data
	 * @return the direction
	 */
	private int getDirection(E toCheck, E data)
	{
		return (int) Math.signum(toCheck.compareTo(data));
	}

	/**
	 * Writes the tree to a .tree file.
	 *
	 * @param f the f
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeFile(File f) throws IOException
	{
		if (!f.exists())
			f.createNewFile();

		BufferedWriter stream = new BufferedWriter(new FileWriter(f));
		writeNode(stream, root);
		stream.flush();
		stream.close();
	}

	private int currSpaces;
	private int index;
	
	/**
	 * Writes the tree to a .tree file.
	 *
	 * @param out the out
	 * @param node the node
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeNode(BufferedWriter out, Node node) throws IOException
	{
		int key = ++index;

		out.append(getSpaces() + "<" + key + " " + node.data.toString() + ">\n");
		currSpaces += 3;
		if (node.left != null)
			writeNode(out, node.left);
		if (node.right != null)
			writeNode(out, node.right);
		currSpaces -= 3;
		out.append(getSpaces() + "</" + key + ">\n");
	}

	/**
	 * Get spaces based on currSpaces
	 *
	 * @return the spaces
	 */
	private String getSpaces()
	{
		char[] spaces = new char[currSpaces];
		for (int i = 0; i < currSpaces; i++)
			spaces[i] = ' ';
		return new String(spaces);
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
		private Node left; // Initialized to null when this object is created
		private Node right;
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
		public Node(E data)
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

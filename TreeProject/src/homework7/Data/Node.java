package homework7.Data;

import java.util.ArrayList;

/**
 * The Node data structure. Is used to make trees.
 * 
 * @param <T>
 *            the generic type
 */
public class Node<T>
{

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private T value;

	private ArrayList<Node<T>> children;

	private Node<T> parent;

	private boolean isRoot;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new node.
	 */
	public Node()
	{
		setChildren(new ArrayList<Node<T>>());
	}

	/**
	 * Instantiates a new node.
	 * 
	 * @param value
	 *            the value
	 */
	public Node(T value)
	{
		this.setValue(value);
		this.setChildren(new ArrayList<Node<T>>());
	}

	/**
	 * Instantiates a new node.
	 * 
	 * @param value
	 *            the value
	 * @param children
	 *            the children
	 */
	public Node(T value, ArrayList<Node<T>> children)
	{
		this.setValue(value);
		this.setChildren(children);
	}

	/**
	 * Instantiates a new node.
	 * 
	 * @param parent
	 *            the parent
	 * @param value
	 *            the value
	 * @param children
	 *            the children
	 */
	public Node(Node<T> parent, T value, ArrayList<Node<T>> children)
	{
		this.setValue(value);
		this.setChildren(children);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public ArrayList<Node<T>> getChildren()
	{
		return children;
	}

	/**
	 * Sets the children.
	 * 
	 * @param children
	 *            the children to set
	 */
	public void setChildren(ArrayList<Node<T>> children)
	{
		this.children = children;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the name
	 */
	public T getValue()
	{
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(T value)
	{
		this.value = value;
	}

	/**
	 * Checks if is root.
	 * 
	 * @return the isRoot
	 */
	public boolean isRoot()
	{
		return isRoot;
	}

	/**
	 * Sets the root.
	 * 
	 * @param isRoot
	 *            the isRoot to set
	 */
	public void setRoot(boolean isRoot)
	{
		this.isRoot = isRoot;
	}

	/**
	 * Gets the parent node.
	 * 
	 * @return the parent
	 */
	public Node<T> getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent node.
	 * 
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Node<T> parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public int getHeight()
	{
		return calculateHeight(this);
	}


	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	

	/**
	 * Calculate height starting from the selected node.
	 * @param node
	 *            the node
	 * @return the int
	 */
	private int calculateHeight(Node<T> node)
	{
		if (node == null)
		{
			return 1;
		}
		else
		{
			int biggest = 0;
			for (int i = 0; i < node.getChildren().size(); i++)
			{
				int calculatedHeight = calculateHeight(node.getChildren().get(i));
				if (calculatedHeight > biggest)
					biggest = calculatedHeight;
			}
			return 1 + biggest;
		}
	}

	/**
	 * Generates a random tree.
	 * 
	 * @return the node
	 */
	static Node<String> generateRandomTree()
	{
		Node<String> root = new Node<String>("root");
		root.setRoot(true);
		for (int i = 1; i < 10; i++)
		{
			int len = (int) (Math.random() * 10 + 1);
			int skip = (int) (Math.random() * 3 + 1);
			Node<String> parent = new Node<String>("Layer 1: " + i);
			for (int j = 1; j < len; j += skip)
			{
				Node<String> child = new Node<String>("Layer 2: " + j);
				parent.getChildren().add(child);
				child.setParent(parent);

				boolean makeChildren = (int) (Math.random() * 10) % 2 == 1;
				if (makeChildren)
				{
					int plen = (int) (Math.random() * 10 + 1);
					int pskip = (int) (Math.random() * 3 + 1);
					for (int k = 0; k < plen; k += pskip)
					{
						Node<String> grandChild = new Node<String>("Layer 3: " + k);
						child.getChildren().add(grandChild);
						grandChild.setParent(child);
					}
				}
			}
			root.getChildren().add(parent);
			parent.setParent(root);
		}

		return root;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
	

}

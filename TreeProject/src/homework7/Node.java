package homework7;

import java.util.ArrayList;

public class Node<T>
{

	private T value;
	private ArrayList<Node<T>> children;
	private Node<T> parent;
	private boolean isRoot;

	public Node()
	{
		setChildren(new ArrayList<Node<T>>());
	}

	public Node(T value)
	{
		this.setValue(value);
		this.setChildren(new ArrayList<Node<T>>());
	}

	public Node(T value, ArrayList<Node<T>> children)
	{
		this.setValue(value);
		this.setChildren(children);
	}

	public Node(Node<T> parent, T value, ArrayList<Node<T>> children)
	{
		this.setValue(value);
		this.setChildren(children);
	}

	/**
	 * @return the children
	 */
	public ArrayList<Node<T>> getChildren()
	{
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(ArrayList<Node<T>> children)
	{
		this.children = children;
	}

	/**
	 * @return the name
	 */
	public T getValue()
	{
		return value;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setValue(T value)
	{
		this.value = value;
	}

	/**
	 * @return the isRoot
	 */
	public boolean isRoot()
	{
		return isRoot;
	}

	/**
	 * @param isRoot
	 *            the isRoot to set
	 */
	public void setRoot(boolean isRoot)
	{
		this.isRoot = isRoot;
	}

	/**
	 * @return the parent
	 */
	public Node<T> getParent()
	{
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Node<T> parent)
	{
		this.parent = parent;
	}

	// Tools //
	public int getHeight()
	{
		return calculateHeight(this);
	}

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

}

package homework7;

import java.util.ArrayList;

public class Node<T>
{

	private T value;
	private ArrayList<Node<T>> children;
	
	public Node() {
		setChildren(new ArrayList<Node<T>>());
	}
	
	public Node(T value) {
		this.setValue(value);
		this.setChildren(new ArrayList<Node<T>>());
	}
	
	public Node(T value, ArrayList<Node<T>> children) {
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
	 * @param children the children to set
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
	 * @param name the name to set
	 */
	public void setValue(T value)
	{
		this.value = value;
	}
	
	
	public ArrayList<Node<T>> getNodesOnLevel(int level) {
		return null;
	}
	
	public ArrayList<Node<T>> getLeafs() {
		return null;
	}
	
	public int getLastLevelIndex() {
		return 0;
	}
	
}

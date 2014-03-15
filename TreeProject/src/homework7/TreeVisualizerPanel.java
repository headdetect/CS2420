package homework7;

import homework7.Drawables.DrawableEdge;
import homework7.Drawables.DrawableNode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.tree.TreeModel;

public class TreeVisualizerPanel extends JPanel implements MouseMotionListener, MouseListener
{

	/**
	 * Generated serial version.
	 */
	private static final long serialVersionUID = 4536105668603835030L;

	private JScrollPane enclosingPane;
	private int lastMouseX, lastMouseY;
	private Node<String> mTree;

	private HashMap<Node<String>, DrawableNode> mNodes;
	private ArrayList<DrawableEdge> mEdges;

	// Design Elements //
	private static Font mFont;
	private static Color mColorBlue;
	private static Color mColorRed;
	private static Color mColorOrange;
	private static Color mColorPurple;
	private static Color mColorGreen;
	private static Color[] colors;

	public TreeVisualizerPanel()
	{
		addMouseListener(this);
		addMouseMotionListener(this);

		mNodes = new HashMap<Node<String>, DrawableNode>();
		mEdges = new ArrayList<DrawableEdge>();

		mFont = new Font("Sans Serif", Font.BOLD, 13);
		mColorBlue = new Color(0x33B5E5);
		mColorRed = new Color(0xFF4444);
		mColorOrange = new Color(0xFFBB33);
		mColorPurple = new Color(0xAA66CC);
		mColorGreen = new Color(0x99CC00);

		colors = new Color[] { mColorBlue, mColorPurple, mColorGreen, mColorOrange, mColorRed };
	}

	public TreeVisualizerPanel(Node<String> tree)
	{
		this();
		setTree(tree);
	}

	/**
	 * Allows the main method to set an instance variable in this panel.
	 * 
	 * @param enclosingPane
	 *            a JScrollPane object that is controlling the view of this panel
	 */
	public void setEnclosingPane(JScrollPane pane)
	{
		enclosingPane = pane;
	}

	@Override
	public void paint(Graphics g)
	{

		int upperLeftX = g.getClipBounds().x;
		int upperLeftY = g.getClipBounds().y;
		int visibleWidth = g.getClipBounds().width;
		int visibleHeight = g.getClipBounds().height;

		g.setColor(Color.WHITE);
		g.fillRect(upperLeftX, upperLeftY, visibleWidth, visibleHeight);

		g.setFont(mFont);

		for (int i = 0; i < mEdges.size(); i++)
		{
			g.setColor(Color.BLACK);
			mEdges.get(i).draw(g);
		}

		Collection<DrawableNode> nodes = mNodes.values();
		for(DrawableNode node : nodes)
		{
			g.setColor(Color.BLACK);
			node.draw(g);
		}

		g.setColor(mColorPurple);
		g.drawString("Height : " + mTree.getHeight(), 30, 30);
	}

	private static int NODE_SIZE = 60;
	private static int PADDING = 0;

	private int currX, currY = NODE_SIZE;
	private int parentX, parentY;
	private int colorIteration = 0;

	/**
	 * Adjusts the scroll pane's view by an amount equal to the mouse motion.
	 */
	public void mouseDragged(MouseEvent e)
	{
		// Compute the offset from the last mouse location.

		int deltaX = e.getX() - lastMouseX;
		int deltaY = e.getY() - lastMouseY;

		// Adjust the scroll pane by the delta. Note that to move the
		// surface with the mouse, we must move the view in the opposite
		// direction. This means that we subtract the delta.

		JViewport view = enclosingPane.getViewport();
		Point pos = view.getViewPosition();
		view.setViewPosition(new Point(Math.max(0, pos.x - deltaX), Math.max(0, pos.y - deltaY)));

		// Keep track of the last mouse location. Note: Because we moved
		// the view, the logical location of the mouse moved an equal amount
		// in the same direction.

		lastMouseX = e.getX() - deltaX;
		lastMouseY = e.getY() - deltaY;

		// Make sure the JViewPort fully repaints. This will make the
		// out-of-bounds area appear all gray instead of giving a
		// torn appearance.

		view.repaint();
	}

	/**
	 * Keeps track of the last mouse position.
	 */
	public void mousePressed(MouseEvent e)
	{
		// Keep track of the last mouse location.

		lastMouseX = e.getX();
		lastMouseY = e.getY();
	}

	// Unused event handlers

	public void mouseMoved(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	/**
	 * @return the mTree
	 */
	public Node<String> getTree()
	{
		return mTree;
	}

	/**
	 * @param mTree
	 *            the mTree to set
	 */
	public void setTree(Node<String> mTree)
	{
		this.mTree = mTree;

		recurseLoad(mTree);

		repaint();
	}

	DrawableNode mParentNode;

	void recurseLoad(Node<String> node)
	{
		if (node == null)
			return;

		if (node.isRoot())
		{
			colorIteration = 0;
			currX = NODE_SIZE;
			currY = NODE_SIZE;

			parentX = currX;
			parentY = currY;
		}

		DrawableNode drawNode = new DrawableNode(currX, currY, node.getValue(), colors[colorIteration % 5]);
		mNodes.put(node, drawNode);

		DrawableEdge drawEdge = new DrawableEdge(mParentNode, drawNode);
		mEdges.add(drawEdge);

		if (node.getChildren().size() > 0)
		{
			mParentNode = drawNode;
			colorIteration++;

			for (int i = 0; i < node.getChildren().size(); i++)
			{
				currX += NODE_SIZE;
				currY += NODE_SIZE;
				recurseLoad(node.getChildren().get(i));
			}

			if (node.getParent() != null)
				mParentNode = mNodes.get(node.getParent());
			colorIteration--;
		}

		currX = Math.max(0, currX - NODE_SIZE);

	}

	void drawElements(Node<String> node, Graphics g)
	{

	}
}

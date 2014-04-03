package homework7.UI;

import homework7.Data.Node;
import homework7.Drawables.DrawableEdge;
import homework7.Drawables.DrawableNode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * Takes in a Node, then draws it on itself.
 */
public class TreeVisualizerPanel extends JPanel implements MouseMotionListener, MouseListener
{

	// ===========================================================
	// Constants
	// ===========================================================

	/**
	 * Generated serial version.
	 */
	private static final long serialVersionUID = 4536105668603835030L;

	private static int NODE_SIZE = 80;
	private static int STARTING_POS = 100;

	// ===========================================================
	// Fields
	// ===========================================================

	private JScrollPane enclosingPane;
	private int lastMouseX, lastMouseY;

	private HashMap<Node<String>, DrawableNode> mNodes;
	private ArrayList<DrawableEdge> mEdges;
	private ArrayList<Rectangle> mRectangles;

	private Node<String> mTree;

	// Design Elements //
	private static Font mFont;
	private static Color mColorBlue;
	private static Color mColorRed;
	private static Color mColorOrange;
	private static Color mColorPurple;
	private static Color mColorGreen;
	private static Color[] colors;

	// Drawing Nodes //

	private int preferredWidth, preferredHeight;

	private int currX = STARTING_POS, currY = STARTING_POS;

	private int currentColorIteration = 0;

	/* Keeps track of the current parent node so we can backtrack */
	private DrawableNode mParentDrawNode;

	// ===========================================================
	// Constructors
	// ===========================================================

	/** Static Constructor **/
	static
	{
		mFont = new Font("Sans Serif", Font.BOLD, 13);
		mColorBlue = new Color(0x33B5E5);
		mColorRed = new Color(0xFF4444);
		mColorOrange = new Color(0xFFBB33);
		mColorPurple = new Color(0xAA66CC);
		mColorGreen = new Color(0x99CC00);

		colors = new Color[] { mColorBlue, mColorPurple, mColorGreen, mColorOrange, mColorRed };
	}

	/**
	 * Instantiates a new tree visualizer panel.
	 */
	public TreeVisualizerPanel()
	{
		addMouseListener(this);
		addMouseMotionListener(this);

		mNodes = new HashMap<Node<String>, DrawableNode>();
		mEdges = new ArrayList<DrawableEdge>();
		mRectangles = new ArrayList<Rectangle>();
		mTree = new Node<String>();
	}

	/**
	 * Instantiates a new tree visualizer panel.
	 * 
	 * @param tree
	 *            the tree
	 */
	public TreeVisualizerPanel(Node<String> tree)
	{
		this();
		setTree(tree);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * Allows the main method to set an instance variable in this panel.
	 * 
	 * @param pane
	 *            the new enclosing pane
	 */
	public void setEnclosingPane(JScrollPane pane)
	{
		enclosingPane = pane;
	}

	/**
	 * Gets the tree.
	 * 
	 * @return the mTree
	 */
	public Node<String> getTree()
	{
		return mTree;
	}

	/**
	 * Sets the tree.
	 * 
	 * @param mTree
	 *            the mTree to set
	 */
	public void setTree(Node<String> mTree)
	{
		this.mTree = mTree;
		mNodes.clear();
		mEdges.clear();

		recurseLoad(mTree);

		if (mTree != null)
		{
			this.setMinimumSize(new Dimension(mTree.getPixelWidth(), mTree.getHeight() * NODE_SIZE));
			this.setPreferredSize(new Dimension(mTree.getPixelWidth(), mTree.getHeight() * NODE_SIZE));
		}
		
		revalidate();
		repaint();

	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		// Turn on anti aliasing so we can get some smoothness up in here //
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int upperLeftX = g.getClipBounds().x;
		int upperLeftY = g.getClipBounds().y;
		int visibleWidth = g.getClipBounds().width;
		int visibleHeight = g.getClipBounds().height;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(upperLeftX, upperLeftY, visibleWidth, visibleHeight);

		if (mTree == null)
			return;

		g2d.setFont(mFont);

		for (int i = 0; i < mEdges.size(); i++)
		{
			g2d.setColor(Color.BLACK);
			mEdges.get(i).draw(g2d);
		}

		Collection<DrawableNode> nodes = mNodes.values();
		for (DrawableNode node : nodes)
		{
			g2d.setColor(Color.BLACK);
			node.draw(g2d);
		}

		for (int i = 0; i < mRectangles.size(); i++)
		{
			g2d.setColor(new Color(i * 100 % 0xFF, 0xFF - (i * 100 % 0xFF), (i * 10 % 0xFF)));
			Rectangle rect = mRectangles.get(i);
			g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		}

		g2d.setColor(mColorPurple);
		g2d.drawString("Height : " + mTree.getHeight(), 30, 30);

	}

	/**
	 * Adjusts the scroll pane's view by an amount equal to the mouse motion.
	 * 
	 * @param e
	 *            the e
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

		// Give a little room because of the scroll bars //
		this.scrollRectToVisible(new Rectangle(Math.max(-20, Math.min(getWidth(), pos.x - deltaX)), Math.max(-10, Math.min(getHeight(), pos.y - deltaY)), 500, 500));

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
	 * 
	 * @param e
	 *            the e
	 */
	public void mousePressed(MouseEvent e)
	{
		// Keep track of the last mouse location.

		lastMouseX = e.getX();
		lastMouseY = e.getY();
	}

	// ------- Unused Events ------- //

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

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Reset layout to root node.
	 */
	public void resetLayout()
	{
		this.scrollRectToVisible(new Rectangle(0, 0, 500, 500));
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private final int WIDTH = 600;

	/**
	 * Recurse load.
	 * 
	 * @param node
	 *            the node
	 */
	void recurseLoad(Node<String> node)
	{
		if (node == null)
			return;

		int nodeWidth = node.getPixelWidth();
		DrawableNode drawNode = new DrawableNode(currX + (nodeWidth / 2), currY, node.getValue(), colors[currentColorIteration % 5]);
		mNodes.put(node, drawNode);

		//mRectangles.add(new Rectangle(currX + (nodeWidth / 2), currY, node.getPixelWidth(), NODE_SIZE));

		DrawableEdge drawEdge = new DrawableEdge(mParentDrawNode, drawNode);
		mEdges.add(drawEdge);

		if (node.getChildren().size() > 0)
		{
			currentColorIteration++;
			currY += NODE_SIZE;

			for (int i = 0; i < node.getChildren().size(); i++)
			{
				mParentDrawNode = drawNode;
				recurseLoad(node.getChildren().get(i));
				currX += node.getChildren().get(i).getPixelWidth();
			}

			if (mParentDrawNode != null)
				currX = mParentDrawNode.getX();

			currY -= NODE_SIZE;
			currentColorIteration--;
		}

	}
}

package homework7;

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
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.setRenderingHints(g,Graphics2D.ANTIALIAS_ON);

		int upperLeftX = g.getClipBounds().x;
		int upperLeftY = g.getClipBounds().y;
		int visibleWidth = g.getClipBounds().width;
		int visibleHeight = g.getClipBounds().height;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(upperLeftX, upperLeftY, visibleWidth, visibleHeight);

		g2d.setFont(mFont);

		for (int i = 0; i < mEdges.size(); i++)
		{
			g2d.setColor(Color.BLACK);
			mEdges.get(i).draw(g2d);
		}

		Collection<DrawableNode> nodes = mNodes.values();
		for(DrawableNode node : nodes)
		{
			g2d.setColor(Color.BLACK);
			node.draw(g2d);
		}

		g2d.setColor(mColorPurple);
		g2d.drawString("Height : " + mTree.getHeight(), 30, 30);
	}

	

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
		
		this.setMinimumSize(new Dimension(preferredWidth, preferredHeight));
		this.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		
		repaint();
	}

	private int preferredWidth, preferredHeight;
	
	private static int NODE_SIZE = 60;
	private static int STARTING_POS = 100;
	private int currX, currY = STARTING_POS;
	private int colorIteration = 0;
	
	DrawableNode mParentNode;

	void recurseLoad(Node<String> node)
	{
		if (node == null)
			return;

		if (node.isRoot())
		{
			colorIteration = 0;
			currX = STARTING_POS;
			currY = STARTING_POS;
		}

		DrawableNode drawNode = new DrawableNode(currX, currY, node.getValue(), colors[colorIteration % 5]);
		mNodes.put(node, drawNode);

		DrawableEdge drawEdge = new DrawableEdge(mParentNode, drawNode);
		mEdges.add(drawEdge);

		if (node.getChildren().size() > 0)
		{
			mParentNode = drawNode;
			colorIteration++;
			
			updatePreferred();

			for (int i = 0; i < node.getChildren().size(); i++)
			{
				currX += NODE_SIZE;
				currY += NODE_SIZE;
				updatePreferred();
				
				recurseLoad(node.getChildren().get(i));
			}

			if (node.getParent() != null)
				mParentNode = mNodes.get(node.getParent());
			colorIteration--;
		}

		currX = Math.max(0, currX - NODE_SIZE);
	}
	
	void updatePreferred() {
		if(currX + NODE_SIZE + 20 > preferredWidth)
			preferredWidth = currX + NODE_SIZE + 20; // Just a little padding //
		
		if(currY + NODE_SIZE + 20 > preferredHeight)
			preferredHeight = currY + NODE_SIZE + 20; // Just a little padding //
	}
}

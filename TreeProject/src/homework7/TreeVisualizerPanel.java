package homework7;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
	
	// Design Elements //
	private static Font mFont;
	private static Color mColorBlue;
	private static Color mColorRed;
	private static Color mColorOrange;
	private static Color mColorPurple;
	private static Color mColorGreen;
	
	public TreeVisualizerPanel()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		
		mFont = new Font("Sans Serif", Font.BOLD, 13);
		mColorBlue = new Color(0x33B5E5);
		mColorRed = new Color(0xFF4444);
		mColorOrange = new Color(0xFFBB33);
		mColorPurple = new Color(0xAA66CC);
		mColorGreen = new Color(0x99CC00);
	}

	public TreeVisualizerPanel(Node<String> tree)
	{
		this();
		mTree = tree;
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
		
		g.setColor(mColorBlue);
		drawElements(mTree, g);
		
		
		g.setColor(mColorPurple);
		g.drawString("Height : " + mTree.getHeight(), 30, 30);
	}

	static int x, y = 60;

	static void drawElements(Node<String> node, Graphics g)
	{
		if (node == null)
			return;

		if (node.isRoot())
		{
			x = 60;
			y = 60;
			g.drawString(node.getValue(), x, y);
		}
		else
		{
			g.drawString(node.getValue(), x, y);
		}

		for (int i = 0; i < node.getChildren().size(); i++)
		{
			x += 20;
			y += 20;
			drawElements(node.getChildren().get(i), g);
		}
		
		x = Math.max(0, x - 20);
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
		repaint();
	}
}

package homework7;

import java.awt.Color;
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
	private boolean mSwitch;
	private TreeModel mTree;

	public TreeVisualizerPanel()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
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

		// Compute a position on the panel that is above and to the left of the currently
		// exposed view.

		int firstX = Math.max(0, upperLeftX - upperLeftX % 100 - 100);
		int firstY = Math.max(0, upperLeftY - upperLeftY % 100 - 100);

		// Compute the last possible position of a coordinate in the visible space.

		int lastX = (upperLeftX + visibleWidth) - (upperLeftX + visibleWidth) % 100 + 200;
		int lastY = (upperLeftY + visibleHeight) - (upperLeftY + visibleHeight) % 100 + 200;

		g.setColor(Color.BLACK);
		g.fillRect(upperLeftX, upperLeftY, visibleWidth, visibleHeight);

	}
	
	private int cellSize = 100;
	private int mWidth, mHeight;
	
	private void drawCell()
	{
		
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
	public TreeModel getTree()
	{
		return mTree;
	}

	/**
	 * @param mTree
	 *            the mTree to set
	 */
	public void setTree(TreeModel mTree)
	{
		this.mTree = mTree;
		repaint();
	}
}

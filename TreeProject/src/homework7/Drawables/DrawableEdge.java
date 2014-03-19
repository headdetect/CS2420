package homework7.Drawables;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The Class DrawableEdge.
 */
public class DrawableEdge implements Drawable
{
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private DrawableNode from, to;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new drawable edge.
	 *
	 * @param nodeFrom the node from
	 * @param nodeTo the node to
	 */
	public DrawableEdge(DrawableNode nodeFrom, DrawableNode nodeTo)
	{
		this.from = nodeFrom;
		this.to = nodeTo;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	public void draw(Graphics2D g)
	{
		if (from == null || to == null)
			return;
		
		
		g.setColor(Color.BLACK);
		
		// Give it some thickness //
		
		g.drawLine(from.getX() - 1, from.getY() - 1, to.getX() + 1, to.getY() + 1);
		g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

	}


	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	

	

	
}

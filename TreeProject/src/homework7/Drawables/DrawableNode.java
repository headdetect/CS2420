package homework7.Drawables;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * The Class DrawableNode wrapper class.
 */
public class DrawableNode implements Drawable
{

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int NODE_THICKNESS = 4;

	private static final int NODE_SIZE = 50;

	// ===========================================================
	// Fields
	// ===========================================================

	private int x, y;

	private String text;

	private Color color;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new drawable node type.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param text
	 *            the text
	 * @param color
	 *            the color
	 */
	public DrawableNode(int x, int y, String text, Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.text = text;
	}

	/**
	 * Instantiates a new drawable node type without a position. (Defaults to 0,0).
	 * 
	 * @param text
	 *            the text
	 * @param color
	 *            the color
	 */
	public DrawableNode(String text, Color color)
	{
		this.color = color;
		this.text = text;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * Gets the x position.
	 * 
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Sets the x position.
	 * 
	 * @param x
	 *            the new x
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Gets the y position.
	 * 
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Sets the y position.
	 * 
	 * @param y
	 *            the new y
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Gets the text of the drawn node.
	 * 
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Sets the text of the drawn node.
	 * 
	 * @param text
	 *            the new text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Gets the color of the drawable.
	 * 
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Sets the color of the drawable.
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(color);
		FontMetrics fontMetrics = g.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(text);
		int textHeight = fontMetrics.getHeight();

		int nSize = NODE_SIZE;
		int nX = x - nSize / 2;
		int nY = y - nSize / 2;

		g.fillOval(nX, nY, nSize, nSize);

		g.setColor(Color.WHITE);
		g.fillOval(nX + (NODE_THICKNESS / 2), nY + (NODE_THICKNESS / 2), nSize - (NODE_THICKNESS), nSize - (NODE_THICKNESS));

		g.setColor(color);

		g.drawString(text, x - textWidth / 2, y + textHeight / 2);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}

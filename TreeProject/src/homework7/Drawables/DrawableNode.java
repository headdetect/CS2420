package homework7.Drawables;

import java.awt.*;

public class DrawableNode extends Drawable
{
	private int x, y;
	private String text;
	private Color color;
	
	private static final int NODE_SIZE = 60;
	private static final int NODE_THICKNESS = 4;
	
	public DrawableNode(int x, int y, String text, Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.text = text;
	}
	
	public DrawableNode(String text, Color color)
	{
		this.color = color;
		this.text = text;
	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(color);
		FontMetrics fontMetrics = g.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(text);
		int textHeight = fontMetrics.getHeight();
		
		g.fillOval(x, y, NODE_SIZE, NODE_SIZE);
		
		g.setColor(Color.WHITE);
		g.fillOval(x + (NODE_THICKNESS / 2), y + (NODE_THICKNESS / 2), NODE_SIZE - (NODE_THICKNESS), NODE_SIZE - (NODE_THICKNESS));
		
		g.setColor(color);
		g.drawString(text, x , y);
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

}

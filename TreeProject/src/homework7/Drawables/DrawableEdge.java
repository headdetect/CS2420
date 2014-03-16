package homework7.Drawables;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawableEdge extends Drawable
{
	private DrawableNode from, to;

	public DrawableEdge(DrawableNode nodeFrom, DrawableNode nodeTo)
	{
		this.from = nodeFrom;
		this.to = nodeTo;
	}

	@Override
	public void draw(Graphics2D g)
	{
		if(from == null || to == null) return;
		g.setColor(Color.BLACK);
		g.drawLine(from.getX() - 1, from.getY() - 1, to.getX() + 1, to.getY() + 1);
		g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

	}

}

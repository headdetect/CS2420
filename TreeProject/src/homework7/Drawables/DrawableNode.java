package homework7.Drawables;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DrawableNode extends Drawable {
	private int x, y;
	private String text;
	private Color color;

	private static final int NODE_SIZE = 8;
	private static final int NODE_THICKNESS = 4;

	public DrawableNode(int x, int y, String text, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.text = text;
	}

	public DrawableNode(String text, Color color) {
		this.color = color;
		this.text = text;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		FontMetrics fontMetrics = g.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(text);
		int textHeight = fontMetrics.getHeight();

		int nX = x - NODE_SIZE;
		int nY = y - textHeight - NODE_SIZE;
		int nSize = textWidth + (NODE_SIZE * 2);

		g.fillOval(nX, nY, nSize, nSize);

		g.setColor(Color.WHITE);
		g.fillOval(nX + (NODE_THICKNESS / 2), nY + (NODE_THICKNESS / 2), nSize
				- (NODE_THICKNESS), nSize - (NODE_THICKNESS));

		g.setColor(color);

		FontMetrics fm = g.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(text, g);
		g.drawString(text, (int) (x + NODE_SIZE / 2 - rect.getWidth() / 2),
				(int) (y + NODE_SIZE / 2 + rect.getHeight() / 2));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}

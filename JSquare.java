package a8;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class JSquare extends JPanel {
	private int width;
	private int height;
	private Color color;
	private boolean filled;

	public JSquare(int width, int height, Color color, boolean filled) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.filled = filled;
	}

	public void paint(Graphics graphics) {
		graphics.setColor(color);
		graphics.drawRect(0, 0, width, height);
		if (filled) {
			graphics.fillRect(0, 0, width, height);
		}
	}
}

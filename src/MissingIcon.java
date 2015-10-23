import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

public class MissingIcon implements Icon 
{	
	private String str;
	private int height;
	private int width;
	
	public MissingIcon(String title, int height, int width)
	{
		this.str = title;
		this.height = height;
		this.width = width;
	}

	@Override
	public int getIconHeight() 
	{
		return (int) height;
	}

	@Override
	public int getIconWidth() 
	{
		return (int) width;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) 
	{
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x, y, width, height);
		
		g2d.setColor(Color.BLACK);
		// Top
		g2d.drawLine(x, y, x + width, y);
		// Bottom
		g2d.drawLine(x, y + height, x + width, y + height);
		// Left
		g2d.drawLine(x, y, x, y + height);
		// Right
		g2d.drawLine(x + width, y, x + width, y + height);
		
		g2d.setFont(new Font("SansSerif", Font.BOLD, 10));
		g2d.drawString(str, x + 5, y + (height / 2));
	}

}

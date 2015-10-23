import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
		
		g2d.setColor(Color.BLACK);
		// Top
		g2d.drawRect(x + 1, y + 1, width, y + 1);
		// Bottom
		//g2d.drawRect(x + 1, y + 1 + height - 2, x + width, y + height);
		// Left
		//g2d.drawRect(x, y, x + width, y + height);
		// Right
		//g2d.drawRect(x + width - 1, y, x + width - 1, y + height);
	}

}

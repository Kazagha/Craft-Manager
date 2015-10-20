import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResourceBar extends JPanel {

	private JLabel gpLabel;
	private JLabel xpLabel;
	private ImageIcon gpImage;
	
	public ResourceBar()
	{
		initComponents();		
	}
	
	private void initComponents()
	{
		gpLabel = new JLabel();
		xpLabel = new JLabel();		
		gpImage = new ImageIcon("images/gold.png");	
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(gpLabel);
		this.add(new JLabel(gpImage));
		this.add(xpLabel);
	}
	
	public void setGP(int num)
	{
		gpLabel.setText(num + "");
	}
	
	public void setXP(int num)
	{
		xpLabel.setText(num + " XP");
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int y = 0;
		g.setColor(new Color(156, 154, 140));
		g.drawLine(0, y, getWidth(), y);
		//g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}
}

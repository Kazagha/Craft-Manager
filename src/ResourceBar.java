import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResourceBar extends JPanel {

	private JLabel gpLabel;
	private JLabel xpLabel;
	private ImageIcon gpImage;
	
	public ResourceBar()
	{
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(gpLabel);
		this.add(new JLabel(gpImage));
		this.add(xpLabel);		
	}
	
	private void initComponents()
	{
		gpLabel = new JLabel();
		xpLabel = new JLabel();
		
		gpImage = new ImageIcon("images/gold.png");		
	}
	
	public void setGP(int num)
	{
		gpLabel.setText(num + "");
	}
	
	public void setXP(int num)
	{
		xpLabel.setText(num + " XP");
	}
}

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class View extends JPanel {

	//Frame Width and Height
	final int FRAME_WIDTH = 300;
	final int FRAME_HEIGHT = 650;
	
	JPanel itemPanel;
	
	public View()
	{
		// Run the setup
		init();
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		JLabel title = new JLabel("Title");
				
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
							.addComponent(title)
							.addComponent(itemPanel)
							)
					.addContainerGap()
			);
				
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(title)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(itemPanel)
					.addContainerGap()
			);
	}
	
	private void init()
	{
		itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));
	}
	
	public void appendItem(Item item) 
	{
		
		itemPanel.add(new JLabel(item.getName()));
	}
	
	public void createAndShowGUI()
	{
		//Create frame setup Window
		JFrame frame = new JFrame("Craft Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_HEIGHT, FRAME_WIDTH));
		
		//Add the content  
		frame.getContentPane().add(this);
		
		//Add the Menu
		//frame.setJMenuBar(createMenu());
		
		//Display the window
		frame.pack();
		frame.setVisible(true);		
	}
}

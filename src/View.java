import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpringLayout;

public class View extends JPanel implements Observer {

	//Frame Width and Height
	final int FRAME_WIDTH = 300;
	final int FRAME_HEIGHT = 650;
	
	JLabel goldLabel;
	JLabel XPLabel;
	
	JPanel itemPanel;
	
	JButton newItemButton;
	JButton craftButton;
	
	ActionListener listener;
	
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
							.addComponent(goldLabel)
							.addComponent(XPLabel)
							.addComponent(craftButton)
							.addComponent(newItemButton)
							.addComponent(itemPanel)
							)
					.addContainerGap()
			);
				
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(title)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(goldLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(XPLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(craftButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(newItemButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(itemPanel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addContainerGap()
			);
	}
	
	private void init()
	{
		goldLabel = new JLabel();
		XPLabel = new JLabel();
		
		itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));		
		//itemPanel.add(Box.createVerticalGlue());
		newItemButton= new JButton("New Item");
		craftButton = new JButton("Craft");
		
	}
	
	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
		
		newItemButton.addActionListener(listener);
		craftButton.addActionListener(listener);
		
		newItemButton.setActionCommand(Controller.Action.NEWITEM.toString());
		craftButton.setActionCommand(Controller.Action.CRAFT.toString());
	}
	
	public void appendPanel(JPanel panel) 
	{		
		((ViewItem) panel).setActionListener(listener);
		itemPanel.add(panel);
	}
	
	public void removePanel(JPanel panel)
	{
		itemPanel.remove(panel);
	}
	
	public void removeAllPanels()
	{
		while(itemPanel.getComponentCount() > 0)
		{
			itemPanel.remove(0);
		}
	}
	
	public int indexOf(JComponent component)
	{
		for(int i = 0; i < itemPanel.getComponentCount(); i++) 
		{
			if(itemPanel.getComponent(i) == component) 
			{
				return i;
			}
		}
		
		return -1;
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

	@Override
	public void update(Observable arg0, Object arg1)
	{
		this.removeAllPanels();
	
		Model m = (Model) arg0;		
		
		for(Item item : m.getQueue())
		{
			if(! item.isComplete())
			{
				Controller.getInstance().appendItemPanel(item);
			}
		}
		
		this.revalidate();		
	}
}

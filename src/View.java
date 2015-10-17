import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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
	JButton clearButton;
	JButton testButton01;
	JButton testButton02;
	
	ActionListener listener = null;
	MouseAdapter mouseListener = null;
	
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
								.addGroup(layout.createSequentialGroup()
										.addComponent(craftButton)
										.addComponent(newItemButton)
										.addComponent(clearButton)
										.addComponent(testButton01)
										.addComponent(testButton02)
										)
								.addComponent(title)
								.addComponent(goldLabel)
								.addComponent(XPLabel)
								.addComponent(itemPanel)								
								)							
					.addContainerGap()
			);
				
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(title)					
					.addComponent(goldLabel)
					.addComponent(XPLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup()
						.addComponent(craftButton)
						.addComponent(newItemButton)
						.addComponent(clearButton)
						.addComponent(testButton01)
						.addComponent(testButton02)
						)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(itemPanel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addContainerGap()
			);
	}
	
	private void init()
	{
		goldLabel = new JLabel("Gold: ");
		XPLabel = new JLabel("XP: ");
		
		itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));		
		//itemPanel.add(Box.createVerticalGlue());
		newItemButton= new JButton("New Item");
		craftButton = new JButton("Craft");
		clearButton = new JButton("Clear Completed");
		testButton01 = new JButton("Save");
		testButton02 = new JButton("Load");		
	}
	
	public void setActionListener(ActionListener listener)
	{
		if(this.listener == null) 
		{
			this.listener = listener;
			
			newItemButton.addActionListener(listener);
			craftButton.addActionListener(listener);
			clearButton.addActionListener(listener);
			testButton01.addActionListener(listener);
			testButton02.addActionListener(listener);
			
			newItemButton.setActionCommand(Controller.Action.NEWITEM.toString());
			craftButton.setActionCommand(Controller.Action.CRAFT.toString());
			clearButton.setActionCommand(Controller.Action.CLEAR.toString());
			testButton01.setActionCommand(Controller.Action.SAVE.toString());
			testButton02.setActionCommand(Controller.Action.LOAD.toString());
		}
	}
	
	public ActionListener getActionListener()
	{
		return this.listener;
	}
	
	public void setMouseListener(MouseAdapter listener)
	{
		this.mouseListener = listener;
	}
	
	public void appendPanel(JPanel panel) 
	{		
		// Set the Action Listener
		((ViewItem) panel).setActionListener(listener);
		// Set the Mouse Listener
		panel.addMouseListener(mouseListener);
		// Add the panel to the view
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
		Controller.getInstance().appendItemPanels(m.getQueue());
		System.out.format("View has been updated %n");
		/* Adding completed items
		for(Item item : m.getQueue())
		{
			if(! item.isComplete())
			{
				Controller.getInstance().appendItemPanel(item);
			}
		}
		*/
		
		this.XPLabel.setText("XP: " + m.getXP());
		this.goldLabel.setText("Gold: " + m.getGold());
		
		this.revalidate();		
	}
}

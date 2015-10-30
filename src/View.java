import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

public class View extends JPanel implements Observer {

	//Frame Width and Height
	final int FRAME_WIDTH = 450;
	final int FRAME_HEIGHT = 650;
	
	JLabel goldLabel;
	JLabel XPLabel;
	
	ResourceBar resourcesPanel;
	JScrollPane scrollPane;
	
	JTabbedPane tabbedPane;
	JPanel itemQueuePanel;
	JPanel itemCompletePanel;
	
	
	JButton newItemButton;
	JButton craftButton;
	JButton clearButton;
	
	ActionListener listener = null;
	MouseAdapter mouseListener = null;
	
	public View()
	{
		// Run the setup
		initComponents();
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
				
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addContainerGap()					
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(craftButton)
										.addComponent(clearButton)
										)
								.addComponent(tabbedPane)
								.addComponent(resourcesPanel)																
								)							
					.addContainerGap()
			);
				
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup()
						.addComponent(craftButton)
						.addComponent(clearButton)
						)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(tabbedPane)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)										
					.addComponent(resourcesPanel, 25, 25, 25)					
			);
	}
	
	private void initComponents()
	{		
		goldLabel = new JLabel();
		XPLabel = new JLabel();		
		resourcesPanel = new ResourceBar();		
			
		itemQueuePanel = new JPanel();
		itemQueuePanel.setLayout(new BoxLayout(itemQueuePanel, BoxLayout.Y_AXIS));
		itemCompletePanel = new JPanel();
		itemCompletePanel.setLayout(new BoxLayout(itemCompletePanel, BoxLayout.Y_AXIS));
						
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Queue", new JScrollPane(itemQueuePanel));
		tabbedPane.addTab("Complete", new JScrollPane(itemCompletePanel));
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
;
		newItemButton= new JButton("New Item");
		
		craftButton = new JButton(new MissingIcon("Craft", 40, 40));
		craftButton.setBorder(BorderFactory.createEmptyBorder());		
		clearButton = new JButton(new MissingIcon("Clear", 40, 40));
		clearButton.setBorder(BorderFactory.createEmptyBorder());	
	}
	
	public void setActionListener(ActionListener listener)
	{
		if(this.listener == null) 
		{
			this.listener = listener;
			
			newItemButton.addActionListener(listener);
			craftButton.addActionListener(listener);
			clearButton.addActionListener(listener);
			
			craftButton.setActionCommand(Controller.Action.CRAFT.toString());
			clearButton.setActionCommand(Controller.Action.CLEAR.toString());
			
			// TODO: Streamline setup of mouse listener
			DragMouseAdapter adapter = new DragMouseAdapter();			
			itemQueuePanel.addMouseListener(adapter);
			itemQueuePanel.addMouseMotionListener(adapter);
		}
	}
	
	public ActionListener getActionListener()
	{
		return this.listener;
	}
	
	@Deprecated
	public void setMouseListener(MouseAdapter listener)
	{
		this.mouseListener = listener;
	}
	
	@Deprecated
	public void setUpPanel(JPanel panel) 
	{		
		// Set the Action Listener
		((ViewItem) panel).setActionListener(listener);
		// Set the Mouse Listener
		panel.addMouseListener(mouseListener);
	}
	
	public void removePanel(JPanel panel)
	{
		itemQueuePanel.remove(panel);
	}
	
	public void removeAllPanels()
	{
		itemQueuePanel.removeAll();
		itemQueuePanel.repaint();
		
		itemCompletePanel.removeAll();
		itemCompletePanel.repaint();
	}
	
	public int indexOf(JComponent component)
	{
		for(int i = 0; i < itemQueuePanel.getComponentCount(); i++) 
		{
			if(itemQueuePanel.getComponent(i) == component) 
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
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		//Add the content  
		frame.getContentPane().add(this);
		
		// Set Size
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		// Display in the middle of the screen
		frame.setLocationRelativeTo(null);
		
		//Add the Menu
		frame.setJMenuBar(new MainMenu(listener));
				
		//Display the window
		frame.pack();
		frame.setVisible(true);		
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		this.removeAllPanels();
	
		Model m = (Model) arg0;
		Controller.getInstance().appendItemPanels(m.getQueue(), itemQueuePanel);
		Controller.getInstance().appendItemPanels(m.getComplete(), itemCompletePanel);
		System.out.format("View has been updated %n");
		
		resourcesPanel.setGP(m.getGold());
		resourcesPanel.setXP(m.getXP());
		
		this.revalidate();		
	}
}

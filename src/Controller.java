import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Controller {
	private Model model;
	private View view;
	
	public static Controller controller;
	
	public enum Action 
	{
		NEWITEM("New Item"),
		EDIT("Edit Item"),
		CRAFT("Craft"),
		CLEAR("Clear Completed Items"),
		LOAD("Load from XML"),
		SAVE("Save to XML"),
		INVALID("Invalid Action");
		
		String command;
		Action(String str)
		{
			this.command = str;
		}
	}
		
	public Controller(Model m, View v) 
	{			
		this.model = m;
		this.view = v;
		this.controller = this;
		
		model.addObserver(view);
		
		v.setActionListener(new MyActionListener());
		this.appendItemPanels(model.getQueue());
	}
	
	public static Controller getInstance()
	{
		return controller;
	}
	
	public void save(Object obj) 
	{
		JAXBController jaxb = new JAXBController(new File("user.xml"));
		jaxb.save(model);		
	}
		
	public void load(File f)
	{
		JAXBController jaxb = new JAXBController(f);
		model = jaxb.load();
		
		view.removeAllPanels();
		this.appendItemPanels(model.getQueue());
	}
		
	public static int check(String checkText) 
	{
		ArrayList<Object> menu = new ArrayList<Object>();
		menu.add(checkText);
		menu.add(new JTextField(""));
		
		// Prompt the user for input
		JOptionPane.showConfirmDialog(null, menu.toArray(), "Check", JOptionPane.OK_CANCEL_OPTION);
		
		int result = 0; 
		result = Integer.valueOf(((JTextField) menu.get(1)).getText());
		
		return result;

	}
	
	public static int editArray(ArrayList<Object> array) 
	{
		return JOptionPane.showConfirmDialog(null, array.toArray(), "New Item", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public void appendItemPanels(ArrayList<Item> array) 
	{
		// For all items in the model
		for(Item item : array)
		{
			this.appendItemPanel(item);
		}	
		
		view.revalidate();
	}
	
	public void appendItemPanel(Item item)
	{
		// Create an item panel
		ViewItem panelView = new ViewItem(item);
					
		// Link the model to the item panel
		item.addObserver(panelView);			
		// Add the item panel to the main view
		view.appendPanel(panelView);
		// Revalidates the view
		view.revalidate();
	}
	
	public class MyActionListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(event.getSource() instanceof JButton) {
				JComponent parent = (JComponent) ((JButton) event.getSource()).getParent();
				
				if(parent instanceof ViewItem) 
				{
					int index = view.indexOf(parent);
					edit(index);
				} else {
					switch(Controller.Action.valueOf(event.getActionCommand()))
					{
					case LOAD:
						Controller.getInstance().load(new File("user.xml"));
						clearComplete();
						break;
					case SAVE:
						Controller.getInstance().save(model);
						break;
					case NEWITEM: 
						model.appendQueue(ItemMundane.create());
						break;						
					case EDIT:
						break;
					case CRAFT:
						// Check there are items in the queue to craft
						if(model.getQueue().size() == 0) 
						{
							JOptionPane.showMessageDialog(view, "There are no items to craft", "Error", JOptionPane.ERROR_MESSAGE);
							break;
						}
													
						switch(getNextItem(null).getItemType())
						{
						case MUNDANE:
							craftMundane();
							break;
						case MAGIC:
							craftMagic();
							break;
						}						
						break;
					case CLEAR:
						clearComplete();
						break;
					default:						
					}
					
					model.notifyObservers();
				}				
			}
		}
	}
	
	public void craftMundane()
	{
		//ItemMundane iMundane = (ItemMundane) getNextItem(Model.ITEM.MUNDANE);
		int check = this.check("Roll Craft Check:");
		int checkPart = check;
		
		//int progress = iMundane.getDC() * check;
		
		while(checkPart > 0 && getNextItem(Item.TYPE.MUNDANE) != null)
		{
			ItemMundane iMundane = (ItemMundane) getNextItem(Item.TYPE.MUNDANE);
			int progress = iMundane.getDC() * checkPart;
			
			if(check >= iMundane.getDC()) 
			{
				// Successful check	
				int diff = iMundane.getBaseCost() - iMundane.getProgress();
				
				// Check if the item will be finished on this craft check
				if(diff >= progress) 
				{
					// Add entire check to the item 
					iMundane.setProgress(iMundane.getProgress() + progress);
					checkPart = 0;
				} else {
					// Add the difference required to complete the item
					iMundane.setProgress(iMundane.getProgress() + diff);
					// Calculate the remaining craft check
					progress -= diff;					
					checkPart = (progress / iMundane.getDC());
				}
				
				// Notify the Observers that this item has been updated
				iMundane.notifyObservers();
			} else if (check < iMundane.getDC() - 4) {						
				// Check Failed by 5 or more: Half raw materials have been destroyed				
				int cost = iMundane.getMatCost() / 2;
				// All progress is lost
				checkPart = 0;
			} else {
				// The check failed but no materials have been destroyed				
				checkPart = 0;
			}
		}
	}
	
	public void craftMagic()
	{
		int progress = 2000;		
		while(progress > 0 && getNextItem(Item.TYPE.MAGIC) != null)
		{
			Item item = getNextItem(Item.TYPE.MAGIC);			
			int diff = progress - item.getProgress();
						
			if(diff >= progress)
			{
				item.setProgress(item.getProgress() + progress);
				progress = 0;
			} else {
				item.setProgress(item.getProgress() + diff);
				progress -= diff;
			}
			
			item.update();
		}
	}
	
	public void edit(int index)
	{
		model.getQueue().get(index).edit();
		model.getQueue().get(index).notifyObservers();
	}
	
	public Item getNextItem(Item.TYPE type) 
	{
		for(Item item : model.getQueue())
		{
			if(! item.isComplete() && (type == null || item.getItemType() == type))
				return item;
		}
		
		return null;
	}
	
	public void clearComplete()
	{
		// Collect completed items in the 'remove' array
		ArrayList<Item> remove = new ArrayList<Item>();
		for(Item item : model.getQueue())
		{
			if(item.isComplete())
			{			
				remove.add(item);
				model.getComplete().add(item);
			}			
		}
		
		// Remove items from the queue
		for(Item item : remove)
		{
			model.removeQueue(item);
		}
		
		// Notify Observers (GUI) of changes
		model.notifyObservers();
	}
	
	public static void main (String[] args)
	{
		Model m = new Model();
		
		// Add Dummy Items
		for(int i = 0; i < 12; i++) {
			ItemMundane item = new ItemMundane("This is an item" + i, 150, 12);
			m.appendQueue(item);
		}
		
		View v = new View();
		
		Controller con = new Controller(m, v);
		v.createAndShowGUI();
	}	
}

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
	public static String key = new String("key");
	public static String keyItem = new String("keyItem");
	public static String keyEffect = new String("keyEffect");
	
	public enum Action 
	{
		NEWITEM("New Item"),
		NEWEFFECT("New Effect"),
		EDIT("Edit Item"),
		CRAFT("Craft"),
		CLEAR("Clear Completed"),
		LOAD("Load from XML"),
		SAVE("Save to XML"),
		INVALID("Invalid Action");
		
		String command;
		Action(String str)
		{
			this.command = str;
		}
		
		public String toString()
		{
			return command;
		}
		
		public static Action valueOfCommand(String str)
		{
			for(Action action : Action.values())
			{
				if(action.toString().equals(str))
					return action;
			}
			
			return null;
		}
	}
		
	public Controller(Model m, View v) 
	{	
		this.controller = this;
		
		setUp(m, v);
	}
	
	private void setUp(Model m, View v)
	{
		this.model = m;
		this.view = v;
		
		// Setup the observer pattern, wire the actions into view
		model.addObserver(view);		
		v.setActionListener(new MyActionListener());
		v.setMouseListener(new mouseListener());
		// Populate the view with items from the model
		//this.appendItemPanels(model.getQueue());
		//model.setGold(1000);
		model.notifyObservers();
	}
	
	public static Controller getInstance()
	{
		return controller;
	}
	
	/**
	 * Save the specified object <code>obj</code> to XML
	 * @param obj
	 */
	public void save(Object obj) 
	{
		JAXBController jaxb = new JAXBController(new File("user.xml"));
		jaxb.save(model);		
	}
		
	/**
	 * Load the specified XML File into the model
	 * @param file
	 */
	public void load(File file)
	{
		JAXBController jaxb = new JAXBController(file);
		model = jaxb.load();		
		
		setUp(model, view);
	}
		
	/**
	 * Prompt the user with <code>checkText</code> to roll a check
	 * @param checkText
	 * @return
	 */
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
	
	/**
	 * Prompt the user to edit <code>array</code> using  the <code>JOptionPane</code>
	 * @param array
	 * @return
	 */
	public int editArray(ArrayList<Object> array) 
	{
		return JOptionPane.showConfirmDialog(view, array.toArray(), "New Item", JOptionPane.OK_CANCEL_OPTION);
	}
	
	/**
	 * Prompt the user to edit <code>array</code> using  the <code>JOptionPane</code>
	 * @param array
	 * @return
	 */

	public int editArray(Object[] array) 
	{
		return JOptionPane.showConfirmDialog(view, array, "New Item", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public void showMessage(String message) 
	{
		JOptionPane.showMessageDialog(view, message);
	}
	
	/**
	 * Add the <code>array</code> of items to the View 
	 * @param array
	 */
	public void appendItemPanels(ArrayList<Item> array) 
	{
		// For all items in the model
		for(Item item : array)
		{
			this.appendItemPanel(item);
		}	
		
		view.revalidate();
	}
	
	/**
	 * Add the specified <code>item</code> to the  View
	 * @param item
	 */
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
			
			if(event.getSource() instanceof JComponent) {
				JComponent source = ((JComponent) event.getSource());
				JComponent parent = (JComponent) source.getParent();
				
				if(parent instanceof ViewItem) 
				{
					int index = view.indexOf(parent);
					edit(index);
				} else {
					switch(Controller.Action.valueOfCommand(event.getActionCommand()))
					{
					case LOAD:
						Controller.getInstance().load(new File("user.xml"));
						clearComplete();
						break;
					case SAVE:
						Controller.getInstance().save(model);
						break;
					case NEWITEM: 
						//model.appendQueue(ItemMundane.create());
						//ItemWand newItem = (ItemWand) ItemWand.create();
						//newItem.addEffect(new SpellEffect().create());
						ItemMagicBasic newItem = ItemMagicBasic.create();
						model.appendQueue(newItem);
						break;
					case NEWEFFECT:
						{
							// Get the Item
							ItemMagic item = (ItemMagic) source.getClientProperty(key);							
							// Get the effect
							Effect effect = (Effect) source.getClientProperty(keyEffect);					
							// Create the new effect on the specified item
							item.addEffect(effect.create());
							
							// TODO: Hack to force item to update
							item.setName(item.getName());
							item.notifyObservers();
						}
						break;
					case EDIT:
						Object obj = source.getClientProperty(key);
						// Check if the object is an item or effect
						if(obj instanceof Item)
						{
							Item item = (Item) obj;
							item.edit();
							item.notifyObservers();
						} else if (obj instanceof Effect) {							
							((Effect) obj).edit();
							
							//TODO: Hack to update the item when the price changes
							Item item = (Item) source.getClientProperty(keyItem);
							item.setName(item.getName());
							item.notifyObservers();
						}
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
	
	class mouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{
			//System.out.format("Mouse Pressed%n");
			showPopup(event);
		}
		
		public void mouseReleased(MouseEvent event)
		{
			//System.out.format("Mouse Released%n");
			showPopup(event);
		}
		
		private void showPopup(MouseEvent event)
		{
			if(event.isPopupTrigger())
			{
				JComponent source = (JComponent) event.getSource();
				
				if(source instanceof ViewItem)
				{
					int index = view.indexOf(source);					
					//System.out.format("Popup Triggered%n%s%n", index);
					
					JPopupMenu menu = createItemMenu(index);
					menu.show(event.getComponent(), event.getX(), event.getY());					
				}
			}
		}
	}
	
	private JPopupMenu createItemMenu(int index)
	{
		ActionListener listener = view.getActionListener();
		Item item = model.getQueue().get(index);
		
		JPopupMenu menu = new JPopupMenu();		
		JMenuItem jmi;
		JMenu subMenu;
		
		jmi = new JMenuItem(item.getName());		
		menu.add(jmi);
		menu.addSeparator();
		
		// TODO: Different menu for different types of items
		
		subMenu = new JMenu("Edit");
		menu.add(subMenu);
		
		jmi = new JMenuItem("Item");
		jmi.addActionListener(listener);
		jmi.setActionCommand(Controller.Action.EDIT.toString());	
		jmi.putClientProperty(key, item);
		subMenu.add(jmi);		
		
		// Check if the item is 'magic' 
		if(item instanceof ItemMagic) {
			
			subMenu.addSeparator();
			
			// Edit existing Effects
			for(Effect effect : ((ItemMagic) item).getEffect())
			{				
				jmi = new JMenuItem(effect.toString());
				jmi.addActionListener(listener);
				jmi.setActionCommand(Controller.Action.EDIT.toString());		
				jmi.putClientProperty(key, effect);
				jmi.putClientProperty(keyItem, item);
				subMenu.add(jmi);
			}
			
			// Create new effects
			subMenu = new JMenu("Add Enchantment");
			menu.add(subMenu);
			
			Effect[] effectArray = new Effect[] { new EffectBonus(), new EffectSpell(), new EffectStatic() };						
			for(Effect effect: effectArray)
			{
				jmi = new JMenuItem(effect.classToString());
				jmi.addActionListener(listener);
				jmi.setActionCommand(Controller.Action.NEWEFFECT.toString());
				jmi.putClientProperty(key, item);
				jmi.putClientProperty(keyEffect, effect);
				subMenu.add(jmi);
			}
		}
		
		return menu;
	}
	
	public void craftMundane()
	{
		int check = this.check("Roll Craft Check:");
		int checkPart = check;
		
		
		while(checkPart > 0 && getNextItem(Item.TYPE.MUNDANE) != null)
		{
			ItemMundane iMundane = (ItemMundane) getNextItem(Item.TYPE.MUNDANE);
			int progress = iMundane.getDC() * checkPart;
			
			// Subtract gold and XP when beginning a new item
			if(iMundane.getProgress() == 0)
			{
				model.setGold(model.getGold() - iMundane.getCraftPrice());
				model.notifyObservers();
			}
			
			if(check >= iMundane.getDC()) 
			{
				// Successful check	
				int diff = iMundane.getPrice() - iMundane.getProgress();
				
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
				int halfMatCost = iMundane.getCraftPrice() / 2;
				model.setGold(model.getGold() - halfMatCost);
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
			// Fetch the next Magic Item
			ItemMagic item = (ItemMagic) getNextItem(Item.TYPE.MAGIC);
			
			// Find the remaining work to complete the item
			int diff = item.getPrice() - item.getProgress();
						
			// Subtract gold and XP when beginning a new item
			if(item.getProgress() == 0)
			{
				model.setGold(model.getGold() - item.getCraftPrice());
				model.setXP(model.getXP() - item.getXP());
				model.notifyObservers();
			}			
						
			// Check if the item can be completed with this craft check
			if(diff >= progress)
			{
				// Item won't be completed; add the entire check
				item.setProgress(item.getProgress() + progress);
				progress = 0;
			} else {
				// Item will be completed; add part if the check required to complete the item
				item.setProgress(item.getProgress() + diff);
				progress -= diff;
			}			
			
			// Notify the Observers  that the item has changed
			item.notifyObservers();
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
		View v = new View();
		
		// Add Dummy Items
		/*
		for(int i = 0; i < 12; i++) {
			ItemMundane item = new ItemMundane("This is an item" + i, 150, 12);
			m.appendQueue(item);
		}
		*/
		
		new Controller(m, v);
		v.createAndShowGUI();
	}	
}

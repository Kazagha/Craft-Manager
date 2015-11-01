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
import javax.swing.JFileChooser;
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
	
	private JFileChooser fc;
	private JAXBController jaxb;
	
	public static Controller controller;
	public static String key = new String("key");
	public static String keyItem = new String("keyItem");
	public static String keyEffect = new String("keyEffect");
	
	public enum Action 
	{
		ADDGOLD("Add Gold"),
		ADDXP("Add XP"),
		NEWMAGICITEM("Item Creation Feat"),
		NEWMUNDANEITEM("Craft Skill"),
		NEWEFFECT("New Effect"),
		EDIT("Edit Item"),
		CRAFT("Craft Item"),
		CLEAR("Clear Completed"),
		LOAD("Open"),
		SAVE("Save"),
		SAVE_AS("Save As..."),
		EXIT("Exit"),
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
		
		init();
		
		setUp(m, v);
	}
	
	private void init()
	{
		jaxb = new JAXBController();
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new XMLFilter());
	}
	
	private void setUp(Model m, View v)
	{
		this.model = m;
		this.view = v;
		
		// Setup the observer pattern, wire the actions into view
		model.addObserver(view);
		// Send through initial updated model
		model.notifyObservers();
		// Setup the action listener
		v.setActionListener(new MyActionListener());		
	}
	
	public static Controller getInstance()
	{
		return controller;
	}
	
	/**
	 * Save the specified object <code>obj</code> to XML
	 * @param obj
	 */
	public void save() 
	{
		if (jaxb.getFile() != null)
		{
			jaxb.save(model);
		} else {
			saveAs();
		}
	}
	
	public void saveAs()
	{
		int result = fc.showOpenDialog(view);
		
		// Check if the user has selected OK
		if (result == JFileChooser.APPROVE_OPTION)
		{
			// Set the file location
			jaxb.setfile(fc.getSelectedFile());
			
			// Save the model
			jaxb.save(model);
		}
	}
		
	/**
	 * Load the specified XML File into the model
	 * @param file
	 */
	public void load()
	{
		int result = fc.showOpenDialog(view);		
		
		// Check if the user has select OK
		if (result == JFileChooser.APPROVE_OPTION)
		{		
			// Set the file location in the JAXBController
			jaxb.setfile(fc.getSelectedFile());
						
			// Load the specified file
			model = jaxb.load();
			
			// Setup the new model/view observers 
			setUp(model, view);
		}
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
	 * @param panel
	 */
	public void appendItemPanels(ArrayList<Item> array, JPanel panel) 
	{
		// For all items in the model
		for(Item item : array)
		{
			this.appendItemPanel(item, panel);
		}	
		
		view.revalidate();
	}
	
	/**
	 * Add the specified <code>item</code> to the  View
	 * @param item
	 * @param panel
	 */
	public void appendItemPanel(Item item, JPanel panel)
	{
		// Create an item panel
		ViewItem panelView = new ViewItem(item);
							
		// Link the model to the item panel
		item.addObserver(panelView);			
		// Add Action and Mouse Listener to the panel
		// TODO: The existing mouse Listener has been disabled
		//view.setUpPanel(panelView);
		// Add the item panel to the specified Panel
		panel.add(panelView);
		// Revalidates the view
		view.revalidate();
	}
	
	public void changeItemLocation(int idxA, int idxB)
	{
		ArrayList<Item> array = model.getQueue();
				
		Item itemA = array.get(idxA);
		
		array.remove(itemA);		
		array.add(idxB, itemA);
		
		model.setGold(model.getGold());
		model.notifyObservers();
	}
	
	public class MyActionListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(event.getSource() instanceof JComponent) {
				JComponent source = ((JComponent) event.getSource());

				switch(Controller.Action.valueOfCommand(event.getActionCommand()))
				{
				case LOAD:
					Controller.getInstance().load();
					break;
				case SAVE:
					Controller.getInstance().save();
					break;
				case SAVE_AS:
					Controller.getInstance().saveAs();
					break;
				case ADDGOLD:
					addGold();
					break;
				case ADDXP:
					addXP();
					break;
				case NEWMAGICITEM:
				{
					ItemMagicBasic newItem = ItemMagicBasic.create();
					model.appendQueue(newItem);
					break;
				}
				case NEWMUNDANEITEM:
				{
					Item newItem = ItemMundane.create();
					model.appendQueue(newItem);
					break;
				}						
				case NEWEFFECT:
					{
						// Get the Item
						ItemMagic item = (ItemMagic) source.getClientProperty(key);							
						// Get the effect
						Effect effect = (Effect) source.getClientProperty(keyEffect);					
						// Create the new effect on the specified item
						item.addEffect(effect.create());
						
						// Let the item know something has changed
						item.setChanged();
						item.notifyObservers();
						
						break;
					}						
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
					if(getNextItem(null) == null) 
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
				case EXIT:
					System.exit(0);
					break;
				default:						
				}
				
				model.notifyObservers();				
			}
		}
	}
	
	public JPopupMenu createItemMenu(int index)
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
			ItemMundane item = (ItemMundane) getNextItem(Item.TYPE.MUNDANE);
			int progress = item.getDC() * checkPart;
			
			// Subtract gold and XP when beginning a new item
			if(item.getProgress() == 0)
			{
				model.setGold(model.getGold() - item.getCraftPrice());
				model.notifyObservers();
			}
			
			if(check >= item.getDC()) 
			{
				// Successful check	
				int diff = item.getPrice() - item.getProgress();
				
				// Check if the item will be finished on this craft check
				if(diff >= progress) 
				{
					// Add entire check to the item 
					item.setProgress(item.getProgress() + progress);
					checkPart = 0;
				} else {
					// Add the difference required to complete the item
					item.setProgress(item.getProgress() + diff);
					// Calculate the remaining craft check
					progress -= diff;					
					checkPart = (progress / item.getDC());
				}
				
				// Notify the Observers that this item has been updated
				item.notifyObservers();
			} else if (check < item.getDC() - 4) {						
				// Check Failed by 5 or more: Half raw materials have been destroyed				
				int halfMatCost = item.getCraftPrice() / 2;
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
	
	public int addGold()
	{		
		JTextField gold = new JTextField();
		Object[] array = new Object[] { "Add Gold", gold };
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION) 
		{
			try
			{
				result = this.editArray(array);
				
				model.setGold(model.getGold() + Integer.valueOf(gold.getText()));
				return result;
			} catch (Exception e) {
				
			}
		}
		
		return result;
	}
	
	public int addXP()
	{
		JTextField xp = new JTextField();
		Object[] array = new Object[] { "Add XP", xp };
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION) 
		{
			try
			{
				result = this.editArray(array);
				
				model.setXP(model.getXP() + Integer.valueOf(xp.getText()));
				return result;
			} catch (Exception e) {
				
			}
		}
		
		return result;
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
				model.getComplete().add(0, item);
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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

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
public class ControllerFX implements ControllerInterface {
	private Model model;
	private ViewFX view;
	
	private FileChooser fc;
	private JAXBController jaxb;
	
	public static ControllerInterface controller;
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
		
	public ControllerFX(Model m, ViewFX v) 
	{	
		this.controller = this;
		
		init();
		
		setUp(m, v);
	}
	
	private void init()
	{
		jaxb = new JAXBController();
		
		fc = new FileChooser();
		// TODO: Set selection mode and file filter 
		//fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//fc.setFileFilter(new XMLFilter());
	}
	
	private void setUp(Model m, ViewFX v)
	{
		this.model = m;
		this.view = v;
		
		Random r = new Random();
		
		for (int i = 0; i < 50; i++)
		{
			ItemMundane item = new ItemMundane();
			item.setName("Test Item: " + i);
			item.setPrice(1000);
			item.setProgress(r.nextInt(1000));
			model.getQueue().add(item);
		}
		
		for (int i = 0; i < 3; i++) 
		{
			ItemMundane item = new ItemMundane();
			item.setName("Finished Item: " + i);
			item.setPrice(1000);
			item.setProgress(1000);
			model.getComplete().add(item);
		}
		
		// Setup the observer pattern, wire the actions into ViewFX
		model.addObserver(view);
		// Send through initial updated model
		model.notifyObservers();
		// Setup the action listener
		//v.setActionListener(new MyActionListener());		
	}
	
	public static ControllerInterface getInstance()
	{
		return controller;
	}
	
	/* (non-Javadoc)
	 * @see ControllerInterface#save()
	 */
	@Override
	public void save() 
	{
		if (jaxb.getFile() != null)
		{
			jaxb.save(model);
		} else {
			saveAs();
		}
	}
	
	@Override
	public void saveAs()
	{
		File file = fc.showOpenDialog(view.getScene().getWindow());
		
		// Check if the file has been selected
		if (file != null)
		{
			// Set the file location
			jaxb.setfile(file);
			
			// Save the model
			jaxb.save(model);
		}
	}

	@Override
	public void load()
	{
		File file = fc.showOpenDialog(view.getScene().getWindow());		
		
		// Check if the user has select OK
		if (file != null)
		{		
			// Set the file location in the JAXBController
			jaxb.setfile(file);
						
			// Load the specified file
			model = jaxb.load();
			
			// Setup the new model/ViewFX observers 
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
		return JOptionPane.showConfirmDialog(null, array.toArray(), "New Item", JOptionPane.OK_CANCEL_OPTION);
	}
	
	/**
	 * Prompt the user to edit <code>array</code> using  the <code>JOptionPane</code>
	 * @param array
	 * @return
	 */

	public int editArray(Object[] array) 
	{
		return JOptionPane.showConfirmDialog(null, array, "New Item", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public void showMessage(String message) 
	{
		// TODO: Migrate away from JOptionPane
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * Add the <code>array</code> of items to the ViewFX 
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
		
		// TODO: Will this need to be revalitaded
		//ViewFX.revalidate();
	}
	
	/**
	 * Add the specified <code>item</code> to the  ViewFX
	 * @param item
	 * @param panel
	 */
	public void appendItemPanel(Item item, JPanel panel)
	{
		// Create an item panel
		ViewItem panelViewFX = new ViewItem(item);							
		// Link the model to the item panel
		item.addObserver(panelViewFX);			
		// Add the item panel to the specified Panel
		panel.add(panelViewFX);
		// Revalidates the ViewFX
		// TODO: Will this need to be revalidated
		// ViewFX.revalidate();
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
	/*
	public class MyEventHandler implements EventHandler 
	{
		@Override
		public void handle(Event event) {
			
			if(event.getSource() instanceof JComponent) {
				JComponent source = ((JComponent) event.getSource());

				switch(ControllerFX.Action.valueOfCommand(event.getActionCommand()))
				{
				case LOAD:
					ControllerFX.getInstance().load();
					break;
				case SAVE:
					ControllerFX.getInstance().save();
					break;
				case SAVE_AS:
					ControllerFX.getInstance().saveAs();
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
						JOptionPane.showMessageDialog(ViewFX, "There are no items to craft", "Error", JOptionPane.ERROR_MESSAGE);
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
	*/
	
	/* (non-Javadoc)
	 * @see ControllerInterface#newItemMagic()
	 */
	@Override
	public void newItemMagic()
	{
		ItemMagicBasic newItem = ItemMagicBasic.create();
		model.appendQueue(newItem);
	}
	
	/* (non-Javadoc)
	 * @see ControllerInterface#newItemMundane()
	 */
	@Override
	public void newItemMundane()
	{
		Item newItem = ItemMundane.create();
		model.appendQueue(newItem);
	}
	
	/* (non-Javadoc)
	 * @see ControllerInterface#newEffect(ItemMagic, Effect)
	 */
	@Override
	public void newEffect(ItemMagic item, Effect effect)
	{					
		// Create the new effect on the specified item
		item.addEffect(effect.create());
		
		// Let the item know something has changed
		item.setChanged();
		item.notifyObservers();
	}
	
	public void edit(Item item)
	{
		item.edit();
		item.notifyObservers();
	}
	
	public void edit(Item item, Effect effect)
	{
		effect.edit();
		
		//TODO: Hack to update the item when the price changes
		item.setName(item.getName());
		item.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see ControllerInterface#createItemMenu(int)
	 */
	@Override
	public JPopupMenu createItemMenu(int index)
	{
		// TODO: Replace the action listener
		//ActionListener listener = view.getActionListener();
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
		//jmi.addActionListener(listener);
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
				//jmi.addActionListener(listener);
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
				//jmi.addActionListener(listener);
				jmi.setActionCommand(Controller.Action.NEWEFFECT.toString());
				jmi.putClientProperty(key, item);
				jmi.putClientProperty(keyEffect, effect);
				subMenu.add(jmi);
			}
		}
		
		return menu;
	}
	
	
	/* (non-Javadoc)
	 * @see ControllerInterface#craftMundane()
	 */
	@Override
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
				// Start crafting the item, even if the initial check failed
				item.setProgress(1);
				
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
	
	/* (non-Javadoc)
	 * @see ControllerInterface#craftMagic()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see ControllerInterface#addGold()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see ControllerInterface#addXP()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see ControllerInterface#clearComplete()
	 */
	@Override
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
}

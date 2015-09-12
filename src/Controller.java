import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private HashMap<Model.ITEM, Integer> craft;
	
	public enum Action 
	{
		NEWITEM("New Item"),
		EDIT("Edit Item"),
		CRAFT("Craft"),
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
		this.appendItemPanels(model.getItems());
	}
	
	public static Controller getInstance()
	{
		return controller;
	}
	
	public void save(Object obj) 
	{
		try {
			JAXBContext jaxb = JAXBContext.newInstance(Controller.class, Item.class, ItemMundane.class);
			
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(obj, System.out);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int check(String checkText) 
	{
		ArrayList<Object> menu = new ArrayList<Object>();
		menu.add(checkText);
		menu.add(new JTextField(""));
		
		// Prompt the user for input
		JOptionPane.showConfirmDialog(null, menu.toArray(), "Check", JOptionPane.OK_CANCEL_OPTION);
		
		return Integer.valueOf(((JTextField) menu.get(1)).getText());
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
	}
	
	public void appendItemPanel(Item item)
	{
		// Create an item panel
		ViewItem panelView = new ViewItem(item);
					
		// Link the model to the item panel
		item.addObserver(panelView);			
		// Add the item panel to the main view
		view.appendPanel(panelView);
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
					case NEWITEM: 
						model.appendItem(ItemMundane.create());
						break;						
					case EDIT:
						break;
					case CRAFT:
						craft();
						break;
					default:						
					}
					
					model.notifyObservers();
				}				
			}
		}
	}
	
	public void craft()
	{
		if(model.getItems().size() > 0) 
		{
			Item item = model.getItems().get(0);
			
			switch(item.getItemType()) 
			{
			case MUNDANE:
				int check = this.check("Roll Craft Check:");
				craft = new HashMap<>();
				craft.put(Model.ITEM.MUNDANE, 100);
				break;
			case MAGIC:
				
				break;				
			}			
		} else {
			JOptionPane.showMessageDialog(view, "There are no items to craft", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void edit(int index)
	{
		model.getItems().get(index).edit();
		model.getItems().get(index).notifyObservers();
	}
	
	public static void main (String[] args) 
	{
		Model m = new Model();
		
		// Add Dummy Items
		for(int i = 0; i < 12; i++) {
			ItemMundane item = new ItemMundane("This is an item" + i, 150, 12);
			m.appendItem(item);
		}
		
		View v = new View();
		
		Controller con = new Controller(m, v);
		v.createAndShowGUI();
	}	
}

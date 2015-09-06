import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Controller {
	Model model;
	View view;
		
	public Controller(Model m, View v) 
	{
		this.model = m;
		this.view = v;
		
		v.setActionListener(new MyActionListener());
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
	
	public class MyActionListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			model.getItems().get(2).setName("this is a test");		
			model.getItems().get(2).notifyObservers();
			
			if(event.getSource() instanceof JButton) {
				JComponent parent = (JComponent) ((JButton) event.getSource()).getParent();
				
				System.out.format("Button Pressed: %s%n", view.indexOf(parent));						
			}
		}
	}
	
	public static void main (String[] args) 
	{
		Model m = new Model();
		
		View v = new View();
		
		Controller con = new Controller(m, v);
		System.out.println(m.getItems());	
		
		for(int i = 0; i < 12; i++) {
			ItemMundane item = new ItemMundane("This is an item" + i, 10, 10);
			ViewItem panelView = new ViewItem(item);
			
			m.appendItem(item);
			
			item.addObserver(panelView);
			
			v.appendPanel(panelView);
		}
		
		v.createAndShowGUI();
	}	
}

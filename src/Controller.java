import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.jaxb.*;
import org.eclipse.persistence.internal.oxm.Marshaller;
import org.eclipse.persistence.*;

@XmlRootElement
public class Controller {
	ArrayList<Item> items;
	Item test;
		
	public Controller() 
	{
		items = new ArrayList<Item>();
	}
	
	@XmlElementRef
	public ArrayList<Item> getItems() 
	{
		return this.items;
	}
	
	public void setItems(ArrayList<Item> array) 
	{
		this.items = array;
	}
	
	public void appendItem(Item item) {
		this.items.add(item);
	}
	
	
	public void save(Object obj) 
	{
		try {
			javax.xml.bind.JAXBContext jaxb = JAXBContext.newInstance(Controller.class, Item.class, ItemMundane.class);
			
			javax.xml.bind.Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(obj, System.out);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) 
	{
		Controller con = new Controller();
		con.appendItem(new ItemMundane("test", 10, 10));
		
		con.save(con);
	}	
}

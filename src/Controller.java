import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

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
			JAXBContext jaxb = JAXBContext.newInstance(Controller.class, Item.class, ItemMundane.class);
			
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
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

import java.util.ArrayList;

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
		Model m = new Model();
		m.appendItem(new ItemMundane("test", 10, 10));
		
		View v = new View();
		
		Controller con = new Controller(m, v);
		System.out.println(m.getItems());	
		
		for(int i = 0; i < 12; i++) {
			v.appendItem(new ItemMundane("this is an item: " + i, 10, 10));
		}
		v.createAndShowGUI();
		//con.save(con);
	}	
}

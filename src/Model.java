import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;

public class Model {
	ArrayList<String> items = new ArrayList<String>();
	
	public Model()
	{
		
	}

	public ArrayList<String> getItems() 
	{
		return this.items;
	}
	
	public void setItems(ArrayList<String> array) 
	{
		this.items = array;
	}
	
	public void appendItem(String s) {
		this.items.add(s);
	}
}

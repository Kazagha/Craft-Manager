import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;

public class Model {
	ArrayList<Item> items = new ArrayList<Item>();
	
	public Model()
	{
		
	}

	public ArrayList<Item> getItems() 
	{
		return this.items;
	}
	
	public void setItems(ArrayList<Item> array) 
	{
		this.items = array;
	}
	
	public void appendItem(Item item) 
	{
		this.items.add(item);
	}
	
	public void removeItem(Item item)
	{
		this.items.remove(item);
	}
}

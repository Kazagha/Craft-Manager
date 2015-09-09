import java.util.ArrayList;
import java.util.Observable;

import javax.xml.bind.annotation.XmlElementRef;

public class Model extends Observable {
	int gold;
	int XP;
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
		setChanged();
	}
	
	public void appendItem(Item item) 
	{
		if(item == null) { return; }
		
		this.items.add(item);
		setChanged();
	}
	
	public void removeItem(Item item)
	{
		this.items.remove(item);
		setChanged();
	}
}

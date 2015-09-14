import java.util.ArrayList;
import java.util.Observable;

import javax.xml.bind.annotation.XmlElementRef;

public class Model extends Observable {
	
	public static enum ITEM {MUNDANE, MAGIC};
	
	private ArrayList<Item> queue = new ArrayList<Item>();
	private ArrayList<Item> complete = new ArrayList<Item>();
	
	public Model()
	{
		
	}

	public ArrayList<Item> getQueue() 
	{
		return this.queue;
	}
	
	public void setQueue(ArrayList<Item> array) 
	{
		this.queue = array;
		setChanged();
	}
	
	public ArrayList<Item> getComplete() 
	{
		return this.complete;
	}
	
	public void setComplete(ArrayList<Item> array) 
	{
		this.complete = array;
		setChanged();
	}
	
	public void appendItem(Item item) 
	{
		if(item == null) { return; }
		
		this.queue.add(item);
		setChanged();
	}
	
	public void removeItem(Item item)
	{
		this.queue.remove(item);
		setChanged();
	}
}

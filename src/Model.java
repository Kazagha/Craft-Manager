import java.util.ArrayList;
import java.util.Observable;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={ "queue", "complete" })
public class Model extends Observable {
	
	//public static enum ITEM {MUNDANE, MAGIC};
	
	private ArrayList<Item> queue = new ArrayList<Item>();
	private ArrayList<Item> complete = new ArrayList<Item>();
		
	public Model()
	{
		
	}

	@XmlElementRef
	public ArrayList<Item> getQueue() 
	{
		return this.queue;
	}
	
	public void setQueue(ArrayList<Item> array) 
	{
		this.queue = array;
		setChanged();
	}
	
	@XmlElementRef
	public ArrayList<Item> getComplete() 
	{
		return this.complete;
	}
	
	public void setComplete(ArrayList<Item> array) 
	{
		this.complete = array;
		setChanged();
	}
	
	public void appendQueue(Item item) 
	{
		if(item == null) { return; }
		
		this.queue.add(item);
		setChanged();
	}
	
	public void removeQueue(Item item)
	{
		this.queue.remove(item);
		setChanged();
	}
	
	public void appendComplete(Item item)
	{
		if(item == null) { return; }
		
		this.complete.add(item);
		setChanged();		
	}
	
	public void removeComplete(Item item)
	{
		this.complete.remove(item);
		setChanged();
	}
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) 
	{
		
	}
}

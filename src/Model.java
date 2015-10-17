import java.util.ArrayList;
import java.util.Observable;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={ "gold", "XP", "queue", "complete" })
public class Model extends Observable {
	
	private int gold;
	private int XP;
	private ArrayList<Item> queue = new ArrayList<Item>();
	private ArrayList<Item> complete = new ArrayList<Item>();
		
	public Model()
	{		
		setChanged();
	}
	
	/**
	 * Set player gold to the specified <code>num</code> of gold pieces
	 * @param num
	 */
	public void setGold(int num)
	{
		this.gold = num;
		setChanged();
	}
	
	/**
	 * Return the number of gold pieces the player owns 
	 * @return
	 */
	public int getGold()
	{
		return this.gold;
	}
	
	/**
	 * Set the amount of XP the player has
	 * @param num
	 */
	public void setXP(int num)
	{
		this.XP = num;
		this.setChanged();
	}
	
	/**
	 * Return the amount of XP the player has
	 * @return
	 */
	public int getXP()
	{
		return this.XP;
	}

	@XmlElementRef(name="Queue")
	public ArrayList<Item> getQueue() 
	{
		return this.queue;
	}
	
	/**
	 * Return an array of <code>Item</code>'s in the queue (to be crafted)
	 * @param array
	 */
	public void setQueue(ArrayList<Item> array) 
	{
		this.queue = array;
		setChanged();
	}
	
	/**
	 * Return an array of <code>Item</code>'- that have been completed
	 * @return
	 */
	@XmlElementRef(name="Complete")
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

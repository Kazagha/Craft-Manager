import java.util.ArrayList;
import java.util.Observable;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={ "gold", "XP", "queue", "complete" })
public class Model extends Observable implements ModelInterface {
	
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
	@Override
	public void setGold(int num)
	{
		this.gold = num;
		setChanged();
	}
	
	/**
	 * Return the number of gold pieces the player owns 
	 * @return
	 */
	@Override
	public int getGold()
	{
		return this.gold;
	}
	
	/**
	 * Set the amount of XP the player has
	 * @param num
	 */
	@Override
	public void setXP(int num)
	{
		this.XP = num;
		this.setChanged();
	}
	
	/**
	 * Return the amount of XP the player has
	 * @return
	 */
	@Override
	public int getXP()
	{
		return this.XP;
	}

	@Override
	@XmlElementRef(name="Queue")
	public ArrayList<Item> getQueue() 
	{
		return this.queue;
	}
	
	/**
	 * Return an array of <code>Item</code>'s in the queue (to be crafted)
	 * @param array
	 */
	@Override
	public void setQueue(ArrayList<Item> array) 
	{
		this.queue = array;
		setChanged();
	}
	
	/**
	 * Return an array of <code>Item</code>'- that have been completed
	 * @return
	 */
	@Override
	@XmlElementRef(name="Complete")
	public ArrayList<Item> getComplete() 
	{
		return this.complete;
	}
	
	@Override
	public void setComplete(ArrayList<Item> array) 
	{
		this.complete = array;
		setChanged();
	}
	
	@Override
	public void appendQueue(Item item) 
	{
		if(item == null) { return; }
		
		this.queue.add(item);
		setChanged();
	}
	
	@Override
	public void removeQueue(Item item)
	{
		this.queue.remove(item);
		setChanged();
	}
	
	@Override
	public void appendComplete(Item item)
	{
		if(item == null) { return; }
		
		this.complete.add(item);
		setChanged();		
	}
	
	@Override
	public void removeComplete(Item item)
	{
		this.complete.remove(item);
		setChanged();
	}
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) 
	{
		// Move all completed items to the completed array
		for (Item item : this.queue)
		{
			if (item.isComplete())
				complete.add(item);
		}
		
		// Remove completed items from the queue
		this.queue.removeAll(complete);
	}
}

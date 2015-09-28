import java.util.Observable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Item  extends Observable {
	private String name;
	private int progress;
	private TYPE itemType;
	
	public static enum TYPE {MUNDANE, MAGIC};
	
	public Item() {}
	
	public Item(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return this.name;		
	}
		
	public int getProgress() 
	{
		return this.progress; 
	}	
	
	public TYPE getItemType()
	{
		return itemType;
	}
	
	public void setName(String name) 
	{
		this.name = name;
		setChanged();
	}
	
	public void setProgress(int i)
	{
		this.progress = i;
		setChanged();
	}
			
	public void setItemType(TYPE type) 
	{
		this.itemType = type;
	}

	public boolean isComplete() 
	{
		return progress >= getPrice();
	}
	
	abstract void update();
	
	public abstract int edit();
	
	public abstract int getPrice();
	
	public abstract int getCraftPrice();
}

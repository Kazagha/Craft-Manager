import java.util.Observable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Item  extends Observable {
	private String name;
	private int baseCost;
	private int matCost;
	private int progress;
	private Model.ITEM itemType;
		
	public Item() {}
	
	public Item(String name, int baseCost, int matCost) 
	{
		this.name = name;
		this.baseCost = baseCost;
		this.matCost = matCost;
	}
	
	public String getName() 
	{
		return this.name;		
	}
	
	public int getBaseCost() 
	{
		return this.baseCost;
	}
	
	public int getMatCost() 
	{
		return this.matCost;
	}
	
	public int getProgress() 
	{
		return this.progress; 
	}	
	
	public void setName(String name) 
	{
		this.name = name;
		setChanged();
	}
	
	public void setBaseCost(int i)
	{
		this.baseCost = i;
		setChanged();
	}
	
	public void setMatCost(int i) 
	{
		this.matCost = i;
		setChanged();
	}
	
	public void setProgress(int i)
	{
		this.progress = i;
		setChanged();
	}
	
	public boolean isComplete() 
	{
		return progress >= baseCost;
	}
	
	/*
	public Model.ITEM getItemType()
	{
		return itemType;
	}
	
	
	public void setItemType(Model.ITEM type) 
	{
		this.itemType = type;
	}
	*/
	
	abstract void update();
	
	abstract int edit();
}

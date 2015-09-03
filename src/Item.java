import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.*;

//@XmlType(propOrder={ "name"})
@XmlRootElement
public abstract class Item {
	private String name;
	private int baseCost;
	private int matCost;
	private int DC;
	private int progress;
	
	public Item() {}
	
	public Item(String name, int baseCost, int DC) 
	{
		
	}
	
	public String getName() 
	{
		return this.name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public boolean isComplete() 
	{
		return progress >= baseCost;
	}
	
	abstract void update();
	

}

import java.util.Observable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@XmlRootElement
public abstract class Item  extends Observable {
	private String name;
	private int progress;
	private TYPE itemType;
	
	@XmlType(name = "ItemType")
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
	
	@Override
	public void setChanged()
	{
		super.setChanged();
	}
			
	public abstract Pane toEditPane();
	
	public abstract int getPrice();
	
	public abstract int getCraftPrice();

	public abstract boolean validateAndStore(); 
	
}

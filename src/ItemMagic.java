import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;

public abstract class ItemMagic extends Item {

	private ArrayList<Effect> effect = new ArrayList<Effect>();
	
	@XmlElementRef
	public ArrayList<Effect> getEffect()
	{
		return effect;
	}
	
	public void setEffect(ArrayList<Effect> effect)
	{
		this.effect = effect;
	}
	
	public void addEffect(Effect effect)
	{
		if(effect == null)
			return; 
		
		this.effect.add(effect);
	}
	
	public int getCraftPrice()
	{
		if(getPrice() == 0)
			return 0;		
		
		return getPrice() / 2;
	}
	
	public int getXP() 
	{
		if(getPrice() == 0)
			return 0;		
		
		return getPrice() / 25;
	}
}

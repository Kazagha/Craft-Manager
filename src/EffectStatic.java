
public class EffectStatic extends Effect {

	String name;
	int price;
	int xp;
	
	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public int getCraftPrice() 
	{		
		return price / 2;
	}

	@Override
	public int getXPCost() 
	{
		return xp;
	}

	@Override
	public int edit() {
		return 0;
	}

}

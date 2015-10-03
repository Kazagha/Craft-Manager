import java.util.ArrayList;


public class ItemMagicGeneric extends ItemMagic {
	
	private ArrayList<Effect> effect;

	public ItemMagicGeneric() {}
	
	public ItemMagicGeneric(String name)
	{
		this.setName(name);
	}
	
	@Override
	ArrayList<Effect> getEffect()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void setEffect(ArrayList<Effect> effect) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	void addEffect(Effect effect) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	int getXP() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPrice() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCraftPrice() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}

import java.util.ArrayList;


public class ItemMagicBasic extends ItemMagic {
	
	private ArrayList<Effect> effect;

	public ItemMagicBasic() {}
	
	public ItemMagicBasic(String name)
	{
		this.setName(name);
	}
	
	public static ItemMagicBasic

	@Override
	public int edit() 
	{
		return 1;
	}

	@Override
	public int getPrice() 
	{
		return 1;
	}
}

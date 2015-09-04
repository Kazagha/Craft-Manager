public class ItemMundane extends Item {

	private int DC;
	
	public ItemMundane(String name, int baseCost, int DC) {		
		super(name, baseCost, baseCost / 3);
		this.DC = DC;
	}
	
	public int getDC() 
	{
		return this.DC;
	}
	
	public void setDC(int DC)
	{
		this.DC = DC;
	}
	
	@Override
	void update() {
				
	}
}
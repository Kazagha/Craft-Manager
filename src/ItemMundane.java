public class ItemMundane extends Item {

	private int DC;
	
	public ItemMundane(String name, int baseCost, int DC) {		
		super(name, baseCost * 10, baseCost / 3 );
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
	void update(Controller controller) {
		String text = String.format("Roll craft check (DC=%s)", getDC());
		int check = controller.check(text);	
			
		// Was the check successful
		if(check > getDC()) 
		{
			// Successful check
			setProgress(getProgress() + (getDC() * check));
		} else if (check < getDC() - 4)
		{
			// Check Failed by 5 or more: Half raw materials have been destroyed
			int cost = getMatCost() / 2;
		}	
		
		System.out.format("DC: %s Check: %s", getDC(), check);			
		notifyObservers();
	}
}
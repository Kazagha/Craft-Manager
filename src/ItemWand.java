
public class ItemWand extends Item{

	int XP;
	
	public ItemWand(String name, int casterLevel, int spellLevel) 
	{		
		super(name, casterLevel * spellLevel * 750, casterLevel * spellLevel * 750 / 2);
		this.XP = casterLevel * spellLevel * 750 / 25;
	}

	@Override
	void update(Controller controller) {
		setProgress(getProgress() + 1000); 
	}
}

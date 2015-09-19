
public class SpellEffect extends Effect {

	private String name;
	private int casterLevel;
	private int spellLevel;
	private int cost;
	private int XP;
	
	public SpellEffect() {}
	
	public SpellEffect(String name, int casterLevel, int spellLevel, int cost, int XP)
	{
		this.name = name;
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
	}
	
	public int getCasterLevel() 
	{
		return casterLevel;
	}

	public void setCasterLevel(int casterLevel) 
	{
		this.casterLevel = casterLevel;
	}

	public int getSpellLevel() 
	{
		return spellLevel;
	}

	public void setSpellLevel(int spellLevel) 
	{
		this.spellLevel = spellLevel;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void setCost(int cost) 
	{
		this.cost = cost;
	}

	public void setXP(int XP)
	{
		this.XP = XP;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public int getCost() {
		return this.cost;
	}

	@Override
	public int getXP() {
		return this.XP;
	}
}

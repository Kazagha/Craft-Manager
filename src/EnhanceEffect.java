
public class EnhanceEffect extends Effect {

	String name;
	int bonus;
		
	enum enhance
	{
		ONE		("+1", 1000),
		TWO		("+2", 4000),
		THREE	("+3", 9000),
		FOUR	("+4", 16000),
		FIVE	("+5", 25000),
		SIX		("+6", 36000),
		SEVEN	("+7", 49000),
		EIGHT	("+8", 64000),
		NINE	("+9", 81000),
		TEN		("+10", 100000);
		
		String str;
		int cost;
		enhance(String s, int i)
		{
			this.str = s;
			this.cost = i;
		}
		
		public static enhance getEnhance(int i)
		{
			return enhance.values()[i];
		}
		
		public static String getString(int i)
		{
			return getEnhance(i).toString();
		}
		
		public static int getCost(int i)
		{
			return getEnhance(i).getCost();
		}

		public String toString() 
		{
			return str;
		}

		public int getCost()
		{
			return cost;
		}
	}
	
	public EnhanceEffect() {};
	
	public EnhanceEffect(String name, int bonus)
	{
		this.name = name;
		this.bonus = bonus;
	}
	
	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() 
	{
		return enhance.getString(bonus).toString();
	}

	@Override
	public int getCost()
	{
		return enhance.getCost(bonus);
	}

	@Override
	public int getCraftCost() 
	{
		return enhance.getCost(bonus) / 2;
	}

	@Override
	public int getXPCost() 
	{
		return enhance.getCost(bonus) / 25;
	}

	@Override
	public int edit() 
	{
		return 0;
	}

}

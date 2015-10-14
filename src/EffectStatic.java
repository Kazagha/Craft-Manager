import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class EffectStatic extends Effect {

	String name;
	int price;
	int xpCost;
	
	public EffectStatic() {};
	
	public EffectStatic(String name, int price, int XP)
	{
		this.name = name;
		this.price = price;
		this.xpCost = XP;
	}
	
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
		return xpCost;
	}	

	@Override
	public int edit() 
	{
		Object[] array;
		
		JTextField name = new JTextField(this.getName());
		JTextField price = new JTextField(String.valueOf(this.getPrice()));
		JTextField xp = new JTextField(String.valueOf(this.getXPCost()));
		
		array = new Object[] { "Name", name, "Price", price, "Additional XP Cost", xp };
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try 
			{
				result = Controller.getInstance().editArray(array);
				
				this.setName(name.getText());
				this.setPrice(Integer.valueOf(price.getText()));
				this.setXpCost(Integer.valueOf(xp.getText()));
				return result;				
			} catch (Exception e) {
				Controller.getInstance().showMessage("Input Error:" + e.getMessage());
			}
		}
		
		return result;
	}

	public void setXpCost(int xpCost) {
		this.xpCost = xpCost;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setPrice(int price) 
	{
		this.price = price;
	}
}
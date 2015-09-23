import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class SpellEffect extends Effect {

	private String name;
	private int casterLevel;
	private int spellLevel;
	private int materialCost;
	private int xpCost;
	
	public SpellEffect() {}
	
	public SpellEffect(String name, int casterLevel, int spellLevel, int materialCost, int xpCost)
	{
		this.name = name;
		this.casterLevel = casterLevel;
		this.spellLevel = spellLevel;
		this.materialCost = materialCost;
		this.xpCost = xpCost;
	}
	
	public static SpellEffect create()
	{
		SpellEffect newEffect = new SpellEffect("", 0, 0, 0, 0);
		
		if(newEffect.edit() == JOptionPane.OK_OPTION)
			return newEffect;
		
		return null;
	}
	
	public int edit()
	{
		ArrayList<Object> array = new ArrayList<Object>();
		
		JTextField name = new JTextField(getName());
		JTextField casterLevel = new JTextField(String.valueOf(getCasterLevel()));
		JTextField spellLevel = new JTextField(String.valueOf(getSpellLevel()));
		JTextField materialCost = new JTextField(String.valueOf(getMaterialCost()));
		JTextField xpCost = new JTextField(String.valueOf(getXPCost()));
		
		array.addAll(Arrays.asList(new Object[] { 
				"Name", name, 
				"Caster Level", casterLevel, 
				"Spell Level", spellLevel, 
				"Material Cost", materialCost, 
				"XP Cost", xpCost 
				}));
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try
			{
				result = Controller.getInstance().editArray(array);
				
				this.setName(name.getText());
				this.setCasterLevel(Integer.valueOf(casterLevel.getText()));
				this.setSpellLevel(Integer.valueOf(spellLevel.getText()));
				this.setMaterialCost(Integer.valueOf(materialCost.getText()));
				this.setXPCost(Integer.valueOf(xpCost.getText()));
				return result;
			} catch (Exception e) {
				
			}
		}
		
		return result;
	}
	
	public int getMaterialCost() 
	{
		return materialCost;
	}

	public void setMaterialCost(int materialCost) 
	{
		this.materialCost = materialCost;
	}

	public int getXPCost() 
	{
		return xpCost;
	}

	public void setXPCost(int xpCost) 
	{
		this.xpCost = xpCost;
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

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getBaseCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}

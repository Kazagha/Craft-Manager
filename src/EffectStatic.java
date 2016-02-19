import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextField;

@XmlRootElement
@XmlType(propOrder={ "name", "price", "xpCost"})
public class EffectStatic extends Effect {

	private String name;
	private int price;
	private int xpCost;
	
	private TextField nameField;
	private TextField priceField;
	private TextField xpField;
	
	public EffectStatic() {};
	
	public EffectStatic(String name, int price, int XP)
	{
		this.name = name;
		this.price = price;
		this.xpCost = XP;
	}
		
	@XmlElement
	@Override
	public String getName() 
	{
		return name;
	}

	@XmlElement
	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public int getCraftPrice() 
	{		
		return price / 2;
	}

	@XmlElement
	@Override
	public int getXpCost() 
	{
		return xpCost;
	}	
	
	public Effect create()
	{
		EffectStatic newEffect = new EffectStatic("", 0, 0);
		
		//if(newEffect.edit() == JOptionPane.OK_OPTION)		
		//	return newEffect;
		
		return null;		
	}
	
	@Override
	public Node toEditPane() {
		Platform.runLater(() -> nameField.requestFocus());
		
		nameField.setText(this.getName());
		priceField.setText(String.valueOf(this.getPrice()));
		xpField.setText(String.valueOf(this.getXpCost()));
		
		return null;
	}

	@Override
	public boolean validateAndStore() {
		if (nameField.getText().equals(""))
			return false;
		
		if (priceField.getText().matches("[A-Za-z]*") ||
				Integer.valueOf(priceField.getText()) <= 0)
			return false;
		
		if (xpField.getText().matches("") ||
				Integer.valueOf(xpField.getText()) < 0)
			return false;
		
		this.setName(nameField.getText());
		this.setPrice(Integer.valueOf(priceField.getText()));
		this.setXpCost(Integer.valueOf(xpField.getText()));
		
		return true;
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
	
	public String toString()
	{
		return this.getName();
	}

	@Override
	public String classToString() 
	{
		return "Special (Set Price)";
	}
}
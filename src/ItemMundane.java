import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@XmlRootElement(name = "MundaneItem")
@XmlType(propOrder={ "price", "DC" })
public class ItemMundane extends Item {

	private int price;
	private int DC;	
	
	private static TextField nameField = new TextField();
	private static TextField priceField = new TextField();
	private static TextField DCField = new TextField();
	
	public ItemMundane(String name, int price, int DC) {	
		super(name);		
		super.setItemType(Item.TYPE.MUNDANE);
		this.price = price;
		this.DC = DC;
		setChanged();
	}
	
	public ItemMundane() {}
	
	public static Item create()
	{
		ItemMundane newItem = new ItemMundane("", 0, 0);
		/*
		if(newItem.edit() == JOptionPane.OK_OPTION)
		{
			return newItem;
		}
		*/
		return null;
	}
		
	public Pane toEditPane()
	{
		Platform.runLater(() -> nameField.requestFocus());
		
		nameField.setText(this.getName());
		priceField.setText(String.valueOf(this.getPrice()));
		DCField.setText(String.valueOf(this.getDC()));
		
		return this.toDialog(
				new Label("Name"), nameField,
				new Label("Price"),	priceField,
				new Label("DC"), DCField				
				);
	}
	
	public boolean validateAndStore()
	{
		if (! nameField.getText().equals("")) 
		{
			return false;
		}
		
		if (priceField.getText().contains("[A-Za-z]")) 
		{
			return false;
		}
		
		if (DCField.getText().contains("[A-Za-z]")) 
		{
			return false;
		}
		
		if (Integer.valueOf(priceField.getText()) <= 0)
		{
			return false;
		}
		
		if (Integer.valueOf(DCField.getText()) <= 0)
		{
			return false;
		}
		
		this.setName(nameField.getText());
		this.setPrice(Integer.valueOf(priceField.getText()));
		this.setDC(Integer.valueOf(DCField.getText()));
		
		return true;
	}		
	
	public int getDC() 
	{
		return this.DC;
	}
	
	public void setDC(int DC)
	{
		this.DC = DC;
	}
	
	public void setPrice(int price) 
	{
		this.price = price;
	}	
	
	@XmlElement
	@Override
	public int getPrice()
	{
		return price;
	}

	@Override
	public int getCraftPrice() {
		return price / 3 / 10;
	}
}
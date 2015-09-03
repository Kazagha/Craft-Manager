import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

@XmlRootElement(name="Mundane")
//@XmlType(propOrder={ "name", "test" })
public class ItemMundane extends Item {
		
	private String test;
	
	public ItemMundane()
	{ 
		super();
	};
	
	public ItemMundane(String name, int baseCost, int DC) {
		super(name, baseCost, DC);
	}

	public String getTest()
	{
		return "test";
	}
	
	public void setTest(String s) {
		this.test = s;
	}
	
	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}
}



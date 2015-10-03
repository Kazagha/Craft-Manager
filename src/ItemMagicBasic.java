import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ItemMagicBasic extends ItemMagic {
	
	private ArrayList<Effect> effect;

	public ItemMagicBasic() {}
	
	public ItemMagicBasic(String name)
	{
		this.setName(name);
	}
	
	@Override
	public int edit() 
	{
		Object[] array;
		
		JTextField name = new JTextField(this.getName());
		
		array = new Object[] { "Name", name };
		
		int result = JOptionPane.OK_OPTION;
		while(result == JOptionPane.OK_OPTION)
		{
			try {
				result = Controller.getInstance().editArray(array);
				
				// Set changes
				this.setName(name.getText());
				return result;
			} catch (Exception e) {
				Controller.getInstance().showMessage("Input Error: " + e.getMessage());
			}
		}
		
		return result;
	}

	@Override
	public int getPrice() 
	{
		return 1;
	}
}

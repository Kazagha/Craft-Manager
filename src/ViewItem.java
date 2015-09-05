import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewItem extends JPanel {
	private JLabel name;
	private JLabel baseCost;
	private JLabel matCost;
	private JLabel progress;
	
	public ViewItem(Item model) 
	{
		name = new JLabel();
		baseCost = new JLabel();
		matCost = new JLabel();
		progress = new JLabel();
		
		notify(model);
	}
	
	public void notify(Item model) 
	{
		setName(model.getName());
		setBaseCost(model.getBaseCost());
		setMatCost(model.getMatCost());
		setProgress(model.getProgress());
	}
	
	public void setName(String str)
	{
		name.setText(str);
	}
	
	public void setBaseCost(int num)
	{
		baseCost.setText(String.valueOf(num));
	}
	
	public void setMatCost(int num)
	{
		matCost.setText(String.valueOf(num));
	}
	
	public void setProgress(int num)
	{
		progress.setText(String.valueOf(num));
	}
}

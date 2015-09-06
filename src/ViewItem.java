import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewItem extends JPanel implements Observer {
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
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(name);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(baseCost);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(matCost);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(progress);
		
		updateItem(model);
	}
	
	public void updateItem(Item model) 
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

	@Override
	public void update(Observable model, Object arg1) {
		this.updateItem((Item) model);		
	}
}

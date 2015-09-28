import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewItem extends JPanel implements Observer {
	private JLabel name;
	private JLabel baseCost;
	private JLabel matCost;
	private JLabel progress;
	
	private JButton checkButton;
	
	public ViewItem(Item model) 
	{
		name = new JLabel();
		baseCost = new JLabel();
		matCost = new JLabel();
		progress = new JLabel();
		checkButton = new JButton("Check");
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 8, 4));
		this.add(name);
		this.add(baseCost);
		this.add(matCost);
		this.add(progress);
		this.add(checkButton);
		
		updateItem(model);
	}
	
	public void updateItem(Item model) 
	{
		setName(model.getName());
		setBaseCost(model.getPrice());
		setMatCost(model.getCraftPrice());
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
	
	public void setActionListener(ActionListener listener)
	{		
		checkButton.addActionListener(listener);
	}

	@Override
	public void update(Observable model, Object arg1) {
		this.updateItem((Item) model);		
	}
}

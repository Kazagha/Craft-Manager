import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;

public class ViewItem extends JPanel implements Observer {
	private JLabel name;
	private JLabel baseCost;
	private JLabel matCost;
	private JLabel progress;
	
	private ImageIcon image;
	private JLabel imagePanel;
	private JProgressBar progressBar;
	
	private JButton checkButton;
	
	public ViewItem(Item model) 
	{
		initComponents();

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(imagePanel)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup()
						.addComponent(name)
						.addComponent(progressBar)
						)				
				.addContainerGap()
				);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(imagePanel)
						.addGroup(layout.createSequentialGroup()
								.addComponent(name)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(progressBar)
								)						
						)
				.addContainerGap()
				);

		updateItem(model);
	}
	
	private void initComponents()
	{
		name = new JLabel();
		baseCost = new JLabel();
		matCost = new JLabel();
		progress = new JLabel();
		checkButton = new JButton("Check");
		
		progressBar = new JProgressBar();
		
		image = new ImageIcon("images/craftIconSmall.png");
		imagePanel = new JLabel(image);
	}
	
	public void updateItem(Item model) 
	{
		setName(model.getName());
		setBaseCost(model.getPrice());
		setMatCost(model.getCraftPrice());
		setProgress(model.getProgress());
		
		setProgressBar(model.getProgress(), model.getPrice());
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
	
	public void setProgressBar(int value, int total)
	{
		progressBar.setMinimum(0);
		progressBar.setMaximum(total);
		progressBar.setValue(value);
		progressBar.setString(String.format("%d / %d", value, total));
		progressBar.setStringPainted(true);
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

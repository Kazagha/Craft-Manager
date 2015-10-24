import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JWindow;

public class DragMouseAdapter extends MouseAdapter {
	
	private final JWindow window = new JWindow();
	private Point startPt;

	public DragMouseAdapter()
	{
		super();
		// Set the background color with transparency
		window.setBackground(new Color(0, true));
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		JComponent parent = (JComponent) e.getComponent();
		
		// Check if there are at least two components to select 
		if (parent.getComponentCount() <= 1)
		{
			startPt = null;
			return;
		}
		
		startPt = e.getPoint();			
	}
}

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
	
	
	/**
	 * Determine the point where the mouse event occurred
	 * Check there are at least two components present to swap places
	 */
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
	
	/**
	 * 
	 * @param parent - The parent of the specified components
	 * @param remove - Remove this component
	 * @param add    - Insert this component at the specified index
	 * @param idx    - The index to insert the component
	 */
	private static void swapComponentLocation(
			Container parent, Component remove, Component add, int idx)
	{
		parent.remove(remove);
		parent.add(add, idx);
		parent.revalidate();
		parent.repaint();
	}
}

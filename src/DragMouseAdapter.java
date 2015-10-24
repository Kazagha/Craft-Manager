import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JWindow;

public class DragMouseAdapter extends MouseAdapter {
	
	private final JWindow window = new JWindow();
	private Point startPt;	
	private final int gestureMotionThreshold = DragSource.getDragThreshold();

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
	public void mousePressed(MouseEvent evt)
	{
		JComponent parent = (JComponent) evt.getComponent();
		
		// Check if there are at least two components to select 
		if (parent.getComponentCount() <= 1)
		{
			startPt = null;
			return;
		}
		
		startPt = evt.getPoint();		
	}
	
	@Override
	public void mouseDragged(MouseEvent evt)
	{
		Point pt = evt.getPoint();
		JComponent parent = (JComponent) evt.getComponent();
		
		// Check the Motion Threshold has been met
		double a = Math.pow(pt.x - startPt.x, 2);
		double b = Math.pow(pt.y - startPt.y, 2);
		// Check the dragging component is not null and the threshold has been met
				
		System.out.format("Mouse Dragged Event: %n");
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class DragMouseAdapter extends MouseAdapter {
	
	private final JWindow window = new JWindow();
	private Component draggingComponent;
	private Component gap;
	private Point startPt;	
	private Point dragOffset;
	private int index = -1;
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
	
	/**
	 * Drag the selected component; update the position of the component and insert gaps 
	 * 
	 * Determine if the mouse drag threshold has been met
	 * Create the dragging component
	 * Update the dragging component's position
	 */
	@Override
	public void mouseDragged(MouseEvent evt)
	{
		Point pt = evt.getPoint();
		JComponent parent = (JComponent) evt.getComponent();
		
		// Check the Motion threshold has been met (a^2 + b^2 = c^2)
		// Check the dragging component is currently null
		double a = Math.pow(pt.x - startPt.x, 2);
		double b = Math.pow(pt.y - startPt.y, 2);				
		if (draggingComponent == null && (Math.sqrt(a + b) > gestureMotionThreshold))
		{
			// Create the dragging component, insert gaps
			startDragging(parent, pt);
			return;
		}
		
		// Return if the there is no dragging occurring
		if (!window.isVisible() || draggingComponent == null)
			return;
		
		// Update the location of the dragging window
		updateWindowLocation(pt, parent);
				
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
	
	/**
	 * Begin dragging the component at the specified point
	 * 
	 * Check the component is not the parent
	 * Calculate the offset between the origin of the component and the point it was clicked
	 * Create gap of the same size where the component was
	 * Set the specified component as the 'window'
	 * Initial update of the location of the window
	 * @param parent
	 * @param pt
	 */
	public void startDragging(JComponent parent, Point pt)
	{
		// Fetch the component at the specified point
		Component c = parent.getComponentAt(pt);
		
		// Return if the component is the parent panel
		// Return if the parent has no child components 
		index = parent.getComponentZOrder(c);		
		if (Objects.equals(c, parent) || index < 0)
			return;		
		
		// Set the dragging component
		draggingComponent = c;
		Dimension dSize = draggingComponent.getSize();
		
		// Calculate offset (between the mouse and origin of component)
		Point dPoint = draggingComponent.getLocation();
		dragOffset = new Point(pt.x - dPoint.x, pt.y - dPoint.y);
		
		// Create filler gap of the same size as the dragged component
		gap = Box.createRigidArea(dSize);
		swapComponentLocation(parent, c, gap, index);
		
		// Create the 'dragging' window
		window.add(draggingComponent);
		window.pack();
		
		// Update the location of the window
		updateWindowLocation(pt, parent);
		window.setVisible(true);				
	}
		
	/**
	 * Update the location of the specified panel 
	 * @param pt
	 * @param parent - Relative to this parent component
	 */
	private void updateWindowLocation(Point pt, JComponent parent)
	{
		// Find the location of the panel including the offset
		Point p = new Point(pt.x - dragOffset.x, pt.y - dragOffset.y);
		SwingUtilities.convertPointToScreen(p, parent);
		// Update the location of the window
		window.setLocation(p);
	}
}

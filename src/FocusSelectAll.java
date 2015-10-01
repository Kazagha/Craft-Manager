import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FocusSelectAll implements FocusListener
{
	@Override
	public void focusGained(FocusEvent event) 
	{		
		SwingUtilities.invokeLater(new selectAll(event.getSource()));		
	}

	@Override
	public void focusLost(FocusEvent event) {}
	
	private class selectAll implements Runnable
	{
		Object source;
		
		public selectAll(Object source)
		{
			this.source = source;
		}

		@Override
		public void run() {
			
			if(this.source instanceof JTextField)
				((JTextField) this.source).selectAll();
		}		
	}
}

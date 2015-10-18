import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainMenu extends JMenuBar {

	public MainMenu(ActionListener listener)
	{
		JMenu subMenu;		
		JMenuItem jmi;
		
		subMenu = new JMenu("File");
		this.add(subMenu);
	
		jmi = new JMenuItem("SAVE");
		jmi.addActionListener(listener);
		subMenu.add(jmi);
		jmi = new JMenuItem("Open");
		subMenu.add(jmi);
		jmi = new JMenuItem("Exit");
		subMenu.add(jmi);		
	}
}

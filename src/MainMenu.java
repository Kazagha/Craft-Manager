import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainMenu extends JMenuBar {

	private ActionListener listener;
	
	public MainMenu(ActionListener listener)
	{
		this.listener = listener;
		
		JMenu subMenu;		
		ActionMenu menu;
		
		subMenu = new JMenu("File");
		this.add(subMenu);
	
		menu = new ActionMenu(Controller.Action.SAVE.toString());
		subMenu.add(menu);
		menu = new ActionMenu(Controller.Action.LOAD.toString());
		subMenu.add(menu);
		menu = new ActionMenu(Controller.Action.EXIT.toString());
		subMenu.add(menu);		
	}
	
	class ActionMenu extends JMenuItem
	{
		ActionMenu(String str)
		{
			super(str);
			this.addActionListener(listener);
		}
	}
}

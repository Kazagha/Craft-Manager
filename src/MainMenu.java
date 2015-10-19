import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainMenu extends JMenuBar {

	private ActionListener listener;
	
	public MainMenu(ActionListener listener)
	{
		this.listener = listener;
		
		JMenu rootMenu;
		JMenu subMenu;		
		ActionMenu menu;
		
		subMenu = new JMenu("File");
		this.add(subMenu);
			
		subMenu.add(new ActionMenu(Controller.Action.SAVE.toString()));
		subMenu.add(new ActionMenu(Controller.Action.LOAD.toString()));
		subMenu.add(new ActionMenu(Controller.Action.EXIT.toString()));		
		
		rootMenu = new JMenu("Crafting");
		this.add(rootMenu);
				
		subMenu = new JMenu("New Item");		
		subMenu.add(new ActionMenu(Controller.Action.NEWMAGICITEM.toString()));
		subMenu.add(new ActionMenu(Controller.Action.NEWMUNDANEITEM.toString()));
		rootMenu.add(subMenu);
		
		subMenu = new JMenu("Add Resources");		
		subMenu.add(Controller.Action.ADDGOLD.toString());
		subMenu.add(Controller.Action.ADDXP.toString());
		rootMenu.add(subMenu);
		
		rootMenu.add(new ActionMenu(Controller.Action.CLEAR.toString()));
		
		this.add(new ActionMenu(Controller.Action.CRAFT.toString()));
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

import java.util.Observable;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public interface ViewInterface {

	/**
	 * Return the <code>Scene</code> which is the root node of the view
	 **/
	Scene getScene();

	/**
	 * Update the view to show the specified amount of XP
	 * @param xp
	 */
	void setXP(int xp);

	/**
	 * Update the view to show specified amount of gold
	 * @param gp
	 */
	void setGP(int gp);
	
	void update(Observable obs, Object obj);
	
	public Pane getSwitchPane();

	public Pane getHistoryPane();
	
	public Pane getQueuePane();
	
	public Pane getNewPane();
	
	public Pane getMenu();
	
	public GridPane toDialog(Node... nodes);
	
	public int checkDialog();
		
	public void hookUpEventHandler(EventHandler handler);
}
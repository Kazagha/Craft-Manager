import java.util.Observable;

import javafx.scene.Scene;
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
	
	Pane getMenu();

	void update(Observable obs, Object obj);

}
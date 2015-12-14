import java.util.Observable;

import javafx.scene.Scene;

public interface ViewInterface {

	/**
	 * Return the <code>Scene</code> which is the root node of the view
	 **/
	Scene getScene();

	void setXP(int i);

	void setGP(int i);

	void update(Observable obs, Object obj);

}
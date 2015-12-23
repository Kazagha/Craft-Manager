import java.util.ArrayList;

import javax.swing.JPopupMenu;

public interface ControllerInterface {

	/**
	 * Save the specified object <code>obj</code> to XML
	 * @param obj
	 */
	void save();

	void saveAs();

	/**
	 * Load the specified XML File into the model
	 * @param file
	 */
	void load();

	void newItemMagic();

	void newItemMundane();

	void newEffect(ItemMagic item, Effect effect);

	JPopupMenu createItemMenu(int index);

	void craftMundane(ItemMundane item);

	void craftMagic(ItemMagic item);

	int addGold();

	int addXP();

	void clearComplete();
	
	void craftItemAt(int index);
	
	public ArrayList<Item> getItemList();

}
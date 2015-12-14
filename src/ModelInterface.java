import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;

public interface ModelInterface {

	/**
	 * Set player gold to the specified <code>num</code> of gold pieces
	 * @param num
	 */
	void setGold(int num);

	/**
	 * Return the number of gold pieces the player owns 
	 * @return
	 */
	int getGold();

	/**
	 * Set the amount of XP the player has
	 * @param num
	 */
	void setXP(int num);

	/**
	 * Return the amount of XP the player has
	 * @return
	 */
	int getXP();

	ArrayList<Item> getQueue();

	/**
	 * Return an array of <code>Item</code>'s in the queue (to be crafted)
	 * @param array
	 */
	void setQueue(ArrayList<Item> array);

	/**
	 * Return an array of <code>Item</code>'- that have been completed
	 * @return
	 */
	ArrayList<Item> getComplete();

	void setComplete(ArrayList<Item> array);

	void appendQueue(Item item);

	void removeQueue(Item item);

	void appendComplete(Item item);

	void removeComplete(Item item);

}
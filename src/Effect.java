import javafx.scene.Node;

public abstract class Effect {

	public abstract String getName();
	
	public abstract int getPrice();
	
	public abstract int getCraftPrice();
	
	public abstract int getXpCost();	
	
	public abstract Effect create();
	
	public abstract String classToString();

	public abstract Node toEditPane(); 

	public abstract boolean validateAndStore();
}

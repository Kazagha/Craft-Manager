import java.util.ArrayList;

public abstract class ItemMagic extends Item {

	abstract ArrayList<Effect> getEffect();
	
	abstract void setEffect(ArrayList<Effect> effect);
	
	abstract void addEffect(Effect effect);
	
	abstract int getXP();
}

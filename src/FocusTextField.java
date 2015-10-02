import javax.swing.JTextField;
import javax.swing.text.Document;


public class FocusTextField extends JTextField {

	private static FocusSelectAll focus = new FocusSelectAll();
	
	public FocusTextField() {
		this.addFocusListener(focus);
	}

	public FocusTextField(String arg0) {
		super(arg0);
		this.addFocusListener(focus);
	}

	public FocusTextField(int arg0) {
		super(arg0);
		this.addFocusListener(focus);
	}

	public FocusTextField(String arg0, int arg1) {
		super(arg0, arg1);
		this.addFocusListener(focus);
	}

	public FocusTextField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		this.addFocusListener(focus);
	}
}

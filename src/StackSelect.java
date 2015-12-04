import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class StackSelect extends StackPane
	{
		private ObservableList<Node> panes;
		private ScrollPane root;
		
		public StackSelect()
		{
			super();
			init();
		}
		
		public StackSelect(Node...nodes)
		{
			super();
			init();			
			panes.addAll(nodes);
			this.setSelected(nodes.length - 1);
		}		
		
		private void init() 
		{
			panes = FXCollections.observableArrayList();			
			this.setAlignment(Pos.TOP_CENTER);
			
			root = new ScrollPane();
			super.getChildren().add(root);
		}
		
		public void setSelected(int i) 
		{
			if (i >= panes.size())
				return;
			
			//this.getChildren().removeAll(panes);
			//this.getChildren().add(panes.get(i));
			root.setContent(panes.get(i));
		}
		
		@Deprecated
		public ObservableList<Node> getChildren()
		{
			return super.getChildren();
		}
		
		public ObservableList<Node> getSwapChildren()
		{
			return panes;
		}
	}
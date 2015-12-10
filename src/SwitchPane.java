import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class SwitchPane extends StackPane
	{
		private ObservableList<Pane> panes;
		private ScrollPane root;
		
		public SwitchPane()
		{
			super();
			init();
		}
		
		public SwitchPane(Pane...nodes)
		{
			super();
			init();			
			panes.addAll(nodes);
			this.switchTo(nodes.length - 1);
		}		
		
		private void init() 
		{
			panes = FXCollections.observableArrayList();			
			this.setAlignment(Pos.TOP_CENTER);
			
			root = new ScrollPane();
			super.getChildren().add(root);
		}
		
		public void switchTo(int i) 
		{
			if (i >= panes.size() || i < 0)
				return;
			
			//this.getChildren().removeAll(panes);
			//this.getChildren().add(panes.get(i));
			root.setContent(panes.get(i));
		}
		
		/**
		 * Return the currently selected Pane
		 * @param node
		 */
		public void switchTo(Pane node)
		{
			for (Pane pane : panes)
			{
				if (pane.equals(node))
				{
					root.setContent(pane);
					return;
				}
			}
		}
		
		public Pane getSelected()
		{
			for (Pane p : panes) 
			{
				if (root.getContent().equals(p))
					return p;
			}
			
			return null;
		}
		
		@Deprecated
		public ObservableList<Node> getChildren()
		{
			return super.getChildren();
		}
		
		public ObservableList<Pane> getSwapChildren()
		{
			return panes;
		}
	}
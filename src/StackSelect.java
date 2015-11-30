import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

class StackSelect 
	{
		private ObservableList<SelectPane> panes;
		
		public StackSelect()
		{
			init();
		}
		
		public StackSelect(SelectPane...nodes)
		{
			init();			
			panes.addAll(nodes);			
		}		
		
		private void init() 
		{
			panes = FXCollections.observableArrayList();
		}
		
		public ObservableList<SelectPane> getPanes()
		{
			return panes;
		}
		
		class SelectPane 
		{
			private ScrollPane pane; 
			
			public SelectPane() 
			{
				init();
			}
			
			public SelectPane(Node n)
			{
				init();
				setContent(n);
			}
			
			public Node getContent()
			{
				return pane.getContent();
			}
			
			public void setContent(Node n) 
			{
				pane.setContent(n);				
			}
			
			private void init()
			{
				pane = new ScrollPane();
			}			
		}
	}
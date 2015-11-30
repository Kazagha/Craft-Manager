import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

class StackSelect extends StackPane
	{
		private ObservableList<Node> panes;
		
		public StackSelect()
		{
			super();
			init();
		}
		
		public StackSelect(SelectPane...nodes)
		{
			super();
			init();			
			panes.addAll(nodes);
			this.setSelected(nodes.length - 1);
		}		
		
		private void init() 
		{
			panes = FXCollections.observableArrayList();
			super.setAlignment(Pos.TOP_CENTER);
		}
		
		public void setSelected(int i) 
		{
			if (i >= panes.size())
				return;
			
			super.getChildren().remove(panes);
			super.getChildren().add(panes.get(i));
		}
		
		@Override
		public ObservableList<Node> getChildren()
		{
			return panes;
		}
		
		class SelectPane extends ScrollPane
		{			
			public SelectPane() 
			{
				this.init();
			}
			
			public SelectPane(Node n)
			{
				super(n);
				this.init();
			}	
			
			private void init()
			{
				
			}
		}
	}
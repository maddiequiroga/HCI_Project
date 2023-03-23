package edu.ou.cs.hci.Application.project.panes;

//import java.lang.*;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import edu.ou.cs.hci.Application.project.Controller;

public final class ShoppingListPage extends AbstractPane
{
    //**********************************************************************
    // Private Class Members
    //**********************************************************************

    private static final String	NAME = "Shopping List";
    private static final String	HINT = "Shopping List for ingredients";

    //**********************************************************************
    // Private Class Members (Effects)
    //**********************************************************************

    private static final Glow			GLOW =
            new Glow(0.3);

    private static final ColorAdjust	COLOR_ADJUST =
            new ColorAdjust(0.0, 0.0, -0.25, 0.0);

    //**********************************************************************
    // Private Members
    //**********************************************************************

    // Data
    private List<List<String>>			data;

    // Layout
    private BorderPane					base;
    private TableView<Record>			table;
    private SelectionModel<Record>		smodel;

    //**********************************************************************
    // Constructors and Finalizer
    //**********************************************************************

    public ShoppingListPage(Controller controller)
    {
        super(controller, NAME, HINT);

        setBase(buildPane());
    }

    //**********************************************************************
    // Public Methods (Controller)
    //**********************************************************************

    // The controller calls this method when it adds a view.
    // Set up the nodes in the view with data accessed through the controller.
    public void	initialize()
    {
        smodel.selectedIndexProperty().addListener(this::changeIndex);

        int	index = (Integer)controller.get("itemIndex");

        smodel.select(index);
    }

    // The controller calls this method when it removes a view.
    // Unregister event and property listeners for the nodes in the view.
    public void	terminate()
    {
        smodel.selectedIndexProperty().removeListener(this::changeIndex);
    }

    // The controller calls this method whenever something changes in the model.
    // Update the nodes in the view to reflect the change.
    public void	update(String key, Object value)
    {
        if ("itemIndex".equals(key))
        {
            int	index = (Integer)value;

            smodel.select(index);
        }
    }

    //**********************************************************************
    // Private Methods (Layout)
    //**********************************************************************

    private Pane	buildPane()
    {
        //first column
        TableColumn<Record, String>	itemColumn =
                new TableColumn<Record, String>("Item");

        itemColumn.setEditable(false);
        itemColumn.setPrefWidth(150);
        itemColumn.setCellValueFactory(
                new PropertyValueFactory<Record, String>("item"));
        itemColumn.setCellFactory(new NameCellFactory());

        // second column
        TableColumn<Record, String>	recipeColumn =
                new TableColumn<Record, String>("Recipe");

        recipeColumn.setEditable(false);
        recipeColumn.setPrefWidth(150);
        recipeColumn.setCellValueFactory(
                new PropertyValueFactory<Record, String>("recipe"));
        recipeColumn.setCellFactory(new NameCellFactory());

        //third column
        TableColumn<Record, String>	quantColumn =
                new TableColumn<Record, String>("Quantity Needed");

        quantColumn.setEditable(false);
        quantColumn.setPrefWidth(150);
        quantColumn.setCellValueFactory(
                new PropertyValueFactory<Record, String>("Quantity Needed"));
        quantColumn.setCellFactory(new NameCellFactory());

        //fourth column
        TableColumn<Record, String>	buyColumn =
                new TableColumn<Record, String>("Quantity to Buy");

        buyColumn.setEditable(false);
        buyColumn.setPrefWidth(150);
        buyColumn.setCellValueFactory(
                new PropertyValueFactory<Record, String>("Quantity to buy"));
        buyColumn.setCellFactory(new NameCellFactory());

        //fifth column
        TableColumn<Record, String>	ownColumn =
                new TableColumn<Record, String>("Quantity owned");

        ownColumn.setEditable(false);
        ownColumn.setPrefWidth(150);
        ownColumn.setCellValueFactory(
                new PropertyValueFactory<Record, String>("Quantity owned"));
        ownColumn.setCellFactory(new NameCellFactory());


        // Create the table from the columns
        table = new TableView<Record>();
        smodel = table.getSelectionModel();

        table.setEditable(false);
        table.setPlaceholder(new Text("No Data!"));
        //table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        table.getColumns().add(itemColumn);
        table.getColumns().add(recipeColumn);
        table.getColumns().add(quantColumn);
        table.getColumns().add(buyColumn);
        table.getColumns().add(ownColumn);

        //table.setItems(records);

        // Create a split pane to share space between the cover pane and table
        SplitPane	splitPane = new SplitPane();
        splitPane.getItems().add(table);
        base = new BorderPane(splitPane);

        return base;
    }


    public static final class Record
    {
        private final SimpleStringProperty	item;

        public Record(String item)
        {
            this.item = new SimpleStringProperty(item);
        }

        public String	getItem()
        {
            return item.get();
        }

        public void	setItem(String v)
        {
            this.item.set(v);
        }

    }
    //**********************************************************************
    // Private Methods (Change Handlers)
    //**********************************************************************

    private void	changeIndex(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue)
    {
        int	index = (Integer)newValue;

        controller.set("itemIndex", index);
    }

    //**********************************************************************
    // Inner Classes (Cell Factories)
    //**********************************************************************

    private static final class NameCellFactory
            implements Callback<TableColumn<Record, String>,
            TableCell<Record, String>>
    {
        public TableCell<Record, String>	call(TableColumn<Record, String> v)
        {
            return new NameCell();
        }
    }


    private static final class NameCell extends TableCell<Record, String>
    {
        protected void	updateItem(String item, boolean empty)
        {
            super.updateItem(item, empty);			// Prepare for setup

            if (empty || (item == null))			// Handle special cases
            {
                setText(null);
                setGraphic(null);

                return;
            }

            setText(item);
            setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        }
    }
}
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

public final class BrowsePage extends AbstractPane {

    // Layout
	private TableView<String> table;
	private SelectionModel<String> smodel;
    private BorderPane pane;
    private static final Insets	PADDING = new Insets(40.0, 20.0, 40.0, 20.0);

    //===============================================================================================

    public BrowsePage(Controller controller) {
        super(controller, "Browse Recipes", "Add/Remove Ingredients");
        setBase(buildBrowsePane());
    }


    private Pane buildBrowsePane()
    {
        // Create a split pane to share space between the cover pane and table
        HBox topSection = new HBox();
        topSection.getChildren().add(buildTableView());
        topSection.getChildren().add(buildFilterView());

        VBox bodySection = new VBox();
        bodySection.getChildren().add(topSection);
        bodySection.getChildren().add(buildDetailsView());

        HBox wholeSection = new HBox();
        wholeSection.getChildren().add(buildAccordion());
        wholeSection.getChildren().add(bodySection);

        pane = new BorderPane(wholeSection);

        return pane;
    }

    private Node buildAccordion()
    {
        // Create an Accordion object
        Accordion accordion = new Accordion();

        // Create several TitledPane objects
        TitledPane pane1 = new TitledPane("Meal Type", new VBox());
        TitledPane pane2 = new TitledPane("Serving Size", new VBox());
        TitledPane pane3 = new TitledPane("Dietary Restrictions", new VBox());
        TitledPane pane4 = new TitledPane("Cooking Time", new VBox());

        // Add the TitledPane objects to the Accordion
        accordion.getPanes().addAll(pane1, pane2, pane3, pane4);

        // Create a VBox to hold the Accordion
        VBox accordionBox = new VBox(accordion);

        return accordionBox;
    }

    private Node buildFilterView()
    {
        // Create a TextField object
        TextField textField = new TextField("Filters Here");
        textField.setPrefHeight(400);

        // Create a VBox to hold the TextField
        VBox filtersBox = new VBox(textField);
        filtersBox.setPrefWidth(100);

        // Create a button to clear the filters
        Button clearFiltersButton = new Button("Clear Filters");
        clearFiltersButton.setPrefWidth(100);
        filtersBox.getChildren().add(clearFiltersButton);
        filtersBox.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);

        return filtersBox;
    }

    private Node buildDetailsView()
    {
        // Create a TextField object
        TextField textField = new TextField("Recipe Details Here");
        textField.setPrefHeight(200);

        // Create a VBox to hold the TextField
        VBox detailsBox = new VBox(textField);
        detailsBox.setPrefWidth(750);

        return detailsBox;
    }

    private Node buildTableView()
    {
        // Create the table and grab its selection model
		table = new TableView<String>();

		// Set up some helpful stuff including single selection mode
		table.setEditable(true);
		table.setPlaceholder(new Text("No Data!"));
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Add columns for title and image
		table.getColumns().add(buildRecipeColumn());
		table.getColumns().add(buildIngredientsColumn());
        table.getColumns().add(buildPortionColumn());
        table.getColumns().add(buildCaloriesColumn());
        table.getColumns().add(buildAllergensColumn());
        table.getColumns().add(buildSavedColumn());

		return table;
    }

    private TableColumn<String, String>	buildRecipeColumn()
	{
		TableColumn<String, String>	column = new TableColumn<String, String>("Recipe Name");

		column.setEditable(true);
		column.setPrefWidth(100);
		column.setCellValueFactory(new PropertyValueFactory<String, String>("Recipe Name"));
		return column;
	}

    private TableColumn<String, String>	buildIngredientsColumn()
	{
		TableColumn<String, String>	column = new TableColumn<String, String>("Ingredients Owned");

		column.setEditable(true);
		column.setPrefWidth(150);
		column.setCellValueFactory(new PropertyValueFactory<String, String>("Ingredients Owned"));

		return column;
	}

    private TableColumn<String, String>	buildPortionColumn()
	{
		TableColumn<String, String>	column = new TableColumn<String, String>("Portion Size");

		column.setEditable(true);
		column.setPrefWidth(100);
		column.setCellValueFactory(new PropertyValueFactory<String, String>("Portion Size"));

		return column;
	}

    private TableColumn<String, String>	buildCaloriesColumn()
	{
		TableColumn<String, String>	column = new TableColumn<String, String>("Calories\nPer Serving");

		column.setEditable(true);
		column.setPrefWidth(100);
		column.setCellValueFactory(new PropertyValueFactory<String, String>("Calories Per Serving"));

		return column;
	}

    private TableColumn<String, String>	buildAllergensColumn()
	{
		TableColumn<String, String>	column = new TableColumn<String, String>("Allergens");

		column.setEditable(true);
		column.setPrefWidth(150);
		column.setCellValueFactory(new PropertyValueFactory<String, String>("Allergens"));

		return column;
	}

    private TableColumn<String, String>	buildSavedColumn()
	{
		TableColumn<String, String>	column = new TableColumn<String, String>("Saved");

		column.setEditable(true);
		column.setPrefWidth(50);
		column.setCellValueFactory(new PropertyValueFactory<String, String>("Saved"));

		return column;
	}
    
}

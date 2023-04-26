package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.Recipe;

import java.util.*;

import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.beans.property.Property;
import javafx.beans.property.*;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.*;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import javax.swing.*;

public final class BrowsePage extends AbstractPane {

    // Layout
    //private ObservableList<Recipe> recipes;
	private TableView<Recipe> table;
	private SelectionModel<Recipe> smodel;
    private BorderPane pane;
    private static final Insets	PADDING = new Insets(40.0, 20.0, 40.0, 20.0);

    // Accordion Elements
    private TextField recipeSearch;

    private CheckBox ingredient1;
    private CheckBox ingredient2;
    private CheckBox ingredient3;

    private Spinner<Integer> servingSize;

    private CheckBox checkboxGluten;
    private CheckBox checkboxDairy;

    private Spinner<Integer> cookingTime;
    private ActionHandler actionHandler;

    private boolean	ignoreSelectionEvents;

    //===============================================================================================

    public BrowsePage(Controller controller) {
        super(controller, "Browse Recipes", "Add/Remove Ingredients");
        setBase(buildBrowsePane());
        //recipes = (ObservableList<Recipe>)controller.getProperty("recipes");
        actionHandler = new ActionHandler();
    }

    public void	initialize()
	{
		registerWidgetHandlers();
		updateFilter();
	}

    private void updateFilter()
	{
		ObservableList<Recipe> recipes = (ObservableList<Recipe>)controller.getProperty("recipes");
		Predicate<Recipe> predicate = new FilterPredicate();

		ignoreSelectionEvents = true;
		table.setItems(FXCollections.observableArrayList(new FilteredList<Recipe>(recipes, predicate)));
		ignoreSelectionEvents = false;
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

    private void registerWidgetHandlers()
    {


    }

    private void unregisterWidgetHandlers()
    {

    }

    private void registerPropertyListeners(ObservableList<Recipe> recipes)
    {

    }

    private void unregisterPropertyListeners(ObservableList<Recipe> recipes)
    {

    }

    private Node buildAccordion()
    {
        // Create an Accordion object
        Accordion accordion = new Accordion();

        // Create several TitledPane objects
        TitledPane pane1 = new TitledPane("Recipe", new VBox());
        Label label = new Label("Search Recipe:");
        recipeSearch = new TextField();
        VBox recipeSearchBox = new VBox(label, recipeSearch);
        pane1.setContent(recipeSearchBox);

        TitledPane pane2 = new TitledPane("Ingredients", new VBox());
        VBox ingredientsBox = (VBox) pane2.getContent();
        ingredient1 = new CheckBox("Chicken");
        ingredient2 = new CheckBox("Lettuce");
        ingredient3 = new CheckBox("Cheese");
        ingredientsBox.getChildren().addAll(ingredient1, ingredient2, ingredient3);

        TitledPane pane3 = new TitledPane("Serving Size", new VBox());
        VBox servingSizeBox = (VBox) pane3.getContent();
        SpinnerValueFactory<Integer> servingSizeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
        servingSizeValueFactory.setWrapAround(true);
        servingSize = new Spinner<>(1, 12, 1);
        servingSize.setValueFactory(servingSizeValueFactory);
        servingSize.setEditable(false);
        servingSize.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);
        servingSizeBox.getChildren().add(servingSize);

        TitledPane pane4 = new TitledPane("Dietary Restrictions", new VBox());
        VBox dietaryRestrictionsBox = (VBox) pane4.getContent();
        checkboxGluten = new CheckBox("Gluten Free");
        checkboxDairy = new CheckBox("Dairy Free");
        dietaryRestrictionsBox.getChildren().addAll(checkboxGluten, checkboxDairy);

        TitledPane pane5 = new TitledPane("Cooking Time", new VBox());
        VBox cookingTimeBox = (VBox) pane5.getContent();
        SpinnerValueFactory<Integer> cookingTimeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 0);
        cookingTimeValueFactory.setWrapAround(true);
        cookingTime = new Spinner<>(0, 120, 0);
        cookingTime.setValueFactory(cookingTimeValueFactory);
        cookingTime.setEditable(true);
        cookingTime.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);
        cookingTimeBox.getChildren().add(cookingTime);

        // Add the TitledPane objects to the Accordion
        accordion.getPanes().addAll(pane1, pane2, pane3, pane4, pane5);

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

    private TableView<Recipe> buildTableView()
    {
        // Create the table and grab its selection model
		table = new TableView<Recipe>();
        table.setPrefSize(700, 400);
        smodel = table.getSelectionModel();

		// Set up some helpful stuff including single selection mode
		table.setEditable(false);
		table.setPlaceholder(new Text("No Data!"));
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Add columns for title and image
		table.getColumns().add(buildRecipeColumn());
		table.getColumns().add(buildIngredientsColumn());
        table.getColumns().add(buildServingColumn());
        table.getColumns().add(buildAllergensColumn());
        table.getColumns().add(buildSavedColumn());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return table;
    }

    private TableColumn<Recipe, String>	buildRecipeColumn()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Recipe");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipeName"));
		return column;
	}

    private TableColumn<Recipe, ArrayList<String>>	buildIngredientsColumn()
	{
		TableColumn<Recipe, ArrayList<String>>	column = new TableColumn<Recipe, ArrayList<String>>("Ingredients");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, ArrayList<String>>("recipeIngredients"));
		return column;
	}

    private TableColumn<Recipe, Integer> buildServingColumn()
	{
		TableColumn<Recipe, Integer> column = new TableColumn<Recipe, Integer>("Serving Size");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("recipeServings"));
		return column;
	}

    private TableColumn<Recipe, Boolean> buildAllergensColumn()
	{
		TableColumn<Recipe, Boolean> column = new TableColumn<Recipe, Boolean>("Allergens");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Boolean>(""));
		return column;
	}

    private TableColumn<Recipe, Boolean> buildSavedColumn()
	{
		TableColumn<Recipe, Boolean> column = new TableColumn<Recipe, Boolean>("Saved");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Boolean>(""));
		return column;
	}

    
    /*
    private ArrayList<ArrayList<String>> divideRecipeColumns()
    {
        // Fruits - 0
        // Vegetables - 1
        // Protein/Alternatives - 2
        // Dairy/Alternatives - 3
        // Grains - 4
        // Fats/Oils/Spices - 5
        ArrayList<ArrayList<String>> grandList = new ArrayList<>();
        ArrayList<String> listRecipeNames = new ArrayList<>();
        ArrayList<String> listIngredients = new ArrayList<>();
        ArrayList<String> listAmountSizes = new ArrayList<>();
        ArrayList<String> listAmountTypes = new ArrayList<>();
        ArrayList<String> listGluten = new ArrayList<>();
        ArrayList<String> listDairy = new ArrayList<>();
        ArrayList<String> listServings = new ArrayList<>();
        ArrayList<String> listTimes = new ArrayList<>();
        ArrayList<String> listDirections = new ArrayList<>();

        // Populate a list for each separate column
        for (Recipe recipe : recipes){
            List<String> strings = recipe.getAllAttributesAsStrings();
            listRecipeNames.add(strings.get(0));
            listIngredients.add(strings.get(1));
            listAmountSizes.add(strings.get(2));
            listAmountTypes.add(strings.get(3));
            listGluten.add(strings.get(4));
            listDairy.add(strings.get(5));
            listServings.add(strings.get(6));
            listTimes.add(strings.get(7));
            listDirections.add(strings.get(8));
        }
        
        // grandList stores lists of each separate column
        grandList.add(listRecipeNames);
        grandList.add(listIngredients);
        grandList.add(listAmountSizes);
        grandList.add(listAmountTypes);
        grandList.add(listGluten);
        grandList.add(listDairy);
        grandList.add(listServings);
        grandList.add(listTimes);
        grandList.add(listDirections);
        
        return grandList;
    }
    */

    private final class ActionHandler
            implements EventHandler<ActionEvent>
    {
        public void	handle(ActionEvent e)
        {
            Object source = e.getSource();

        }
    }

    private final class FilterPredicate
		implements Predicate<Recipe>
	{
		public boolean	test(Recipe recipe)
		{
			String rn = recipeSearch.getText();
            if ((rn.length() > 0) && !recipe.getRecipeName().contains(rn))
				return false;

            if (ingredient1.isSelected() && !recipe.getIngredients().contains("Chicken"))
                return false;

            if (ingredient2.isSelected() && !recipe.getIngredients().contains("Lettuce"))
                return false;

            if (ingredient3.isSelected() && !recipe.getIngredients().contains("Cheese"))
                return false;

            if (recipe.getServings() < servingSize.getValue())
                return false;
            
            if (checkboxGluten.isSelected() && recipe.getGluten())
                return false;

            if (checkboxDairy.isSelected() && recipe.getDairy())
                return false;

            if (recipe.getTime() < cookingTime.getValue())
                return false;
            return true;
		}
	}
    
}



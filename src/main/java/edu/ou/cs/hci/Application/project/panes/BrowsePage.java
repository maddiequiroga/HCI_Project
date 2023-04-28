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

    private Spinner<Double> cookingTime;
    private ActionHandler actionHandler;

    private Label directions;

    private CheckBox saved;

    private boolean	ignoreSelectionEvents;

    //===============================================================================================

    public BrowsePage(Controller controller) {
        super(controller, "Browse Recipes", "Add/Remove Ingredients");
        setBase(buildBrowsePane());
        actionHandler = new ActionHandler();
    }

    public void	initialize()
	{
		registerWidgetHandlers();

		Recipe recipe = (Recipe)controller.getProperty("recipe");

		if (recipe == null)
			populateWidgetsWithDefaultValues();
		else
            populateWidgetsWithCurrentValues(recipe);

		if (recipe != null)
			registerPropertyListeners(recipe);

		updateFilter();

		smodel.select(recipe);
	}

    private void updateFilter()
	{
		ObservableList<Recipe> recipes = (ObservableList<Recipe>)controller.getProperty("recipes");
		Predicate<Recipe> predicate = new FilterPredicate();

		ignoreSelectionEvents = true;
		table.setItems(new FilteredList<Recipe>(recipes, predicate));
		ignoreSelectionEvents = false;
	}

    public void	updateProperty(String key, Object newValue, Object oldValue)
	{
		if ("recipe".equals(key))
		{
			Recipe	rold = (Recipe)oldValue;
			Recipe	rnew = (Recipe)newValue;

			if (rold != null)
				unregisterPropertyListeners(rold);

			if (rnew == null)
				populateWidgetsWithDefaultValues();
			else
				populateWidgetsWithCurrentValues(rnew);

			if (rnew != null)
				registerPropertyListeners(rnew);

			smodel.select(rnew);
		}
		else if ("recipes".equals(key))
		{
			Recipe	recipe = (Recipe)controller.getProperty("recipe");

			updateFilter();
		}
	}

    private void populateWidgetsWithDefaultValues()
	{
		directions.setText("(Select a recipe to view its directions!)");
	}

	private void populateWidgetsWithCurrentValues(Recipe recipe)
	{
		directions.setText(recipe.getDirections());
	}

    private Pane buildBrowsePane()
    {
        // Create a split pane to share space between the cover pane and table
        HBox topSection = new HBox();
        topSection.getChildren().add(buildTableView());

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
        smodel.selectedItemProperty().addListener(this::changeItem);
        recipeSearch.setOnAction(actionHandler);
        servingSize.valueProperty().addListener(this::changeInteger);
        checkboxDairy.selectedProperty().addListener(this::changeBoolean);
        checkboxGluten.selectedProperty().addListener(this::changeBoolean);
        cookingTime.valueProperty().addListener(this::changeDouble);
        saved.selectedProperty().addListener(this::changeBoolean);
    }

    private void unregisterWidgetHandlers()
    {
        smodel.selectedItemProperty().removeListener(this::changeItem);
        recipeSearch.setOnAction(null);
        servingSize.valueProperty().removeListener(this::changeInteger);
        checkboxDairy.selectedProperty().removeListener(this::changeBoolean);
        checkboxGluten.selectedProperty().removeListener(this::changeBoolean);
        cookingTime.valueProperty().removeListener(this::changeDouble);
        saved.selectedProperty().removeListener(this::changeBoolean);
    }

    private void registerPropertyListeners(Recipe recipe)
    {
        recipe.directionsProperty().addListener(this::handleChangeS);
    }

    private void unregisterPropertyListeners(Recipe recipe)
    {
        recipe.directionsProperty().removeListener(this::handleChangeS);
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

        TitledPane pane3 = new TitledPane("Serving Size", new VBox());
        VBox servingSizeBox = (VBox) pane3.getContent();
        SpinnerValueFactory<Integer> servingSizeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 50);
        servingSizeValueFactory.setWrapAround(true);
        servingSize = new Spinner<>(1, 50, 50);
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
        SpinnerValueFactory<Double> cookingTimeValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 12.0, 12.0, 0.25);
        cookingTimeValueFactory.setWrapAround(true);
        cookingTime = new Spinner<>(0.0, 12.0, 12.0);
        cookingTime.setValueFactory(cookingTimeValueFactory);
        cookingTime.setEditable(true);
        cookingTime.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);
        cookingTimeBox.getChildren().add(cookingTime);

        // Add the TitledPane objects to the Accordion
        accordion.getPanes().addAll(pane1, pane3, pane4, pane5);

        // Create a VBox to hold the Accordion
        VBox accordionBox = new VBox(accordion);

        return accordionBox;
    }

    private Node buildDetailsView()
    {
        // Create a TextField object
        directions = new Label();
        directions.setWrapText(true);

        saved = new CheckBox();

        // Create a VBox to hold the TextField
        VBox detailsBox = new VBox(directions);
        detailsBox.setPrefWidth(750);

        return detailsBox;
    }

    private TableView<Recipe> buildTableView()
    {
        // Create the table and grab its selection model
		table = new TableView<Recipe>();
        table.setPrefSize(800, 400);
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
        table.getColumns().add(buildTimeColumn());
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

    private TableColumn<Recipe, String>	buildIngredientsColumn()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Ingredients");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipeIngredients"));
		return column;
	}

    private TableColumn<Recipe, Integer> buildServingColumn()
	{
		TableColumn<Recipe, Integer> column = new TableColumn<Recipe, Integer>("Serving Size");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("servings"));
		return column;
	}

    private TableColumn<Recipe, Boolean> buildAllergensColumn()
	{
		TableColumn<Recipe, Boolean> column = new TableColumn<Recipe, Boolean>("Allergens");
        column.setCellFactory(x -> new TableCell<Recipe, Boolean>() {
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
            } else {
                Recipe object = table.getItems().get(getIndex());
                boolean dairyFree = object.getDairy();
                boolean glutenFree = object.getGluten();
                if (dairyFree && glutenFree) {
                    setText("Dairy Free, Gluten Free");
                } else if (dairyFree) {
                    setText("Dairy Free");
                } else if (glutenFree) {
                    setText("Gluten Free");
                } else {
                    setText("");
                }
            }
        }
});
		return column;
	}

    private TableColumn<Recipe, Double> buildTimeColumn()
    {
        TableColumn<Recipe, Double> column = new TableColumn<Recipe, Double>("Time (Hours)");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("time"));
		return column;
    }

    private TableColumn<Recipe, Boolean> buildSavedColumn()
    {
        TableColumn<Recipe, Boolean> column = new TableColumn<Recipe, Boolean>("Saved");
        column.setCellValueFactory(new PropertyValueFactory<Recipe, Boolean>("isSaved"));
        column.setCellFactory(new Callback<TableColumn<Recipe, Boolean>, TableCell<Recipe, Boolean>>() {
            @Override
            public TableCell<Recipe, Boolean> call(TableColumn<Recipe, Boolean> param) {
                return new SavedCell();
            }
        });
        return column;
    }

    private class SavedCell extends TableCell<Recipe, Boolean> {
        private CheckBox checkBox = new CheckBox();
        
        public SavedCell() {
            checkBox.setOnAction(e -> {
                int index = getIndex();
                Recipe recipe = table.getItems().get(index);
                if (recipe != null) {
                    recipe.setIsSaved(checkBox.isSelected());
                }
            });
        }
        
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty) {
                setGraphic(null);
            } else {
                int index = getIndex();
                Recipe recipe = table.getItems().get(index);
                if (recipe != null) {
                    checkBox.setSelected(recipe.getIsSaved());
                }
                setGraphic(checkBox);
            }
        }
    }

   private void changeItem(ObservableValue<? extends Recipe> observable, Recipe oldValue, Recipe newValue)
	{
		if (ignoreSelectionEvents)
			return;

		if (observable == smodel.selectedItemProperty())
			controller.setProperty("recipe", newValue);
	}

    
    private void changeInteger(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
	{
		if (observable == servingSize.valueProperty())
			updateFilter();
	}

    private void changeDouble(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
    {
        if (observable == cookingTime.valueProperty())
            updateFilter();
    }
    
    private void changeBoolean(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
    {
        if (newValue){
            updateFilter();
        }
    }

   private void	handleChangeS(ObservableValue<? extends String> observable,
								  String oldValue, String newValue)
	{
		Recipe selectedRecipe = (Recipe)controller.getProperty("recipe");

		if (observable == selectedRecipe.directionsProperty())
			directions.setText(newValue);
	}

    private final class ActionHandler
            implements EventHandler<ActionEvent>
    {
        public void	handle(ActionEvent e)
        {
            Object source = e.getSource();

            if (source == recipeSearch)
                updateFilter();
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
            
            if (recipe.getServings() > servingSize.getValue())
                return false;
            
            if (checkboxGluten.isSelected() && !recipe.getGluten()){
                return false;
            }

            if (checkboxDairy.isSelected() && !recipe.getDairy()){
                return false;
            }

            if (recipe.getTime() > cookingTime.getValue())
                return false;
            return true;
		}
	}
    
}



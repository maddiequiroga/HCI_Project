package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.resources.Ingredient;
import edu.ou.cs.hci.Application.project.Recipe;

//import java.lang.*;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
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
import java.util.ArrayList;

import javax.swing.*;


public final class ShoppingListPage extends AbstractPane
{
    // Private Class Members
    private ObservableList<Ingredient>  ingredients;
    private ObservableList<Recipe>      recipes;
    private SelectionModel<Recipe>      smodel;
    private Pane                        shoppingPane;
    private static final String	NAME = "Shopping List";
    private static final String	HINT = "Shopping List for ingredients";
    private List<List<String>>			data;
    private TableColumn<Recipe, String> shoppingColumn;
    private ArrayList<ObservableList<Ingredient>> ingredItems;
    private ArrayList<String>          recipeIngredients;
    private ArrayList<Double>          recipeAmountSize;
    private ArrayList<String>          recipeAmountType;
    // Layout
    private BorderPane					base;
    private TableView<Recipe>			table;

    // Constructors

    public ShoppingListPage(Controller controller)
    {
        super(controller, NAME, HINT);
        //ingredients = (ObservableList<Ingredient>)controller.getProperty("ingredients");
        setBase(buildPane());
    }

    public void	initialize()
	{
        recipeIngredients = new ArrayList<>();
        recipeAmountSize = new ArrayList<>();
        recipeAmountType = new ArrayList<>();
		Recipe recipe = (Recipe)controller.getProperty("recipe");
        updateFilter();
		smodel.select(recipe);
	}

    private void updateFilter()
	{
		ObservableList<Recipe> recipes = (ObservableList<Recipe>)controller.getProperty("recipes");
        //Recipe recipe = controller.getProperty(recipe);
      //  ingredients = (ObservableList<Ingredient>)controller.getProperty("ingredients");
		table.setItems(new FilteredList<Recipe>(recipes));
	}

    // Private Methods (Layout)
    private TableColumn<Recipe, Boolean> buildSavedColumn()
	{
		TableColumn<Recipe, Boolean> column = new TableColumn<Recipe, Boolean>("Saved");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Boolean>(""));
		return column;
	}
    private TableColumn<Recipe, String>	buildIngredientsColumn()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Ingredients");
        //for(Recipe recipe : recipes) {
        //    recipe = (Recipe)controller.getProperty("recipe");
        //    recipeIngredients = recipe.getRecipeIngredients();
            //need to add each ingredient here to the column...
        //}
     
        
        column.setCellValueFactory(feature -> feature.getValue().getRecipeIngredients1());
        //column.setCellValueFactory(feature -> feature.getValue().getRecipeIngredients2());
        //column.setCellValueFactory(feature -> feature.getValue().getRecipeIngredients3());
        //column.setCellValueFactory(feature -> feature.getValue().getRecipeIngredients4());
        //column.setCellValueFactory(feature -> feature.getValue().getRecipeIngredients5());
		//column.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipeIngredients"));
		return column;
	}

    private TableColumn<Recipe, String>	buildRecipeColumn()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Recipe");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipeName"));
		return column;
	}

    private TableColumn<Recipe, String> buildQuantColumn()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Amount needed");
		column.setCellValueFactory(feature -> feature.getValue().getRecipeAmount1());
        //column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("recipeAmountSize"));
		return column;
	}
    
    private TableColumn<Recipe, Double> buildDQuantColumn()
	{
		TableColumn<Recipe, Double>	column = new TableColumn<Recipe, Double>("Amount needed");
		column.setCellValueFactory(feature -> feature.getValue().getRecipeAmount0());
        //column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("recipeAmountSize"));
		return column;
	}
     
    private TableColumn<Recipe, String> buildQuant2Column()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Units");
		column.setCellValueFactory(feature -> feature.getValue().getAmount1());
        //column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("recipeAmountSize"));
		return column;
	}

    private TableColumn<Recipe, Double> buildBuyColumn()
	{
        TableColumn<Recipe, Double>	column = new TableColumn<Recipe, Double>("Amount to Buy");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("recipeAmountSize"));
		return column;
	}

    private TableColumn<Recipe, Double> buildOwnColumn()
	{
		TableColumn<Recipe, Double>	column = new TableColumn<Recipe, Double>("Amount Owned");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("recipeAmountSize"));
		return column;
	}
    

    private Pane buildPane()
    {
        // Create the table from the columns
        table = new TableView<>();
        //smodel = table.getSelectionModel();

        table.setEditable(false);
        table.setPlaceholder(new Text("No Data!"));
       // table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //table.getColumns().add(buildSavedColumn());
        table.getColumns().add(buildIngredientsColumn());
        table.getColumns().add(buildRecipeColumn());
        table.getColumns().add(buildDQuantColumn());
        table.getColumns().add(buildQuant2Column());
        //table.getColumns().add(buildBuyColumn());
        //table.getColumns().add(buildOwnColumn());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create a split pane to share space between the cover pane and table
        SplitPane	splitPane = new SplitPane();
        splitPane.getItems().add(table);
        base = new BorderPane(splitPane);

        return base;
    }
        

}
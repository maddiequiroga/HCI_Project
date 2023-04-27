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
		Recipe recipe = (Recipe)controller.getProperty("recipe");
        updateFilter();
		smodel.select(recipe);
	}

    private void updateFilter()
	{
		ObservableList<Recipe> recipes = (ObservableList<Recipe>)controller.getProperty("recipes");
        //Recipe recipe = controller.getProperty(recipe);
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
		column.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipeIngredients"));
		return column;
	}

    private TableColumn<Recipe, String>	buildRecipeColumn()
	{
		TableColumn<Recipe, String>	column = new TableColumn<Recipe, String>("Recipe");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipeName"));
		return column;
	}

    private TableColumn<Recipe, Double> buildQuantColumn()
	{
		TableColumn<Recipe, Double>	column = new TableColumn<Recipe, Double>("Amount needed");
		column.setCellValueFactory(new PropertyValueFactory<Recipe, Double>("recipeAmountSize"));
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
        table.getColumns().add(buildQuantColumn());
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
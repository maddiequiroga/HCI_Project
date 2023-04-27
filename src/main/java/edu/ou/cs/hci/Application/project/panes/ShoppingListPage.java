package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.resources.Ingredient;
import edu.ou.cs.hci.Application.project.Recipe;

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
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("unchecked")
public final class ShoppingListPage extends AbstractPane
{
    // Private Class Members
    private ObservableList<Ingredient>  ingredients;
    private ObservableList<Recipe>      recipes;
    private SelectionModel<Ingredient>  smodel;
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
        ingredients = (ObservableList<Ingredient>)controller.getProperty("ingredients");
        recipes = (ObservableList<Recipe>)controller.getProperty("recipes");
        setBase(buildPane());
    }

    // Public Methods (Controller), controller calls this method when it adds a view.

    public void initialize()
    {
        if (ingredients != null)
            registerPropertyListeners(ingredients);
        //if (recipes != null)
           // registerPropertyListeners(recipes);    
    }

    public void terminate()
    {
        if (ingredients != null)
            unregisterPropertyListeners(ingredients);
        //if (recipes != null)
           // unregisterPropertyListeners(recipes);   
    }

    public void update(String key, Object value)
    {
        if (ingredients == null)
                return;
        if (recipes == null)
                return;        
    }

    /*     
    public void updateProperty(String key, Object nexVal, Object prevVal)
    {
        if ("ingredients".equals(key))
        {
            ObservableList<Ingredient> prev = (ObservableList<Ingredient>) prevVal;
            ObservableList<Ingredient> nex = (ObservableList<Ingredient>) nexVal;

            if (prev != null)
                unregisterPropertyListeners(prev);

            if (nex != null)
                registerPropertyListeners(nex);
        }
        if ("recipe".equals(key))
        {
            ObservableList<Recipe> prev = (ObservableList<Recipe>) prevVal;
            ObservableList<Recipe> nex = (ObservableList<Recipe>) nexVal;

            if (prev != null)
                unregisterPropertyListeners(prev);

            if (nex != null)
                registerPropertyListeners(nex);
        }
    }
    */
    private void registerPropertyListeners(ObservableList<Ingredient> ingredients)
    {

    }

    private void unregisterPropertyListeners(ObservableList<Ingredient> ingredients)
    {

    }

    // Private Methods (Layout)

    private Pane buildPane()
    {

        //shoppingColumn = new TableColumn<>("Ingredient");
        
        //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //table.getColumns().add(shoppingColumn);

        //first column
        TableColumn<Recipe, String>	itemColumn =
                new TableColumn<Recipe, String>("Item");

        itemColumn.setEditable(false);
        itemColumn.setPrefWidth(150);
        itemColumn.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("item"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipe_name"));         
    //    itemColumn.setItems()

        // second column
        TableColumn<Recipe, String>	recipeColumn =
                new TableColumn<Recipe, String>("Recipe");

        recipeColumn.setEditable(false);
        recipeColumn.setPrefWidth(150);
        recipeColumn.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("recipe"));
        recipeColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipe_name"));         
        //recipeColumn.setItems()

        //third column
        TableColumn<Recipe, String>	quantColumn =
                new TableColumn<Recipe, String>("Quantity Needed");

        quantColumn.setEditable(false);
        quantColumn.setPrefWidth(150);
        quantColumn.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("Quantity Needed"));
        //quantColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipe_name"));                         
      

        //fourth column
        TableColumn<Recipe, String>	buyColumn =
                new TableColumn<Recipe, String>("Quantity Needed");

        buyColumn.setEditable(false);
        buyColumn.setPrefWidth(150);
        buyColumn.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("Quantity Needed"));
        //buyColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipe_name"));         
   

        //fifth column
        TableColumn<Recipe, String>	ownColumn =
                new TableColumn<Recipe, String>("Quantity owned");

        ownColumn.setEditable(false);
        ownColumn.setPrefWidth(150);
        ownColumn.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("Quantity owned"));
        //ownColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("recipe_name"));         


        // Create the table from the columns
        table = new TableView<>();

        //table = new TableView<Recipe>();
        //smodel = table.getSelectionModel();

        table.setEditable(false);
        table.setPlaceholder(new Text("No Data!"));
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        table.getColumns().add(itemColumn);
        table.getColumns().add(recipeColumn);
        table.getColumns().add(quantColumn);
        table.getColumns().add(buyColumn);
        table.getColumns().add(ownColumn);

        table.setItems(recipes);

        // Create a split pane to share space between the cover pane and table
        SplitPane	splitPane = new SplitPane();
        splitPane.getItems().add(table);
        base = new BorderPane(splitPane);

        return base;
    }


    public static final class Recipe
    {
        private final SimpleStringProperty	item;

        public Recipe(String item)
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

    private ArrayList<ObservableList<Recipe>> getIngredients()
    {
        ArrayList<ObservableList<Recipe>> ingredItems = new ArrayList<>();

        for (Recipe recipe : recipes) {
            ArrayList<String> ingredients_list = new ArrayList<>();
           // ingredients_list = recipe.getIngredients();
            for(int i =0; i < 5; i++) {
           //     ingredItems.add(ingredients_list[i]);
            }
         }
        return ingredItems;
    }
        

}
package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.resources.Ingredient;

import java.util.*;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
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

@SuppressWarnings("unchecked")
public class IngredientsPage extends AbstractPane {

    private ObservableList<Ingredient>  ingredients;
    private int                         selectFoodGroup;
    private SelectionModel<Ingredient>  smodel;
    private Pane                        ingredientsPane;
    private Button                      fruitButton;
    private Button                      vegButton;
    private Button                      proteinButton;
    private Button                      dairyButton;
    private Button                      grainsButton;
    private Button                      fatsButton;
    private TableView<Ingredient>       browser;
    private TableColumn<Ingredient, String> ingredientNameColumn;
    private ArrayList<ObservableList<Ingredient>> foodGroups;
    private ActionHandler               actionHandler;

    public IngredientsPage(Controller controller) {
        super(controller, "View Ingredients", "View Ingredients");

        selectFoodGroup = 1;
        ingredients = (ObservableList<Ingredient>)controller.getProperty("ingredients");
        actionHandler = new ActionHandler();

        setBase(buildPane());
    }

    public void initialize()
    {
        registerWidgetHandlers();

        if (ingredients != null)
            registerPropertyListeners(ingredients);
    }

    public void terminate()
    {
        if (ingredients != null)
            unregisterPropertyListeners(ingredients);

        unregisterWidgetHandlers();
    }

    public void update(String key, Object value)
    {

        if (ingredients == null)
                return;
    }

    public void updateProperty(String key, Object newValue, Object oldValue)
    {
        if ("ingredients".equals(key))
        {
            ObservableList<Ingredient> iold = (ObservableList<Ingredient>) oldValue;
            ObservableList<Ingredient> inew = (ObservableList<Ingredient>) newValue;

            if (iold != null)
                unregisterPropertyListeners(iold);

            if (inew != null)
                registerPropertyListeners(inew);
        }
    }

    private void registerWidgetHandlers()
    {


    }

    private void unregisterWidgetHandlers()
    {

    }

    private void registerPropertyListeners(ObservableList<Ingredient> ingredients)
    {

    }

    private void unregisterPropertyListeners(ObservableList<Ingredient> ingredients)
    {

    }

    private Pane buildPane() {

        ingredientsPane = new Pane();

        GridPane leftPane = new GridPane();
        leftPane.setAlignment(Pos.CENTER_LEFT);

        HBox leftPaneLayout = new HBox();
        leftPaneLayout.getChildren().addAll(buildFoodCategories(), buildBrowser());
        leftPane.getChildren().addAll(leftPaneLayout);

        GridPane rightPane = new GridPane();
        rightPane.setAlignment(Pos.CENTER_RIGHT);

        Button nextPage = new Button("Next");

        nextPage.setOnAction(event -> {
            changePage("BrowsePage");
        });

        ingredientsPane.getChildren().addAll(leftPane, rightPane, nextPage);

        return ingredientsPane;
    }

    private Pane buildFoodCategories() {

        VBox foodCats = new VBox();

        foodCats.setPadding(new Insets(50, 0, 0, 0));
        foodCats.setAlignment(Pos.CENTER_LEFT);
        fruitButton = new Button("Fruits");
        fruitButton.setOnAction(actionHandler);
        fruitButton.setMinWidth(180);
        fruitButton.setAlignment(Pos.CENTER_LEFT);

        vegButton = new Button("Vegetables");
        vegButton.setMinWidth(180);
        vegButton.setAlignment(Pos.CENTER_LEFT);
        vegButton.setOnAction(actionHandler);

        proteinButton = new Button("Protein & Alternatives");
        proteinButton.setMinWidth(180);
        proteinButton.setAlignment(Pos.CENTER_LEFT);
        proteinButton.setOnAction(actionHandler);

        dairyButton = new Button("Dairy & Alternatives");
        dairyButton.setMinWidth(180);
        dairyButton.setAlignment(Pos.CENTER_LEFT);
        dairyButton.setOnAction(actionHandler);

        grainsButton = new Button("Grains");
        grainsButton.setMinWidth(180);
        grainsButton.setAlignment(Pos.CENTER_LEFT);
        grainsButton.setOnAction(actionHandler);

        fatsButton = new Button("Fats/Oils/Spices");
        fatsButton.setMinWidth(180);
        fatsButton.setAlignment(Pos.CENTER_LEFT);
        fatsButton.setOnAction(actionHandler);

        foodCats.getChildren().addAll(fruitButton, vegButton, proteinButton,
                dairyButton, grainsButton, fatsButton);

        return foodCats;
    }
    private TableView<Ingredient> buildBrowser() {

        foodGroups = divideIngredients();

        browser = new TableView<>();
        browser.setPrefSize(500, 1000);

        smodel = browser.getSelectionModel();
        browser.setEditable(false);
        browser.setPlaceholder(new Text("No Data!"));
        browser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ingredientNameColumn = new TableColumn<>("Ingredient");
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("ingredientName"));
        browser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        browser.getColumns().add(ingredientNameColumn);
        browser.setItems(foodGroups.get(selectFoodGroup));

        return browser;
    }

    // Divides the ingredients into different categories.
    private ArrayList<ObservableList<Ingredient>> divideIngredients()
    {
        // Fruits - 0
        // Vegetables - 1
        // Protein/Alternatives - 2
        // Dairy/Alternatives - 3
        // Grains - 4
        // Fats/Oils/Spices - 5
        ArrayList<ObservableList<Ingredient>> foodGroups = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ArrayList<Ingredient> foodGroup = new ArrayList<Ingredient>();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getIngredientType() == i) {
                    foodGroup.add(ingredient);
                }
            }
            ObservableList<Ingredient> temp = FXCollections.observableArrayList(foodGroup);
            foodGroups.add(temp);
        }
        return foodGroups;
    }

    private final class ActionHandler
            implements EventHandler<ActionEvent>
    {
        public void	handle(ActionEvent e)
        {
            Object source = e.getSource();
            if (source == fruitButton)
                selectFoodGroup = 0;
            if (source == vegButton)
                selectFoodGroup = 1;
            if (source == proteinButton)
                selectFoodGroup = 2;
            if (source == dairyButton)
                selectFoodGroup = 3;
            if (source == grainsButton)
                selectFoodGroup = 4;
            if (source == fatsButton)
                selectFoodGroup = 5;
            System.out.println("Selected Food Group: " + selectFoodGroup);
            browser.setItems(foodGroups.get(selectFoodGroup));

        }
    }
}


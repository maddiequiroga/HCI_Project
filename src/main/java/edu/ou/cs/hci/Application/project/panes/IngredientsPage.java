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

@SuppressWarnings("unchecked")
public class IngredientsPage extends AbstractPane {

    private ObservableList<Ingredient> ingredients;
    private List<Ingredient> chosenIngredients;
    private List<Tab> foodCategories;
    private Pane ingredientsPane;

    public IngredientsPage(Controller controller) {
        super(controller, "View Ingredients", "View Ingredients");

        foodCategories = new ArrayList<Tab>();
        ingredients = (ObservableList<Ingredient>)controller.getProperty("ingredients");


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
        Button fruitButton = new Button("Fruits");
        fruitButton.setMinWidth(180);
        fruitButton.setAlignment(Pos.CENTER_LEFT);
        Button vegButton = new Button("Vegetables");
        vegButton.setMinWidth(180);
        vegButton.setAlignment(Pos.CENTER_LEFT);
        Button proteinButton = new Button("Protein & Alternatives");
        proteinButton.setMinWidth(180);
        proteinButton.setAlignment(Pos.CENTER_LEFT);
        Button dairyButton = new Button("Dairy & Alternatives");
        dairyButton.setMinWidth(180);
        dairyButton.setAlignment(Pos.CENTER_LEFT);
        Button grainsButton = new Button("Grains");
        grainsButton.setMinWidth(180);
        grainsButton.setAlignment(Pos.CENTER_LEFT);
        Button fatsButton = new Button("Fats/Oils/Spices");
        fatsButton.setMinWidth(180);
        fatsButton.setAlignment(Pos.CENTER_LEFT);

        foodCats.getChildren().addAll(fruitButton, vegButton, proteinButton,
                dairyButton, grainsButton, fatsButton);

        return foodCats;
    }
    public TableView<Ingredient> buildBrowser() {

        TableView<Ingredient> browser = new TableView<>();
        browser.setPrefSize(500, 1000);

        browser.setEditable(false);
        browser.setPlaceholder(new Text("No Data!"));
        browser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Ingredient, String> ingredientNameColumn = new TableColumn<>("Ingredient");
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("ingredientName"));
        browser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        browser.getColumns().add(ingredientNameColumn);

        for (Ingredient ingredient: ingredients)
            browser.getItems().add(ingredient);

        return browser;
    }
}


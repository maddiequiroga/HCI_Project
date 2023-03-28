package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.*;
import sun.security.krb5.internal.PAData;

public class IngredientsPage extends AbstractPane {

    private List<String> ingredients;
    private List<Ingredient> chosenIngredients;
    private List<Tab> foodCategories;
    private Pane ingredientsPane;

    public IngredientsPage(Controller controller) {
        super(controller, "View Ingredients", "View Ingredients");

        foodCategories = new ArrayList<Tab>();

        setBase(buildPane());
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

        ingredientsPane.getChildren().addAll(leftPane,rightPane);

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
    public Pane buildBrowser() {
        GridPane pane = new GridPane();
        TableView<Ingredient> browser = new TableView<>();
        browser.setPrefWidth(300);

        Ingredient test = new Ingredient("Lettuce", 10, 2, new Date(3/28/2023));
        browser.getItems().add(test);

        pane.getChildren().add(browser);
        return pane;
    }
}


class Ingredient {
    public String name;
    public int quantity_size;
    public int quantity_type;
    public Date expiration_date;

    public Ingredient(String name, int quantity_size, int quantity_type, Date expiration_date) {
        this.name = name;
        this.quantity_size = quantity_size;
        this.quantity_type = quantity_type;
        this.expiration_date = expiration_date;
    }
}
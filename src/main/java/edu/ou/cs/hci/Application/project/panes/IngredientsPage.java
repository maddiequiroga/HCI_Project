package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.resources.Ingredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.*;

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

        Button nextPage = new Button("Next");

        nextPage.setOnAction(event -> {
            changePage("BrowsePage");
        });

        ingredientsPane.getChildren().addAll(leftPane,rightPane, nextPage);

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

        pane.getChildren().add(browser);
        return pane;
    }

}

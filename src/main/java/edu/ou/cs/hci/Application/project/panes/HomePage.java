package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends AbstractPane {
    private Label welcomeLabel;
    private Button addIngredients;
    private Button browseRecipes;
    private Button viewSavedRecipes;
    private Button viewShoppingList;
    private Pane homePane;

    public HomePage(Controller controller) {
        super(controller, "Home Page", "Welcome to APP");
        setBase(buildPane());
    }

    private Pane buildPane(){
        homePane = new Pane();


        VBox homeVB = new VBox();
        homeVB.setAlignment(Pos.CENTER);
        homeVB.getChildren().addAll(buildLabel(), buildButtons());

        homePane.getChildren().addAll(homeVB);

        return homePane;
    }

    private Pane buildButtons(){
        VBox buttons = new VBox();
        buttons.setPadding(new Insets(50, 10, 10, 10));
        buttons.setAlignment(Pos.CENTER);

        addIngredients = new Button("Add Ingredients");
        addIngredients.setMinWidth(180);
        addIngredients.setAlignment(Pos.CENTER);

        browseRecipes = new Button("Browse Recipes");
        browseRecipes.setMinWidth(180);
        browseRecipes.setAlignment(Pos.CENTER);

        viewSavedRecipes = new Button("View Saved Recipes");
        viewSavedRecipes.setMinWidth(180);
        viewSavedRecipes.setAlignment(Pos.CENTER);

        viewShoppingList = new Button("View Shopping List");
        viewShoppingList.setMinWidth(180);
        viewShoppingList.setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(addIngredients, browseRecipes, viewSavedRecipes, viewShoppingList);

        return buttons;
    }

    private Pane buildLabel(){
        VBox label = new VBox();
        label.setPadding(new Insets(50, 0, 0, 0));
        label.setAlignment(Pos.CENTER);

        welcomeLabel = new Label("WELCOME TO USE YOUR FOOD!");
        welcomeLabel.setMinWidth(180);
        welcomeLabel.setAlignment(Pos.CENTER);

        label.getChildren().addAll(welcomeLabel);

        return label;

    }
}

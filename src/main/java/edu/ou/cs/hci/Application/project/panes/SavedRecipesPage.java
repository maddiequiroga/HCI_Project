package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.*;
import javafx.geometry.*;
//import javax.naming.ldap.Control;

public class SavedRecipesPage extends AbstractPane {

    private ListView<String> recipeList;
    private TableView<String> recipeTable;
    private TableColumn<String, String> ingredientCol;
    private TableColumn<String, String> caloriesCol;
    private TableColumn<String, String> amountCol;
    private TableColumn<String, String> portionCol;
    private TextArea instructionText;
    private Label titleLabel;
    private Label portionSize;
    private RadioButton fullPortion;
    private RadioButton halfPortion;

    public SavedRecipesPage(Controller controller) {
        super(controller, "Saved Recipes", "View Saved Recipes");
        setBase(buildPane());
    }

    private Pane buildPane() {
        buildWidgets();
        return buildLayout();
    }

    private void buildWidgets() {
        recipeList = new ListView<>();
        recipeList.getItems().addAll("Spaghetti", "Chicken Alfredo", "Lasagne");
        recipeTable = new TableView<>();
        ingredientCol = new TableColumn<>("Ingredient");
        caloriesCol = new TableColumn<>("Calories");
        amountCol = new TableColumn<>("Amount Needed");
        portionCol = new TableColumn<>("Portion Size");
        instructionText = new TextArea("Add recipe descriptions here");
        titleLabel = new Label("Saved Recipes");
        portionSize = new Label("Portion Size: ");
        halfPortion = new RadioButton("Half");
        fullPortion = new RadioButton("Full");
    }

    private Pane buildLayout() {
        ingredientCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new SimpleStringProperty(p.getValue());
            }
        });
        caloriesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new SimpleStringProperty(p.getValue());
            }
        });
        amountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new SimpleStringProperty(p.getValue());
            }
        });
        portionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new SimpleStringProperty(p.getValue());
            }
        });

        recipeTable.getColumns().addAll(ingredientCol, caloriesCol, amountCol, portionCol);

        // Create the main pane and set its size
        Pane pane = new Pane();
        pane.setPrefSize(800, 600);

        // Set title label
        titleLabel.setLayoutX(100);
        titleLabel.setLayoutY(10);

        // Set Portion label
        portionSize.setLayoutX(500);
        portionSize.setLayoutY(10);

        // Set half portion 
        halfPortion.setLayoutX(600);
        halfPortion.setLayoutY(10);

        // Set full portion
        fullPortion.setLayoutX(650);
        fullPortion.setLayoutY(10);

        // Set the position and size of recipe list
        recipeList.setPrefSize(250, 435);
        recipeList.setLayoutX(15);
        recipeList.setLayoutY(35);

        // Set the position and size of recipe table
        recipeTable.setPrefSize(590, 230);
        recipeTable.setLayoutX(300);
        recipeTable.setLayoutY(35);

        // Set the position and size of instruction text
        instructionText.setPrefSize(590, 200);
        instructionText.setLayoutX(300);
        instructionText.setLayoutY(270);

        // Set the position and size of the table columns
        ingredientCol.setPrefWidth(200);
        caloriesCol.setPrefWidth(110);
        amountCol.setPrefWidth(130);
        portionCol.setPrefWidth(150);

        // Add the columns to the table
        recipeTable.getColumns().addAll(ingredientCol, caloriesCol, amountCol, portionCol);

        // Add the widgets to the pane
        pane.getChildren().addAll(titleLabel, portionSize, halfPortion, fullPortion, recipeList, recipeTable, instructionText);

        // Set the background color of the pane
        pane.setStyle("-fx-background-color: #f0f0f0;");

        // Return the main pane
        return pane;
    }

}

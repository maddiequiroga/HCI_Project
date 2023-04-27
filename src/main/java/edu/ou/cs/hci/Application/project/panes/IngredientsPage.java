package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.resources.Ingredient;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.*;
import javafx.util.Callback;
import javafx.util.converter.*;

import javax.swing.*;

@SuppressWarnings("unchecked")
public class IngredientsPage extends AbstractPane {
    private ObservableList<Ingredient>  ingredients;
    private int                         selectFoodGroup;
    private SelectionModel<Ingredient>  smodelB;
    private SelectionModel<Ingredient>  smodelE;
    private Button                      fruitButton;
    private Button                      vegButton;
    private Button                      proteinButton;
    private Button                      dairyButton;
    private Button                      grainsButton;
    private Button                      fatsButton;
    private Button                      addIngredient;
    private Button                      removeIngredient;
    private TableView<Ingredient>       browser;
    private TableView<Ingredient>       editor;
    private DatePicker                  dp;

    private ArrayList<ObservableList<Ingredient>> foodGroups;
    private final ActionHandler               actionHandler;

    public IngredientsPage(Controller controller) {
        super(controller, "View Ingredients", "View Ingredients");

        ingredients = abstractIngredients;
        selectFoodGroup = 1;
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

        FlowPane ingredientsPane = new FlowPane();
        ingredientsPane.setPadding(new Insets(10, 10, 0, 10));
        addIngredient = new Button("Add");
        removeIngredient = new Button("Remove");
        Button nextPage = new Button("Next");

        nextPage.setOnAction(event -> {
            changePage("BrowsePage");
        });

        HBox ingBrowser = new HBox();
        ingBrowser.getChildren().addAll(buildFoodCategories(), buildBrowser());
        addIngredient.setOnAction(actionHandler);
        removeIngredient.setOnAction(actionHandler);

        dp = new DatePicker();
        dp.setMinWidth(180);

        HBox edBrowser = new HBox();
        edBrowser.getChildren().addAll(buildEditor(), dp);
        edBrowser.setAlignment(Pos.CENTER_RIGHT);

        HBox left = new HBox();
        left.setAlignment(Pos.CENTER_LEFT);
        left.setSpacing(20);
        left.getChildren().addAll(ingBrowser, addIngredient);

        HBox right = new HBox();
        right.setAlignment(Pos.CENTER_RIGHT);
        right.setSpacing(20);
        right.getChildren().addAll(removeIngredient, edBrowser);

        ingredientsPane.setHgap(100);
        ingredientsPane.getChildren().addAll(left, right);

        return ingredientsPane;
    }

    private Pane buildFoodCategories() {

        VBox foodCats = new VBox();

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
        browser.setPrefSize(625, 1000);

        smodelB = browser.getSelectionModel();
        browser.setEditable(false);
        browser.setPlaceholder(new Text("No Data!"));
        browser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        browser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        browser.getColumns().add(buildBrowserColumn());
        browser.setItems(foodGroups.get(selectFoodGroup));

        return browser;
    }

    private TableView<Ingredient> buildEditor()
    {
        editor = new TableView<Ingredient>();
        editor.setPrefHeight(1000);


        smodelE = editor.getSelectionModel();
        editor.setEditable(true);
        editor.setPlaceholder(new Text("Select ingredients in the browser!"));
        editor.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        editor.getColumns().addAll(buildNameCol(), buildSizeCol(), buildTypeCol(), buildDateCol(), buildCatCol());

        editor.setOnMouseClicked(event -> {
            Date d = smodelE.getSelectedItem().parseExpirationDate();
            dp.setValue(d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        });
        return editor;
    }

    private TableColumn<Ingredient, String> buildBrowserColumn()
    {
        TableColumn<Ingredient, String> tableColumn = new TableColumn<>("Ingredient Browser");
        tableColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("ingredientName"));
        tableColumn.setPrefWidth(125);
        return tableColumn;
    }

    private TableColumn<Ingredient, String> buildNameCol() {
        TableColumn<Ingredient, String> tableColumn = new TableColumn<>();

        tableColumn.setText("Ingredient");
        tableColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("ingredientName"));
        tableColumn.setPrefWidth(125);
        return tableColumn;
    }
    private TableColumn<Ingredient, Double> buildSizeCol() {
        TableColumn<Ingredient, Double> tableColumn = new TableColumn<>();
        tableColumn = new TableColumn<>("Quantity Size");
        tableColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Double>("quantitySize"));
        tableColumn.setPrefWidth(125);
        tableColumn.setCellFactory(new SizeCellFactory());

        return tableColumn;
    }
    private TableColumn<Ingredient, String> buildTypeCol() {
        TableColumn<Ingredient, String> tableColumn = new TableColumn<>();

        tableColumn = new TableColumn<>("Quantity Type");
        tableColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String >("quantityType"));
        tableColumn.setPrefWidth(125);
        tableColumn.setCellFactory(new TypeCellFactory());

        return tableColumn;
    }
    private TableColumn<Ingredient, Date> buildDateCol() {
        TableColumn<Ingredient, Date> tableColumn = new TableColumn<>();
        tableColumn = new TableColumn<>("Expiration Date");
        tableColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Date>("expirationDate"));
        tableColumn.setPrefWidth(125);

        return tableColumn;
    }
    private TableColumn<Ingredient, Integer> buildCatCol() {
        TableColumn<Ingredient, Integer> tableColumn = new TableColumn<>();

        tableColumn = new TableColumn<>("Food Category");
        tableColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("ingredientType"));
        tableColumn.setPrefWidth(125);

        return tableColumn;
    }

    // Divides the ingredients into different categories.
    private ArrayList<ObservableList<Ingredient>> divideIngredients()
    {

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

            if (source == addIngredient) {
                selectedIngredients.add(smodelB.getSelectedItem());
                editor.setItems(FXCollections.observableArrayList(selectedIngredients));
            }
            if (source == removeIngredient) {
                selectedIngredients.remove(smodelE.getSelectedItem());
                editor.setItems(FXCollections.observableArrayList(selectedIngredients));

            }
        }
    }

    private final class SizeCellFactory
            implements Callback<TableColumn<Ingredient, Double>,
            TableCell<Ingredient, Double>>
    {
        public TableCell<Ingredient, Double>	call(TableColumn<Ingredient, Double> v)
        {
            return new SizeCell();
        }
    }
    private final class TypeCellFactory
            implements Callback<TableColumn<Ingredient, String>,
            TableCell<Ingredient, String>>
    {
        public TableCell<Ingredient, String>	call(TableColumn<Ingredient, String> v)
        {
            return new TypeCell();
        }
    }
    private final class SizeCell
            extends TextFieldTableCell<Ingredient, Double>
    {
        public SizeCell()
        {
            super(new DoubleStringConverter());	// Since values are Strings
        }
        public void	updateItem(Double value, boolean isEmpty)
        {
            super.updateItem(value, isEmpty);		// Prepare for setup

            if (isEmpty || (value == null))		// Handle special cases
            {
                setText(null);
                return;
            }
            Double	size = value;

            setText(size.toString());
            setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
            setGraphic(null);
        }
    }

    private final class TypeCell
            extends TextFieldTableCell<Ingredient, String>
    {
        public TypeCell()
        {
            super(new DefaultStringConverter());	// Since values are Strings
        }
        public void	updateItem(String value, boolean isEmpty)
        {
            super.updateItem(value, isEmpty);		// Prepare for setup

            if (isEmpty || (value == null))		// Handle special cases
            {
                setText(null);
                return;
            }
            String	type = value;

            setText(type);
            setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
            setGraphic(null);
        }
    }
}


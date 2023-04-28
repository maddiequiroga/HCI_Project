package edu.ou.cs.hci.Application.project.panes;

import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.View;
import edu.ou.cs.hci.Application.project.Recipe;

import java.util.*;

import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.beans.property.Property;
import javafx.beans.property.*;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class HomePage extends AbstractPane {

    //Elements
    private FlowPane pane;
    private Button ingredientButton;
    private Button browseButton;
    private Button saveButton;
    private Button shopButton;
    private Label ing;
    private Label browse;
    private Label save;
    private Label shop;


    public HomePage(Controller controller) {
        super(controller, "Home Page", "Welcome to APP");
        setBase(buildPane());
    }

    private FlowPane	buildPane()
	{
		VBox		box = new VBox();

        ing = new Label("Ingredients Page: View what ingredients you own in categories of different food groups. ");
        browse = new Label("Browse Page: View recipes in the database and filter the recipes based on your dietary preference.");
        save = new Label("Saved Recipes: View recipes you have saved and details about them.");
        shop = new Label("Shopping List: View the ingredients and amounts needed for each recipe.");

		box.getChildren().add(ing);
		box.getChildren().add(browse);
		box.getChildren().add(save);
		box.getChildren().add(shop);
	
		for (Node node : box.getChildren())
			VBox.setMargin(node, new Insets(10));


        VBox	buttonBox = new VBox();

        ingredientButton = new Button("Ingredients Page");
        browseButton = new Button("Browse Page");
        saveButton = new Button("Save Page");
        shopButton = new Button("Shop Page");
		buttonBox.getChildren().add(ingredientButton);
		buttonBox.getChildren().add(browseButton);
		buttonBox.getChildren().add(saveButton);
        buttonBox.getChildren().add(shopButton);
	
		for (Node node : buttonBox.getChildren())
			VBox.setMargin(node, new Insets(10));    
	

		// Create a pane to layout the split pane and button pane
        FlowPane flow_pane = new FlowPane(20.0, 20.0, box);
    
		return flow_pane;
	}

}

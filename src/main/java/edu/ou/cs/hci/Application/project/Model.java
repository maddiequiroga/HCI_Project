package edu.ou.cs.hci.Application.project;


import java.io.File;
import java.net.MalformedURLException;
import java.util.*;

import edu.ou.cs.hci.Application.project.resources.Ingredient;
import edu.ou.cs.hci.Application.project.resources.Resources;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class Model
{
    //**********************************************************************
    // Private Members
    //**********************************************************************

    // Master of the program, manager of the data, mediator of all updates
    private final Controller				controller;

    // Easy, extensible way to store multiple simple, independent parameters
    private final HashMap<String, Object> properties;
    // Add an ObservableMap to store a set of observable objects.
    private final HashMap<String, Observable> observables;

    public Model(Controller controller) {
        this.controller = controller;
        properties = new HashMap<String, Object>();
        addProperties();

        observables = new HashMap<String, Observable>();
        addObservables();

    }

    private void addProperties()
    {
        properties.put("selectedRecipeIndex", -1);
        properties.put("recipe.name", "");
        properties.put("recipe.ingredients", "");
        properties.put("recipe.ingredient.amount", 0);
        properties.put("recipe.ingredient.amount.type", "");
        properties.put("recipe.gluten", false);
        properties.put("recipe.dairy", false);
        properties.put("recipe.servings", 0);
        properties.put("recipe.time", 0.0);
        properties.put("recipe.directions", "");

        properties.put("ingredient.name", "");
        properties.put("ingredient.quantity.size", 0.0);
        properties.put("ingredient.quantity.type", "");
        properties.put("ingredient.expiration", 0.0);
        properties.put("ingredient.type", 0);

    }
    private void	addObservables()
    {
        // For each observable, (1) create a parameter or data structure object;
        // (2) create a property for it; and (3) add the property to the map.
        // For some observables, also (4) add a listener to handle changes.

        // ********** Collection File **********
        // The collection CSV file displayed in the UI. Defaults to null. The
        // model loads the collection data from that file. If the file is null,
        // example data is loaded from a fixed resource path (see below).
        SimpleObjectProperty<File>	pfile = new SimpleObjectProperty<File>();

        observables.put("file", pfile);

        pfile.addListener(this::load);		// Listener to process changes


        // ********** Recipes List **********
        // List of rating strings. Loaded from a file at a fixed resource path.
        List<List<String>>				rd = Resources.getCSVData("data/recipes.csv");
        List<Recipe>                    ra = new ArrayList<Recipe>();

        for (List<String> item : rd)
            ra.add(new Recipe(item));

        ObservableList<Recipe>		rl = FXCollections.observableArrayList(ra);
        SimpleListProperty<Recipe>	rp = new SimpleListProperty<Recipe>(rl);

        observables.put("recipes", rp);

        List<List<String>>		    id = Resources.getCSVData("data/ingredients.csv");
        List<Ingredient>			ia = new ArrayList<Ingredient>();

        for (List<String> item : id)
            ia.add(new Ingredient(item));

        ObservableList<Ingredient>		il = FXCollections.observableArrayList(ia);
        SimpleListProperty<Ingredient>	ip = new SimpleListProperty<Ingredient>(il);

        observables.put("ingredients", ip);


        Recipe							r = null;//rl.get(0);
        SimpleObjectProperty<Recipe>	sp = new SimpleObjectProperty<Recipe>(r);

        observables.put("recipe", sp);
    }

    private void	load(ObservableValue<? extends File> observable,
                         File oldValue, File newValue)
    {
        try
        {
            List<List<String>>		rd;

            if (newValue == null)
                rd = Resources.getCSVData("data/recipes.csv");
            else
                rd = Resources.getCSVData(newValue.toURI().toURL());

            List<Recipe>			ra = new ArrayList<Recipe>();

            for (List<String> item : rd)
                ra.add(new Recipe(item));

            ObservableList<Recipe>	rl = FXCollections.observableArrayList(ra);

            setPropertyValue("recipe", null);
            setPropertyValue("recipes", rl);
            setPropertyValue("recipe", ((rl.size() > 0) ? rl.get(0) : null));


            List<List<String>>		id;

            if (newValue == null)
                id = Resources.getCSVData("data/ingredients.csv");
            else
                id = Resources.getCSVData(newValue.toURI().toURL());

            List<Ingredient>		    ia = new ArrayList<Ingredient>();

            for (List<String> item : id)
                ia.add(new Ingredient(item));

            ObservableList<Ingredient>	il = FXCollections.observableArrayList(ia);

            setPropertyValue("ingredient", null);
            setPropertyValue("ingredients", il);
            setPropertyValue("ingredient", ((il.size() > 0) ? il.get(0) : null));
        }
        catch (SecurityException ex)
        {
            System.err.println("***Error accessing file.***");
            return;
        }
        catch (MalformedURLException ex)
        {
            System.err.println("***Error converting file path to URL.***");
            return;
        }
        catch (Exception ex)
        {
            System.err.println("***Error loading data from file.***");
            return;
        }
    }

    // Saves the collection to a file, which becomes the new value of 'file'.
    // This is an easy but not very safe way to support opening/saving of files.
    @SuppressWarnings("unchecked")
    public void	save(File file)
    {
        List<Recipe>		recipes = (List<Recipe>)getPropertyValue("recipes");
        List<List<String>>	data = new ArrayList<List<String>>();

        for (Recipe recipe : recipes)
            data.add(recipe.getAllAttributesAsStrings());

        Resources.putCSVData(file, data);

        setValue("file", file);
    }
    //**********************************************************************
    // Public Methods (Controller)
    //**********************************************************************


    public Object	getPropertyValue(String key)
    {
        return ((Property)observables.get(key)).getValue();
    }
    public void	setPropertyValue(String key, Object newValue)
    {
        if (!observables.containsKey(key))
            return;

        Object	oldValue = ((Property)observables.get(key)).getValue();

        // Ignore when newValue == oldValue (including when both are null).
        if ((oldValue == null) ? (newValue == null) : oldValue.equals(newValue))
        {
            System.out.println("  model: property value not changed");
            return;
        }

        Platform.runLater(new PropertyUpdater(key, newValue, oldValue));
    }

    public Object	getValue(String key)
    {
        return properties.get(key);
    }

    public void	setValue(String key, Object value)
    {
        if (properties.containsKey(key) &&
                properties.get(key).equals(value))
        {
            System.out.println("  model: value not changed");
            return;
        }

        Platform.runLater(new Updater(key, value));
    }

    public void	trigger(String name)
    {
        System.out.println("  model: (not!) calculating function: " + name);
    }

    //**********************************************************************
    // Inner Classes (Updater)
    //**********************************************************************

    private class Updater
            implements Runnable
    {
        private final String	key;
        private final Object	value;

        public Updater(String key, Object value)
        {
            this.key = key;
            this.value = value;
        }

        public void	run()
        {
            properties.put(key, value);
            controller.update(key, value);
        }
    }

    private class PropertyUpdater
            implements Runnable
    {
        private final String	key;
        private final Object	newValue;
        private final Object	oldValue;

        public PropertyUpdater(String key, Object newValue, Object oldValue)
        {
            this.key = key;
            this.newValue = newValue;
            this.oldValue = oldValue;
        }

        // Suppress unchecked warnings. This is risky because View code can try
        // to assign a value of one type to a property of a different type.
        @SuppressWarnings("unchecked")
        public void	run()
        {
            ((Property)(observables.get(key))).setValue(newValue);
            controller.updateProperty(key, newValue, oldValue);
        }
    }
}


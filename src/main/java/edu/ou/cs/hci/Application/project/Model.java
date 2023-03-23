package edu.ou.cs.hci.Application.project;


import java.util.*;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;


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
        observables = new HashMap<String, Observable>();

    }
    //**********************************************************************
    // Public Methods (Controller)
    //**********************************************************************

    // This method is called whenever the value of the 'file' changes. From the
    // named file, it loads movie CSV data, creates Movie objects, puts them in
    // a list, and updates 'movies' and 'movie' so the first Movie is selected.

    public Object	getPropertyValue(String key)
    {
        return ((Property)observables.get(key)).getValue();
    }
    public void	setPropertyValue(String key, Object newValue)
    {
//        if (!observables.containsKey(key))
//            return;
//
//        Object	oldValue = ((Property)observables.get(key)).getValue();
//
//        // Ignore when newValue == oldValue (including when both are null).
//        if ((oldValue == null) ? (newValue == null) : oldValue.equals(newValue))
//        {
//            System.out.println("  model: property value not changed");
//            return;
//        }
//
//        Platform.runLater(new PropertyUpdater(key, newValue, oldValue));
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
}


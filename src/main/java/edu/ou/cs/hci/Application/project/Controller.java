package edu.ou.cs.hci.Application.project;

//import java.lang.*;
import java.io.File;
import java.util.ArrayList;


public final class Controller
{
    // Private Members
    private Model					model;

    // Where the data is shown; can be in multiple places.
    private final ArrayList<View>	views;

    // Constructors and Finalizer

    public Controller()
    {
        this.views = new ArrayList<View>();
    }
    // Public Methods (Model)

    public void	setModel(Model model)
    {
        this.model = model;
    }

    // Pass updates from the model
    public void	update(String key, Object value)
    {

        for (View v : views)
            v.update(key, value);
    }

    // Pass updates from the model
    public void	updateProperty(String key, Object newValue, Object oldValue)
    {

        for (View v : views)
            v.updateProperty(key, newValue, oldValue);
    }

    // Public Methods (Views)

    public void	addView(View view)
    {
        view.initialize();		// Couple view appearance to model state
        views.add(view);		// Make sure view receives all future updates
    }

    public void	removeView(View view)
    {
        views.remove(view);	// Make sure view receives no more updates
        view.terminate();		// Decouple view appearance from model state

        if (views.isEmpty())	// Nothing left to see here...
            System.exit(0);
    }

    // For views to access data values 
    public Object	get(String key)
    {

        return model.getValue(key);
    }

    // For views to modify data values 
    public void	set(String key, Object value)
    {
        System.out.println("controller: set " + key + " to " + value);

        model.setValue(key, value);
    }


    public void	trigger(String name)
    {
        System.out.println("controller: trigger " + name);
        model.trigger(name);
    }

    public Object	getProperty(String key)
    {

        return model.getPropertyValue(key);
    }

    public void	setProperty(String key, Object value)
    {
        System.out.println("controller: set property " + key + " to " + value);

        model.setPropertyValue(key, value);
    }

    public void	save(File file)
    {
        System.out.println("controller: saving to file " + file);

        model.save(file);
    }
}


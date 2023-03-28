package edu.ou.cs.hci.Application.project;

//import java.lang.*;
import java.io.File;
import java.util.*;
import java.net.URL;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import edu.ou.cs.hci.Application.project.panes.*;

//******************************************************************************

public class View
{
    //**********************************************************************
    // Private Class Members (Layout)
    //**********************************************************************

    private static final double	SCENE_W = 960;		// Scene width
    private static final double	SCENE_H = 540;		// Scene height

    //**********************************************************************
    // Private Members
    //**********************************************************************

    // Master of the program, manager of the data, mediator of all updates
    private final Controller				controller;
    private MenuItem					aboutItem;
    private MenuItem					quitItem;
    private MenuItem					newItem;
    private MenuItem					openItem;
    private MenuItem					closeItem;
    private MenuItem					saveItem;
    private MenuItem					printItem;
    private MenuItem					undoItem;
    private MenuItem					redoItem;
    private MenuItem					cutItem;
    private MenuItem					copyItem;
    private MenuItem					pasteItem;
    private static TabPane                     tabPane;

    // File data
    private File						file;
    // Handlers
    private final ActionHandler			actionHandler;
    private final WindowHandler			windowHandler;

    // Layout
    private final Stage					stage;
    private final ArrayList<AbstractPane>	panes;

    //**********************************************************************
    // Constructors and Finalizer
    //**********************************************************************

    // Construct a scene and display it in a window (stage).
    public View(Controller controller, String name, double x, double y)
    {
        this.controller = controller;

        // Create a listener for various widgets that emit ActionEvents
        actionHandler = new ActionHandler();

        // Create a listener to handle WINDOW_CLOSE_REQUESTs
        windowHandler = new WindowHandler();

        // Create a set of panes to include
        panes = new ArrayList<AbstractPane>();

        // Construct the pane for the scene.
        Pane	view = buildView();

        // Create a scene with an initial size, and attach a style sheet to it
        Scene		scene = new Scene(view, SCENE_W, SCENE_H);
        URL		url = View.class.getResource("View.css");
        String		surl = url.toExternalForm();

        scene.getStylesheets().add(surl);

        // Create a window/stage with a name and an initial position on screen
        stage = new Stage();

        stage.setOnHiding(windowHandler);
        stage.setScene(scene);
        stage.setTitle(name);
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    //**********************************************************************
    // Public Methods (Controller)
    //**********************************************************************

    // The controller calls this method when it adds a view.
    // Set up the nodes in the view with data accessed through the controller.
    public void	initialize()
    {
        for (AbstractPane pane : panes)
            pane.initialize();
    }

    // The controller calls this method when it removes a view.
    // Unregister event and property listeners for the nodes in the view.
    public void	terminate()
    {
        for (AbstractPane pane : panes)
            pane.terminate();

    }

    // The controller calls this method whenever something changes in the model.
    // Update the nodes in the view to reflect the change.
    public void	update(String key, Object value)
    {
        for (AbstractPane pane : panes)
            pane.update(key, value);

        // Update your menus as needed when any old model properties change...
    }

    // The controller calls this method whenever something changes in the model.
    // Update the nodes in the view to reflect the change.
    public void	updateProperty(String key, Object newValue, Object oldValue)
    {
        for (AbstractPane pane : panes)
            pane.updateProperty(key, newValue, oldValue);

        // Update your menus as needed when any new model observables change...
    }

    public static TabPane getTabPane() {
        return tabPane;
    }
    //**********************************************************************
    // Private Methods (Layout)
    //**********************************************************************

    private Pane	buildView()
    {

        panes.add(new HomePage(controller));
        panes.add(new IngredientsPage(controller));
        panes.add(new BrowsePage(controller));
        panes.add(new SavedRecipesPage(controller));
        panes.add(new ShoppingListPage(controller));

        // Create a tab pane with tabs for the set of included panes
        tabPane = new TabPane();

        for (AbstractPane pane : panes) {
            tabPane.getTabs().add(pane.createTab());
        }


        return new BorderPane(tabPane, null, null, null, null);
    }

    private final class ActionHandler
            implements EventHandler<ActionEvent>
    {
        public void	handle(ActionEvent e)
        {
            Object	source = e.getSource();

        }
    }

    private final class WindowHandler
            implements EventHandler<WindowEvent>
    {
        public void	handle(WindowEvent e)
        {
            if (e.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST)
                controller.removeView(View.this);
        }
    }
}
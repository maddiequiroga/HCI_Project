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
  
    // Private Class Members (Layout)
 
    private static final double	SCENE_W = 960;		// Scene width
    private static final double	SCENE_H = 540;		// Scene height

   
    // Private Members
    
    private final Controller				controller;
    private static TabPane                     tabPane;

    // File data
    private File						file;
    // Handlers
    private final ActionHandler			actionHandler;
    private final WindowHandler			windowHandler;

    // Layout
    private final Stage					stage;
    private final ArrayList<AbstractPane>	panes;

    // Constructors and Finalizer

    // Construct a scene and display
    public View(Controller controller, String name, double x, double y)
    {
        this.controller = controller;

        // Create a listener 
        actionHandler = new ActionHandler();

        windowHandler = new WindowHandler();

        // Create a set of panes
        panes = new ArrayList<AbstractPane>();

        // Construct the pane 
        Pane	view = buildView();

        // Create a scene
        Scene		scene = new Scene(view, SCENE_W, SCENE_H + 32);
        URL		url = View.class.getResource("View.css");
        String		surl = url.toExternalForm();

        scene.getStylesheets().add(surl);

        // Create a window/stage
        stage = new Stage();

        stage.setOnHiding(windowHandler);
        stage.setScene(scene);
        stage.setTitle(name);
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }


    // Public Methods (Controller)

    public void	initialize()
    {
        for (AbstractPane pane : panes)
            pane.initialize();
    }

    
    public void	terminate()
    {
        for (AbstractPane pane : panes)
            pane.terminate();

    }

    public void	update(String key, Object value)
    {
        for (AbstractPane pane : panes)
            pane.update(key, value);

    
    }


    public void	updateProperty(String key, Object newValue, Object oldValue)
    {
        for (AbstractPane pane : panes)
            pane.updateProperty(key, newValue, oldValue);

        
    }

    public static TabPane getTabPane() {
        return tabPane;
    }

    // Private Methods (Layout)
  

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

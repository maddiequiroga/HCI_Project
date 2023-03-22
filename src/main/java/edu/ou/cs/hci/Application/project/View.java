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

/**
 * The <CODE>View</CODE> class.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class View
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

    // TODO #2a: Add members for your menus/items here...
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

        //Initialize your menus/items here...
        aboutItem.setOnAction(actionHandler);
        quitItem.setOnAction(actionHandler);

        newItem.setOnAction(actionHandler);
        openItem.setOnAction(actionHandler);
        closeItem.setOnAction(actionHandler);
        saveItem.setOnAction(actionHandler);
        printItem.setOnAction(actionHandler);

        undoItem.setOnAction(actionHandler);
        redoItem.setOnAction(actionHandler);
        cutItem.setOnAction(actionHandler);
        copyItem.setOnAction(actionHandler);
        pasteItem.setOnAction(actionHandler);

        openItem.setOnAction(actionHandler);
        saveItem.setOnAction(actionHandler);

    }

    // The controller calls this method when it removes a view.
    // Unregister event and property listeners for the nodes in the view.
    public void	terminate()
    {
        for (AbstractPane pane : panes)
            pane.terminate();

        // Terminate your menus/items here...
        aboutItem.setOnAction(null);
        quitItem.setOnAction(null);

        newItem.setOnAction(null);
        openItem.setOnAction(null);
        closeItem.setOnAction(null);
        saveItem.setOnAction(null);
        printItem.setOnAction(null);

        undoItem.setOnAction(null);
        redoItem.setOnAction(null);
        cutItem.setOnAction(null);
        copyItem.setOnAction(null);
        pasteItem.setOnAction(null);

        openItem.setOnAction(null);
        saveItem.setOnAction(null);
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

    //**********************************************************************
    // Private Methods (Layout)
    //**********************************************************************

    private Pane	buildView()
    {
        // OPTIONAL: Swap the comments for the pairs of panes below if you'd
        // like to see a reference solution to Prototype B. Swap them back to
        // work on the TODOs for Prototype C.

        //panes.add(new CollectionPaneB(controller));
        //panes.add(new EditorPaneB(controller));

        panes.add(new BrowsePage(controller));
        panes.add(new HomePage(controller));
        panes.add(new IngredientsPage(controller));
        panes.add(new SavedRecipesPage(controller));
        panes.add(new ShoppingListPage(controller));

        // Create a tab pane with tabs for the set of included panes
        TabPane	tabPane = new TabPane();

        for (AbstractPane pane : panes)
            tabPane.getTabs().add(pane.createTab());

        MenuBar	menuBar = buildMenuBar();

        return new BorderPane(tabPane, menuBar, null, null, null);
    }

    //**********************************************************************
    // Inner Classes (Menus)
    //**********************************************************************

    private MenuBar	buildMenuBar()
    {
        MenuBar	menuBar = new MenuBar();

        // TODO #2b: Build your Menus and MenuBar below. For any MenuItems you
        // use, add members and code to initialize(), terminate(), update(), and
        // updateProperty() above, as needed. The following example code creates
        // a menu with one item in it, with examples of graphics for decoration.

        // Create some simple Nodes used as examples of graphics in menus/items.
        Rectangle	decoration1 = new Rectangle(8.0, 8.0, Color.RED);
        Circle		decoration2 = new Circle(4.0, Color.BLUE);

        // Create MenuItems...
        aboutItem = new MenuItem("About", decoration1);
        quitItem = new MenuItem("Quit", decoration1);
        newItem = new MenuItem("New", decoration1);
        openItem = new MenuItem("Open", decoration1);
        closeItem = new MenuItem("Close", decoration1);
        saveItem = new MenuItem("Save", decoration1);
        printItem = new MenuItem("Print", decoration1);
        undoItem = new MenuItem("Undo", decoration1);
        redoItem = new MenuItem("Redo", decoration1);
        cutItem = new MenuItem("Cut", decoration1);
        copyItem = new MenuItem("Copy", decoration1);
        pasteItem = new MenuItem("Paste", decoration1);

        // ...create Menus to hold them...
        Menu	movieMenu = new Menu("Movie", decoration2);
        Menu	fileMenu = new Menu("File", decoration2);
        Menu	editMenu = new Menu("Edit", decoration2);
        Menu	windowMenu = new Menu("Window", decoration2);


        // ...add the MenuItems to their menus...
        movieMenu.getItems().addAll(aboutItem);
        movieMenu.getItems().addAll(quitItem);

        fileMenu.getItems().addAll(newItem);
        fileMenu.getItems().addAll(openItem);
        fileMenu.getItems().addAll(closeItem);
        fileMenu.getItems().addAll(saveItem);
        fileMenu.getItems().addAll(printItem);

        editMenu.getItems().addAll(undoItem);
        editMenu.getItems().addAll(redoItem);
        editMenu.getItems().addAll(cutItem);
        editMenu.getItems().addAll(copyItem);
        editMenu.getItems().addAll(pasteItem);

        // ...then add the Menus to the MenuBar.
        menuBar.getMenus().addAll(movieMenu);
        menuBar.getMenus().addAll(fileMenu);
        menuBar.getMenus().addAll(editMenu);
        menuBar.getMenus().addAll(windowMenu);

        return menuBar;
    }

    // TODO #3a: Implement the File/Open menu item handler, allowing the user
    // to select a CSV file to open. See the javafx.stage.FileChooser class.
    // Pass the chosen file to the Model via Controller.setProperty() to open.
    private void	handleFileOpenMenuItem()
    {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(stage);

        controller.setProperty("file", file);
    }

    // TODO #3b: Implement the File/Save menu item handler, allowing the user
    // to select a CSV file to save. See the javafx.stage.FileChooser class.
    // Pass the chosen file to the Model via Controller.save() to save.
//    private void	handleFileSaveMenuItem()
//    {
//        controller.save(file);
//    }

    //**********************************************************************
    // Inner Classes (Event Handling)
    //**********************************************************************

    // TODO #2c: Add code to process user selection of each of your MenuItems.
    // Call your handleFileOpenMenuItem() and handleFileSaveMenuItem() methods
    // above for those two menu items. For all other menu items, print a brief
    // but informative message to the console.
    private final class ActionHandler
            implements EventHandler<ActionEvent>
    {
        public void	handle(ActionEvent e)
        {
            Object	source = e.getSource();

            if (source == aboutItem)
                System.out.println("The about option displays information pertinent to the app and movie");
            if (source == quitItem)
                System.out.println("The quit option closes the app");
            if (source == newItem)
                System.out.println("The new option creates a blank file for information to be created");
            if (source == closeItem)
                System.out.println("The close option closes the file currently opened");
            if (source == printItem)
                System.out.println("Print's the movie information");
            if (source == undoItem)
                System.out.println("Undo action");
            if (source == redoItem)
                System.out.println("Redo action");
            if (source == cutItem)
                System.out.println("Cut's selected values");
            if (source == copyItem)
                System.out.println("Copy's selected values");
            if (source == pasteItem)
                System.out.println("Paste values from clipboard");
            if (source == openItem)
                handleFileOpenMenuItem();
//            if (source == saveItem)
//                handleFileSaveMenuItem();
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
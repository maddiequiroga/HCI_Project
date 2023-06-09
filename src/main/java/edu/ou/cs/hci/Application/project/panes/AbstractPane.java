package edu.ou.cs.hci.Application.project.panes;

//import java.lang.*;
import java.util.*;

import edu.ou.cs.hci.Application.project.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import edu.ou.cs.hci.Application.project.Controller;
import edu.ou.cs.hci.Application.project.resources.*;
import edu.ou.cs.hci.Application.project.View;


@SuppressWarnings("unchecked")
public abstract class AbstractPane
{
 
    // Public Class Members
   
    public static final String[] CATEGORIES = {
            "Fruits", "Vegetables", "Protein/Alternatives",
            "Dairy/Alternatives", "Grains", "Fats/Oils/Spices"
    };
    public static final String	RSRC		= "edu/ou/cs/hci/resources/";

    public static final String	SWING_ICON	= RSRC + "example/swing/icon/";
    public static final String	FX_ICON	= RSRC + "example/fx/icon/";
    public static final String	FX_TEXT	= "example/fx/text/";

  
    // Private Members

    protected final Controller	controller;
    protected final String		name;
    protected final String		hint;
    protected ObservableList<Recipe> abstractRecipes;
    protected ObservableList<Ingredient> abstractIngredients;
    protected ArrayList<Ingredient>       selectedIngredients;

    // Provided when the subclass constructor calls setBase()
    protected Node				base;


    // Constructors and Finalizer
   

    protected AbstractPane(Controller controller, String name, String hint)
    {
        this.controller = controller;
        this.name = name;
        this.hint = hint;

        // Makes it so that you can call these ingredients in any pane without having to refer to the controller.
        abstractRecipes = (ObservableList<Recipe>)controller.getProperty("recipes");
        abstractIngredients = (ObservableList<Ingredient>)controller.getProperty("ingredients");
        selectedIngredients = new ArrayList<>();
    }

    // Getters and Setters
   

    public String	getName()
    {
        return name;
    }

    public String	getHint()
    {
        return hint;
    }

    public Node	    getBase()
    {
        return base;
    }

    // Called by the subclass constructor to provide its scene subgraph.
    protected void	setBase(Node base)
    {
        this.base = base;
    }


    // Public Methods
  

    // Convenience method to put the base node into a tab. Warning: Don't
    // attempt to layout both the base node and tabs containing it! (Each
    // node can be included only once in a scene graph.)
    public Tab	createTab()
    {
        return createFixedTab(base, name, hint);
    }

 
    // Public Methods (Controller)
    

   
    public void	initialize()
    {
    }

    
    public void	terminate()
    {
    }

    
    public void	update(String key, Object value)
    {
    }
   
    public void	updateProperty(String key, Object newValue, Object oldValue)
    {
    }


    // Public Class Methods (Resources)
   

    // WARNING: May not be a long-term solution
    // Works as intended now, but will need to monitor once we start adding listeners
    // For example of implementation, go to IngredientsPage:54
    // 0 - HomePage | 1 - IngredientsPage | 2 - BrowsePage | 3 - SavedRecipesPages | 4 - ShoppingListPage
    public void changePage(String pageName) {

        System.out.println("Changed Tab to:");
        SelectionModel<Tab> sm = View.getTabPane().getSelectionModel();
        if (pageName.equals("HomePage")) {
            sm.select(0);
        }
        else if (pageName.equals("IngredientsPage")) {
            sm.select(1);
        }
        else if (pageName.equals("BrowsePage")) {
            sm.select(2);
        }
        else if (pageName.equals("SavedRecipesPage")) {
            sm.select(3);
        }
        else if (pageName.equals("ShoppingListPage")) {
            sm.select(4);
        }
        System.out.println(sm.getSelectedItem().getText());
    }

    // Convenience method to create a node for an image located in resources
    public static ImageView	createSwingIcon(String url, int size)
    {
        Image	image = new Image(SWING_ICON + url, size, size, false, false);

        return new ImageView(image);
    }

    // Convenience method to create a node for an image located in resources
    public static ImageView	createFXIcon(String url, double w, double h)
    {
        Image	image = new Image(FX_ICON + url, w, h, false, true);

        return new ImageView(image);
    }

    // Convenience method to slurp a CSV formatted file located in resources
    public static List<List<String>>	loadFXData(String url)
    {
        return Resources.getCSVData(FX_TEXT + url);
    }


    // Public Class Methods (Layout)

    // Convenience method to create an unclosable tab with a tooltip.
    public static Tab	createFixedTab(Node node, String title, String text)
    {
        Tab	tab = new Tab(title, node);

        tab.setClosable(false);
        tab.setTooltip(new Tooltip(text));

        return tab;
    }

    // Convenience method to lookup nodes inside others in the scene graph.
    public static Node	getDescendant(Node node, int... index)
    {
        for (int i=0; i<index.length; i++)
        {
            if (!(node instanceof Parent))
                throw new IllegalArgumentException("Ancestor is not a Parent");

            node = ((Parent)node).getChildrenUnmodifiable().get(index[i]);
        }

        return node;
    }


    public static Pane	createTitledPane(Region region, String title)
    {
        StackPane	stackPane = new StackPane(region);

        stackPane.setId("gallery-pane");

        TitledPane	titledPane = new TitledPane(title, stackPane);

        titledPane.setCollapsible(false);	// Usually true, in Accordians

        return new StackPane(titledPane);
    }

    public static void	setBackgroundColor(Region region, Color color)
    {
        BackgroundFill	bf = new BackgroundFill(color, null, null);
        Background		bg = new Background(bf);

        region.setBackground(bg);
    }
}

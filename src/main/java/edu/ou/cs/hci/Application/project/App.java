package edu.ou.cs.hci.Application.project;

//import java.lang.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class App extends Application
{
    //**********************************************************************
    // Main
    //**********************************************************************

    public static void	main(String[] args)
    {
        javafx.application.Application.launch(args);
    }

    //**********************************************************************
    // Private Members
    //**********************************************************************

    // Master of the program, manager of the data, mediator of all updates
    private Controller			controller;

    // Where the data lives; only one place.
    private Model				model;

    //**********************************************************************
    // Override Methods (Application)
    //**********************************************************************

    public void	init()
    {
        controller = new Controller();
        model = new Model(controller);

        controller.setModel(model);
    }

    public void	start(Stage stage)
    {
        Text		text = new Text("Eat Your Food");
        StackPane	root = new StackPane();

        root.getChildren().add(text);

        Scene		scene = new Scene(root, 400, 80);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        // Animation: scale text to fill stage over 0.50 seconds
        Duration			sd = new Duration(500);
        ScaleTransition	st = new ScaleTransition(sd, text);

        st.setFromX(0.01);
        st.setFromY(0.01);
        st.setToX(5.0);
        st.setToY(5.0);

        st.play();

        // Animation: Hide stage after 0.75 seconds
        Timeline	timeline = new Timeline();
        Duration	duration = new Duration(750);
        KeyFrame	endframe = new KeyFrame(duration, new EndSplash(stage));

        timeline.getKeyFrames().add(endframe);
        timeline.play();
    }

    public void	stop()
            throws Exception
    {
    }

    //**********************************************************************
    // Override Methods (EventHandler<ActionEvent>)
    //**********************************************************************

    public final class EndSplash
            implements EventHandler<ActionEvent>
    {
        private final Stage	stage;

        public EndSplash(Stage stage)
        {
            this.stage = stage;
        }

        public void	handle(ActionEvent e)
        {
            View		view1 = new View(controller, "Eat Your Food",  40,  40);

            controller.addView(view1);

            stage.hide();
        }
    }
}


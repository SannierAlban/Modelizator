package fr.m3105.projetmode.Views;

import fr.m3105.projetmode.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class ViewWindow extends Application{

	private int height = 800;
	private int width = 600;
	private Model model;
	
	public ViewWindow(int height, int width, Model model) {
		this.height = height;
		this.width = width;
		this.model = model;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.out.println(model.toString());
		/*
		HBox root = new HBox();
        root.getChildren().addAll();
        // Set Spacing of the HBox
        root.setSpacing(10);
 
        // Set the Style
        root.setStyle
        (
            "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: blue;"
        );
 
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        primaryStage.setScene(scene);
        // Set the Title of the Stage
        primaryStage.setTitle("J'ai tant envie de dormir");
        // Display the Stage
        primaryStage.show();
        */
	}
}
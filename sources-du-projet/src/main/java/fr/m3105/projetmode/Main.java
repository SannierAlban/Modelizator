package fr.m3105.projetmode;

import fr.m3105.projetmode.utils.Model;
import fr.m3105.projetmode.utils.ViewWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	ViewWindow vw = new ViewWindow(800, 600, new Model());
    	try {
			vw.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
    	
        Application.launch();
    }
}

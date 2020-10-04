package fr.m3105.projetmode;

import fr.m3105.projetmode.utils.Model;
import fr.m3105.projetmode.utils.ViewWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
<<<<<<< HEAD
    public void start(Stage stage) {
    	ViewWindow vw = new ViewWindow(800, 600, new Model());
    	try {
			vw.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

=======
    public void start(Stage PrimaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        PrimaryStage.setTitle("Main frame");
        PrimaryStage.setScene(new Scene(root));
        PrimaryStage.show();
    }
>>>>>>> branch 'master' of https://gitlab.univ-lille.fr/francois.deroubaix.etu/projetmode-i6
    public static void main(String[] args) {
<<<<<<< HEAD
    	
        Application.launch();
=======
        launch(args);
>>>>>>> branch 'master' of https://gitlab.univ-lille.fr/francois.deroubaix.etu/projetmode-i6
    }
}

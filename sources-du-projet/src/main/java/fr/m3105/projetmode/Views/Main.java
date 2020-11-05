package fr.m3105.projetmode.Views;

import com.sun.prism.paint.Color;

import fr.m3105.projetmode.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    //TODO: changer le main en stage3d et mettre le openfile en Application principale
//    @Override
//    public void start(Stage PrimaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//        /*Model m = new Model();
//        ViewWindow vw = new ViewWindow(100, 100,m);
//        vw.start(PrimaryStage);
//         */
//        PrimaryStage.setTitle("Main frame");
//        PrimaryStage.setScene(new Scene(root));
//        PrimaryStage.show();
//    }
	
    @Override
    public void start(Stage stage) {
    	
    	Model m = new Model();
    	
        ThreeDView threeDView = new ThreeDView(m);
        
        Scene scene = new Scene(new StackPane(threeDView.getCanvas()), 640, 480);
        stage.setScene(scene);
        stage.show();
        
        threeDView.backgroundFill();
        
        threeDView.drawModel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

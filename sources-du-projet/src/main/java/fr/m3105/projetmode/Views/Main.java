package fr.m3105.projetmode.Views;

import fr.m3105.projetmode.utils.Model;
import fr.m3105.projetmode.utils.ViewWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //TODO: changer le main en stage3d et mettre le openfile en Application principale
    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        /*Model m = new Model();
        ViewWindow vw = new ViewWindow(100, 100,m);
        vw.start(PrimaryStage);
         */
        PrimaryStage.setTitle("Main frame");
        PrimaryStage.setScene(new Scene(root));
        PrimaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

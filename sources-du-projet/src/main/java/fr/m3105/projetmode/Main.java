package fr.m3105.projetmode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        PrimaryStage.setTitle("Main frame");
        PrimaryStage.setScene(new Scene(root));
        PrimaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

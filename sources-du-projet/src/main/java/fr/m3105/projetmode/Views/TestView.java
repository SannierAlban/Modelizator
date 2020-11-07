package fr.m3105.projetmode.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("testView.fxml"));
        /*Model m = new Model();
        ViewWindow vw = new ViewWindow(100, 100,m);
        vw.start(PrimaryStage);
         */
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

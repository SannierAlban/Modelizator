package fr.m3105.projetmode.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpenFileView extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("openFileView.fxml"));

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            PrimaryStage.setX(event.getScreenX() - xOffset);
            PrimaryStage.setY(event.getScreenY() - yOffset);
        });

        PrimaryStage.setTitle("OpenFile");
        PrimaryStage.setResizable(false);
        PrimaryStage.initStyle(StageStyle.UNDECORATED);
        PrimaryStage.setScene(new Scene(root));
        PrimaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

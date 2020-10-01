package fr.m3105.projetmode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label l = new Label(welcomeMessage());
        Label l2 = new Label("0");

        VBox vb = new VBox();

        vb.getChildren().addAll(l,l2);
        Scene scene = new Scene(new StackPane(vb), 640, 480);


        stage.setScene(scene);
        stage.show();

    }

    private String welcomeMessage() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        return
                "Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".\n" +
                        "             (and powered by maven)\n";
    }

    public static void main(String[] args) {
        launch();
    }
}

package fr.m3105.projetmode.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainStage extends Stage {

    private File file;

    public MainStage(File f) throws IOException {
        this.file = f;
        Parent root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        /*Model m = new Model();
        ViewWindow vw = new ViewWindow(100, 100,m);
        vw.start(PrimaryStage);
         */
        this.setTitle("Main frame");
        this.setScene(new Scene(root));
        this.show();
    }
}

package fr.m3105.projetmode.Views;

import fr.m3105.projetmode.controller.MainController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainStage.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setStage(this);
        this.setTitle("Main frame : " + f.getName());
        this.setScene(new Scene(root));
        this.show();
    }

    public File getFile() {
        return file;
    }
}

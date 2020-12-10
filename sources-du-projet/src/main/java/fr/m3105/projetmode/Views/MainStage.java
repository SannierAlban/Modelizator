package fr.m3105.projetmode.Views;

import fr.m3105.projetmode.controller.ControllerFactory;
import fr.m3105.projetmode.controller.ViewController;
import fr.m3105.projetmode.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainStage extends Stage {

    private File file;
    private FXMLLoader loader;
    public MainStage(File f) throws IOException {
        this.file = f;
        loader = new FXMLLoader(getClass().getResource("mainStage.fxml"));
        ViewController controller = (new ControllerFactory()).create("face");
        loader.setController(controller);
        Parent root = loader.load();
        controller.setStage(this);
        this.setTitle("Main frame : " + f.getName());
        this.setScene(new Scene(root));
        this.show();
    }

    public ViewController getController(){
        return loader.getController();
    }

    public void setController(ViewController vc, Model m) throws IOException {
        loader = new FXMLLoader(getClass().getResource("mainStage.fxml"));
        loader.setController(vc);
        Parent root = loader.load();
        vc.setStage(this);
        vc.setValue(m);
        vc.draw();
        this.setScene(new Scene(root));
    }

    public File getFile() {
        return file;
    }
}

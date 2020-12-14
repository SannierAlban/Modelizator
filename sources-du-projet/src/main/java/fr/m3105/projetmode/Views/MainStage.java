package fr.m3105.projetmode.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.File;
import java.io.IOException;

public class MainStage extends View {

    public MainStage(File f) throws IOException {
        file = f;
        loader = newLoader();
        controllerInitializer();
        Parent root = loader.load();
        controller.setStage(this);
        this.setTitle("Main frame : " + f.getName());
        this.setScene(new Scene(root));
        this.show();
    }

    @Override
    public boolean isCamera() {
        return false;
    }

    @Override
    public FXMLLoader newLoader() {
        return new FXMLLoader(getClass().getResource("mainStage.fxml"));
    }
}

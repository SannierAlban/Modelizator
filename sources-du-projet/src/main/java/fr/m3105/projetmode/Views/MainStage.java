package fr.m3105.projetmode.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class MainStage extends View {

    public MainStage(File file) throws IOException {
        this.file = file;
        loader = newLoader();
        controllerInitializer();
        Parent root = loader.load();
        controller.setStage(this);
        this.setTitle("Main frame : " + file.getName());
        this.setScene(new Scene(root));
        this.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.png")));
        this.setResizable(false);
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

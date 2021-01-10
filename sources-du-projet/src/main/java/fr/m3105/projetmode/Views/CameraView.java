package fr.m3105.projetmode.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class CameraView extends View {

    public CameraView(File file) throws IOException {
        this.file = file;
        loader = newLoader();
        controllerInitializer();
        Parent root = loader.load();
        controller.setStage(this);
        this.setTitle("Camera View");
        this.setScene(new Scene(root));
        this.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.png")));
        this.setResizable(false);
        this.show();
    }

    @Override
    public boolean isCamera() {
        return true;
    }

    @Override
    public FXMLLoader newLoader() {
        return new FXMLLoader(getClass().getResource("cameraView.fxml"));
    }
}

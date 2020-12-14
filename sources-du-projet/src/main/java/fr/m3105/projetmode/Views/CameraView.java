package fr.m3105.projetmode.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class CameraView extends View {

    public CameraView(File f) throws IOException {
        this.file = f;
        loader = newLoader();
        controllerInitializer();
        Parent root = loader.load();
        controller.setStage(this);
        this.setTitle("Camera View");
        this.setScene(new Scene(root));
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

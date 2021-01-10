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

public abstract class View extends Stage {
    protected File file;
    protected FXMLLoader loader;
    protected ViewController controller;
    public ViewController getController(){
        return loader.getController();
    }

    public void setController(ViewController viewController, Model model) throws IOException {
        loader = newLoader();
        loader.setController(viewController);
        Parent root = loader.load();
        viewController.setStage(this);
        viewController.setModel(model);
        viewController.draw();
        this.setScene(new Scene(root));
    }

    public void controllerInitializer() throws IOException {
        controller = (new ControllerFactory()).create("facesegment");
        loader.setController(controller);
    }
    public abstract boolean isCamera();
    public abstract FXMLLoader newLoader();

    public File getFile() {
        return file;
    }
}

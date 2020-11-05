package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class OpenFileController {
    @FXML
    private JFXButton openButton;

    public void fileChooser(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PLY file","*.ply"));
        File f = fc.showOpenDialog(null);
        if (f != null){
            System.out.println(f.getAbsolutePath());
            Stage stage = (Stage) openButton.getScene().getWindow();
            stage.close();
        }
    }
}

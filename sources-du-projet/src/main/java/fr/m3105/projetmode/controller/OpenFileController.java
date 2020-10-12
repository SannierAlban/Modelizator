package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXButton;
import fr.m3105.projetmode.Views.HelpView;
import fr.m3105.projetmode.Views.MainStage;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class OpenFileController {
    @FXML
    private JFXButton openButton;
    @FXML
    private ImageView helpButton;

    public void fileChooser() throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PLY file","*.ply"));
        File f = fc.showOpenDialog(null);
        if (f != null){
            System.out.println(f.getAbsolutePath());
            new MainStage(f);
        }
    }

    public void openHelpView() throws Exception {
        new HelpView();
    }
}

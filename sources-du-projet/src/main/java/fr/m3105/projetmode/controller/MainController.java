package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MainController {
    @FXML
    private JFXToggleButton lightActivation;
    @FXML
    private ImageView helpIcons;

    public void lightAction(){
        if (lightActivation.isSelected()){
            System.out.println("Activation des lumières");
        }
    }

    public void openHelpView() throws Exception {
        new HelpView();
    }

    public void openOpenFile() throws IOException {
        new OpenFileView();
    }
}

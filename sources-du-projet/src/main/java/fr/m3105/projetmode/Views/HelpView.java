package fr.m3105.projetmode.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpView extends Stage {

    public HelpView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("helpView.fxml"));
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Aide");
        this.setScene(new Scene(root));
        this.show();
    }
}

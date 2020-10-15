package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXButton;
import fr.m3105.projetmode.Views.HelpView;
import fr.m3105.projetmode.Views.MainStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OpenFileController implements Initializable {
    @FXML
    private JFXButton openButton;
    @FXML
    private ImageView helpButton;
    @FXML
    private VBox fileChooserVBox;

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

    public void closeWindow(){
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            File dir = new File("exemples");
            String[] extensions = new String[] {"ply"};
            System.out.println("get from : " + dir.getCanonicalPath());
            List<File> files = (List<File>) FileUtils.listFiles(dir,extensions,true);
            for (File f : files){
                HBox fileHbox = new HBox();
                Label fileName = new Label(f.getName());
                Button btn = new Button("ouvrir");
                System.out.println("ok");
                fileHbox.getChildren().addAll(fileName,btn);
                fileChooserVBox.getChildren().add(fileHbox);
                btn.setOnAction(event -> {
                    try {
                        new MainStage(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setButtonAction(Button btn){

    }
}

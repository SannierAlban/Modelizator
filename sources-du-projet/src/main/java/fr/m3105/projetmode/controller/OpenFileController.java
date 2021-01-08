package fr.m3105.projetmode.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.m3105.projetmode.Views.HelpView;
import fr.m3105.projetmode.Views.MainStage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OpenFileController implements Initializable {

    class PLY extends RecursiveTreeObject<PLY> {
        StringProperty name;
        StringProperty date;
        StringProperty url;
        JFXButton button;
        File f;

        public PLY(String name, String date, String url,File f) {
            this.name = new SimpleStringProperty(name) ;
            this.date = new SimpleStringProperty(date) ;
            this.url = new SimpleStringProperty(url);
            this.button = new JFXButton("Ouvrir");
            this.f = f;
        }

        public void prepareButton(){
            button.setButtonType(JFXButton.ButtonType.RAISED);
            button.setOnAction(actionEvent -> {
                try {
                    new MainStage(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    @FXML
    private JFXTreeTableView<PLY> treeListView;

    public void fileChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PLY file","*.ply"));
        File temFile = fileChooser.showOpenDialog(null);
        if (temFile != null){
            System.out.println(temFile.getAbsolutePath());
            new MainStage(temFile);
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

            JFXTreeTableColumn<PLY, String> nameColumn = new JFXTreeTableColumn<>("Name");
            nameColumn.setPrefWidth(150);
            nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PLY, String> param) ->{
                if(nameColumn.validateValue(param)) return param.getValue().getValue().name;
                else return nameColumn.getComputedValue(param);
            });

            JFXTreeTableColumn<PLY, String> dateColumn = new JFXTreeTableColumn<>("Date");
            dateColumn.setPrefWidth(150);
            dateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PLY, String> param) ->{
                if(dateColumn.validateValue(param)) return (ObservableValue<String>) param.getValue().getValue().date;
                else return dateColumn.getComputedValue(param);
            });

            JFXTreeTableColumn<PLY, String> plyColumn = new JFXTreeTableColumn<>("Url");
            plyColumn.setPrefWidth(250);
            plyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PLY, String> param) ->{
                if(plyColumn.validateValue(param)) return param.getValue().getValue().url;
                else return plyColumn.getComputedValue(param);
            });

            JFXTreeTableColumn<PLY, JFXButton> actionColumn = new JFXTreeTableColumn<>("Action");
            actionColumn.setPrefWidth(150);
            actionColumn.setCellValueFactory(cellData->
                    new SimpleObjectProperty<>(cellData.getValue().getValue().button));

            ObservableList<PLY> plys = FXCollections.observableArrayList();
            File dir = new File("exemples");
            String[] extensions = new String[] {"ply"};
            System.out.println("get from : " + dir.getCanonicalPath());
            List<File> files = (List<File>) FileUtils.listFiles(dir,extensions,true);

            for (File f : files){
                Path filePath = f.toPath();
                BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath,BasicFileAttributes.class);
                DateFormat df = new SimpleDateFormat("dd:MM:yy - HH:mm:ss");
                Date date = new Date(basicFileAttributes.lastAccessTime().toMillis());
                PLY ply = new PLY(f.getName(),df.format(date), f.getPath(), f);
                plys.add(ply);
                ply.prepareButton();
            }

            final TreeItem<PLY> root = new RecursiveTreeItem<>(plys, RecursiveTreeObject::getChildren);

            treeListView.setRoot(root);
            treeListView.setShowRoot(false);
            treeListView.setEditable(false);
            treeListView.getColumns().setAll(nameColumn,dateColumn,plyColumn,actionColumn);

            Label size = new Label();
            size.textProperty().bind(Bindings.createStringBinding(()->treeListView.getCurrentItemsCount()+"",
                    treeListView.currentItemsCountProperty()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

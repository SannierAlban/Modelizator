package fr.m3105.projetmode.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.m3105.projetmode.Views.HelpView;
import fr.m3105.projetmode.Views.MainStage;
import fr.m3105.projetmode.model.ModelHeader;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller de la partie ouverture de fichier, cette class aussi la création et l'affichage de certains composant.
 */
public class OpenFileController implements Initializable {
    /**
     * Classe interne permettant de stocké et trié les fichiers PLY
     */
    class PLYFile extends RecursiveTreeObject<PLYFile> {
        StringProperty name;
        StringProperty date;
        IntegerProperty nbfaces;
        JFXButton button;
        File file;

        public PLYFile(String name, String date, int nbfaces, File file) {
            this.name = new SimpleStringProperty(name) ;
            this.date = new SimpleStringProperty(date) ;
            this.nbfaces = new SimpleIntegerProperty(nbfaces);
            this.button = new JFXButton("Ouvrir");
            this.file = file;
        }

        /**
         * Permet d'instancier chaque bouton de la liste
         */
        public void prepareButton(){
            button.setButtonType(JFXButton.ButtonType.RAISED);
            button.setOnAction(actionEvent -> {
                try {
                    new MainStage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    @FXML
    private JFXTreeTableView<PLYFile> treeListView;
    @FXML
    JFXTextField filterField;
    @FXML
    Label size;

    /**
     * Fonction permettant de récupérer et de lancer un fichier via l'explorateur de fichier
     * Utilisation de la librarie org.apache.commons.io.
     */
    public void fileChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PLY file","*.ply"));
        File temFile = fileChooser.showOpenDialog(null);
        if (temFile != null){
            new MainStage(temFile);
        }
    }

    /**
     * Fonction permettant d'afficher la model d'help
     */
    public void openHelpView() throws Exception {
        new HelpView();
    }

    public void closeWindow(){
        Platform.exit();
    }


    /**
     * Fonction permettant de généré les composants de notre fenêtre avant l'affichage
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            JFXTreeTableColumn<PLYFile, String> nameColumn = new JFXTreeTableColumn<>("Name");
            nameColumn.setPrefWidth(150);
            nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PLYFile, String> param) ->{
                if(nameColumn.validateValue(param)) return param.getValue().getValue().name;
                else return nameColumn.getComputedValue(param);
            });

            JFXTreeTableColumn<PLYFile, String> dateColumn = new JFXTreeTableColumn<>("Date");
            dateColumn.setPrefWidth(150);
            dateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PLYFile, String> param) ->{
                if(dateColumn.validateValue(param)) return param.getValue().getValue().date;
                else return dateColumn.getComputedValue(param);
            });

            JFXTreeTableColumn<PLYFile, Integer> plyColumn = new JFXTreeTableColumn<>("Nombres de faces");
            plyColumn.setPrefWidth(250);
            plyColumn.setCellValueFactory(plyIntegerCellDataFeatures -> new SimpleObjectProperty<>(plyIntegerCellDataFeatures.getValue().getValue().nbfaces.getValue()));

            JFXTreeTableColumn<PLYFile, JFXButton> actionColumn = new JFXTreeTableColumn<>("Action");
            actionColumn.setPrefWidth(150);
            actionColumn.setCellValueFactory(cellData->
                    new SimpleObjectProperty<>(cellData.getValue().getValue().button));

            ObservableList<PLYFile> plys = FXCollections.observableArrayList();
            File dir = new File("exemples");
            String[] extensions = new String[] {"ply"};
            List<File> files = (List<File>) FileUtils.listFiles(dir,extensions,true);

            for (File f : files){
                Path filePath = f.toPath();
                BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath,BasicFileAttributes.class);
                DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy - HH:mm:ss");
                Date date = new Date(basicFileAttributes.lastAccessTime().toMillis());
                ModelHeader modelHeader = new ModelHeader(f.getAbsolutePath());
                PLYFile ply = new PLYFile(f.getName(),dateFormat.format(date), modelHeader.getNbDeFace(), f);
                plys.add(ply);
                ply.prepareButton();
            }

            final TreeItem<PLYFile> root = new RecursiveTreeItem<>(plys, RecursiveTreeObject::getChildren);

            treeListView.setRoot(root);
            treeListView.setShowRoot(false);
            treeListView.setEditable(false);
            treeListView.getColumns().setAll(nameColumn,dateColumn,plyColumn,actionColumn);

            size.textProperty().bind(Bindings.createStringBinding(()->treeListView.getCurrentItemsCount()+"",
                    treeListView.currentItemsCountProperty()));

            filterField.textProperty().addListener((o,oldVal,newVal)->{
                treeListView.setPredicate(user -> user.getValue().name.get().contains(newVal));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

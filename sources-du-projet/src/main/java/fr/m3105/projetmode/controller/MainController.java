package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import fr.m3105.projetmode.Views.MainStage;
import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private JFXToggleButton lightActivation;
    @FXML
    private JFXToggleButton coupeActivation;
    @FXML
    private JFXSlider coupeSlider;
    @FXML
    private Canvas mainCanvas;

    private Model m;
    private GraphicsContext gc;
    private File f;
    MainStage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coupeSlider.setVisible(false);

        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);

        m = new Model();

        m.translate(new Vector(150,150,0));

        draw();
    }

    public void draw(){
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(),gc.getCanvas().getHeight() );
        for (Face f: m.faces){
            for (int i = 0; i <f.getPoints().size();i++){
                if (i < f.getPoints().size()- 1){
                    gc.strokeLine(f.getPoints().get(i).x,f.getPoints().get(i).y,f.getPoints().get(i+1).x,f.getPoints().get(i+1).y);
                }else{
                    gc.strokeLine(f.getPoints().get(i).x,f.getPoints().get(i).y,f.getPoints().get(0).x,f.getPoints().get(0).y);
                }
            }
        }
    }

    public void setStage(MainStage stage){
        this.stage = stage;
        f = stage.getFile();
    }

    public void rotateXHaut(){
        m.rotateOnXAxis(3.14159/32);
        draw();
    }

    public void rotateXBas(){
        m.rotateOnXAxis(-3.14159/32);
        draw();
    }

    public void rotateYGauche(){
        m.rotateOnYAxis(3.14159/32);
        draw();
    }

    public void rotateYDroite(){
        m.rotateOnYAxis(-3.14159/32);
        draw();
    }

    public void lightAction(){
        if (lightActivation.isSelected()){
            System.out.println("Activation des lumières");
        }
    }

    public void coupeAction(){
        if (coupeActivation.isSelected()){
            System.out.println("Activation de la coupe");
            coupeSlider.setVisible(true);
        }else if(!coupeActivation.isSelected()){
            coupeSlider.setVisible(false);
        }
    }
}

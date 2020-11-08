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
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView pauseButton;

    private Model m;
    private GraphicsContext gc;
    private File f;
    private MainStage stage;
    private boolean isPlaying = false;
    Thread daemonThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coupeSlider.setVisible(false);
        pauseButton.setVisible(false);
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
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
        m = new Model(f);
        draw();
    }

    public void translationHaut(){
        m.translate(new Vector(0,5,0));
        draw();
    }

    public void translationBas(){
        m.translate(new Vector(0,-5,0));
        draw();
    }

    public void translationGauche(){
        m.translate(new Vector(5,0,0));
        draw();
    }

    public void translationDroite(){
        m.translate(new Vector(-5,0,0));
        draw();
    }

    public void rotateXBas(){
        m.rotateOnXAxis(3.14159/32);
        draw();
    }

    public void rotateXHaut(){
        m.rotateOnXAxis(-3.14159/32);
        draw();
    }

    public void rotateYDroite(){
        m.rotateOnYAxis(3.14159/32);
        draw();
    }

    public void rotateYGauche(){
        m.rotateOnYAxis(-3.14159/32);
        draw();
    }

    public void rotateZ(){
        m.rotateOnZAxis(3.14159/32);
        draw();
    }

    public void lightAction(){
        if (lightActivation.isSelected()){
            System.out.println("Activation des lumières");
        }
    }

    public void zoom(){
        m.zoom(1.2);
        draw();
    }

    public void deZoom(){
        m.zoom(0.8);
        draw();
    }

    public void coupeAction(){
        if (coupeActivation.isSelected()){
            System.out.println("Activation de la coupe");
            coupeSlider.setVisible(true);
        }else if(!coupeActivation.isSelected()){
            coupeSlider.setVisible(false);
        }
    }

    public void pause(){
        isPlaying = false;
        pauseButton.setVisible(false);
        playButton.setVisible(true);
    }

    public void play(){
        pauseButton.setVisible(true);
        playButton.setVisible(false);

        isPlaying = true;

        daemonThread = new Thread(() -> {
           try {
               while (isPlaying){
                   try {
                       Thread.sleep(70);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   m.rotateOnXAxis(3.14159/32);
                   m.rotateOnYAxis(3.14159/16);
                   m.rotateOnZAxis(3.14159/32);
                   draw();
               }

           }finally {
               //System.out.println("fin");
           }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

    }

}

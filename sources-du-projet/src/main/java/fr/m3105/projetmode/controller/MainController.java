package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import fr.m3105.projetmode.Views.MainStage;
import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Point;
import fr.m3105.projetmode.model.Vector;
import fr.m3105.projetmode.model.utils.ConnectableProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends ConnectableProperty implements Initializable {

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

    //public final static Point WINDOW_CENTER = new Point(mainCanvas.getWidth()/2,mainCanvas.getHeight(),0);

    //private Model m;
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
        for (Point p: ((Model) this.getValue()).points){
            gc.strokeLine(p.x,p.y,p.x,p.y);
        }
               for (Face f: sortFace(((Model) this.getValue()).faces)){
                    gc.setFill(Color.RED);
                    for (int i = 0; i <f.getPoints().size();i++){
                        if (i < f.getPoints().size()- 1){
                            //System.out.println("x1 : " + f.getPoints().get(i).x + " y1: " + f.getPoints().get(i).y + "x2 : " + f.getPoints().get(i+1).x + " y2: " + f.getPoints().get(i+1).y);
                            gc.strokeLine(f.getPoints().get(i).x,f.getPoints().get(i).y,f.getPoints().get(i+1).x,f.getPoints().get(i+1).y);
                        }else{
                            gc.strokeLine(f.getPoints().get(i).x,f.getPoints().get(i).y,f.getPoints().get(0).x,f.getPoints().get(0).y);
                        }
                        //gc.setFill(Color.BLUE);
                        gc.setFill(Color.color((double)f.getRed()/1000,(double)f.getGreen()/1000,(double)f.getBlue()/1000));
                        gc.fillPolygon(f.getX(),f.getY(),f.getnbPtn());
            }
        }
    }

    public void setStage(MainStage stage){
        this.stage = stage;
        f = stage.getFile();
        System.out.println(f.getPath());
        System.out.println(f.getName());
        this.setValue(new Model(f));
        ((Model) this.getValue()).zoom(5);
        ((Model) this.getValue()).translate(new Vector(mainCanvas.getWidth()/2-((Model) this.getValue()).getCenter().x,mainCanvas.getHeight()/2-((Model) this.getValue()).getCenter().y,0));
        draw();
    }

    public void translationHaut(){
        ((Model) this.getValue()).translate(new Vector(0,-5,0));
        draw();
    }

    public void translationBas(){
        ((Model) this.getValue()).translate(new Vector(0,5,0));
        draw();
    }

    public void translationGauche(){
        ((Model) this.getValue()).translate(new Vector(-5,0,0));
        draw();
    }

    public void translationDroite(){
        ((Model) this.getValue()).translate(new Vector(5,0,0));
        draw();
    }

    public void rotateXBas(){
        ((Model) this.getValue()).rotateOnXAxis(-3.14159/32);
        draw();
    }

    public void rotateXHaut(){
        ((Model) this.getValue()).rotateOnXAxis(3.14159/32);
        draw();
    }

    public void rotateYDroite(){
        ((Model) this.getValue()).rotateOnYAxis(-3.14159/32);
        draw();
    }

    public void rotateYGauche(){
        ((Model) this.getValue()).rotateOnYAxis(3.14159/32);
        draw();
    }

    public void rotateZ(){
        ((Model) this.getValue()).rotateOnZAxis(-3.14159/32);
        draw();
    }

    public void lightAction(){
        if (lightActivation.isSelected()){
            System.out.println("Activation des lumières");
        }
    }

    public void zoom(){
        ((Model) this.getValue()).zoom(1.2);
        //m.translate(new Vector(mainCanvas.getWidth()/2-m.getComplexCenter().x,mainCanvas.getHeight()/2-m.getComplexCenter().y,0));
        draw();
    }

    public void deZoom(){
        ((Model) this.getValue()).zoom(0.8);
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
                       Thread.sleep(35);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   ((Model) this.getValue()).rotateOnXAxis(3.14159/32);
                   ((Model) this.getValue()).rotateOnYAxis(3.14159/16);
                   //m.rotateOnZAxis(3.14159/32);
                   draw();
               }
           }finally {
               //System.out.println("fin");
           }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public List<Face> sortFace(List<Face> faces){
        List<Face> tempList = new ArrayList<>(faces);
        Collections.sort(tempList);
        return tempList;
    }

}

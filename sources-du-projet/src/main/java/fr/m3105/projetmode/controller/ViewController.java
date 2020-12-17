package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import fr.m3105.projetmode.Views.CameraView;
import fr.m3105.projetmode.Views.MainStage;
import fr.m3105.projetmode.Views.View;
import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Vector;
import fr.m3105.projetmode.model.utils.ConnectableProperty;
import fr.m3105.projetmode.model.utils.Observer;
import fr.m3105.projetmode.model.utils.Subject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public abstract class ViewController extends ConnectableProperty implements Initializable {
    @FXML
    protected JFXToggleButton lightActivation;
    @FXML
    protected JFXToggleButton coupeActivation;
    @FXML
    protected JFXSlider coupeSlider;
    @FXML
    protected Canvas mainCanvas;
    @FXML
    protected ImageView playButton;
    @FXML
    protected ImageView pauseButton;
    @FXML
    protected Pane mainPane;
    @FXML
    protected Pane secondPane;
    @FXML
    protected Canvas secondCanvas;

    protected GraphicsContext gc;
    protected File f;
    protected View stage;
    protected boolean isPlaying = false;
    protected Thread daemonThread;
    protected List<View> views = new ArrayList<>();

    public abstract void draw();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
                MainStage tmpstage = (MainStage) stage;
                coupeSlider.setVisible(false);
                pauseButton.setVisible(false);
        }catch (Exception e){

        }
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
    }

    public void setStage(View stage){
        this.stage =stage;
        f = this.stage.getFile();
        this.setValue(new Model(f));
        ((Model) this.getValue()).zoom(5);
        ((Model) this.getValue()).translate(new Vector(mainCanvas.getWidth()/2-((Model) this.getValue()).getCenter().x,mainCanvas.getHeight()/2-((Model) this.getValue()).getCenter().y,0));
        draw();
    }

    public void translationHaut(){
        ((Model) this.getValue()).translate(new Vector(0,-5,0));
        this.notifyObservers(this.getValue());
        draw();
    }

    public void translationBas(){
        ((Model) this.getValue()).translate(new Vector(0,5,0));
        this.notifyObservers(this.getValue());
        draw();
    }

    public void translationGauche(){
        ((Model) this.getValue()).translate(new Vector(-5,0,0));
        this.notifyObservers(this.getValue());
        draw();
    }

    public void translationDroite(){
        ((Model) this.getValue()).translate(new Vector(5,0,0));
        this.notifyObservers(this.getValue());
        draw();
    }

    public void rotateXBas(){
        ((Model) this.getValue()).rotateOnXAxis(-3.14159/32);
        this.notifyObservers(this.getValue());
        draw();
    }

    public void rotateXHaut(){
        ((Model) this.getValue()).rotateOnXAxis(3.14159/32);
        this.notifyObservers(this.getValue());
        draw();
    }

    public void rotateYDroite(){
        ((Model) this.getValue()).rotateOnYAxis(-3.14159/32);
        this.notifyObservers(this.getValue());
        draw();
    }

    public void rotateYGauche(){
        ((Model) this.getValue()).rotateOnYAxis(3.14159/32);
        this.notifyObservers(this.getValue());
        draw();
    }

    public void rotateZ(){
        ((Model) this.getValue()).rotateOnZAxis(-3.14159/32);
        this.notifyObservers(this.getValue());
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
        this.notifyObservers(this.getValue());
        draw();
    }

    public void deZoom(){
        ((Model) this.getValue()).zoom(0.8);
        this.notifyObservers(this.getValue());
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
                    this.notifyObservers(this.getValue());
                    draw();
                }

            }finally {

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

    public void deuxScene() throws IOException {
        //mainPane.setPrefWidth(mainPane.getWidth()/2);
        //mainCanvas.setWidth(mainCanvas.getWidth()/2);
        Model model = (Model) this.getValue();
        View cameraView = new CameraView(this.f);
        views.add(cameraView);
        this.attach(cameraView.getController());
        cameraView.getController().setValue(model);
    }

    public void drawFace() throws IOException {
        stage.setController((new ControllerFactory()).create("face"),(Model)this.getValue());
        stage.getController().setViews(views);
        for (View view: views){
            this.detach(view.getController());
            view.setController((new ControllerFactory()).create("face"),(Model)this.getValue());
            stage.getController().attach(view.getController());
        }
        draw();
    }

    public void drawPoint() throws IOException {
        stage.setController((new ControllerFactory()).create("point"),(Model)this.getValue());
        stage.getController().setViews(views);
        for (View view: views){
            this.detach(view.getController());
            view.setController((new ControllerFactory()).create("point"),(Model)this.getValue());
            stage.getController().attach(view.getController());
        }
        draw();
    }
    public void drawSegment() throws IOException {
        stage.setController((new ControllerFactory()).create("segment"),(Model)this.getValue());
        stage.getController().setViews(views);
        for (View view: views){
            this.detach(view.getController());
            view.setController((new ControllerFactory()).create("segment"),(Model)this.getValue());
            stage.getController().attach(view.getController());
        }
        draw();
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    @Override
    public void update(Subject other, Object data) {
        setValue(data);
        draw();
    }
}
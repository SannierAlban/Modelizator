package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXToggleButton;

import fr.m3105.projetmode.Views.CameraView;
import fr.m3105.projetmode.Views.View;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.utils.Observer;
import fr.m3105.projetmode.model.utils.Subject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.*;

public abstract class ViewController implements Initializable, Observer {
    @FXML
    protected JFXToggleButton lightActivation;
    @FXML
    protected Canvas mainCanvas;
    @FXML
    protected ImageView playButton;
    @FXML 
    protected ImageView pauseButton;
    @FXML
    protected ColorPicker colorPicker;

    protected GraphicsContext graphicsContext;
    protected File file;
    protected View stage;
    protected boolean isPlaying = false;
    protected List<View> views = new ArrayList<>();
    boolean lightsOn = false;
    protected Model model;

    public abstract void draw();

    class ModelRun extends Task<Void> {
        @Override
        protected Void call() {
            while (isPlaying) {
                try {
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    // do nothing
                }
                if (isPlaying) {
                    Platform.runLater(() -> {
                        model.rotateOnXAxis(3.14159 / 32);
                        model.rotateOnYAxis(3.14159 / 16);
                    });
                }
            }
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pauseButton.setVisible(false);
        }catch (Exception e){

        }
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLUE);
    }

    public void setStage(View stage){
        this.stage =stage;
        file = this.stage.getFile();
        model = new Model(file);
        System.out.println(System.currentTimeMillis());
        model.attach(this);
        //Permet de creer un affichage en console
        //model.attach(new ConsoleView(model));
        model.zoom(6);
        try{
            if (model.isColored()){
                colorPicker.setVisible(false);
            }
        }catch (Exception e){

        }
        centerModel();
        System.out.println(System.currentTimeMillis());
    }

    public void translationHaut(){
        model.translate(new double[] {0,-5,0},true);
    }

    public void translationBas(){
        model.translate(new double[] {0,5,0},true);
    }

    public void translationGauche(){
        model.translate(new double[] {-5,0,0},true);
    }

    public void translationDroite(){
        model.translate(new double[] {5,0,0},true);
    }

    public void rotateXBas(){
        model.rotateOnXAxis(-3.14159/64);
    }

    public void rotateXHaut(){
        model.rotateOnXAxis(3.14159/64);
    }

    public void rotateYDroite(){
        model.rotateOnYAxis(-3.14159/64);
    }

    public void rotateYGauche(){
        model.rotateOnYAxis(3.14159/64);
    }

    public void rotateZ(){
        model.rotateOnZAxis(-3.14159/64);
    }
    public void inverseRotateZ(){
        model.rotateOnZAxis(3.14159/64);
    }

    public void lightAction(){
        if (lightActivation.isSelected()){
            lightsOn = true;
            draw();
        }else {
            lightsOn = false;
        	model.restoreColor();
            System.out.println("desac");
        	draw();
        }
    }

    public void zoom(){
        model.zoom(1.1);
    }

    public void centerModel(){
        model.translate(new double[] {mainCanvas.getWidth()/2-model.getCenter()[0],mainCanvas.getHeight()/2-model.getCenter()[1],0},true);
    }

    public void deZoom(){
        model.zoom(0.9);
    }

    public void pause(){
        pauseButton.setVisible(false);
        playButton.setVisible(true);
        stopThread();
    }

    public void play(){
        pauseButton.setVisible(true);
        playButton.setVisible(false);
        isPlaying = true;
        startThread();
    }

    public void changeColor(){
        model.changeColor((int)(colorPicker.getValue().getRed()*255),(int)(colorPicker.getValue().getGreen()*255),(int)(colorPicker.getValue().getBlue()*255));
    }

    // start thread
    public synchronized void startThread() {
        Task<Void> bg = new ModelRun();
        Thread taskThread = new Thread(bg);
        taskThread.setDaemon(true);
        taskThread.start();
    }

    // stop thread
    public synchronized void stopThread() {
        isPlaying = false;
    }

    public class Face implements Comparable<Face>{
        int p1;int p2;int p3;int r1;int g1;int b1;int r2;int g2;int b2;
        public Face(int p1,int p2,int p3,int r1,int g1,int b1,int r2,int g2,int b2){
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.r1 = r1;
            this.g1 = g1;
            this.b1 = b1;
            this.r2 = r2;
            this.g2 = g2;
            this.b2 = b2;
        }
        public Face(int p1,int p2,int p3){
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }

        public double moyenne(){
            return (model.getPoint(p1)[2] + model.getPoint(p2)[2] + model.getPoint(p3)[2])/3;
        }
        @Override
        public int compareTo(Face o) {
            if (this.moyenne() > o.moyenne()) {
                return -1;
            } else if (this.moyenne() < o.moyenne()) {
                return 1;
            } else {
                Double maxT1 = Double.max(p1, Double.max(p2, p3));
                Double maxT2 = Double.max(p1, Double.max(p2, p3));
                if (maxT1 > maxT2) {
                    return -1;
                }else if (maxT1 < maxT2) {
                    return 1;
                }
                return 0;
            }
        }
    }
    
//	trie par réference les 2 tableau
    public int[][] sortFace(int[][] faces){
//				Array de x,y,z,R,G,B
    	List<Face> facesRGB = new ArrayList<>();
    	
    	if(model.isColor() && !model.isRgbSurPoints()) {
    		for(int i = 0; i < model.getFaces()[0].length; i++) {
    			int r1 = model.getRgbAlpha()[0][i];
    			int g1 = model.getRgbAlpha()[1][i];
    			int b1 = model.getRgbAlpha()[2][i];
                int r2 = model.getBaseRGB()[0][i];
                int g2 = model.getBaseRGB()[1][i];
                int b2 = model.getBaseRGB()[2][i];
    			facesRGB.add(new Face(model.getFaces()[0][i],model.getFaces()[1][i],model.getFaces()[2][i],r1,g1,b1,r2,g2,b2));
    		}
    	}
    	else {
        	for(int i = 0; i < model.getFaces()[0].length; i++)
                facesRGB.add(new Face(model.getFaces()[0][i],model.getFaces()[1][i],model.getFaces()[2][i]));
    	}
        Collections.sort(facesRGB);
    	//Collections.shuffle(facesRGB);
    	//int vertexMoinsUn = model.getVertex() - 1;
    	if(model.isColor() && !model.isRgbSurPoints()) {
        	for(int i = 0; i < facesRGB.size(); i++) {
        		model.getFaces()[0][i] = facesRGB.get(i).p1;
        		model.getFaces()[1][i] = facesRGB.get(i).p2;
        		model.getFaces()[2][i] = facesRGB.get(i).p3;
        		model.getRgbAlpha()[0][i] = facesRGB.get(i).r1;
        		model.getRgbAlpha()[1][i] = facesRGB.get(i).g1;
        		model.getRgbAlpha()[2][i] = facesRGB.get(i).b1;
                model.getBaseRGB()[0][i] = facesRGB.get(i).r2;
                model.getBaseRGB()[1][i] = facesRGB.get(i).g2;
                model.getBaseRGB()[2][i] = facesRGB.get(i).b2;
        	}
    	}
    	else {
        	for(int i = 0; i <= facesRGB.size(); i++) {
        		model.getFaces()[0][i] = facesRGB.get(i).p1;
        		model.getFaces()[1][i] = facesRGB.get(i).p2;
        		model.getFaces()[2][i] = facesRGB.get(i).p3;
        	}
    	}
    	return model.FACES;
    }
    
    /**
     * Applies lights to the associated Model
     * @param lightSourcePoint
     */
    public void applyLights(double[] lightSourcePoint) {
    	if(lightSourcePoint.length!=3) throw new InvalidParameterException();
    	model.applyLights(lightSourcePoint);
    }

	//TODO: ici faire une fonction setModel pour passer le model
	public void deuxScene() throws IOException {
        View cameraView = new CameraView(this.file);
        views.add(cameraView);
        model.attach(cameraView.getController());
        cameraView.getController().setModel(model);
    }

    public void drawFace() throws IOException {
        stage.setController((new ControllerFactory()).create("face"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }

    public void drawPoint() throws IOException {
        stage.setController((new ControllerFactory()).create("point"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }
    public void drawSegment() throws IOException {
        stage.setController((new ControllerFactory()).create("segment"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }

    public void drawFaceSegment() throws IOException {
        stage.setController((new ControllerFactory()).create("facesegment"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    @Override
    public void update(Subject subj){
        model = (Model) subj;
        draw();
    }
    @Override
    public void update(Subject subj,Object data){
        //On ne mets rien
    }

    public void setModel(Model model) {
        this.model = model;
        draw();
    }

    public void keyDetected(KeyEvent event){
        if (event.getCode().equals(KeyCode.ADD))
            zoom();
        if (event.getCode().equals(KeyCode.SUBTRACT))
            deZoom();
        if (event.getCode().equals(KeyCode.Z))
            translationHaut();
        if (event.getCode().equals(KeyCode.Q))
            translationGauche();
        if (event.getCode().equals(KeyCode.S))
            translationBas();
        if (event.getCode().equals(KeyCode.D))
            translationDroite();
        if (event.getCode().equals(KeyCode.A))
            rotateZ();
        if (event.getCode().equals(KeyCode.E))
            inverseRotateZ();
        if (event.getCode().equals(KeyCode.I))
            rotateXHaut();
        if (event.getCode().equals(KeyCode.J))
            rotateYGauche();
        if (event.getCode().equals(KeyCode.K))
            rotateXBas();
        if (event.getCode().equals(KeyCode.L))
            rotateYDroite();
    }
}
package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXToggleButton;

import fr.m3105.projetmode.Views.CameraView;
import fr.m3105.projetmode.Views.View;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.utils.Observer;
import fr.m3105.projetmode.utils.Subject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * ViewController est le controller principale de notre application
 * il gère l'affichage mais aussi la partie interraction utilisateur
 */
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
    @FXML
    protected Text textChangeColor;

    protected GraphicsContext graphicsContext;
    protected File file;
    protected View stage;
    protected boolean isPlaying = false;
    protected List<View> views = new ArrayList<>();
    protected boolean lightsOn = false;
    protected Model model;
    protected Task<Void> modelRun;

    public abstract void draw();

    /**
     * Class interne permetant de gérer les threads avec javafx pour réaliser une animation
     */
    class ModelRun extends Task<Void> {
        @Override
        protected Void call() {
            while (isPlaying) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // do nothing
                }
                if (isPlaying) {
                    Platform.runLater(() -> {
                        model.rotateOnYAxis(3.14159 / 32);
                    });
                }
            }
            return null;
        }
    }

    /**
     * Fonction d'initialisation du controller
     * Permet de gérer la différence entre une vue normal et une vue camera.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pauseButton.setVisible(false);
        }catch (Exception e){

        }
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLUE);
    }

    /**
     *
     * @param stage : View à qui le controller sera attribué
     */
    public void setStage(View stage){
        this.stage =stage;
        file = this.stage.getFile();
        model = new Model(file);
        model.attach(this);
        //Permet de creer un affichage en console
        //model.attach(new ConsoleView(model));
        model.zoom(6);
        try{
            if (model.isColored()){
                colorPicker.setVisible(false);
                textChangeColor.setVisible(false);
            }
        }catch (Exception e){

        }
        centerModel();
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
        model.rotateOnYAxis(3.14159/64);
    }

    public void rotateYGauche(){
        model.rotateOnYAxis(-3.14159/64);
    }

    public void rotateZ(){
        model.rotateOnZAxis(-3.14159/64);
    }
    public void inverseRotateZ(){
        model.rotateOnZAxis(3.14159/64);
    }

    /**
     * Fonction qui gère la lumière (activation, désactivation et restoration de couleur)
     */
    public void lightAction(){
        if (lightActivation.isSelected()){
            lightsOn = true;
            draw();
        }else {
            lightsOn = false;
        	model.restoreColor();
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

    /**
     * Fonction qui permet de démarrer un thread d'animation de type ModelRun
     */
    public synchronized void startThread() {
        modelRun= new ModelRun();
        Thread taskThread = new Thread(modelRun);
        taskThread.setDaemon(true);
        taskThread.start();
    }

    /**
     * Permet de stopper un thread ModelRun
     */
    public synchronized void stopThread() {
        isPlaying = false;
        modelRun.cancel(true);
    }

    /**
     * Class interne qui permet de gérer les faces et qui permet de les tier
     */
    public class Face implements Comparable<Face>{
        int point1;int point2;int point3;int redRGBAlpha;int greenRGBAlpha;int blueRGBAlpha;int redBaseRGB;int greenBaseRGB;int blueBaseRGB;
        public Face(int point1, int point2, int point3, int redRGBAlpha, int greenRGBAlpha, int blueRGBAlpha, int redBaseRGB, int greenBaseRGB, int blueBaseRGB){
            this.point1 = point1;
            this.point2 = point2;
            this.point3 = point3;
            this.redRGBAlpha = redRGBAlpha;
            this.greenRGBAlpha = greenRGBAlpha;
            this.blueRGBAlpha = blueRGBAlpha;
            this.redBaseRGB = redBaseRGB;
            this.greenBaseRGB = greenBaseRGB;
            this.blueBaseRGB = blueBaseRGB;
        }
        public Face(int point1, int point2, int point3){
            this.point1 = point1;
            this.point2 = point2;
            this.point3 = point3;
        }

        /**
         * fait permettant de calculer la moyenne des Z
         * @return moyenne des points sur la composante z
         */
        public double moyenne(){
            return (model.getPoint(point1)[2] + model.getPoint(point2)[2] + model.getPoint(point3)[2])/3;
        }
        @Override
        public int compareTo(Face otherFace) {
            if (this.moyenne() > otherFace.moyenne()) {
                return -1;
            } else if (this.moyenne() < otherFace.moyenne()) {
                return 1;
            } else {
                Double maxT1 = Double.max(point1, Double.max(point2, point3));
                Double maxT2 = Double.max(point1, Double.max(point2, point3));
                if (maxT1 > maxT2) {
                    return -1;
                }else if (maxT1 < maxT2) {
                    return 1;
                }
                return 0;
            }
        }
    }

    /**
     * Fonction des trie qui va trier les faces selon leurs moyenne des Z et qui va en même tant trier les tableaux de couleurs
     * @param faces : tableau à trier
     * @return tableau trié
     */
    public int[][] sortFace(int[][] faces){
//				Array de x,y,z,R,G,B
    	List<Face> facesRGB = new ArrayList<>();
    	
    	if(model.isColor() && !model.isRgbSurPoints()) {
    		for(int i = 0; i < model.getFaces()[0].length; i++) {
    			int redRGBAlpha = model.getRgbAlpha()[0][i];
    			int greenRGBAlpha = model.getRgbAlpha()[1][i];
    			int blueRGBAlpha = model.getRgbAlpha()[2][i];
                int redBaseRGB = model.getBaseRGB()[0][i];
                int greenBaseRGB = model.getBaseRGB()[1][i];
                int blueBaseRGB = model.getBaseRGB()[2][i];
    			facesRGB.add(new Face(model.getFaces()[0][i],model.getFaces()[1][i],model.getFaces()[2][i],redRGBAlpha,greenRGBAlpha,blueRGBAlpha,redBaseRGB,greenBaseRGB,blueBaseRGB));
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
        		model.getFaces()[0][i] = facesRGB.get(i).point1;
        		model.getFaces()[1][i] = facesRGB.get(i).point2;
        		model.getFaces()[2][i] = facesRGB.get(i).point3;
        		model.getRgbAlpha()[0][i] = facesRGB.get(i).redRGBAlpha;
        		model.getRgbAlpha()[1][i] = facesRGB.get(i).greenRGBAlpha;
        		model.getRgbAlpha()[2][i] = facesRGB.get(i).blueRGBAlpha;
                model.getBaseRGB()[0][i] = facesRGB.get(i).redBaseRGB;
                model.getBaseRGB()[1][i] = facesRGB.get(i).greenBaseRGB;
                model.getBaseRGB()[2][i] = facesRGB.get(i).blueBaseRGB;
        	}
    	}
    	else {
        	for(int i = 0; i < facesRGB.size(); i++) {
        		model.getFaces()[0][i] = facesRGB.get(i).point1;
        		model.getFaces()[1][i] = facesRGB.get(i).point2;
        		model.getFaces()[2][i] = facesRGB.get(i).point3;
        	}
    	}
    	return model.faces;
    }
    
    /**
     * Applies lights to the associated Model
     * @param lightSourcePoint : point source de la lumière
     */
    public void applyLights(double[] lightSourcePoint) {
    	if(lightSourcePoint.length!=3) throw new InvalidParameterException();
    	model.applyLights(lightSourcePoint);
    }

    /**
     * Fonction qui lance une vue de type caméra
     */
	public void deuxScene() throws IOException {
        View cameraView = new CameraView(this.file);
        views.add(cameraView);
        model.attach(cameraView.getController());
        cameraView.getController().setModel(model);
    }

    public void drawFace() throws IOException {
        lightsOn = false;
        model.restoreColor();
        draw();
        stage.setController((new ControllerFactory()).create("face"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }

    public void drawPoint() throws IOException {
        lightsOn = false;
        model.restoreColor();
        draw();
        stage.setController((new ControllerFactory()).create("point"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }
    public void drawSegment() throws IOException {
        lightsOn = false;
        model.restoreColor();
        draw();
        stage.setController((new ControllerFactory()).create("segment"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        draw();
    }

    public void drawFaceSegment() throws IOException {
        lightsOn = false;
        model.restoreColor();
        draw();
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
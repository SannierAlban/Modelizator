package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import fr.m3105.projetmode.Views.CameraView;
import fr.m3105.projetmode.Views.View;
import fr.m3105.projetmode.model.Model;
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
import java.security.InvalidParameterException;
import java.util.*;

public abstract class ViewController implements Initializable, Observer {
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

    protected GraphicsContext graphicsContext;
    protected File file;
    protected View stage;
    protected boolean isPlaying = false;
    protected Thread daemonThread;
    protected List<View> views = new ArrayList<>();
    boolean lightsOn = false;
    protected Model model;

    public abstract void draw();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //MainStage tmpstage = (MainStage) stage;
            coupeSlider.setVisible(false);
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
        model.attach(this);
        //Permet de créer un affichage en console
        //model.attach(new ConsoleView(model));
        model.zoom(5);
        //model.translate(new Vector(mainCanvas.getWidth()/2-model.getCenter().x,mainCanvas.getHeight()/2-model.getCenter().y,0));
        model.translate(new double[] {mainCanvas.getWidth()/2-model.getCenter()[0],mainCanvas.getHeight()/2-model.getCenter()[1],0},true);
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
        model.rotateOnXAxis(-3.14159/32);
    }

    public void rotateXHaut(){
        model.rotateOnXAxis(3.14159/32);
    }

    public void rotateYDroite(){
        model.rotateOnYAxis(-3.14159/32);
    }

    public void rotateYGauche(){
        model.rotateOnYAxis(3.14159/32);
    }

    public void rotateZ(){
        model.rotateOnZAxis(-3.14159/32);
    }

    public void lightAction(){
        if (lightActivation.isSelected()){
            System.out.println("Activation des lumières");
            lightsOn = true;
        }else {
        	model.restoreColor();
        	lightsOn = false;
        }
    }

    public void zoom(){
        model.zoom(1.2);
        //m.translate(new Vector(mainCanvas.getWidth()/2-m.getComplexCenter().x,mainCanvas.getHeight()/2-m.getComplexCenter().y,0));
    }

    public void deZoom(){
        model.zoom(0.8);
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
                        Thread.sleep(10);
                        model.rotateOnXAxis(3.14159/32);
                        model.rotateOnYAxis(3.14159/16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                daemonThread.interrupt();
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public int[][] sortFacePasTerribleDeFrancoisMaisBonIlYADesLambdasExpressions(int[][] faces){
        int[][] tmpFaces = faces.clone();

        Arrays.sort(tmpFaces, (o1, o2) -> {
            double moyenne1 = model.getPoint(o1[0])[2]+model.getPoint(o1[1])[2]+model.getPoint(o1[2])[2];
            double moyenne2 = model.getPoint(o2[0])[2]+model.getPoint(o2[1])[2]+model.getPoint(o2[2])[2];
            moyenne1 = moyenne1/3;
            moyenne2 = moyenne2/3;

            if (moyenne1 == moyenne2)
                return 0;
            if(moyenne1 > moyenne2){
                return 1;
            }
            return -1;
        });
        return tmpFaces;
    }
    
    /**
     * Returns a copy of the parameter sorted using bubblesort method
     * @param faces
     * @return sorted array of faces
     */
    public int[][] sortFace(int[][] faces){
	    	int[][] tmpFaces = faces.clone();
	    	double[][] points = model.getPoints();
	    	int lengthRowFaces = faces[0].length;
	    	int lengthColFaces = faces.length;
	    	//trier les int[] (sous forme de colonnes) dans l'ordre croissant
	    	boolean sorted = false;
	    	do {
	    		sorted = true;
		    	for(int idxFace=0; idxFace<lengthRowFaces-1; idxFace++) {
		    		double sumA=0.0,sumB=0.0;
		    		for(int idxPoint=0;idxPoint<lengthColFaces;idxPoint++) {
		    			sumA+=points[2][tmpFaces[idxPoint][idxFace]];
		    			sumB+=points[2][tmpFaces[idxPoint][idxFace+1]];
		    		}
		    		if(sumA/lengthColFaces>sumB/lengthColFaces) {
		    			tmpFaces = swap(faces, idxFace, idxFace+1);
		    			sorted = false;
		    		}
		    	}
	    	}while(!sorted);
	    	
	    	return tmpFaces;
    }
    
    /**
     * 
     * @param lightSourcePoint
     */
    public void applyLights(double[] lightSourcePoint) {
    	if(lightSourcePoint.length!=3) throw new InvalidParameterException();
    	model.applyLights(lightSourcePoint);
    }
    
    /**
     * Swaps two columns of the faces array 
     * @param faces
     * @param idxA
     * @param idxB
     * @return faces array with idxA and idxB swaped
     */
    private int[][] swap(int[][] faces,int idxA,int idxB){
        //JE SAIS JE RENVOIE LE PARAM, JE SAIS QUE C'EST PAS CLEAN CODE
        for(int axis=0;axis<3;axis++) {
            int tmp = faces[axis][idxA];
            faces[axis][idxA] = faces[axis][idxB];
            faces[axis][idxB] = tmp;
        }
        model.swapRgbAlpha(idxA,idxB);
        return faces;
    }

	//TODO: ici faire une fonction setModel pour passer le model
	public void deuxScene() throws IOException {
        View cameraView = new CameraView(this.file);
        views.add(cameraView);
        model.attach(cameraView.getController());
        cameraView.getController().setModel(model);
    }

//    public void newDraw() throws IOException{
//        stage.setController((new ControllerFactory()).create("face"),model);
//        stage.getController().setViews(views);
//        for (View view: views){
//            model.detach(view.getController());
//            view.setController((new ControllerFactory()).create("face"),model);
//            //stage.getController().attach(view.getController());
//        }
//    }
    //TODO: refaire ces fonctions
    public void drawFace() throws IOException {
        stage.setController((new ControllerFactory()).create("face"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        for (View view: views){
            model.detach(view.getController());
            view.setController((new ControllerFactory()).create("face"),model);
            model.attach(stage.getController());
            stage.getController().model.attach(view.getController());
        }
        draw();
    }

    public void drawPoint() throws IOException {
        stage.setController((new ControllerFactory()).create("point"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        for (View view: views){
            model.detach(view.getController());
            view.setController((new ControllerFactory()).create("point"),model);
            model.attach(stage.getController());
            stage.getController().model.attach(view.getController());
        }
        draw();
    }
    public void drawSegment() throws IOException {
        stage.setController((new ControllerFactory()).create("segment"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        for (View view: views){
            model.detach(view.getController());
            view.setController((new ControllerFactory()).create("segment"),model);
            model.attach(stage.getController());
            stage.getController().model.attach(view.getController());
        }
        draw();
    }

    public void drawFaceSegment() throws IOException {
        stage.setController((new ControllerFactory()).create("facesegment"),model);
        stage.getController().setViews(views);
        model.attach(stage.getController());
        for (View view: views){
            model.detach(view.getController());
            view.setController((new ControllerFactory()).create("facesegment"),model);
            model.attach(stage.getController());
            stage.getController().model.attach(view.getController());
        }
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
}
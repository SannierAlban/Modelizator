package fr.m3105.projetmode.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.paint.*;

import java.net.URL;
import java.util.ArrayList;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coupeSlider.setVisible(false);
        
        GraphicsContext g = mainCanvas.getGraphicsContext2D();
        Model modelAAfficher = new Model();
        ArrayList<Face> Faces = modelAAfficher.getFaces();
        
        //printFillOnCanvas(g, Faces);
        printBoneOnCanvas(g, Faces);
        //g.clearRect(0, 0, 800, 750); // Efface tout ce qu'il y a sur le Canvas
                
    }

	public void printFillOnCanvas(GraphicsContext graphContext, ArrayList<Face> Faces) {
		for (Face face : Faces) {
        	double[] z = face.getZ();
			for(int i = 0; i < face.getnbPtn(); i++) {
				graphContext.setFill(Color.rgb(face.getRed(), face.getGreen(), face.getBlue()));
				graphContext.fillPolygon(cooTo3d(face.getX(), z),cooTo3d(face.getY(), z), face.getnbPtn());
			}
		}
	}
	
	public void printBoneOnCanvas(GraphicsContext graphContext, ArrayList<Face> Faces) {
		for (Face face : Faces) {
        	double[] z = face.getZ();
			for(int i = 0; i < face.getnbPtn(); i++) {
				graphContext.setFill(Color.rgb(face.getRed(), face.getGreen(), face.getBlue()));
				graphContext.strokePolygon(cooTo3d(face.getX(), z),cooTo3d(face.getY(), z), face.getnbPtn());
			}
		}
	}
	

    public double[] cooTo3d(double[] x_Ou_Y, double[] z) {
    	int len = x_Ou_Y.length;
    	double[] ret = new double[len];
    	
    	for(int i = 0; i < len; i++) {
    		ret[i] = x_Ou_Y[i] * Math.pow(1.01,z[i]);
    		//ou un autre calcule ..............................
    	}
    	return ret;
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

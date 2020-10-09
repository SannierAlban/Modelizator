package fr.m3105.projetmode.Views;

import java.util.ArrayList;

import com.sun.jdi.Value;
import com.sun.prism.paint.Color;

import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class ThreeDView {
	private final int size = 480;
	private final int dF = 1; // distance focal
	
	private Canvas canvas;
	private ArrayList<Face> modelFaces;
	
	
	public ThreeDView(Model modelToPrint) {
		this.canvas = new Canvas(size,size);
		modelFaces = modelToPrint.getFaces();
	}
	
	public void backgroundFill() {
		GraphicsContext g = this.canvas.getGraphicsContext2D();
		g.setFill(Paint.valueOf("grey"));
		g.fillRect(0, 0, size, size);
	}
	
	public void drawModel() {
		GraphicsContext g = this.canvas.getGraphicsContext2D();
		 
		g.setFill(Paint.valueOf("black"));
		
		for (Face face : modelFaces) {
			//System.out.println(face.toString());
			
			for(int i = 0;i < face.getnbPtn();i++) {
				if(i%2==0) g.setFill(Paint.valueOf("yellow"));
				else g.setFill(Paint.valueOf("black"));
				g.fillPolygon(face.getX(), face.getY(), face.getnbPtn());
			}

		}
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}

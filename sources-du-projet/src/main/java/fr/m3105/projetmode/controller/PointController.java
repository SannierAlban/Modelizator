package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class PointController extends ViewController{
    @Override
    public void draw() {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        int tabLenght = ((Model) this.getValue()).points[0].length;
        double[][] tempPoint = ((Model) this.getValue()).points;
        for(int i=0;i<tabLenght;i++){
            gc.strokeLine(tempPoint[0][i],tempPoint[1][i],tempPoint[0][i],tempPoint[1][i]);
        }
    }
}

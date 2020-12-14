package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Point;
import javafx.scene.paint.Color;

public class PointController extends ViewController{
    @Override
    public void draw() {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(),gc.getCanvas().getHeight() );
        for (Point p: ((Model) this.getValue()).points){
            gc.strokeLine(p.x,p.y,p.x,p.y);
        }
    }
}

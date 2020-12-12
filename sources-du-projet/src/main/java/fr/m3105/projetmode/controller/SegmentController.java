package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Point;
import javafx.scene.paint.Color;

public class SegmentController extends ViewController{
    @Override
    public void draw() {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Face f : sortFace(((Model) this.getValue()).faces)) {
            for (int i = 0; i < f.getPoints().size(); i++) {
                if (i < f.getPoints().size() - 1) {
                    gc.strokeLine(f.getPoints().get(i).x, f.getPoints().get(i).y, f.getPoints().get(i + 1).x, f.getPoints().get(i + 1).y);
                } else {
                    gc.strokeLine(f.getPoints().get(i).x, f.getPoints().get(i).y, f.getPoints().get(0).x, f.getPoints().get(0).y);
                }
            }
        }
    }
}

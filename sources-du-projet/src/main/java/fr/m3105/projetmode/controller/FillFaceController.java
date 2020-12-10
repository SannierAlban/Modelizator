package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class FillFaceController extends ViewController {
    @Override
    public void draw() {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Face f : sortFace(((Model) this.getValue()).faces)) {
            gc.setFill(Color.RED);
            for (int i = 0; i < f.getPoints().size(); i++) {
                gc.setFill(Color.color((double) f.getRed() / 1000, (double) f.getGreen() / 1000, (double) f.getBlue() / 1000));
                gc.fillPolygon(f.getX(), f.getY(), f.getnbPtn());
            }
        }
    }
}

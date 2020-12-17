package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.Views.CameraView;
import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class FillFaceController extends ViewController {
    @Override
    public void draw() {
        try {
            CameraView cameraView = (CameraView) this.stage;
            if (cameraView.isCamera()){
                Model temp =(Model) this.getValue();
                Model tmpMod = new Model((Model)this.getValue());
                tmpMod.rotateOnYAxis(180);
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                for (Face f : sortFace(tmpMod.faces)){
                    for (int i = 0; i < f.getPoints().size(); i++) {
                        gc.setFill(Color.color((double) f.getRed() / 1000, (double) f.getGreen() / 1000, (double) f.getBlue() / 1000));
                        gc.fillPolygon(f.getX(), f.getY(), f.getnbPtn());
                    }
                }
                tmpMod.points = temp.getPoints();
                tmpMod.faces = temp.getFaces();
            }
        }catch (Exception e){
            System.out.println("coucou");
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            for (Face f : sortFace(((Model) this.getValue()).faces)) {
                for (int i = 0; i < f.getPoints().size(); i++) {
                    gc.setFill(Color.color((double) f.getRed() / 1000, (double) f.getGreen() / 1000, (double) f.getBlue() / 1000));
                    gc.fillPolygon(f.getX(), f.getY(), f.getnbPtn());
                }
            }
        }


    }
}
/*

 */

package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.Views.CameraView;
import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class FillFaceController extends ViewController {
    @Override
    public void draw() {
            int tabLenght = ((Model) this.getValue()).getFaces()[0].length;
            int[][] tempFace = ((Model) this.getValue()).getFaces();
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            for(int k = 0;k<tabLenght;k++){
                double[] x = new double[] {((Model) this.getValue()).getPoint(tempFace[0][k])[0],((Model) this.getValue()).getPoint(tempFace[1][k])[0],((Model) this.getValue()).getPoint(tempFace[2][k])[0]};
                double[] y = new double[] {((Model) this.getValue()).getPoint(tempFace[0][k])[1],((Model) this.getValue()).getPoint(tempFace[1][k])[1],((Model) this.getValue()).getPoint(tempFace[2][k])[1]};
                gc.fillPolygon(x,y,3);
            }
    }
}
/*

 */

package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class SegmentController extends ViewController{
    @Override
    public void draw() {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        int tabLenght = ((Model) this.getValue()).getFaces()[0].length;
        int[][] tempFace = ((Model) this.getValue()).getFaces();
        for(int k = 0;k<tabLenght;k++){
            for(int i = 0;i<3;i++){
                if(i<2){
                    gc.strokeLine(((Model) this.getValue()).getPoint(tempFace[i][k])[0], ((Model) this.getValue()).getPoint(tempFace[i][k])[1], ((Model) this.getValue()).getPoint(tempFace[i+1][k])[0], ((Model) this.getValue()).getPoint(tempFace[i+1][k])[1]);
                }else{
                    gc.strokeLine(((Model) this.getValue()).getPoint(tempFace[i][k])[0], ((Model) this.getValue()).getPoint(tempFace[i][k])[1], ((Model) this.getValue()).getPoint(tempFace[0][k])[0], ((Model) this.getValue()).getPoint(tempFace[0][k])[1]);
                }
            }
        }
    }
}

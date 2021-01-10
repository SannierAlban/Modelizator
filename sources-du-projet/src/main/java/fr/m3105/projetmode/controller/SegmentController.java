package fr.m3105.projetmode.controller;

import javafx.scene.paint.Color;

/**
 * SegmentController permet de dessiner notre modèle avec des segments.
 */
public class SegmentController extends ViewController{
    @Override
    public void draw() {
        if (this.stage.isCamera()){
            graphicsContext.setStroke(Color.RED);
        }else{
            graphicsContext.setStroke(Color.BLACK);
        }
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        int tabLenght = model.getFaces()[0].length;
        int[][] tempFace = model.getFaces();
        for(int k = 0;k<tabLenght;k++){
            for(int i = 0;i<3;i++){
                if(i< tempFace.length-1){
                    graphicsContext.strokeLine(model.getPoint(tempFace[i][k])[0], model.getPoint(tempFace[i][k])[1], model.getPoint(tempFace[i+1][k])[0], model.getPoint(tempFace[i+1][k])[1]);
                }else{
                    graphicsContext.strokeLine(model.getPoint(tempFace[i][k])[0], model.getPoint(tempFace[i][k])[1], model.getPoint(tempFace[0][k])[0], model.getPoint(tempFace[0][k])[1]);
                }
            }
        }
    }
}

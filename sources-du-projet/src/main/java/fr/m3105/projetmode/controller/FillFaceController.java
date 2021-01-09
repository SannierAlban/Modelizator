package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class FillFaceController extends ViewController {
    @Override
    public void draw() {
        if (stage.isCamera()) {
            Model bidule = new Model(this.model);
            bidule.rotateOnXAxis(Math.PI);
            dessine(bidule);
        } else {
            dessine(this.model);
        }
    }

    public void dessine(Model model) {
        int tabLenght = model.getFaces()[0].length;
        sortFace(model.getFaces());
        int[][] tempFace = model.getFaces();
        
        if (lightsOn) applyLights(new double[]{0, 1, 0});
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        for (int k = 0; k < tabLenght; k++) {
            int pos = 0;
            for (int i = 0; i < tabLenght; i++) {
                if (tempFace[0][k] == model.getFaces()[0][i] && tempFace[1][k] == model.getFaces()[1][i] && tempFace[2][k] == model.getFaces()[2][i]) {
                    pos = i;
                    break;
                }
            }
            //System.out.println(pos);
            graphicsContext.setFill(Color.color((double) model.getRgbAlpha()[0][pos] / 255, (double) model.getRgbAlpha()[1][pos] / 255, (double) model.getRgbAlpha()[2][pos] / 255));
            double[] x = new double[]{model.getPoint(tempFace[0][k])[0], model.getPoint(tempFace[1][k])[0], model.getPoint(tempFace[2][k])[0]};
            double[] y = new double[]{model.getPoint(tempFace[0][k])[1], model.getPoint(tempFace[1][k])[1], model.getPoint(tempFace[2][k])[1]};
            graphicsContext.fillPolygon(x, y, 3);
        }
    }
}
/*

 */

package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Model;
import javafx.scene.paint.Color;

public class FillFaceController extends ViewController {
    @Override
    public void draw() {
        Model model = ((Model) this.getValue());
        int tabLenght = model.getFaces()[0].length;
        int[][] tempFace = sortFace(model.getFaces());
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        for (int k = 0; k < tabLenght; k++) {
            if (model.isColor() && !model.isRgbSurPoints()){
                graphicsContext.setFill(Color.color((double)model.getRgbAlpha()[0][k]/1000,(double)model.getRgbAlpha()[1][k]/1000,(double)model.getRgbAlpha()[2][k]/1000));
            }
            double[] x = new double[]{model.getPoint(tempFace[0][k])[0], model.getPoint(tempFace[1][k])[0], model.getPoint(tempFace[2][k])[0]};
            double[] y = new double[]{model.getPoint(tempFace[0][k])[1], model.getPoint(tempFace[1][k])[1], model.getPoint(tempFace[2][k])[1]};
            graphicsContext.fillPolygon(x, y, 3);
        }
    }
}
/*

 */

package fr.m3105.projetmode.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(75,75,100,100);
        gc.fillRect(75*0.8,75*0.8,100,100);
       // gc.fillPolygon( 75,75*0.8,2);
        gc.setFill(Color.BLACK);

        for (int i = 0; i<2;i++){
            System.out.println((Math.pow(0.8,i)));
            if (i>0){
                gc.strokeLine(75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i)));
                gc.strokeLine(75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100);
                //gc.strokeLine(75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i))+100);
                //gc.strokeLine(75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i))+100);
            }else{
                gc.strokeLine(75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i)));
                gc.strokeLine(75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100);
                gc.strokeLine(75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i))+100);
                gc.strokeLine(75*(Math.pow(0.8,i)),75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i))+100,75*(Math.pow(0.8,i))+100);
            }
        }

        gc.strokeLine(75,75,75*0.8,75*0.8);
        gc.strokeLine(175,75,(75*0.8)+100,(75*0.8));
        gc.strokeLine(75,175,75*0.8,(75*0.8)+100);
        //gc.strokeLine(175,175,(75*0.8)+100,(75*0.8)+100);


    }
}

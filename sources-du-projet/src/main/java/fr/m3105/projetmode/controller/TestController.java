package fr.m3105.projetmode.controller;

import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Point;
import fr.m3105.projetmode.model.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //System.out.println(canvas.getScaleX());

        gc.setFill(Color.BLUE);

        Point p0 = new Point(150,150,150);
        Point p1 = new Point(100,150,150);
        Point p2 = new Point(100,100,150);
        Point p3 = new Point(150,100,150);
        Point p4 = new Point(150,150,100);
        Point p5 = new Point(100,150,100);
        Point p6 = new Point(100,100,100);
        Point p7 = new Point(150,100,100);

        ArrayList<Point> points = new ArrayList<>();
        points.add(p0);points.add(p1);points.add(p2);points.add(p3);points.add(p4);points.add(p5);points.add(p6);points.add(p7);

        int[][] project = new int[2][3];
        project[0][0] = 1;
        project[0][1]  = 0;
        project[0][2]  = 0;
        project[1][0]  = 0;
        project[1][1]  = 1;
        project[1][2]  = 0;

        Model m = new Model();

        m.rotateOnXAxis(3.14159/8);
        m.rotateOnYAxis(3.14159/8);
        m.translate(new Vector(150,150,0));

        for (Point p: m.points){
            double newX = p.x * project[0][0] + p.y * project[0][1] + p.z * project[0][2];
            double newY = p.x * project[1][0] + p.y * project[1][1] + p.z * project[1][2];
//           gc.setFill(Color.YELLOW);
//           gc.fillPolygon(m.getFaces().get(0).getX(),m.getFaces().get(0).getY(),4);
//           gc.setFill(Color.RED);
//           gc.fillPolygon(m.getFaces().get(1).getX(),m.getFaces().get(1).getY(),4);
//           gc.setFill(Color.GREEN);
//           gc.fillPolygon(m.getFaces().get(2).getX(),m.getFaces().get(2).getY(),4);
//           gc.setFill(Color.PINK);
//           gc.fillPolygon(m.getFaces().get(3).getX(),m.getFaces().get(3).getY(),4);
//           gc.setFill(Color.BLUE);
//           gc.fillPolygon(m.getFaces().get(4).getX(),m.getFaces().get(4).getY(),4);
//
//           gc.setFill(Color.PURPLE);
//           gc.fillPolygon(m.getFaces().get(5).getX(),m.getFaces().get(5).getY(),4);

             gc.setFill(Color.BLACK);
             gc.strokeLine(newX,newY,newX,newY);
        }

        for (Face f: m.faces){
            for (int i = 0; i <f.getPoints().size();i++){
                if (i < f.getPoints().size()- 1){
                    gc.strokeLine(f.getPoints().get(i).x,f.getPoints().get(i).y,f.getPoints().get(i+1).x,f.getPoints().get(i+1).y);
                }else{
                    gc.strokeLine(f.getPoints().get(i).x,f.getPoints().get(i).y,f.getPoints().get(0).x,f.getPoints().get(0).y);
                }
            }
        }

        //gc.strokeLine(50,50,50,50);
        //gc.strokeLine(100,100,100,100);
        //gc.strokeLine(50,100,50,100);
        //gc.strokeLine(100,50,100,50);


       /* gc.fillRect(75,75,100,100);
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

        */
    }


}

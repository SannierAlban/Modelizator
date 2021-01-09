package fr.m3105.projetmode.model;

import java.io.File;

public class ModelSplit {

    private int vertex;
    private int nbFaces;
    public double[][] points;
    private int nbDePoints;
    //(with n an int superior to 0 and v an int between 0 and 6)
    //Basically, this array contains n FACES and n*v points, therefore each column contains v references to its respectful points located in the points array.
    //Because this 2d array only contains FACES and references to points, the values of this array don't need to be changed
    public int[][] FACES;

    private int[][] rgbAlpha;
    private int[][] baseRGB;

    private boolean color;
    private boolean alpha;
    private boolean rgbSurPoints;
	
	public ModelSplit(File f,int nombreDeCoupe) {
        Parser parser = new Parser(f.getPath(), false);
        vertex = parser.getVertex();
        nbFaces = parser.getNbFaces();

        points = parser.getPoints();
        nbDePoints = points[0].length;
        FACES = parser.getFaces();
        rgbAlpha = parser.getRgbAlpha();

        color = parser.isColor();
        alpha = parser.isAlpha();
        rgbSurPoints = parser.isRgbSurPoints();
        
        if(color) {
        	baseRGB = new int[rgbAlpha.length][rgbAlpha[0].length];
        	for(int idx=0;idx<rgbAlpha[0].length;idx++) {
        		for(int idxColor=0;idxColor<3;idxColor++) {
        			baseRGB[idxColor][idx] = rgbAlpha[idxColor][idx];
        		}
        	}
        }
	}
	
	public ModelSplit getModelSplit() {
		return this;
	}
}

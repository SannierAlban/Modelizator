package fr.m3105.projetmode.utils;

import java.util.ArrayList;

public class Model {
	
	private int vertex;
	private final String NAME;
	private int nbFaces;
	private final String PATH;
	public ArrayList<Point3> points;
	public ArrayList<Face> faces;
	
	//basic constructor
	public Model(String path) {
		PATH = "";
		NAME = "empty";
	}
	
	//tests constructor
	public Model() {
		vertex = 8;
		NAME = "cube";
		nbFaces = 6;
		PATH = "exemples/cube.ply";
		points = new ArrayList<Point3>();
		points.add(new Point3(0, 0, 0));
		points.add(new Point3(0, 4, 0));
		points.add(new Point3(4, 0, 0));
		points.add(new Point3(4, 4, 0));
		faces.add(new Face(new Point3[] {points.get(0),points.get(1),points.get(2),points.get(3),},1,1,1,1));
	}
}

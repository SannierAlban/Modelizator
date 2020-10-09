package fr.m3105.projetmode.model;

import java.util.ArrayList;

public class Model {
	
	private int vertex;
	private final String NAME;
	private int nbFaces;
	private final String PATH;
	public ArrayList<Point> points;
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
		points = new ArrayList<Point>();
		faces = new ArrayList<Face>();
		points.add(new Point(0, 0, 0));
		points.add(new Point(0, 4, 0));
		points.add(new Point(4, 0, 0));
		points.add(new Point(4, 4, 0));
		faces.add(new Face(new Point[] {points.get(0),points.get(1),points.get(2),points.get(3)}));
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Model [vertex=" + vertex + ", NAME=" + NAME + ", nbFaces=" + nbFaces + ", PATH=" + PATH + "]\nPOINTS :\n");
		for(Point tmp:points) res.append(tmp.toString(false)+"\n");
		for(Face tmp:faces) res.append(tmp.toString()+"\n");
		return res.toString();
	}
	
}

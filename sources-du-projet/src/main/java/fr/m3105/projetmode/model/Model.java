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
		
		int pa = 25;
		int pb = 100;
		
		points.add(new Point(pa,pa,pa));		//stock tout les point lu dans une arraylist
		points.add(new Point(pa,pb,pa));
		points.add(new Point(pb,pa,pb));
		points.add(new Point(pa,pb,pb));
		points.add(new Point(pa,pa,pb));
		points.add(new Point(pb,pb,pa));
		points.add(new Point(pb,pa,pa));
		points.add(new Point(pb,pb,pb));
		
		ArrayList<Point> a = new ArrayList<Point>(); 
		a.add(new Point(points.get(0)));				//distribut les points dans leur face correspondante
		a.add(new Point(points.get(6)));
		a.add(new Point(points.get(2)));
		a.add(new Point(points.get(4)));
		faces.add(new Face(a));

		ArrayList<Point> b = new ArrayList<Point>(); 
		b.add(new Point(points.get(1)));				
		b.add(new Point(points.get(3)));
		b.add(new Point(points.get(7)));
		b.add(new Point(points.get(5)));
		faces.add(new Face(b));

		
		ArrayList<Point> c = new ArrayList<Point>(); 
		c.add(new Point(points.get(5)));				
		c.add(new Point(points.get(7)));
		c.add(new Point(points.get(2)));
		c.add(new Point(points.get(6)));
		faces.add(new Face(c));

		ArrayList<Point> d = new ArrayList<Point>(); 
		d.add(new Point(points.get(1)));				
		d.add(new Point(points.get(0)));
		d.add(new Point(points.get(4)));
		d.add(new Point(points.get(3)));
		faces.add(new Face(d));

		ArrayList<Point> e = new ArrayList<Point>(); 
		e.add(new Point(points.get(3)));				
		e.add(new Point(points.get(4)));
		e.add(new Point(points.get(2)));
		e.add(new Point(points.get(7)));
		faces.add(new Face(e));
		
		ArrayList<Point> p = new ArrayList<Point>(); 
		p.add(new Point(points.get(1)));				
		p.add(new Point(points.get(5)));
		p.add(new Point(points.get(6)));
		p.add(new Point(points.get(0)));
		faces.add(new Face(p));
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Model [vertex=" + vertex + ", NAME=" + NAME + ", nbFaces=" + nbFaces + ", PATH=" + PATH + "]\nPOINTS :\n");
		for(Point tmp:points) res.append(tmp.toString(false)+"\n");
		for(Face tmp:faces) res.append(tmp.toString()+"\n");
		return res.toString();
	}
	
	public ArrayList<Face> getFaces() {
		return faces;
	}
}

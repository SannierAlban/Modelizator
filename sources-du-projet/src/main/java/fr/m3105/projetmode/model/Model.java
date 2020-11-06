package fr.m3105.projetmode.model;

import java.util.ArrayList;
import fr.m3105.projetmode.model.Point;

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
		
		points.add(new Point(0,0,0));		//stock tout les point lu dans une arraylist
		points.add(new Point(0,4,0));
		points.add(new Point(4,0,4));
		points.add(new Point(0,4,4));
		points.add(new Point(0,0,4));
		points.add(new Point(4,4,0));
		points.add(new Point(4,0,0));
		points.add(new Point(4,4,4));
		
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
		
		System.out.println(this.toString());
	}
	public Model(ArrayList<Point> points) {
		this();
		this.points = points;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Model [vertex=" + vertex + ", NAME=" + NAME + ", nbFaces=" + nbFaces + ", PATH=" + PATH + "]\nPOINTS :\n");
		for(Point tmp:points) res.append(tmp.toString()+"\n");
		for(Face tmp:faces) res.append(tmp.toString()+"\n");
		res.append("Center point : "+getCenter().toString());
		return res.toString();
	}
	
	public ArrayList<Face> getFaces() {
		return faces;
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}

	public Point getCenter() {
		double xsum=0, ysum=0, zsum=0;
		for(Point i:points) {
			xsum+=i.x;
			ysum+=i.y;
			zsum+=i.z;
		} 
		return new Point(xsum/points.size(),ysum/points.size(),zsum/points.size());
	}
	/**
	 * Rotates the entire Model on the X axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3 dimensions points
	 * @param angle the Model will rotate
	 */
	public void rotateOnXAxis(double angle) {
		final short NB_DIMENSIONS = 3;
		final double[][] ROTATION_MATRIX = new double[][]{ {1,0,0},{0,Math.cos(angle),-Math.sin(angle),0},{0,Math.sin(angle),Math.cos(angle)}};
		//is the array only composed of Points3 ? 
			
			//for ligne res
			for(int idxPoint=0;idxPoint<points.size();idxPoint++) {
				Point crtPoint = points.get(idxPoint);
				//creating the new point
				double[] tmpCoords = new double[NB_DIMENSIONS];
				for(int idxNewPoint=0;idxNewPoint<NB_DIMENSIONS;idxNewPoint++) {
					
					tmpCoords[idxNewPoint] = ROTATION_MATRIX[idxNewPoint][0]*crtPoint.x + ROTATION_MATRIX[idxNewPoint][1]*crtPoint.y + ROTATION_MATRIX[idxNewPoint][2]*crtPoint.z;
				}
				System.out.println(
						"New coords of Point "+idxPoint+" : coords "+points.get(idxPoint).toString()+" INTO "+new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]).toString());
				points.set(idxPoint,new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]));
						
			}
	}
}

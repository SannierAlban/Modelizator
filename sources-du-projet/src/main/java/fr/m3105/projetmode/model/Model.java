package fr.m3105.projetmode.model;

import java.sql.Array;
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
	/**
	 * DO NOT USE : FOR TEST PURPOSES
	 * @param points
	 */
	public Model(ArrayList<Point> points) {
		this.points = points;
		this.NAME = "";
		this.PATH = "";
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Model [vertex=" + vertex + ", NAME=" + NAME + ", nbFaces=" + nbFaces + ", PATH=" + PATH + "]\nPOINTS :\n");
		for(Point tmp:points) res.append(tmp.toString()+"\n");
		for(Face tmp:faces) res.append(tmp.toString()+"\n");
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
	 * Translates the Model using a Vector
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points and 3d vectors
	 * @param v Vector representing the directions and distance all the points will translate
	 */
	public void translate(Vector v) {
		for(int idxPoints=0;idxPoints<points.size();idxPoints++) {
			Point tmp = points.get(idxPoints);
			points.set(idxPoints, new Point(tmp.x+v.x,tmp.y+v.y,tmp.z+v.z));
		}
	}
	
	
	/**
	 * Increases or decreases the size of the Model (param superior to 0 and inferior to 1 = zoom out, param superior to 1 (excluded) = zoom in)
	 * @param relation positive double value representing the amount of zoom 
	 */
	public void zoom(double relation) {
		transformPoints(new double[][] {{relation,0,0},{0,relation,0},{0,0,relation}});
	}
	
	/**
	 * Rotates the entire Model on the X axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnXAxis(double angle) {
		
		transformPoints(new double[][]{ {1,0,0},{0,Math.cos(angle),-Math.sin(angle)},{0,Math.sin(angle),Math.cos(angle)}});
	}
	
	
	/**
	 * Rotates the entire Model on the Y axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnYAxis(double angle) {
		
		transformPoints(new double[][]{ {Math.cos(angle),0,-Math.sin(angle)},{0,1,0},{Math.sin(angle),0,Math.cos(angle)}});
	}
	
	
	/**
	 * Rotates the entire Model on the Z axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnZAxis(double angle) {
	
		transformPoints(new double[][]{ {Math.cos(angle),-Math.sin(angle),0},{Math.sin(angle),-Math.cos(angle),0},{0,0,1}});
	}
	

	private void transformPoints(final double[][] TRANSFORM_MATRIX) {
		final short NB_DIMENSIONS = 3;
		ArrayList<Face> tempFaces = new ArrayList<>();
		ArrayList<Point> tempPoints = new ArrayList<>(points);
		for(int idxPoint=0;idxPoint<points.size();idxPoint++) {
			
			Point crtPoint = points.get(idxPoint);
			
			//creating the new point
			double[] tmpCoords = new double[NB_DIMENSIONS];
			for(int idxNewPoint=0;idxNewPoint<NB_DIMENSIONS;idxNewPoint++) {	
				tmpCoords[idxNewPoint] = TRANSFORM_MATRIX[idxNewPoint][0]*crtPoint.x + TRANSFORM_MATRIX[idxNewPoint][1]*crtPoint.y + TRANSFORM_MATRIX[idxNewPoint][2]*crtPoint.z;
			}/*
			System.out.println(
					"New coords of Point "+idxPoint+" : coords "+points.get(idxPoint).toString()+" INTO "+new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]).toString());*/
			points.set(idxPoint,new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]));		
		}


	}
}

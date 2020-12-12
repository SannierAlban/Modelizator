package fr.m3105.projetmode.model;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

import fr.m3105.projetmode.model.utils.MultiThreadTranslate;

public class Model {
	
	private int vertex;
	private int nbFaces;
	
	public ArrayList<Point> points;
	public ArrayList<Face> faces;
	
	
	//basic constructor
	public Model(File f) {
		Parser parser = new Parser(f.getPath());
		points = parser.points;
		for (Point p:points){
			p.x = p.x * -1;
			p.y = p.y * -1;
			p.z = p.z * -1;
		}
		faces = parser.faces;
		for (Face face:faces){
			for (Point p :face.getPoints()){
				p.x = p.x * -1;
				p.y = p.y * -1;
				p.z = p.z * -1;
			}
		}
		nbFaces = parser.nbFaces;
		vertex = parser.points.size();
	}
	
	//tests constructor
	public Model() {
		vertex = 8;
		nbFaces = 6;
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
		this.faces = new ArrayList<>();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Model [vertex=" + vertex + ", nbFaces=" + nbFaces + ", PATH=]\nPOINTS :\n");
		for(Point tmp:points) res.append(tmp.toString()+"\n");
		for(Face tmp:faces) res.append(tmp.toString()+"\n");
		return res.toString();
	}
	
	public ArrayList<Face> getFaces() {
		return faces;
	}
	
	public void setFace(int idx,Face f) {
		if(f!=null) faces.set(idx, f);
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	/**
	 * Returns the center of the Model. The method consists of creating a virtual container of all the points and returning the center of this container
	 * WARNING : This method shouldn't be used using a basic and precise Model, else the Point returned won't approach the real center of the Model
	 * @return Point Representing the center of the Model
	 */
	public Point getComplexCenter() {
		double xmin = 0,xmax = 0,ymin = 0,ymax = 0,zmin = 0,zmax = 0;
		for(int i=0;i<points.size();i++) {
			Point tmp = points.get(i);
			if(tmp.x<=xmin) xmin = tmp.x;
			if(tmp.y<=ymin) ymin = tmp.y;
			if(tmp.x<=zmin) zmin = tmp.z;
			if(tmp.x>=xmax) xmax = tmp.x;
			if(tmp.y>=ymax) ymax = tmp.y;
			if(tmp.z>=zmax) zmax = tmp.z;
		}
		return new Point((xmin+xmax)/2,(ymin+ymax)/2,(zmin+zmax)/2);
	}
	
	/**
	 * Returns the center of the Model. The center is calculated using the average points coordinates
	 * WARNING : This method shouldn't be used using a complex Model, else the Point returned won't approach the real center of the Model
	 * @return Point Representing the center of the Model
	 */
	public Point getCenter() {
		if(points.size()>=30) return getComplexCenter();
		else {
			double xsum=0, ysum=0, zsum=0;
			for(Point tmp:points) {
				xsum+=tmp.x;
				ysum+=tmp.y;
				zsum+=tmp.z;
			}
			int size = points.size();
			return new Point(xsum/size,ysum/size,zsum/size);
		}
	}
	
	/**
	 * Translates the Model using a Vector
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points and 3d vectors
	 * @param v Vector representing the directions and distance all the points will translate
	 */
	public void translate(Vector v) {
//		ArrayList<Point> tempPoints = new ArrayList<>(points);
//		for(int idxPoints=0;idxPoints<points.size();idxPoints++) {
//			Point tmp = points.get(idxPoints);
//			points.set(idxPoints, new Point(tmp.x+v.x,tmp.y+v.y,tmp.z+v.z));
//		}
//		restructureFace(tempPoints);
		translateMultiThread(v);
	}
	
	public void translateMultiThread(Vector v) {
		MultiThreadTranslate[] multiThread = new MultiThreadTranslate[Runtime.getRuntime().availableProcessors()];
		for (int i = 0; i < multiThread.length; i++) {
			multiThread[i] = new MultiThreadTranslate(this.points,v);
			multiThread[i].start();
		}
		try {
			for (int i = 0; i < multiThread.length; i++) {
				multiThread[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("translateMultiThread Fin");
	}
	
	/**
	 * Increases or decreases the size of the Model (param superior to 0 and inferior to 1 = zoom out, param superior to 1 (excluded) = zoom in)
	 * @param relation positive double value representing the amount of zoom 
	 */
	public void zoom(double relation) {
		final Point CENTER = getCenter();
		translate(new Vector(-CENTER.x,-CENTER.y,-CENTER.z));
		transformPoints(new double[][] {{relation,0,0},{0,relation,0},{0,0,relation}});
		translate(new Vector(CENTER.x,CENTER.y,CENTER.z));
	}
	
	/**
	 * Rotates the entire Model on the X axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnXAxis(double angle) {
		final Point CENTER = getCenter();
		translate(new Vector(-CENTER.x,-CENTER.y,-CENTER.z));
		transformPoints(new double[][]{ {1,0,0},{0,Math.cos(angle),-Math.sin(angle)},{0,Math.sin(angle),Math.cos(angle)}});
		translate(new Vector(CENTER.x,CENTER.y,CENTER.z));
	}
	
	
	/**
	 * Rotates the entire Model on the Y axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnYAxis(double angle) {
		final Point CENTER = getCenter();
		translate(new Vector(-CENTER.x,-CENTER.y,-CENTER.z));
		transformPoints(new double[][]{ {Math.cos(angle),0,-Math.sin(angle)},{0,1,0},{Math.sin(angle),0,Math.cos(angle)}});
		translate(new Vector(CENTER.x,CENTER.y,CENTER.z));
	}
	
	
	/**
	 * Rotates the entire Model on the Z axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnZAxis(double angle) {
		final Point CENTER = getCenter();
		translate(new Vector(-CENTER.x,-CENTER.y,-CENTER.z));
		transformPoints(new double[][]{ {Math.cos(angle),-Math.sin(angle),0},{Math.sin(angle),Math.cos(angle),0},{0,0,1}});
		translate(new Vector(CENTER.x,CENTER.y,CENTER.z));
	}
	
	/**
	 * Transforms all the points of the model using the parameter 2d double array
	 * @param TRANSFORM_MATRIX A 2d double array representing a matrix
	 */
	private void transformPoints(final double[][] TRANSFORM_MATRIX) {
		final short NB_DIMENSIONS = 3;
		ArrayList<Point> tempPoints = new ArrayList<>(points);
		for(int idxPoint=0;idxPoint<points.size();idxPoint++) {
			
			Point crtPoint = points.get(idxPoint);
			
			//creating the new point
			double[] tmpCoords = new double[NB_DIMENSIONS];
			for(int idxNewPoint=0;idxNewPoint<NB_DIMENSIONS;idxNewPoint++) {	
				tmpCoords[idxNewPoint] = TRANSFORM_MATRIX[idxNewPoint][0]*crtPoint.x + TRANSFORM_MATRIX[idxNewPoint][1]*crtPoint.y + TRANSFORM_MATRIX[idxNewPoint][2]*crtPoint.z;
			}
			//System.out.println(
			//	"New coords of Point "+idxPoint+" : coords "+points.get(idxPoint).toString()+" INTO "+new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]).toString());
			points.set(idxPoint,new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]));		
		}
		restructureFace(tempPoints);
	}
	
	private void transformPointsv2(final double[][] TRANSFORM_MATRIX) {
		final short NB_DIMENSIONS = 3;
		for(int idxFace=0;idxFace<faces.size();idxFace++) {
			Face tmpFace = faces.get(idxFace);
			
			for(int idxPoint=0;idxPoint<tmpFace.getPoints().size();idxPoint++) {
				
				Point crtPoint = tmpFace.getPoint(idxPoint);
				//creating the new point
				double[] tmpCoords = new double[NB_DIMENSIONS];
				for(int idxNewPoint=0;idxNewPoint<NB_DIMENSIONS;idxNewPoint++) {	
					tmpCoords[idxNewPoint] = TRANSFORM_MATRIX[idxNewPoint][0]*crtPoint.x + TRANSFORM_MATRIX[idxNewPoint][1]*crtPoint.y + TRANSFORM_MATRIX[idxNewPoint][2]*crtPoint.z;
				}
				//System.out.println(
				//	"New coords of Point "+idxPoint+" : coords "+points.get(idxPoint).toString()+" INTO "+new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]).toString());
				tmpFace.setPoint(idxPoint,new Point(tmpCoords[0],tmpCoords[1],tmpCoords[2]));	
			}
			setFace(idxFace, tmpFace);
		}
	}

	public void restructureFace(ArrayList<Point> tempPoints){
		int i = 0;
		for (Point p:tempPoints){
			for (Face f: faces){
				//System.out.println("p = " + p + " id= " + i + " val= " + points.get(tempPoints.indexOf(p)));
				f.replace(p,points.get(i));
			}
			i++;
		}

	}
}

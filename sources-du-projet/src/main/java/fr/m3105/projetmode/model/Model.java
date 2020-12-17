package fr.m3105.projetmode.model;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

public class Model {

	private int vertex;
	private int nbFaces;
	public double[][] points;

	//(with n an int superior to 0 and v an int between 0 and 6)
	//Basically, this array contains n FACES and n*v points, therefore each column contains v references to its respectful points located in the points array.
	//Because this 2d array only contains FACES and references to points, the values of this array doesn't need to be changed
	public final int[][] FACES;
/*
	//basic constructor
	public Model(File f) {
		Parser parser = new Parser(f.getPath());
		points = parser.points;
		for (Point p:points){
			p.x = p.x * -1;
			p.y = p.y * -1;
			p.z = p.z * -1;
		}
		FACES = parser.faces;
		for (Face face:FACES){
			for (Point p :face.getPoints()){
				p.x = p.x * -1;
				p.y = p.y * -1;
				p.z = p.z * -1;
			}
		}
		nbFaces = parser.nbFaces;
		vertex = parser.points.size();
	}*/
	/*
	//tests constructor
	public Model() {
		vertex = 8;
		nbFaces = 6;
		points = new ArrayList<Point>();
		FACES = new ArrayList<Face>();
		
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
		FACES.add(new Face(a));

		ArrayList<Point> b = new ArrayList<Point>(); 
		b.add(new Point(points.get(1)));				
		b.add(new Point(points.get(3)));
		b.add(new Point(points.get(7)));
		b.add(new Point(points.get(5)));
		FACES.add(new Face(b));

		
		ArrayList<Point> c = new ArrayList<Point>(); 
		c.add(new Point(points.get(5)));				
		c.add(new Point(points.get(7)));
		c.add(new Point(points.get(2)));
		c.add(new Point(points.get(6)));
		FACES.add(new Face(c));

		ArrayList<Point> d = new ArrayList<Point>(); 
		d.add(new Point(points.get(1)));				
		d.add(new Point(points.get(0)));
		d.add(new Point(points.get(4)));
		d.add(new Point(points.get(3)));
		FACES.add(new Face(d));

		ArrayList<Point> e = new ArrayList<Point>(); 
		e.add(new Point(points.get(3)));				
		e.add(new Point(points.get(4)));
		e.add(new Point(points.get(2)));
		e.add(new Point(points.get(7)));
		FACES.add(new Face(e));
		
		ArrayList<Point> p = new ArrayList<Point>(); 
		p.add(new Point(points.get(1)));				
		p.add(new Point(points.get(5)));
		p.add(new Point(points.get(6)));
		p.add(new Point(points.get(0)));
		FACES.add(new Face(p));
	}*/
	/**
	 * DO NOT USE : FOR TEST PURPOSES WITHOUT USE OF THE FACES
	 * @param points
	 */
	public Model(double[][] points) {
		this.points = points;
		this.FACES = new int[0][0];
	}


	//toString functions :

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Model [vertex=" + vertex + ", nbFaces=" + nbFaces + ", PATH=]\nPOINTS :\n");
		int pointsLength=points[0].length,facesLength=FACES[0].length;
		for(int idxPoint=0;idxPoint<pointsLength;idxPoint++) {
			res.append("point "+idxPoint+" [x = "+points[0][idxPoint]+" | y = "+points[1][idxPoint]+" | z = "+points[2][idxPoint]+"]\n");
		}
		for(int idxFace=0;idxFace<facesLength;idxFace++) {
			String tmp = "face "+idxFace+" [ ";
			int nbFacesPoints = FACES.length/FACES[0].length;
			for(int idxFacesPoint=0;idxFacesPoint<nbFacesPoints;idxFacesPoint++) {
				tmp+="index_p"+idxFacesPoint+" = "+FACES[idxFacesPoint][idxFace];
			}
			res.append(tmp+"]\n");
		}

		return res.toString();
	}


	//getters and setters of FACES and points :

	public double[] getPoint(int idxPoint) {
		if(idxPoint<points[0].length) return new double[] {points[0][idxPoint],points[1][idxPoint],points[2][idxPoint]};
		else throw new ArrayIndexOutOfBoundsException();
	}

	public double[][] getPoints() {
		return points;
	}

	private boolean setPoint(int idxPoint, double[] newCoordinates) {
		final int length = newCoordinates.length;
		if(idxPoint<points[0].length && length==3) {
			for(int axis=0;axis<length;axis++) {
				points[axis][idxPoint] = newCoordinates[axis];
			}
		}else return false;
		return true;
	}

	public double[] getFace(int idxFace) {
		if(idxFace<points[0].length) return new double[] {FACES[0][idxFace],FACES[1][idxFace],FACES[2][idxFace]};
		else throw new ArrayIndexOutOfBoundsException();
	}

	public int[][] getFaces() {
		return FACES;
	}

	/**
	 * Returns the center of the Model. The method consists of creating a virtual container of all the points and returning the center of this container
	 * WARNING : This method shouldn't be used using a basic and precise Model, else the Point returned won't approach the real center of the Model
	 * @return Point Representing the center of the Model
	 */
	private double[] getComplexCenter() {
		double xmin = 0,xmax = 0,ymin = 0,ymax = 0,zmin = 0,zmax = 0;
		for(int i=0;i<points.length;i++) {
			double[] tmp = {points[0][i],points[1][i],points[2][i]};
			if(tmp[0]<=xmin) xmin = tmp[0];
			if(tmp[1]<=ymin) ymin = tmp[1];
			if(tmp[2]<=zmin) zmin = tmp[2];
			if(tmp[0]>=xmax) xmax = tmp[0];
			if(tmp[1]>=ymax) ymax = tmp[1];
			if(tmp[2]>=zmax) zmax = tmp[2];
		}
		return new double[] {(xmin+xmax)/2,(ymin+ymax)/2,(zmin+zmax)/2};
	}

	/**
	 * Returns the center of the Model. The center is calculated using the average points coordinates
	 * WARNING : This method shouldn't be used using a complex Model, else the Point returned won't approach the real center of the Model
	 * @return Point Representing the center of the Model
	 */
	public double[] getCenter() {
		final int NBMIN_POINTS_COMPLEX_MODEL = 30;
		if(points[0].length>=NBMIN_POINTS_COMPLEX_MODEL) return getComplexCenter();
		else {
			int length = points[0].length;
			//System.out.println("points[0].length :"+points[0].length+", points.length :"+points.length);
			double xsum=0, ysum=0, zsum=0;
			for(int idxPoint=0;idxPoint<length;idxPoint++) {
				System.out.println("CENTER : Working with "+toStringPoint(idxPoint));
				xsum+=points[0][idxPoint];
				ysum+=points[1][idxPoint];
				zsum+=points[2][idxPoint];
			}

			return new double[] {xsum/length,ysum/length,zsum/length};
		}
	}

	/**
	 * Translates the Model using a Vector
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points and 3d vectors
	 * @param vector v representing the directions and distance all the points will translate
	 */
	public void translate(double[] vector) {
		final int length = points[0].length;
		System.out.println("Translating by "+String.format("X : %.3f / Y : %.3f / Z : %.3f",vector[0],vector[1],vector[2]));
		for(int idxPoints=0;idxPoints<length;idxPoints++) {
			for(int axis=0;axis<3;axis++) {
				points[axis][idxPoints]+=vector[axis];
			}
		}
	}


	/**
	 * Increases or decreases the size of the Model (param superior to 0 and inferior to 1 = zoom out, param superior to 1 (excluded) = zoom in)
	 * @param relation positive double value representing the amount of zoom
	 */
	public void zoom(double relation) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][] {{relation,0,0},{0,relation,0},{0,0,relation}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}

	/**
	 * Rotates the entire Model on the X axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnXAxis(double angle) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][]{ {1,0,0},{0,Math.cos(angle),-Math.sin(angle)},{0,Math.sin(angle),Math.cos(angle)}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}


	/**
	 * Rotates the entire Model on the Y axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnYAxis(double angle) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][]{ {Math.cos(angle),0,-Math.sin(angle)},{0,1,0},{Math.sin(angle),0,Math.cos(angle)}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}


	/**
	 * Rotates the entire Model on the Z axis using the double parameter clockwise
	 * More precisely, it overwrites the previous values of the Model.points array
	 * WARNING: This method only works with 3d points
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnZAxis(double angle) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][]{ {Math.cos(angle),-Math.sin(angle),0},{Math.sin(angle),Math.cos(angle),0},{0,0,1}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}

	private void transformPoints(final double[][] TRANSFORM_MATRIX) {
		final short NB_AXIS = 3;
		final int length = points[0].length;
		for(int idxPoint=0;idxPoint<length;idxPoint++) {

			double[] crtPoint = getPoint(idxPoint);
			//creating the new point
			double[] tmpCoords = new double[NB_AXIS];
			for(int idxNewPoint=0;idxNewPoint<NB_AXIS;idxNewPoint++) {
				tmpCoords[idxNewPoint] = TRANSFORM_MATRIX[idxNewPoint][0]*crtPoint[0]
						+ TRANSFORM_MATRIX[idxNewPoint][1]*crtPoint[1]
						+ TRANSFORM_MATRIX[idxNewPoint][2]*crtPoint[2];
			}
			System.out.println(
					"TRANSFORMATION : New coords of Point "+idxPoint+" : coords "+toStringPoint(idxPoint)+" INTO  "+String.format("X : %.3f / Y : %.3f / Z : %.3f",tmpCoords[0],tmpCoords[1],tmpCoords[2]));
			setPoint(idxPoint,new double[]{tmpCoords[0],tmpCoords[1],tmpCoords[2]});
		}
	}

	/**
	 * A basic toString function dealing with points. Given an integer (basically the index), this function will return a String containing the 3 coordinates of this given point
	 * @param idxPoint int : the index of the point located in the points array
	 * @return String textual representation of the point
	 */
	public String toStringPoint(int idxPoint) {
		return "[Point "+idxPoint+" "+String.format("X : %.3f / Y : %.3f / Z : %.3f",points[0][idxPoint],points[1][idxPoint],points[2][idxPoint])+"]";
	}
}
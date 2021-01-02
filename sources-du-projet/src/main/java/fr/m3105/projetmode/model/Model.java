package fr.m3105.projetmode.model;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Model {
	
	private int vertex;
	private int nbFaces;
	public double[][] points;

	//(with n an int superior to 0 and v an int between 0 and 6)
	//Basically, this array contains n FACES and n*v points, therefore each column contains v references to its respectful points located in the points array.
	//Because this 2d array only contains FACES and references to points, the values of this array don't need to be changed
	public final int[][] FACES;
	
	private int[][] rgbAlpha;
	
	private boolean color;
	private boolean alpha;
	private boolean rgbSurPoints;

	//basic constructor
	public Model(File f) throws IOException{
		Parser parser = new Parser(f.getPath());
		vertex = parser.getVertex();
		nbFaces = parser.getNbFaces();
		
		points = parser.getPoints();
		FACES = parser.getFaces();
		rgbAlpha = parser.getRgbAlpha();
		
		color = parser.isColor();
		alpha = parser.isAlpha();
		rgbSurPoints = parser.isRgbSurPoints();
		int tempLenght = this.points[0].length;
		for(int i = 0;i<tempLenght;i++){
			points[0][i] *= -1;
			points[1][i] *= -1;
			points[2][i] *= -1;
		}
	}
	
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

	//getters and setters of various attributes :

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

	public int[] getFace(int idxFace) {
		if(idxFace<points[0].length) return new int[] {FACES[0][idxFace],FACES[1][idxFace],FACES[2][idxFace]};
		else throw new ArrayIndexOutOfBoundsException();
	}

	public int[][] getFaces() {
		return FACES;
	}
	
	public boolean hasColor() {
		return color;
	}
	
	public boolean hasAlpha() {
		return alpha;
	}

	/**
	 * OLD AND ODDLY UNACCURATE<br>
	 * Returns the center of the Model. The method consists of creating a virtual container of all the points and returning the center of this container <br>
	 * <b>WARNING : This method shouldn't be used using a basic and precise Model, else the Point returned won't approach the real center of the Model</b>
	 * @return Point Representing the center of the Model
	 */
	public double[] getComplexCenter() {
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
	 * @return Point Representing the center of the Model
	 */
	public double[] getCenter() {
		int length = points[0].length;
		//System.out.println("points[0].length :"+points[0].length+", points.length :"+points.length);
		double xsum=0, ysum=0, zsum=0;
		for(int idxPoint=0;idxPoint<length;idxPoint++) {
			//System.out.println("CENTER : Working with "+toStringPoint(idxPoint));
			xsum+=points[0][idxPoint];
			ysum+=points[1][idxPoint];
			zsum+=points[2][idxPoint];
		}
		return new double[] {xsum/length,ysum/length,zsum/length};
	}

	/**
	 * Translates the Model using a Vector<br>
	 * More precisely, it overwrites the previous values of the Model.points array<br>
	 * @param vector v representing the directions and distance all the points will translate
	 */
	public void translate(double[] vector) {
		final int length = points[0].length;
		//System.out.println("Translating by "+String.format("X : %.3f / Y : %.3f / Z : %.3f",vector[0],vector[1],vector[2]));
		for(int idxPoints=0;idxPoints<length;idxPoints++) {
			for(int axis=0;axis<3;axis++) {
				points[axis][idxPoints]+=vector[axis];
			}
		}
	}

	/**
	 * Increases or decreases the size of the Model (param superior to 0 and inferior to 1 = zoom out, param superior to 1 (excluded) = zoom in)<br>
	 * @param relation positive double value representing the amount of zoom
	 */
	public void zoom(double relation) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][] {{relation,0,0},{0,relation,0},{0,0,relation}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}

	/**
	 * Rotates the entire Model on the X axis using the double parameter clockwise<br>
	 * More precisely, it overwrites the previous values of the Model.points array<br>
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
	 * Rotates the entire Model on the Y axis using the double parameter clockwise<br>
	 * More precisely, it overwrites the previous values of the Model.points array<br>
	 * <b>WARNING: This method only works with 3d points</b>
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnYAxis(double angle) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][]{ {Math.cos(angle),0,-Math.sin(angle)},{0,1,0},{Math.sin(angle),0,Math.cos(angle)}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}

	/**
	 * Rotates the entire Model on the Z axis using the double parameter clockwise<br>
	 * More precisely, it overwrites the previous values of the Model.points array<br>
	 * <b>WARNING: This method only works with 3d points</b>
	 * @param angle double value representing how much the Model will rotate
	 */
	public void rotateOnZAxis(double angle) {
		final double[] CENTER = getCenter();
		translate(new double[] {-CENTER[0],-CENTER[1],-CENTER[2]});
		transformPoints(new double[][]{ {Math.cos(angle),-Math.sin(angle),0},{Math.sin(angle),Math.cos(angle),0},{0,0,1}});
		translate(new double[] {CENTER[0],CENTER[1],CENTER[2]});
	}
	/**
	 * Changes the values of points array using the double[][] parameter. <br>
	 * More precisely, this function overwrites the points array using a matricial multiplication of points and the parameter<br>
	 * <b>CURRENTLY APPLY LIGHTS AT THE END</b>
	 * @param TRANSFORM_MATRIX
	 */
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
			//System.out.println(
			//		"TRANSFORMATION : New coords of Point "+idxPoint+" : coords "+toStringPoint(idxPoint)+" INTO  "+String.format("X : %.3f / Y : %.3f / Z : %.3f",tmpCoords[0],tmpCoords[1],tmpCoords[2]));
			setPoint(idxPoint,new double[]{tmpCoords[0],tmpCoords[1],tmpCoords[2]});
			applyLights(new double[] {10,10,-10});
		}
	}
	/**
	 * Applies lights to all FACES
	 * @param lightSourcePoint double[3] representing the coordinates of the lightsource point
	 */
	public void applyLights(double[] lightSourcePoint) {
		if(lightSourcePoint.length!=3) throw new InvalidParameterException();
		if(rgbSurPoints) {
			for(int idxFace=0;idxFace<FACES[0].length;idxFace++) {
				double[] normalVector = getNormalVector(idxFace);
				double normSource = getNorm(lightSourcePoint);
				double normNormal = getNorm(normalVector);
				double gamma = 0.5*(Math.pow(normSource,2)+Math.pow(normNormal,2)-Math.pow((normSource+normNormal),2));
				rgbAlpha[0][idxFace]*=gamma;
				rgbAlpha[1][idxFace]*=gamma;
				rgbAlpha[2][idxFace]*=gamma;
				System.out.println("Using normal vector "+normalVector[0]+", "+normalVector[1]+", "+normalVector[2]+
						"\nWith norm of L (light) : "+normSource+", and norm of N : "+normNormal+
						"\nApplying "+gamma+" to face "+idxFace);
			}
			System.out.println();
		}else {
			System.out.println("ERROR : THERE IS NO RGB ON THIS MODEL");
		}
	}
	/**
	 * Returns the norm (consider the distance) of a given vector
	 * @param vector double[3] array representing the coordinates of the vector
	 * @return double the norm
	 */
	public double getNorm(double[] vector) {
		if(vector.length==3) return Math.sqrt(Math.pow(vector[0],2)+Math.pow(vector[1],2)+Math.pow(vector[2],2));
		else throw new InvalidParameterException();
	}

	/**
	 * Returns the normal vector using the coordinates of a Face's points. <br>
	 * Basically this method calculates the cross product of the two first points located at the idxFace index.
	 * @param idxFace the index of the face you wish to get the normal vector
	 * @return double[3] the normal vector
	 */
	public double[] getNormalVector(int idxFace) {
		double[] vector1 = determineVector(idxFace, 0, 1);
		double[] vector2 = determineVector(idxFace, 0, 2);
		return new double[] {vector1[1]*vector2[2] - vector1[2]*vector2[1],
				vector1[2]*vector2[0] - vector1[0]*vector2[2],
				vector1[0]*vector2[1] - vector1[1]*vector2[0]};
	}
	
	/**
	 * Using two points, returns the vector of the given points.<br>
	 * Moreover, this function requires first the index of the face where the two points are stored next the indexes of those points.
	 * @param idxFace int the index of the face stored in FACES array
	 * @param idxPointA int the index of the first point stored in points array 
	 * @param idxPointB int the index of the second point stored in points array
	 * @return double[3] the vector of the two points
	 */
	public double[] determineVector(int idxFace, int idxPointA, int idxPointB) {
		final short maxAxis=3;
		if(idxFace>FACES[0].length || idxPointA>maxAxis || idxPointB>maxAxis) throw new InvalidParameterException();
		return new double[] {points[0][FACES[idxPointB][idxFace]]-points[0][FACES[idxPointA][idxFace]],
				points[1][FACES[idxPointB][idxFace]]-points[1][FACES[idxPointA][idxFace]],
				points[2][FACES[idxPointB][idxFace]]-points[2][FACES[idxPointA][idxFace]]};
	}

	/**
	 * A basic toString function dealing with points.<br>
	 * Given an integer (basically the index), this function will return a String containing the 3 coordinates of this given point
	 * @param idxPoint int : the index of the point located in the points array
	 * @return String textual representation of the point
	 */
	public String toStringPoint(int idxPoint) {
		return "[Point "+idxPoint+" "+String.format("X : %.3f / Y : %.3f / Z : %.3f",points[0][idxPoint],points[1][idxPoint],points[2][idxPoint])+"]";
	}
}
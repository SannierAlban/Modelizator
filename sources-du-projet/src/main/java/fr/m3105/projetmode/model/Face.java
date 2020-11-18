package fr.m3105.projetmode.model;

import java.util.ArrayList;

public class Face{

	private int red, green, blue, alpha;

	private ArrayList<Point> points;
	
	private int nbPtn;
	
	public Face(int red,int green,int blue,int alpha, ArrayList<Point> listePoints) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
		nbPtn = listePoints.size();
		points = listePoints;
	}
	
	public Face(ArrayList<Point> listePoints) {
		this(1,1,1,1,listePoints);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Point p:points) {
			sb.append("x:").append(p.x).append(" y:").append(p.y).append(" z:").append(p.z).append("\n");
		}
		return sb.toString();
	}
	
	public int getnbPtn() {
		return nbPtn;
	}
	public double[] getX() {
		double[] pointsX = new double[nbPtn];
		int i = 0;
		for (Point p: points){
			pointsX[i] = p.x;
			i++;
		}
		return pointsX;
	}
	public double[] getY() {
		double[] pointsY = new double[nbPtn];
		int i = 0;
		for (Point p: points){
			pointsY[i] = p.y;
			i++;
		}
		return pointsY;
	}
	public double[] getZ() {
		double[] pointsZ = new double[nbPtn];
		int i = 0;
		for (Point p: points){
			pointsZ[i] = p.z;
			i++;
		}
		return pointsZ;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public void replace(Point remplacer,Point remplaceur){
		if (points.contains(remplacer)){
			for (int i=0;i<points.size();i++){
				//System.out.println("p: " + points.get(i) + " remplace: " + remplacer + " test : " + points.get(i).equals(remplacer));
				if (points.get(i).equals(remplacer)){
					//System.out.println("eh salut");
					points.set(i,remplaceur);
				}
			}
		}
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	
	/**
	 * Returns the point at the index idx of the points attribute, if idx is inferior to the points array length
	 * @param idx The index
	 * @return Point at index idx
	 */
	public Point getPoint(int idx) {
		if(idx<points.size()) return points.get(idx);
		else return null;
	}
	
	
	public void setPoint(int idx,Point p) {
		if(p!=null) points.set(idx,p);
	}
	
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + alpha;
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + nbPtn;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + red;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Face other = (Face) obj;
		if (alpha != other.alpha)
			return false;
		if (blue != other.blue)
			return false;
		if (green != other.green)
			return false;
		if (nbPtn != other.nbPtn)
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (red != other.red)
			return false;
		return true;
	}
	
	
}

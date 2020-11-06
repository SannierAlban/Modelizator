package fr.m3105.projetmode.model;

import java.util.ArrayList;

public class Face{

	private int red, green, blue, alpha;
	
	private double[] x;
	private double[] y;
	private double[] z;
	
	private int nbPtn;
	
	public Face(int red,int green,int blue,int alpha, ArrayList<Point> listePoints) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
		nbPtn = listePoints.size();
		x = new double[nbPtn];
		y = new double[nbPtn];
		z = new double[nbPtn];
		
		for(int i = 0;i < nbPtn;i++) {
			x[i] = listePoints.get(i).x;
			y[i] = listePoints.get(i).y;
			z[i] = listePoints.get(i).z;
		}
	}
	
	public Face(ArrayList<Point> listePoints) {
		this(1,1,1,1,listePoints);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i< x.length;i++) {
			sb.append(i+") x:"+x[i]+" y:"+y[i]+" z:"+z[i]+"\n");
		}
		return sb.toString();
	}
	
	public int getnbPtn() {
		return nbPtn;
	}
	public double[] getX() {
		return x;
	}
	public double[] getY() {
		return y;
	}
	public double[] getZ() {
		return z;
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
}

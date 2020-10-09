package fr.m3105.projetmode.model;

import java.awt.Polygon;

public class Face extends Polygon{
	
	private static final long serialVersionUID = 1L;
	private Point[] points;
	private int r=1, g=1, b=1, alpha=1;
	
	public Face(Point[] points, int r, int g, int b, int alpha) {
		super();
		this.points = points;
		this.alpha = alpha;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public Face(Point[] points) {
		super();
		this.points = points;
	}
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Face [");
		for(int i=0;i<points.length;i++) res.append(points[i].toString(true)+" | ");
		return res.toString()+"R = "+r +" G = "+g +" B = "+b +" alpha = "+alpha;
	}
}

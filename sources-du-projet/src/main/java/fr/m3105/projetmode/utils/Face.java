package fr.m3105.projetmode.utils;

import java.awt.Polygon;

public class Face extends Polygon{
	
	private static final long serialVersionUID = 1L;
	private Point3[] points;
	private int r, g, b, alpha;
	
	public Face(Point3[] points, int r, int g, int b, int alpha) {
		super();
		this.points = points;
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}
}

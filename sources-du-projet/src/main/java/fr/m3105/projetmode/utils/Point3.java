package fr.m3105.projetmode.utils;

public class Point3 implements Property{
	
	public double x;
	public double y;
	public double z;
	
	public Point3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "X : "+x+", Y : "+y+", Z : "+z;
	}
	
	@Override
	public String getType() {
		return "Point3";
	}
}

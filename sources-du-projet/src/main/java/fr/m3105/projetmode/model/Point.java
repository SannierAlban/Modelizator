package fr.m3105.projetmode.model;

import fr.m3105.projetmode.model.utils.Property;

public class Point implements Property {
	
	public double x;
	public double y;
	public double z;
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String toString(boolean compact) {
		if(compact) return "X="+x+",Y="+y+",Z="+z;
		else return "X : "+x+" / Y : "+y+" / Z : "+z;
	}
	
	@Override
	public String getType() {
		return "Point3";
	}
}

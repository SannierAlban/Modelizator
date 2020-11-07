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

	public Point(Point p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}
	@Override
	public String toString() {
		return String.format("X : %.3f / Y : %.3f / Z : %.3f",x,y,z);
	}
	public boolean equals(Point p) {
		return p.x==x && p.y==y && p.z==z;
	}
	/**
	 * Verifies if the Point and the parameter Point are approximatively equals
	 * @param p The other Point
	 * @param offset double value which represents the max offset between the coordinates values of the compared points
	 * @return boolean
	 */
	public boolean equalsApprox(Point p, double offset) {
		return x<=p.x+offset && x>=x-offset && y<=p.y+offset && y>=y-offset && z<=p.z+offset && z>=z-offset;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point point = (Point) o;
		return this.equalsApprox(point,0.01);
	}

	public String getType() {
		return "Point3";
	}
}

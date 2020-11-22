package fr.m3105.projetmode.model.utils;

import java.util.ArrayList;

import fr.m3105.projetmode.model.Point;
import fr.m3105.projetmode.model.Vector;

public class MultiThreadTranslate extends Thread{

	private static Integer compteur;
	private ArrayList<Point> points;
	private Vector v;
	
	
	public MultiThreadTranslate(ArrayList<Point> p, Vector vec) {
		if(compteur == null)
			compteur = 0;
		points = p;
		v = vec;
	}
	
	static synchronized int getAndInc() {
		return compteur++;
	}
	
	@Override
	public void run() {
		int idx;
		int size = points.size();
		while((idx = getAndInc()) < size) {
			
			points.get(idx).translate(v);
		}
		super.run();
	}

}

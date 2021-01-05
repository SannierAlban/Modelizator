package fr.m3105.projetmode.model.utils;

public class MultiThreadTranslate extends Thread{
	
	private double[][] tab;
	double[] vector;
	int start;
	int end;
	
	public MultiThreadTranslate(double[][] tab, double[] vec, int start, int end) {
		this.tab = tab;
		this.vector = vec;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		while(start < end) {
			tab[0][start] += vector[0];
			tab[1][start] += vector[1];
			tab[2][start] += vector[2];
			start++;
		}
	}
	
}

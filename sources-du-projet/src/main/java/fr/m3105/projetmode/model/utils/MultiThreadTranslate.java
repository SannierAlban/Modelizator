package fr.m3105.projetmode.model.utils;

public class MultiThreadTranslate extends Thread{
	
	private double[] x;
	double transX;
	int len;
	
	public MultiThreadTranslate(double[] xOuYOuZ, double transXOuYOuZ, int lenght) {
		this.x = xOuYOuZ;
		this.transX = transXOuYOuZ;
		this.len = lenght;
	}

	@Override
	public void run() {
		int idx = 0;
		for (int i = 0; i < len; i++) {
			x[idx] += transX;
		}
	}
	
}

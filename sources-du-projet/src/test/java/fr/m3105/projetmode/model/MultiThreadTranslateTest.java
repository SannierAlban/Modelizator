package fr.m3105.projetmode.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Random;

import org.junit.jupiter.api.Test;

class MultiThreadTranslateTest {
	
	@Test
	void testSurCraneValide() {
		Model crane1 = new Model(new File("exemples/crane.ply"));
		Model crane2 = new Model(new File("exemples/crane.ply"));
		
		Random rand = new Random();
		double[] vector = new double[] {rand.nextDouble(),rand.nextDouble(),rand.nextDouble()};
		
		crane1.translate(vector);
		crane2.translate(vector);
		
		assertTrue(crane1.points.equals(crane1.points));
	}
	
	
	@Test
	void testSurCraneTemps() {
		long debut;
		long fin;
		
		long resSansThread;
		long resAvecThread;
		
		Random rand = new Random();
		
		double[] vector = new double[] {rand.nextDouble(),rand.nextDouble(),rand.nextDouble()};
		
		Model crane1 = new Model(new File("exemples/crane.ply"));
		Model crane2 = new Model(new File("exemples/crane.ply"));
		
		debut = System.nanoTime();
		crane1.translate(vector);
		fin = System.nanoTime();
		resSansThread = fin - debut;
		
		debut = System.nanoTime();
		crane2.translateMultiThread(vector);
		fin = System.nanoTime();
		resAvecThread = fin - debut;
		
		System.out.println("resSansThread =["+resSansThread+"]");
		System.out.println("resAvecThread =["+resAvecThread+"]");
		assertTrue(resAvecThread < resSansThread);
	}

}

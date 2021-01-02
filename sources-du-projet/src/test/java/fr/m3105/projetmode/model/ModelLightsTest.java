package fr.m3105.projetmode.model;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * A JUnit test class which deals with all the functions related to light calculation.
 * @author CALOT Lohan, 2nd-year student at the IT dpt of Lille's IUT-A
 *
 */
class ModelLightsTest {
	
	/**
	 * Some tests about norm calculations using Model.getNorm(double[]) function 
	 * <b>DISCLAIMER : USES exemples/de34.ply, PLEASE VERIFY THE FILE EXISTS
	 */
	@Test
	void norm() {
		Model model = instantiateModel("exemples/de34.ply");
		assertTrue(model.hasColor());
		assertEquals(Math.sqrt(10), model.getNorm(new double[] {0,1,3}));
		assertEquals(Math.sqrt(10), model.getNorm(new double[] {0,-1,-3}));
		assertEquals(Math.sqrt(61), model.getNorm(new double[] {4,6,-3}));
	}

	@Test
	void normalVector() {
		
	}
	
	/**
	 * Some tests about norm vector calculations using Model.determineVector(int,int,int) function 
	 * <b>DISCLAIMER : USES exemples/de34.ply, PLEASE VERIFY THE FILE EXISTS
	 */
	@Test
	void vectorCreator() {
		Model model = instantiateModel("exemples/de34.ply");
		System.out.println(model.toStringPoint(model.FACES[0][0]));
		System.out.println(model.toStringPoint(model.FACES[1][0]));
		System.out.println(model.toStringPoint(model.FACES[2][0]));
		assertArrayEquals(new double[] {0.1,0.1,0.1},model.determineVector(0, 0, 1));
	}
	
	/**
	 * Refrator of recurrent lines, basically verifies if the String (a path) points towards an existing file
	 * @param filepath
	 * @return Model
	 */
	private Model instantiateModel(String filepath) {
		Model model = null;
		try {
			 model = new Model(new File(filepath));
		}catch(Exception e) {
			fail("unable to instantiate model, please verify "+filepath+" exists");
			System.exit(1);
		}
		//System.out.println(model.toStringPoint(1));
		return model;
	}
}

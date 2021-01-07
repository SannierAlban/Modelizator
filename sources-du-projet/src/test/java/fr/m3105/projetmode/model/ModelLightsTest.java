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
	
	Model model_de34 = instantiateModel("exemples/de34.ply",false);
	
	/**
	 * Some tests about norm calculations using Model.getNorm(double[]) function 
	 * <b>DISCLAIMER : USES exemples/de34.ply, PLEASE VERIFY THE FILE EXISTS</b>
	 */
	@Test
	void norm() {
		assertTrue(model_de34.hasColor());
		assertEquals(Math.sqrt(10), model_de34.getNorm(new double[] {0,1,3}));
		assertEquals(Math.sqrt(10), model_de34.getNorm(new double[] {0,-1,-3}));
		assertEquals(Math.sqrt(61), model_de34.getNorm(new double[] {4,6,-3}));
	}

	@Test
	void normalVector() {
		
	}
	
	/**
	 * Some tests about norm vector calculations using Model.determineVector(int,int,int) function 
	 * <b>DISCLAIMER : USES exemples/de34.ply, PLEASE VERIFY THE FILE EXISTS</b>
	 */
	@Test
	void vectorCreator() {
		pointEqualsApprox(new double[] {-4.0,0.0,-4.0},model_de34.determineVector(5, 2, 0),0.01);
		pointEqualsApprox(new double[] {0.0,0.0,4.0},model_de34.determineVector(0, 0, 1),0.01);
	}
	
	/**
	 * Refrator of recurrent lines, basically verifies if the String (a path) points towards an existing file
	 * @param filepath
	 * @param boolean if true the Model will have all his coordinates inverted
	 * @return Model
	 */
	private Model instantiateModel(String filepath,boolean invert) {
		Model model = null;
		try {
			 model = new Model(new File(filepath),invert);
		}catch(Exception e) {
			fail("unable to instantiate model, please verify "+filepath+" exists");
			System.exit(1);
		}
		//System.out.println(model.toStringPoint(1));
		return model;
	}
	
	
	protected boolean pointEqualsApprox(double[] point1, double[] point2, double offset) {
		for(int axis = 0; axis < 3; axis++) {
			if(!(point1[axis]<=point2[axis]+offset && 
					point1[axis]>=point2[axis]-offset)) {
				System.out.println("Values not equals !\nCause : "+toStringPoint(point1)+" different of "+toStringPoint(point2));
				return false;
			}
		}
		return true;
	}
	protected String toStringPoint(double[] point) {
		return "[Point "+String.format("X : %.3f / Y : %.3f / Z : %.3f",point[0],point[1],point[2])+"]";
	}
}

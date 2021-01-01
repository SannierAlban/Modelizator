package fr.m3105.projetmode.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class ModelLightsTest {
	
	/**
	 * Some tests about norm calculations using Model.getNorm(double[]) function 
	 * <b>DISCLAIMER : USES exemples/de34.ply, PLEASE VERIFY IF THE FILE EXISTS
	 */
	@Test
	void norm() {
		String filepath = "exemples/de34.ply";
		Model model = null;
		try {
			 model = new Model(new File(filepath));
		}catch(IOException e) {
			fail("unable to instantiate model, please verify "+filepath+" exists");
			System.exit(1);
		}
		assertTrue(model.hasColor());
		//more tests will come soon
	}

}

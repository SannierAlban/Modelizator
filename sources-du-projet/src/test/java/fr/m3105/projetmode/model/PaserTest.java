package fr.m3105.projetmode.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PaserTest {

	static Parser parserTestDe34;
	static Parser parserTestMeuh;
	static Parser parserTestCrane;

	@BeforeAll
	static void init() {
		parserTestDe34 = new Parser("exemples/de34.ply");
		parserTestMeuh = new Parser("exemples/meuh.ply");
		parserTestCrane = new Parser("exemples/crane.ply");
	}
	
	@Test
	void testHeaderForDe34() {
		assertEquals(16,parserTestDe34.getNbFaces());
		assertEquals(10,parserTestDe34.getVertex());
		assertEquals(true, parserTestDe34.isColor());
		assertEquals(false, parserTestDe34.isAlpha());
	}
	
	@Test
	void testHeaderForCrane() {
		assertEquals(248999,parserTestCrane.getNbFaces());
		assertEquals(133009,parserTestCrane.getVertex());
		assertEquals(true, parserTestCrane.isColor());
		assertEquals(false, parserTestCrane.isAlpha());
	}
	
	@Test
	void testHeaderForMeuh() {
		assertEquals(5804,parserTestMeuh.getNbFaces());
		assertEquals(2903,parserTestMeuh.getVertex());
		assertEquals(false, parserTestMeuh.isColor());
		assertEquals(false, parserTestMeuh.isAlpha());
	}
	
	@Test
	void testPointsForDe34() {
		double[][] points = parserTestDe34.getPoints();
		assertEquals(0.1, points[0][0]);
		assertEquals(0.1, points[1][0]);
		assertEquals(0.1, points[2][0]);
		
		assertEquals(2.1, points[0][9]);
		assertEquals(4.1, points[1][9]);
		assertEquals(4.1, points[2][9]);
	}
	
	@Test
	void testPointsForCrane() {
		double[][] points = parserTestCrane.getPoints();
		assertEquals(-0.0, points[0][0]);
		assertEquals(-0.0, points[1][0]);
		assertEquals(-0.0, points[2][0]);
		
		int lastIdx = parserTestCrane.getVertex() - 1;
		assertEquals(-18.061632, points[0][lastIdx]);
		assertEquals(-119.617180, points[1][lastIdx]);
		assertEquals(70.996452, points[2][lastIdx]);
	}
	
	@Test
	void testPointsForMeuh() {
		double[][] points = parserTestMeuh.getPoints();
		assertEquals(0.605538, points[0][0]);
		assertEquals(0.183122, points[1][0]);
		assertEquals(-0.472278, points[2][0]);
		
		int lastIdx = parserTestMeuh.getVertex() - 1;
		assertEquals(-1.48926, points[0][lastIdx]);
		assertEquals(0.64369, points[1][lastIdx]);
		assertEquals(0.227226, points[2][lastIdx]);
	}
	
	@Test
	void testFacesForDe34() {
		int [][] faces = parserTestDe34.getFaces();
		assertEquals(0, faces[0][0]);
		assertEquals(1, faces[1][0]);
		assertEquals(3, faces[2][0]);
		int lastIdx = parserTestDe34.getNbFaces()-1;
		assertEquals(7, faces[0][lastIdx]);
		assertEquals(9, faces[1][lastIdx]);
		assertEquals(8, faces[2][lastIdx]);
	}
	
	@Test
	void testFacesForCrane() {
		int [][] faces = parserTestCrane.getFaces();
		assertEquals(3, faces[0][0]);
		assertEquals(2, faces[1][0]);
		assertEquals(1, faces[2][0]);
		int lastIdx = parserTestCrane.getNbFaces()-1;
		assertEquals(122236, faces[0][lastIdx]);
		assertEquals(122231, faces[1][lastIdx]);
		assertEquals(122230, faces[2][lastIdx]);
	}
	
	@Test
	void testFacesForMeuh() {
		int [][] faces = parserTestMeuh.getFaces();
		assertEquals(0, faces[0][0]);
		assertEquals(1, faces[1][0]);
		assertEquals(2, faces[2][0]);
		int lastIdx = parserTestMeuh.getNbFaces()-1;
		assertEquals(2498, faces[0][lastIdx]);
		assertEquals(2381, faces[1][lastIdx]);
		assertEquals(2433, faces[2][lastIdx]);
	}
	
	@Test
	void testRGBforDe34() {
		int [][] rgbAlpha = parserTestDe34.getRgbAlpha();
		int idxMaxFace = parserTestDe34.getNbFaces();
		assertEquals(3, rgbAlpha.length);
		assertEquals(idxMaxFace, rgbAlpha[0].length);
		
		assertEquals(255, rgbAlpha[0][0]);
		assertEquals(128, rgbAlpha[1][0]);
		assertEquals(128, rgbAlpha[2][0]);
		
		assertEquals(128, rgbAlpha[0][idxMaxFace - 1]);
		assertEquals(128, rgbAlpha[1][idxMaxFace - 1]);
		assertEquals(128, rgbAlpha[2][idxMaxFace - 1]);
	}
	
	@Test
	void testRGBforCrane() {
		int [][] rgbAlpha = parserTestCrane.getRgbAlpha();
		assertEquals(3, rgbAlpha.length);
		int idxMaxVertex = parserTestCrane.getVertex();
		assertEquals( idxMaxVertex , rgbAlpha[0].length);
		
		assertEquals(41, rgbAlpha[0][0]);
		assertEquals(35, rgbAlpha[1][0]);
		assertEquals(21, rgbAlpha[2][0]);
		
		assertEquals(100, rgbAlpha[0][idxMaxVertex - 1]);
		assertEquals(73, rgbAlpha[1][idxMaxVertex - 1]);
		assertEquals(62, rgbAlpha[2][idxMaxVertex - 1]);
	}
	
	@Test
	void testRGBforMeuh() {
		assertNull(parserTestMeuh.getRgbAlpha());
		assertFalse(parserTestMeuh.isColor());
		assertFalse(parserTestMeuh.isAlpha());
	}
}

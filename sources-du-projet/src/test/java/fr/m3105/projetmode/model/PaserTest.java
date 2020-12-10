package fr.m3105.projetmode.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.m3105.projetmode.model.Face;
import fr.m3105.projetmode.model.Parser;
import fr.m3105.projetmode.model.Point;

class PaserTest {

	Parser parserTest;

	@Test
	void testHeaderForCrane() {
		parserTest = new Parser("exemples/crane.ply");
		assertEquals(248999,parserTest.nbFaces);
		assertEquals(133009,parserTest.vertex);
		assertEquals(true, parserTest.color);
		assertEquals(false, parserTest.alpha);
		
		
	}
	
	@Test
	void TestHeaderForDragon() {
		parserTest = new Parser("exemples/dragon.ply");
		assertEquals(871414,parserTest.nbFaces);
		assertEquals(437645,parserTest.vertex);
		assertEquals(false, parserTest.color);
		assertEquals(false, parserTest.alpha);
	}

	@Test
	void TestReadPointsForCrane() {
		parserTest = new Parser("exemples/crane.ply");
		ArrayList<Point> points = parserTest.points;
		Point pointTest;
		
		assertEquals(parserTest.vertex, parserTest.points.size());
		
		pointTest = new Point(-0.000000 ,-0.000000 ,-0.000000);
		assertEquals(pointTest,	points.get(0));
		pointTest = new Point(-18.061632 ,-119.617180 ,70.996452);
		assertEquals(pointTest, points.get(points.size()-1));
	}
	
	@Test
	void TestReadFacesForCrane() {
		parserTest = new Parser("exemples/crane.ply");
		ArrayList<Point> points = parserTest.points;
		ArrayList<Face> faces = parserTest.faces;
		
		ArrayList<Point> arrayPointsTest = new ArrayList<Point>();
		
		arrayPointsTest.add(points.get(2));			//premiere valeur
		arrayPointsTest.add(points.get(1));
		arrayPointsTest.add(points.get(0));
		Face faceTest = new Face(arrayPointsTest);
		assertEquals(faceTest, faces.get(0));
		
		arrayPointsTest.clear();					//deuxieme dernier valeur
		arrayPointsTest.add(points.get(3));	
		arrayPointsTest.add(points.get(2));
		arrayPointsTest.add(points.get(0));
		faceTest = new Face(arrayPointsTest);
		
		assertEquals(faceTest, faces.get(1));
				
		arrayPointsTest.clear();					//avant dernier valeur
		arrayPointsTest.add(points.get(127597));	
		arrayPointsTest.add(points.get(127599));
		arrayPointsTest.add(points.get(127598));
		faceTest = new Face(arrayPointsTest);
		
		assertEquals(faceTest, faces.get(faces.size()-2));
		
		arrayPointsTest.clear();					//dernier test
		arrayPointsTest.add(points.get(122235));
		arrayPointsTest.add(points.get(122230));
		arrayPointsTest.add(points.get(122229));
		faceTest = new Face(arrayPointsTest);
		
		assertEquals(faceTest, faces.get(faces.size()-1));
	}
	
}

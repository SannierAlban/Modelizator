package fr.m3105.projetmode.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModelManipulatorTest {
	
	//basic pyramid
	double[][] pyramid = new double[][]{{1,1.5,2,1.5},{1,2,1,1.5},{0,0,0,1}};
	
	//basic cube
	double[][] cube = new double [][]{{1,1.5,2.5,2,2,1,1.5,2.5},{2,2.5,2.5,2,1,1,1.5,1.5},{0,1,1,0,0,0,1,1}};
	
	//an other basic cube, values verified by M.Chlebowski and M.Guyomarch
	double[][] cube2 = new double[][] {{0.1,0.1,0.1,0.1,4.1,4.1,4.1,4.1,4.1,2.1},{0.1,0.1,4.1,4.1,0.1,0.1,4.1,4.1,2.6,4.1},{0.1,4.1,0.1,4.1,0.1,4.1,0.1,3.1,4.1,4.1}};
										
	/**
	 * A junit-test concerning Model zooming/length increasing and decreasing
	 * Moreover, the values have been manually verified
	 */
	@Test
	void testZoom() {
		//basic pyramid
		
		Model m = new Model(pyramid);
		m.zoom(2);
		
		//double[][] pyramidZoomed2 = new double[][] {{0.5,0.5,0},{1.5,2.5,0},{2.5,0.5,0},{1.5,1.5,2}};
		double[][] pyramidZoomed2 = new double[][] {{0.5,1.5,2.5,1.5},{0.5,2.5,0.5,1.5},{0,0,0,2}};
		assertTrue(arrayPointEquals(m.getPoints(), pyramidZoomed2,0.25));
	}
	
	/**
	 * A junit-test concerning Model translations
	 * Moreover, the values have been manually verified
	 */
	@Test
	void testTranslation() {
		//basic cube
		
		Model m = new Model(cube);
		m.translate(new double[] {2,0,0});
		
		//verifying an horizontal(x) translation of 2 units
		double[][] points = new double[][] {{3,3.5,4.5,4,4,3,3.5,4.5},{2,2.5,2.5,2,1,1,1.5,1.5},{0,1,1,0,0,0,1,1}};
		/*points1.add(new Point(3,2,0));
		points1.add(new Point(3.5,2.5,1));
		points1.add(new Point(4.5,2.5,1));
		points1.add(new Point(4,2,0));
		points1.add(new Point(4,1,0));
		points1.add(new Point(3,1,0));
		points1.add(new Point(3.5,1.5,1));
		points1.add(new Point(4.5,1.5,1));*/
		
		assertTrue(arrayPointEquals(m.getPoints(), points,0.1));
		
		//verifying complex translation
		m.translate(new double[] {-4,10,3});
		double[][] points2 = new double[][] {{-1,-0.5,0.5,0,0,-1,-0.5,0.5},{12,12.5,12.5,12,11,11,11,11},{3,4,4,3,3,3,4,4}};
		/*points2.add(new Point(-1,12,3));
		points2.add(new Point(-0.5,12.5,4));
		points2.add(new Point(0.5,12.5,4));
		points2.add(new Point(0,12,3));
		points2.add(new Point(0,11,3));
		points2.add(new Point(-1,11,3));
		points2.add(new Point(-0.5,11.5,4));
		points2.add(new Point(0.5,11.5,4));*/
		
		assertTrue(arrayPointEquals(m.getPoints(), points2,0.1));
		
	}
	
	/**
	 * A junit-test concerning Model rotations
	 * The values used by this method are available at <a href="https://moodle.univ-lille.fr/pluginfile.php/1109673/mod_resource/content/1/maths_projet_20201009a.pdf">this link</a> and were provided by M.CHLEBOWSKI
	 * NOT WORKING STILL THE MODEL ROTATES PROPERLY, UPDATE OF THE VALUES NEEDED
	 */
	@Test
	void testRotation() {
		Model m = new Model(cube2);
		m.rotateOnXAxis(3.14159/8);
		
		double[][] points = new double[][] {{0.1,0.1,0.1,0.1,4.1,4.1,4.1,4.1,4.1,2.1},{0.054,-1.48,3.748,2.214,0.054,-1.48,3.748,2.597,0.0828,2.214},
			{0.131,3.825,1.665,5.359,0.131,3.825,1.665,4.436,4.784,5.359}};
		/*points1.add(new Point(0.1,0.054,0.131));
		points1.add(new Point(0.1,-1.48,3.825));
		points1.add(new Point(0.1,3.748,1.665));
		points1.add(new Point(0.1,2.214,5.359));
		points1.add(new Point(4.1,0.054,0.131));
		points1.add(new Point(4.1,-1.48,3.825));
		points1.add(new Point(4.1,3.748,1.665));
		points1.add(new Point(4.1,2.597,4.436));
		points1.add(new Point(4.1,0.828,4.784));
		points1.add(new Point(2.1,2.214,5.359));*/
		
		assertTrue(arrayPointEquals(m.getPoints(),points,0.2));
	}
	
	@Test
	void testCenter() {
		//basic cube (same as points0 variable of testRotation() method)
		Model m1 = new Model(cube2);
		assertTrue(pointEqualsApprox(new double []{2,2,2},m1.getCenter(),0.4));
		
		//basic pyramid
		Model m2 = new Model(pyramid);
		assertTrue(pointEqualsApprox(new double[] {1.5,1.5,1},m2.getCenter(),0.1));
	}
	
	/**
	 * Returns true if the two parameters, which have to be double[][3], contains equals values or approximate (using errorMargin) at same positions<br>
	 * Disclaimer : will return true even if arrays haven't the same size
	 * @param array1 The first array of double[][3]
	 * @param array2 The second array of double[][3]
	 * @param errorMargin double value that 
	 * @return boolean
	 * @throws IllegalStateException if one of the arrays is empty
	 */
	protected boolean arrayPointEquals(double[][] array1, double[][] array2,double errorMargin) {
		
		if(array1.length==0 || array2.length==0) throw new IllegalStateException();
		
		for(int idxCol=0;idxCol<array1.length;idxCol++) {
			
			if(!pointEqualsApprox(new double[] {array1[0][idxCol],array1[1][idxCol],array1[2][idxCol]}
									,new double[] {array2[0][idxCol],array2[1][idxCol],array2[2][idxCol]},errorMargin)) {
				
				System.out.println("At "+idxCol+" index\n\n");
				return false;
			}
		}
		
		return true;
	}
	/**
	 * Given two integers (basically the indexes of the points), this function will return wether or not the 3 coordinates of the two given points at the indexes are exactly identical.
	 * I highly suggest to use pointsApproxEquals() instead.
	 * @param idxPoint1 a point to compare
	 * @param idxPoint2 the other point to compare
	 * @return boolean true if the coordinates of the two points are exactly identical, false otherwise
	 */
	protected boolean pointsEquals(double[] point1, double[] point2) {
		return pointEqualsApprox(point1, point2, 0.0);
	}
	/**
	 * Given two integers (basically the indexes of the points), this function will return wether or not the 3 coordinates of the two given points at the indexes are identical according to a margin of error (the offset).
	 * @param idxPoint1 a point to compare
	 * @param idxPoint2 the other point to compare
	 * @param offset double value which represents the max offset between the coordinates values of the compared points
	 * @return boolean true if the coordinates of the two points are exactly identical, false otherwise
	 */
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

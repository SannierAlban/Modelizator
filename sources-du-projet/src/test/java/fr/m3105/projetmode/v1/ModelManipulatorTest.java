package fr.m3105.projetmode.v1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.Point;

class ModelManipulatorTest {
	
	/**
	 * A junit-test concerning Model rotations
	 * The values used by this method are available at <a href="https://moodle.univ-lille.fr/pluginfile.php/1109673/mod_resource/content/1/maths_projet_20201009a.pdf">this link</a> and were provided by M.CHLEBOWSKI
	 */
	@Test
	void testRotation() {
		ArrayList<Point> points0 = new ArrayList<>();
		points0.add(new Point(0.1,0.1,0.1));
		points0.add(new Point(0.1,0.1,4.1));
		points0.add(new Point(0.1,4.1,0.1));
		points0.add(new Point(0.1,4.1,4.1));
		points0.add(new Point(4.1,0.1,0.1));
		points0.add(new Point(4.1,0.1,4.1));
		points0.add(new Point(4.1,4.1,0.1));
		points0.add(new Point(4.1,4.1,3.1));
		points0.add(new Point(4.1,2.6,4.1));
		points0.add(new Point(2.1,4.1,4.1));
		Model m1 = new Model(points0);
		m1.rotateOnXAxis(3.14159/8);
		
		ArrayList<Point> points1 = new ArrayList<>();
		points1.add(new Point(0.1,0.054,0.131));
		points1.add(new Point(0.1,-1.48,3.825));
		points1.add(new Point(0.1,3.748,1.665));
		points1.add(new Point(0.1,2.214,5.359));
		points1.add(new Point(4.1,0.054,0.131));
		points1.add(new Point(4.1,-1.48,3.825));
		points1.add(new Point(4.1,3.748,1.665));
		points1.add(new Point(4.1,2.597,4.436));
		points1.add(new Point(4.1,0.828,4.784));
		points1.add(new Point(2.1,2.214,5.359));
		
		assertTrue(pointsArrayListEquals(points0,points1));
	}
	/**
	 * Returns true if the two parameters, which have to be Point ArrayLists, contains equals objects at same positions
	 * As an exemple, the first Point of array1 has to be equal to the first Point of array2
	 * Precision : will return true even if arrays haven't the same size
	 * @param array1 The first arrayList of Point
	 * @param array2 The second arrayList of Point
	 * @return boolean
	 * @throws IllegalStateException if one of the arrays is empty
	 */
	private boolean pointsArrayListEquals(ArrayList<Point> array1,ArrayList<Point> array2) {
		if(array1.size()==0 || array2.size()==0) throw new IllegalStateException();
		for(int i=0;i<array1.size();i++) {
			if(!array1.get(i).equalsApprox(array2.get(i),0.1)) {
				System.out.println("Objects not equals at "+i+" index\nCause : "+array1.get(i).toString()+" different of "+array2.get(i).toString());
				return false;
			}
		}
		return true;
	}
}

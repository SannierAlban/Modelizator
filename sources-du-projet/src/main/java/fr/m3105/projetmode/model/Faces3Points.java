package fr.m3105.projetmode.model;

public class Faces3Points {

//				[1,2,3][x,y,z]
		private int [][] points;
		
//				[1,2][x,y,z]
		private int [][] pointsSection;
		
		public Faces3Points(int[][] points,int X) {
			this.points = points;
			pointsSection = new int[2][3];
		}
		
		private void fillPointsSection() {
			
		}
		

}

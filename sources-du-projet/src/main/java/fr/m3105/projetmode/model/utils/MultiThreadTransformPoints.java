package fr.m3105.projetmode.model.utils;

public class MultiThreadTransformPoints extends Thread{

	private double points[][];
	private int min;
	private int max;
	
	private double[][] transform_matrix;
	private final int MAX_AXIS = 3;
	
    public MultiThreadTransformPoints(double[][] points, int start, int end, double[][] transform_matrix) {
		super();
		this.points = points;
		this.min = start;
		this.max = end;
		this.transform_matrix = transform_matrix;
	}

    @Override
    public void run() {
    	for(int idxPoint = min; idxPoint < max; idxPoint++) {
    		double[] crtPoint = new double[] {points[0][idxPoint],points[1][idxPoint],points[2][idxPoint]};
    		for(int idxNewPoint = 0; idxNewPoint < MAX_AXIS; idxNewPoint++) {
    			points[idxNewPoint][idxPoint] = transform_matrix[idxNewPoint][0]*crtPoint[0] + 
    											transform_matrix[idxNewPoint][1]*crtPoint[1] + 
    											transform_matrix[idxNewPoint][2]*crtPoint[2];
    		}
    	}
    }
    
    
//	private void transformPoints(final double[][] TRANSFORM_MATRIX) {
//        final int length = points[0].length;
//        for(int idxPoint = 0 ; idxPoint < length ; idxPoint++) {
//
//            double[] crtPoint = getPoint(idxPoint);
//            double[] tmpCoords = new double[MAX_AXIS];
//            
//            for(int idxNewPoint=0;idxNewPoint<MAX_AXIS;idxNewPoint++) {
//                tmpCoords[idxNewPoint] = TRANSFORM_MATRIX[idxNewPoint][0]*crtPoint[0] + TRANSFORM_MATRIX[idxNewPoint][1]*crtPoint[1] + TRANSFORM_MATRIX[idxNewPoint][2]*crtPoint[2];
//            }
//            //System.out.println(
//            //		"TRANSFORMATION : New coords of Point "+idxPoint+" : coords "+toStringPoint(idxPoint)+" INTO  "+String.format("X : %.3f / Y : %.3f / Z : %.3f",tmpCoords[0],tmpCoords[1],tmpCoords[2]));
//            setPoint(idxPoint,new double[]{tmpCoords[0],tmpCoords[1],tmpCoords[2]});
//        }
//    }
}

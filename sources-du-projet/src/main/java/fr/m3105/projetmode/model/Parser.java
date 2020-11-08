package fr.m3105.projetmode.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.m3105.projetmode.v1.ErreurFichierException;

public class Parser {
	
	public int ligneEnCour;
	
	public int vertex;
	public int nbFaces;
	
	public boolean color;
	public boolean alpha;
	
	public ArrayList<Point> points;
	public ArrayList<Face> faces;
	
	BufferedReader reader;
	
	public Parser(String path) {
		try {
			ligneEnCour = 0;
			points = new ArrayList<Point>();
			faces = new ArrayList<Face>();
			reader = new BufferedReader(new FileReader(path));
			
			
			readTwoFirstLineHeader();
			readFlexibleHeader();
			
			if(color)
				readPointsAndColor();
			else
				readPoints();
			
			readFaces();
			
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (ErreurFichierException e) {
			System.out.println(e.getMessage());
		}

	}
	
	private void readFaces() throws ErreurFichierException {
		String line = readPLYLigne();
		
		for(int lineIdx = 0; line != null && !line.isBlank() && lineIdx < nbFaces; lineIdx++) {
			faces.add(stringToFace(line));
			line = readPLYLigne();
		}
	}
	
	private Face stringToFace(String str) {

		int nbPoints;
		ArrayList<Point> arrayRetPoint = new ArrayList<Point>();
		
		int charIdx = 0;
		int lastCharIdx;
		
		while(!Character.isWhitespace(str.charAt(charIdx)))
			charIdx++;
		nbPoints = Integer.parseInt(str.substring(0, charIdx));
		
		lastCharIdx = charIdx+1;
		charIdx++;
		
		for(; charIdx < str.length(); charIdx++) {
			if(charIdx == str.length()-1) {
				int idx = Integer.parseInt(str.substring(lastCharIdx, charIdx+1));
				Point ptn = new Point(points.get(idx-1));
				arrayRetPoint.add(ptn);
			}
			else if(Character.isWhitespace(str.charAt(charIdx))) {
				int idx = Integer.parseInt(str.substring(lastCharIdx, charIdx));
				Point ptn = new Point(points.get(idx-1));
				arrayRetPoint.add(ptn);
				lastCharIdx = charIdx+1;
			}
		}
		
		return new Face(arrayRetPoint);
	}
	
	
	private void readPointsAndColor() throws IOException {
		readPoints();//TODO implementer cette methode
	}
	
	private void readPoints() throws IOException {
		double[] xyz = new double[3];
		
		String str;
		int lastCharIdx;
		
		for(int lineIdx = 0; lineIdx < vertex; lineIdx++) {
			str = reader.readLine();
			lastCharIdx = 0;
			
			for(int charIdx = 0, tabIdx = 0 ; charIdx < str.length() && tabIdx < 3; charIdx++) {
				if(Character.isWhitespace(str.charAt(charIdx))) {
					xyz[tabIdx] = Double.parseDouble(str.substring(lastCharIdx,charIdx));
					lastCharIdx = charIdx;
					tabIdx++;
				}
			}
			points.add(new Point(xyz[0],xyz[1], xyz[2]));
			//System.out.println("x="+xyz[0] +" y=" + xyz[1] +" z="+ xyz[2]);
		}
	}
	
 	private void readFlexibleHeader() throws ErreurFichierException {
		String line = readPLYLigne();
		int countColor = 0;
		//int countXYZ = 0;
		
		nbFaces = -1;
		vertex = -1;
		
		while(!line.contains("end_header")) {
			if(line.contains("element face"))
				nbFaces = Integer.parseInt(line.substring(13, line.length()));
			else if(line.contains("element vertex"))
				vertex = Integer.parseInt(line.substring(15, line.length()));
			else if(line.contains("red") || line.contains("green") || line.contains("blue"))
				countColor++;
			else if(line.contains("alpha"))
				alpha = true;	
			line = readPLYLigne();
		}	
		if(countColor == 3)
			color = true;
		if(nbFaces == -1)
			throw new ErreurFichierException("element face non lu");
		if(vertex == -1)
			throw new ErreurFichierException("element vertex non lu");
	}
	
	private void readTwoFirstLineHeader() throws IOException, ErreurFichierException{
		String line;
		
		line = readPLYLigne();
		if(! line.equals("ply"))
			throw new ErreurFichierException("Format incorecte :["+line+"] ligne "+ligneEnCour);
		line = readPLYLigne();
		if(! line.equals("format ascii 1.0"))
			throw new ErreurFichierException("Format Incorecte :["+line+"] ligne "+ligneEnCour);
	}
	
	private String readPLYLigne() throws ErreurFichierException {
		String ret = null;
		try {
			do {
				ligneEnCour++;
				ret = reader.readLine();
			}while(ret.contains("comment")); //si c'est un commentaire passe la ligne
			
			if(ret.isBlank())
				throw new ErreurFichierException("la ligne :"+ligneEnCour+" es vide");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		while(Character.isWhitespace(ret.charAt(ret.length()-1)))
//			ret = ret.substring(0, ret.length()-1);
		return ret;
	}

}

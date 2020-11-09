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
	
	private Face stringToFace(String str) throws ErreurFichierException {

		int nbPoints;
		ArrayList<Point> arrayRetPoint = new ArrayList<Point>();
		
		int charIdx = 0;
		int lastCharIdx;
		
		while(!Character.isWhitespace(str.charAt(charIdx)))
			charIdx++;
		nbPoints = Integer.parseInt(str.substring(0, charIdx));
		
		lastCharIdx = charIdx+1;
		charIdx++;
		
		str = str.substring(lastCharIdx, str.length());
		//System.out.println("StringToFace 77, nbPoints ="+nbPoints+"\n\tstr=["+str+"]");

		String[] strTab = str.split(" ");
		
		for (int i = 0; i < nbPoints; i++) {
			arrayRetPoint.add(new Point(points.get(Integer.parseInt(strTab[i]))));
		}	
		
		int[] redGreenBlue = readRGB(strTab);
		
		return new Face(redGreenBlue[0],redGreenBlue[1],redGreenBlue[2],1,arrayRetPoint);
	}
	
	int [] readRGB(String[] strTab) throws ErreurFichierException {
		int[] redGreenBlue = new int[] {1,1,1};
		
		if(!color)
			return redGreenBlue;
		
		if(strTab.length > 6)
			throw new ErreurFichierException("Lecture de la couleur ligne : ["+ligneEnCour+"] imposible car strTab.len=["+strTab.length+"](>6)");
		else if(strTab.length < 6)
			throw new ErreurFichierException("Lecture de la couleur ligne : ["+ligneEnCour+"] imposible car strTab.len=["+strTab.length+"](<6)");
		
		redGreenBlue[0] = Integer.parseInt(strTab[3]);
		redGreenBlue[1] = Integer.parseInt(strTab[4]);
		redGreenBlue[2] = Integer.parseInt(strTab[5]);
		
//		for (int i : redGreenBlue) {
//			System.out.println("i:"+i);
//		}
//		System.out.println("_________");
		
		return redGreenBlue;
	}
	
	
	private void readPointsAndColor() throws IOException, ErreurFichierException {
		readPoints();//TODO implementer cette methode
	}
	
	private void readPoints() throws IOException, ErreurFichierException {
		double[] xyz = new double[3];
		
		String str;
		int lastCharIdx;
		
		for(int lineIdx = 0; lineIdx < vertex; lineIdx++) {
			str = readPLYLigne();
			lastCharIdx = 0;
			
			String[] strTab = str.split(" ");
			
			for (int i = 0; i < strTab.length && i < 3; i++) {
				xyz[i] = Double.parseDouble(strTab[i]);
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
			
			String[]tabRet = ret.split(" ");
			ret = "";
			
			for (String s : tabRet) {
				if(!s.isBlank()) {
					ret += s+" ";
				}


			}
			ret = ret.substring(0,ret.length()-1);
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//System.out.println("["+ret+"]");
		return ret;
	}

}

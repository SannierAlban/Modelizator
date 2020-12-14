package fr.m3105.projetmode.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.m3105.projetmode.v1.ErreurFichierException;

public class Parser {
	
	private int ligneEnCour;
	
	private int vertex;
	private int nbFaces;
	
	private boolean color;
	private boolean alpha;
	private boolean rgbSurPoints;
	
	private double[][] points;
	private int[][] faces;
	private int[][] rgbAlpha;
	
	BufferedReader reader;
	
	public Parser(String path) {
		try {
			ligneEnCour = 0;
			reader = new BufferedReader(new FileReader(path));
			
			readTwoFirstLineHeader();
			readFlexibleHeader();
			
			points = new double [3][vertex];
			faces = new int [3][nbFaces];
			
			readPoints();
			readFaces();
			
		}
		catch (IOException e) {
			System.out.println("parser 1"+e.getMessage());
		}
		catch (ErreurFichierException e) {
			System.out.println("parser 2"+e.getMessage());
		}

	}
	
	void rgbAlphaInit(boolean rgbAlphaSurPoints) {
		int nombreDElements = 3;
		if(alpha)
			nombreDElements++;
		
		if(rgbAlphaSurPoints)
			rgbSurPoints = true;
		else
			rgbSurPoints = false;

		if(rgbSurPoints) {
			rgbAlpha = new int [nombreDElements][vertex];
		}
		else {
			rgbAlpha = new int [nombreDElements][nbFaces];
		}
	}
	
	private void readFaces() throws ErreurFichierException {
		String line = readPLYLigne();
		String [] tabLine;
		int lineIdx = 0;

		while(line != null && lineIdx < nbFaces){
			tabLine = line.split(" ");
			if(Integer.parseInt(tabLine[0]) != 3)
				throw new ErreurFichierException("Ligne "+ligneEnCour+": faces non composer de 3 points");
			
			faces[0][lineIdx] = Integer.parseInt(tabLine[1]);
			faces[1][lineIdx] = Integer.parseInt(tabLine[2]);
			faces[2][lineIdx] = Integer.parseInt(tabLine[3]);
			
			if(color && !rgbSurPoints)
				putRGB(tabLine, 4, lineIdx);
			
			line = readPLYLigne();
			lineIdx++;
		}
	}
	
	void putRGB(String[] strTab,int firstIdx, int lineIdx) {
		rgbAlpha[0][lineIdx] = (int)Integer.parseInt(strTab[firstIdx]);
		rgbAlpha[1][lineIdx] = (int)Integer.parseInt(strTab[firstIdx+1]);
		rgbAlpha[2][lineIdx] = (int)Integer.parseInt(strTab[firstIdx+2]);
		if(alpha)
			rgbAlpha[3][lineIdx] = Integer.parseInt(strTab[firstIdx+3]);
	}
	
	private void readPoints() throws IOException, ErreurFichierException {
		double[] xyz = new double[3];
		String str;
		String[] strTab;
		
		boolean gate = true;
		
		for(int lineIdx = 0; lineIdx < vertex; lineIdx++) {
			str = readPLYLigne();
			strTab = str.split(" ");
			
			if(gate) {
				if(color && strTab.length >= 6)
					rgbAlphaInit(true);
				else if(color)
					rgbAlphaInit(false);
				gate = false;
			}
			
			for (int i = 0; i < strTab.length && i < 3; i++) {
				xyz[i] = Double.parseDouble(strTab[i]);
			}
			putXYZInPoints(xyz, lineIdx);
			
			if(color && rgbSurPoints) {
				putRGB(strTab,3,lineIdx);
			}
		}
	}

	private void putXYZInPoints(double[] xyz, int lineIdx) {
		points[0][lineIdx] = xyz[0];
		points[1][lineIdx] = xyz[1];
		points[2][lineIdx] = xyz[2];
	}
	
 	private void readFlexibleHeader() throws ErreurFichierException {
		String line = readPLYLigne();
		int countColor = 0;
		
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
			//System.out.println("parser3 = "+e.getMessage());
		}
		return ret;
	}

	public int getVertex() {
		return vertex;
	}

	public int getNbFaces() {
		return nbFaces;
	}

	public boolean isColor() {
		return color;
	}

	public boolean isAlpha() {
		return alpha;
	}

	public boolean isRgbSurPoints() {
		return rgbSurPoints;
	}

	public double[][] getPoints() {
		return points;
	}

	public int[][] getFaces() {
		return faces;
	}

	public int[][] getRgbAlpha() {
		return rgbAlpha;
	}
}

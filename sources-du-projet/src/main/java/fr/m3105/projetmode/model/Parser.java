package fr.m3105.projetmode.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import fr.m3105.projetmode.utils.ErreurFichierException;
import fr.m3105.projetmode.model.utils.ThreadReadFace;
import fr.m3105.projetmode.model.utils.ThreadReadPoints;

/**
 * 
 * class use to parse a ply file
 *
 */
public class Parser {
	
	private int ligneEnCour;
	
	/**
	 * number of points in the model
	 */
	private int vertex;
	/**
	 * number of faces
	 */
	private int nbFaces;
	/**
	 * true if there's color in the file
	 */
	private boolean color;
	/**
	 * true if there's alpha in the file
	 */
	private boolean alpha;
	/**
	 * true if the colors are indexed to the dots
	 */
	private boolean rgbSurPoints;
	/**
	 * [0=x, 1=y, 2=z][0 - vertex]
	 */
	private double[][] points;
	/**
	 * [point n1,point n2,point n3,][nbFaces]
	 */
	private int[][] faces;
	/**
	 * [r,g,b,alpha(only if bool alpha is true)]
	 */
	private int[][] rgbAlpha;
	
	BufferedReader reader;
	
	/**
	 * 
	 * @param path is the file that will be parse
	 * @param onlyHeader if you just want the information : don't fill faces, points and rgbAlpha
	 */
	public Parser(String path,boolean onlyHeader) {
		try(FileReader fileReader = new FileReader(path)) {
			ligneEnCour = 0;
			reader = new BufferedReader(fileReader);
			
			readTwoFirstLineHeader();
			readFlexibleHeader();
			
			int finHeader = ligneEnCour;
			//System.out.println(path + "  finHeader :"+finHeader);
			
			if(!onlyHeader) {
				points = new double [3][vertex];
				faces = new int [3][nbFaces];
				ThreadReadPoints threadReadPoints = new ThreadReadPoints(this, path,ligneEnCour,finHeader);
				threadReadPoints.start();

				ThreadReadFace threadReadFace = new ThreadReadFace(this, path, ligneEnCour, finHeader);
				threadReadFace.start();
				try {
					threadReadPoints.join();
					threadReadFace.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			fileReader.close();
		}

		catch (IOException e) {
			System.out.println("parser 1"+e.getMessage());
		}
		catch (ErreurFichierException e) {
			System.out.println("parser 2"+e.getMessage());
		}

	}
	/**
	 * 	initializes the rgb table takes into account having alpha in the ply file
	 * @param rgbAlphaSurPoints true if the colors are indexed to the dots
	 */
	public void rgbAlphaInit(boolean rgbAlphaSurPoints) {
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
	
	public void putRGB(String[] strTab,int firstIdx, int lineIdx) {
		rgbAlpha[0][lineIdx] = (int)Integer.parseInt(strTab[firstIdx]);
		rgbAlpha[1][lineIdx] = (int)Integer.parseInt(strTab[firstIdx+1]);
		rgbAlpha[2][lineIdx] = (int)Integer.parseInt(strTab[firstIdx+2]);
		if(alpha)
			rgbAlpha[3][lineIdx] = Integer.parseInt(strTab[firstIdx+3]);
	}

	public void putXYZInPoints(double[] xyz, int lineIdx) {
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
	
	/**
	 * with a space between each word
	 * @return return the next string in the file (no comment)
	 */
	private String readPLYLigne() throws ErreurFichierException {
		String ret = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			do {
				ligneEnCour++;
				ret = reader.readLine();
			}while(ret.contains("comment")); //si c'est un commentaire passe la ligne
			
			if(ret.isBlank())
				throw new ErreurFichierException("la ligne :"+ligneEnCour+" es vide");
			
			String[]tabRet = ret.split(" ");
			
			for (int i = 0; i < tabRet.length; i++) {
				if(!tabRet[i].isBlank()) {
					stringBuilder.append(tabRet[i]);
					if(i != tabRet.length - 1) {
						stringBuilder.append(" ");
					}
				}
			}
			
		}
		catch (Exception e) {
			//System.out.println("parser err3 nbFaces="+nbFaces+"ligne en cour ="+ligneEnCour+" message erreur:"+e.getMessage());
		}
		return stringBuilder.toString();
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
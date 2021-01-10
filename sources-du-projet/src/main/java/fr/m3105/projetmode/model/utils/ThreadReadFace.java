package fr.m3105.projetmode.model.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.m3105.projetmode.model.Parser;
import fr.m3105.projetmode.utils.ErreurFichierException;

/**
 * useful class to create a thread only to fill the table faces of a parser
 *
 *
 */
public class ThreadReadFace extends Thread{
	
	Parser parser;
	int ligneEnCour;
	BufferedReader reader;
	int premiereLigne;
	
	public ThreadReadFace(Parser parser,String path, int ligneEnCour,int premiereLigne) throws FileNotFoundException {
		super();
		this.parser = parser;
		reader = new BufferedReader(new FileReader(path));
		this.ligneEnCour = ligneEnCour;
		this.premiereLigne = premiereLigne;
	}
	
	
	public void run() {
		
		for (int i = 0; i < premiereLigne + parser.getVertex(); i++) {
			try {
				reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String line = null;
		try {
			line = readPLYLigne();
		} catch (ErreurFichierException e) {

			e.printStackTrace();
		}
		
		String [] tabLine;
		int lineIdx = 0;

		while(line != null && lineIdx < parser.getNbFaces()){
			tabLine = line.split(" ");
			
//			if(Integer.parseInt(tabLine[0]) != 3)
//				throw new ErreurFichierException("Ligne "+ligneEnCour+": faces non composer de 3 points");
			
			parser.getFaces()[0][lineIdx] = Integer.parseInt(tabLine[1]);
			parser.getFaces()[1][lineIdx] = Integer.parseInt(tabLine[2]);
			parser.getFaces()[2][lineIdx] = Integer.parseInt(tabLine[3]);
			
			if(parser.isColor() && !parser.isRgbSurPoints())
				parser.putRGB(tabLine, 4, lineIdx);
			
			try {
				line = readPLYLigne();
			} catch (ErreurFichierException e) {
				e.printStackTrace();
			}
			lineIdx++;
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * with a space between each word
	 * @return return the next string in the file (no comment) 
	 * @throws ErreurFichierException
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
			//System.out.println("parser err3 ligne en cour ="+ligneEnCour+" message erreur:"+e.getMessage());
		}
		return stringBuilder.toString();
	}
}

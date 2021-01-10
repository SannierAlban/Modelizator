package fr.m3105.projetmode.model.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.m3105.projetmode.model.Parser;
import fr.m3105.projetmode.utils.ErreurFichierException;

public class ThreadReadPoints extends Thread {
	
	Parser parser;
	int ligneEnCour;
	BufferedReader reader;
	int premiereLigne;
	/**
	 * 
	 * @param parser parser used when you thread the task
	 * @param path of the file you need to parse
	 * @param ligneEnCour useful for error messages
	 * @param premiereLigne position of the last line of the header
	 */
	public ThreadReadPoints(Parser parser,String path, int ligneEnCour,int premiereLigne) throws FileNotFoundException {
		super();
		this.parser = parser;
		reader = new BufferedReader(new FileReader(path));
		this.ligneEnCour = ligneEnCour;
		this.premiereLigne = premiereLigne;
	}
	
	public void run() {
		double[] xyz = new double[3];
		String str;
		String[] strTab;
		boolean gate = true;
		
		for (int i = 0; i < premiereLigne; i++) {
			try {
				reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int lineIdx = 0; lineIdx < parser.getVertex(); lineIdx++) {
			str = null;
			try {
				str = readPLYLigne();
			} catch (ErreurFichierException e) {
				e.printStackTrace();
			}
			//System.out.println("str :"+str);
			
			strTab = str.split(" ");
			if(gate) {
				if(parser.isColor() && strTab.length >= 6)
					parser.rgbAlphaInit(true);
				else if(parser.isColor())
					parser.rgbAlphaInit(false);
				gate = false;
			}
			
			for (int i = 0; i < strTab.length && i < 3; i++) {
				xyz[i] = Double.parseDouble(strTab[i]);
			}
			parser.putXYZInPoints(xyz, lineIdx);
			
			if(parser.isColor() && parser.isRgbSurPoints()) {
				parser.putRGB(strTab,3,lineIdx);
			}
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

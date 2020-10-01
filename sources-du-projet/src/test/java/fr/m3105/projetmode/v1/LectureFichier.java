package fr.m3105.projetmode.v1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectureFichier {
	
	BufferedReader reader;
	
	public static void lecture(String path) throws IOException{
		
		
		try {
			reader= new BufferedReader(new FileReader(path));
			
			String line;
			String [] format=new String[] {reader.readLine(), reader.readLine()};
			
			if (!this.verifFormat(format)) System.exit(1);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean verifFormat(String[] l) {
		if (l[0]==null || l[1]==null) throws ErreurFichierException("Erreur: fichier vide");
		if (!l[0].equals("ply")) throws ErreurFichierException("Erreur: format incompatible (format nécessaire: ply) -ligne 1-");
		if (!l[1].equals("format ascii 1.0")) throws ErreurFichierException("Erreur: format incompatible (format nécessaire: ascii 1.0) -ligne 2-")
		return true;
	}
	
	private int lectureVertex(String l) {
		if (!l.substring(0,14).equals("element vertex"))
	}
}

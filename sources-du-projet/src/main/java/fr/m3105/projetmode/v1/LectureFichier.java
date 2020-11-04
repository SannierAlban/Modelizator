package fr.m3105.projetmode.v1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectureFichier {
	
	BufferedReader reader;
	
	public void lecture(String path) throws IOException{
		
		
		try {
			BufferedReader reader= new BufferedReader(new FileReader(path));
			
			String line;
			String [] format=new String[] {reader.readLine(), reader.readLine()};
			
			if (!verifFormat(format)) System.exit(1);
			int vertex=this.lectureVertex(reader.readLine());
			
		}catch(IOException | ErreurFichierException e) {
			e.printStackTrace();
		}
	}
	
	private boolean verifFormat(String[] l) throws ErreurFichierException {
		if (l[0]==null || l[1]==null) throw new ErreurFichierException("Erreur: fichier vide");
		if (!l[0].equals("ply")) throw new ErreurFichierException("Erreur: format incompatible (format necessaire: ply) -ligne 1-");
		if (!l[1].equals("format ascii 1.0")) throw new ErreurFichierException("Erreur: format incompatible (format necessaire: ascii 1.0) -ligne 2-");
		return true;
	}
	
	private int lectureVertex(String l) throws ErreurFichierException{
		if (!l.substring(0,14).equals("element vertex")) throw new ErreurFichierException("Erreur : mauvaise déclaration des points -ligne 3-");
		String vertex=l.substring(15);
		int res=this.isInt(vertex);
		if(res<=0) throw new ErreurFichierException("Erreur: le nombre de points doit être >0");
		return res;
	}
	
	private int isInt(String testing) {
		try {
			int res=Integer.parseInt(testing);
			return res;
		}catch(Exception e) {
			return -1;
		}
	}
	
	private List<String> lectureProperties() {
		List<String> res=new ArrayList<String>();
		
		return null; 
	}
}

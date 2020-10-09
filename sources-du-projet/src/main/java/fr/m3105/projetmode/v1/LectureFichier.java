package fr.m3105.projetmode.v1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectureFichier {
	
	BufferedReader reader;
	protected int vertex;
	protected List<String> props;
	protected int faces;
	
	LectureFichier(String path) throws IOException{
		this.lecture(path);
	}
	
	private void ouvertureFichier(String path) throws ErreurFichierException {
		try {
			reader= new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			throw new ErreurFichierException("Erreur: fichier introuvable");
		}
	}
	
	private void lecture(String path) throws IOException{
		
		
		try {
	
			this.ouvertureFichier(path);
			String [] format=new String[] {reader.readLine(), reader.readLine()};
			
			if (!verifFormat(format)) System.exit(1);
			vertex=this.lectureVertex(reader.readLine());
			props=this.lectureProperties();
			this.faces=this.lectureFace(reader.readLine());
			
			
		}catch(IOException | ErreurFichierException e) {
			e.printStackTrace();
		}
	}
	
	private boolean verifFormat(String[] lines) throws ErreurFichierException {
		if (lines[0]==null || lines[1]==null) throw new ErreurFichierException("Erreur: fichier vide");
		if (!lines[0].equals("ply")) throw new ErreurFichierException("Erreur: format incompatible (format necessaire: ply) -ligne 1-");
		if (!lines[1].equals("format ascii 1.0")) throw new ErreurFichierException("Erreur: format incompatible (format necessaire: ascii 1.0) -ligne 2-");
		return true;
	}
	
	private int lectureVertex(String line) throws ErreurFichierException{
		if (!line.substring(0,14).equals("element vertex")) throw new ErreurFichierException("Erreur : mauvaise déclaration des points -ligne 3-");
		String vertexStr=line.substring(15);
		int res=this.isInt(vertexStr);
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
	
	private List<String> lectureProperties() throws IOException {
		List<String> res=new ArrayList<String>();
		String current;
		while ((current=reader.readLine()).substring(0,8).equals("property")) {
			res.add(current.substring(9));
		}
		return res; 
	}
	
	private int lectureFace(String line) throws ErreurFichierException {
		if (!line.substring(0,12).equals("element face")) throw new ErreurFichierException("Erreur : mauvaise déclaration des faces");
		String faceStr=line.substring(15);
		int res=this.isInt(faceStr);
		if(res<=0) throw new ErreurFichierException("Erreur: le nombre de faces doit être >0");
		return res;
	}
	
}

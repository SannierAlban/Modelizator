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
	protected List<String> vertexProps;
	protected int faces;
	protected List<String> faceProps;
	protected List<Double[]> listVertex;
	
	LectureFichier(String path) throws IOException{
		//this.lecture(path);
	}
	
	BufferedReader ouvertureFichier(String path) throws ErreurFichierException {
		try {
			reader= new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			throw new ErreurFichierException("Erreur: fichier introuvable");
		}
		return reader;
	}
	
	private void lecture(String path) throws IOException{
		try {
	
			this.ouvertureFichier(path);
			this.lectureHeader();
			this.verifFinHeader(this.getNextLine());
			
		}catch(IOException | ErreurFichierException e) {
			e.printStackTrace();
		}
	}
	
	private void lectureHeader() throws IOException, ErreurFichierException {
		String [] format=new String[] {this.getNextLine(), this.getNextLine()};
		if (!verifFormat(format)) System.exit(1);
		this.vertex=this.lectureVertex(this.getNextLine());
		this.vertexProps=this.lectureProperties();
		this.faces=this.lectureFace(this.getNextLine());
		this.faceProps=this.lectureProperties();
	}
	
	private boolean isComment(String line) {
		return (line.substring(0,7).toLowerCase().equals("comment")); 
	}
	
	private String getNextLine() throws IOException {
		String current=reader.readLine();
		while (this.isComment((current=reader.readLine())));
		return current;
	}

	private boolean verifFormat(String[] lines) throws ErreurFichierException {
		if (lines[0]==null || lines[1]==null) throw new ErreurFichierException("Erreur: fichier vide");
		if (!lines[0].equals("ply")) throw new ErreurFichierException("Erreur: format incompatible (format necessaire: ply)");
		if (!lines[1].equals("format ascii 1.0")) throw new ErreurFichierException("Erreur: format incompatible (format necessaire: ascii 1.0)");
		return true;
	}
	
	private int lectureVertex(String line) throws ErreurFichierException{
		if (!line.substring(0,14).equals("element vertex")) throw new ErreurFichierException("Erreur : mauvaise declaration des points");
		String vertexStr=line.substring(15);
		int res=this.isInt(vertexStr);
		if(res<=0) throw new ErreurFichierException("Erreur: le nombre de points doit etre >0");
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
		while ((current=this.getNextLine()).substring(0,8).toLowerCase().equals("property")) {
			res.add(current.substring(9));
		}
		return res; 
	}
	
	private int lectureFace(String line) throws ErreurFichierException {
		if (!line.substring(0,12).equals("element face")) throw new ErreurFichierException("Erreur : mauvaise declaration des faces");
		String faceStr=line.substring(15);
		int res=this.isInt(faceStr);
		if(res<=0) throw new ErreurFichierException("Erreur: le nombre de faces doit etre >0");
		return res;
	}
	
	private void verifFinHeader(String line) throws ErreurFichierException {
		if (!line.equals("end_header"))throw new ErreurFichierException("Erreur: le header n'est pas fermé");
	}
	
	private List<Double[]> VertexVacuum(){
		
		return null;
	}
	
	private Double[] LineParser(String line) {
		String current="";
		int index=0;
		char c;
		Double[] res=new Double[3];
		while ((c=line.charAt(index++))!='\n') {
			while (c!=' ') {
				current+=c;
			}
			
		}
		return null;
	}
	
}

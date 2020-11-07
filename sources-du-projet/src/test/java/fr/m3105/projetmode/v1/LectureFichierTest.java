package fr.m3105.projetmode.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LectureFichierTest {

	private BufferedReader reader_test;
	LectureFichier liseuse;
	
	@BeforeAll
	public void setup() {
		try {
			reader_test=new BufferedReader(new FileReader("../../../exemples/meuh/ply"));
			liseuse=new LectureFichier("");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getNextLine_test() {
		
	}
	
	@Test
	public void ouvertureFichier_test() throws ErreurFichierException {
		BufferedReader reader=liseuse.ouvertureFichier("../../../exemples/meuh/ply");
	}
}


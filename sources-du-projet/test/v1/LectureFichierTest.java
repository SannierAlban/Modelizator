package v1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LectureFichierTest {
		
	
	@Test
	public void prints_the_file() {
		String path="../exemples/meuh.ply";
		LectureFichier.lecture(path);
	}
	
}

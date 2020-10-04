package fr.m3105.projetmode.v1;

public class ErreurFichierException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public ErreurFichierException(String errMsg) {
		super(errMsg);
	}
}

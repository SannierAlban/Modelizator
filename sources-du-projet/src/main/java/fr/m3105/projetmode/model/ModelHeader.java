package fr.m3105.projetmode.model;

/**
 * collects the model's information
 */
public class ModelHeader {

	private int nbDePoint;
	private int nbDeFace;
	
	private boolean color;
	private boolean alpha;
	
	public ModelHeader(String path) {
		Parser p = new Parser(path, true);
		nbDePoint = p.getVertex();
		nbDeFace = p.getNbFaces();
		
		color = p.isColor();
		alpha = p.isAlpha();
	}

	@Override
	public String toString() {
		return "ModelHeader [nbDePoint=" + nbDePoint + ", nbDeFace=" + nbDeFace + ", color=" + color + ", alpha="
				+ alpha + "]";
	}

	public int getNbDePoint() {
		return nbDePoint;
	}

	public int getNbDeFace() {
		return nbDeFace;
	}

	public boolean isColor() {
		return color;
	}

	public boolean isAlpha() {
		return alpha;
	}
	
}

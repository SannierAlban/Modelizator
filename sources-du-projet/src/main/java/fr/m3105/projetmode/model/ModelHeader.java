package fr.m3105.projetmode.model;

/**
 * collects the model's information
 */
public class ModelHeader {

	private final int nbDePoint;
	private final int nbDeFace;
	
	private final boolean color;
	private final boolean alpha;
	
	public ModelHeader(String path) {
		Parser parser = new Parser(path, true);
		nbDePoint = parser.getVertex();
		nbDeFace = parser.getNbFaces();
		
		color = parser.isColor();
		alpha = parser.isAlpha();
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

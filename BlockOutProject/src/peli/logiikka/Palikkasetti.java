package peli.logiikka;

public enum Palikkasetti {
	FLAT("Flat"), BASIC("Basic"), EXTENDED("Extended");
	
	private String nimi;
	
	private Palikkasetti(String setti) {
		this.nimi = setti;
	}
	
	public String annaNimi() {
		return this.nimi;
	}
}
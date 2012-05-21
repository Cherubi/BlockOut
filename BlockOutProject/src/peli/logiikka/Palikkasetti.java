package peli.logiikka;

public enum Palikkasetti {
	FLAT("Flat"), BASIC("Basic"), EXTENDED("Extended");
	
	private String nimi;
	
	/**
	* Luo pelin palikkasetin.
	* 
	* @param setti Halutun setin String-esitys
	*/
	private Palikkasetti(String setti) {
		this.nimi = setti;
	}
	
	/**
	* Antaa palikkasetin nimen.
	* 
	* @return Palikkasetin String-esitys
	*/
	public String annaNimi() {
		return this.nimi;
	}
}
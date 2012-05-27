package peli;

public class Koordinaatti {
	private int x, y, z;
	private int hashCode;
	
	/**
	* Sailoo (x, y) koordinaatin.
	* 
	* @param x X-koordinaatti
	* @param y Y-koordinaatti
	*/
	public Koordinaatti(int x, int y) {
		this(x, y, 0);
	}
	
	/**
	* Sailoo (x, y, z) koordinaatin.
	* 
	* @param x X-koordinaatti
	* @param y Y-koordinaatti
	* @param z Z-koordinaatti
	*/
	public Koordinaatti(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.hashCode = -1;
	}
	
	/**
	* Antaa x-koordinaatin.
	* 
	* @return X-koordinaatti
	*/
	public int annaX() {
		return this.x;
	}
	
	/**
	* Asettaa uuden x-koordinaatin.
	* 
	* @param x Uusi x-koordinaatti
	*/
	public void asetaX(int x) {
		this.x = x;
	}
	
	/**
	* Antaa y-koordinaatin.
	* 
	* @return Y-koordinaatti
	*/
	public int annaY() {
		return this.y;
	}
	
	/**
	* Asettaa uuden y-koordinaatin.
	* 
	* @param y Uusi y-koordinaatti
	*/
	public void asetaY(int y) {
		this.y = y;
	}
	
	/**
	* Antaa z-koordinaatin.
	* 
	* @return Z-koordinaatti
	*/
	public int annaZ() {
		return this.z;
	}
	
	/**
	* Asettaa uuden z-koordinaatin.
	* 
	* @param z Uusi z-koordinaatti
	*/
	public void asetaZ(int z) {
		this.z = z;
	}
	
	/**
	* Antaa koordinaatille hashkoodin, joka on yksiloiva kun x, y ja z ovat valilla [0-100[
	* 
	* @return Yksiloiva hashkoodi
	*/
	@Override
	public int hashCode() {
		this.hashCode = 10000*x + 100*y + z;
		return this.hashCode;
	}
	
	/**
	* Testaa onko toinen koordinaatti sama taman kanssa.
	* 
	* @param toinen Toinen koordinaatti.
	* @return Tieto siita olivatko samoja vai eivat
	*/
	public boolean equals(Object toinen) {
		if (hashCode() == toinen.hashCode() ) {
			return true;
		}
		return false;
	}
}
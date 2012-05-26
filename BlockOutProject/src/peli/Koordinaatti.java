package peli;

public class Koordinaatti {
	private int x, y, z;
	private int hashCode;
	
	public Koordinaatti(int x, int y) {
		this(x, y, 0);
	}
	
	public Koordinaatti(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.hashCode = -1;
	}
	
	public int annaX() {
		return this.x;
	}
	
	public void asetaX(int x) {
		this.x = x;
	}
	
	public int annaY() {
		return this.y;
	}
	
	public void asetaY(int y) {
		this.y = y;
	}
	
	public int annaZ() {
		return this.z;
	}
	
	public void asetaZ(int z) {
		this.z = z;
	}
	
	//toimii kun luvut >= 0 ja < 100
	public int hashCode() {
		if (this.hashCode != -1) {
			return this.hashCode;
		}
		
		this.hashCode = 10000*x + 100*y + z;
		return this.hashCode;
	}
	
	public boolean equals(Object toinen) {
		if (hashCode() == toinen.hashCode() ) {
			return true;
		}
		return false;
	}
}
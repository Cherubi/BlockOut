package peli.grafiikka;

public class DoubleKoordinaatti {
	private double x, y, z;
	
	public DoubleKoordinaatti(double x, double y) {
		this(x, y, 0);
	}
	
	public DoubleKoordinaatti(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double annaX() {
		return this.x;
	}
	
	public double annaY() {
		return this.y;
	}
	
	public double annaZ() {
		return this.z;
	}
}
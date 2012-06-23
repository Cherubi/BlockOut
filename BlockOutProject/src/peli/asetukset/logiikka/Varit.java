package peli.asetukset.logiikka;

import java.util.ArrayList;
import java.awt.Color;

public class Varit {
	private ArrayList<Color> varit;
	
	/**
	* Sisaltaa pelin kerrosten piirrossa kaytettavat varit.
	*/
	public Varit() {
		this.varit = new ArrayList<Color>();
		
		varit.add(Color.BLUE);
		varit.add(Color.GREEN);
		varit.add(Color.CYAN);
		varit.add(Color.RED);
		varit.add(Color.MAGENTA);
		varit.add(Color.ORANGE);
	}
	
	/**
	* Avaa varit tallenteesta.
	* 
	* @param tallenne Tallenne
	* @return Tieto siita tuleeko varien jalkeen viela tietoa asetuksista
	*/
	public boolean avaaVarit(String tallenne) {
		String[] tallennevarit = tallenne.split(" ");
		
		if (tallennevarit.length > 0) {
			varit.clear();
		}
		
		for (int i=0; i<tallennevarit.length; i++) {
			if (tallennevarit[i].equals("...")) {
				return true;
			}
			
			try {
				int hashKoodi = Integer.parseInt(tallennevarit[i]);
				Color vari = new Color( hashKoodi );
				this.varit.add(vari);
			} catch (NumberFormatException e) {
				System.out.println("Varin luonti ei onnistunut, koska syote ei ollut kokonaisnumero vaan " + tallennevarit[i].getClass() + "\nSyšte oli " + tallennevarit[i]);
				this.varit.add(Color.GRAY);
			} catch (Exception e) {
				System.out.println("Varin luonti ei onnistunut hashKoodista, joka on outoa koska kaikille kokonaisluvuille tulisi olla vari");
				this.varit.add(Color.GRAY);
			}
		}
		
		return false;
	}
	
	/**
	* Luo vareista tallenteen.
	* 
	* @return Tallenne
	*/
	public String tallennaVarit() {
		String tallenne = "";
		
		for (int i=0; i<varit.size(); i++) {
			tallenne += varit.get(i).hashCode();
			if (i<varit.size()-1) {
				tallenne += " ";
			}
		}
		
		return tallenne;
	}
	
	/**
	* Antaa asetetut varit.
	*
	* @return Varit
	*/
	public ArrayList<Color> annaVarit() {
		return this.varit;
	}
	
	/**
	* Antaa tietyn kerroksen varin.
	* 
	* @param kerros Kerros, jonka vari halutaan. Kerroksien numerointi alkaa pohjalta.
	* @return Vari, joka halutulle kerrokselle on asetettu
	*/
	public Color annaVari(int kerros) {
		while (kerros > varit.size()) {
			kerros = kerros - varit.size();
		}
		
		if (kerros <= 0) {
			System.out.println("Talle kerrokselle (" + kerros + ") ei voida antaa varia.");
			return Color.GRAY;
		}
		
		return varit.get(kerros-1);
	}
	
	/**
	* Vaihtaa tietyn kerroksen varin toiseksi.
	*
	* @param kerros Kerros, jonka varia halutaan vaihtaa. Kerroksien numerointi alkaa pohjalta.
	* @param vari Vari, joka kerrokselle halutaan asettaa.
	*/
	public boolean vaihdaVari(int kerros, Color vari) {
		if (kerros <= 0) {
			System.out.println("Talle kerrokselle (" + kerros + ") ei voida antaa varia.");
			return false;
		}
		
		if (kerros <= varit.size()) {
			vaihdaOlemassaOlevanKerroksenVari(kerros, vari);
			return true;
		}
		if (kerros == varit.size()+1) {
			varit.add(vari);
			return true;
		}
		
		System.out.println("Talle kerrokselle (" + kerros + ") ei voida antaa varia.");
		return false;
	}
	
	/**
	* Vaihtaa jo maaritellyn kerroksen varin.
	* 
	* @param kerros Kerros, jonka vari vaihdetaan
	* @param vari Vari, joka kerrokselle asetetaan
	*/
	private void vaihdaOlemassaOlevanKerroksenVari(int kerros, Color vari) {
		varit.remove(kerros-1);
		varit.add(kerros-1, vari);
	}
	
	/**
	* Poistaa varin variasetuksista.
	* 
	* @param kerros Kerros, jonka vari halutaan poistaa asetuksista
	* @return Tieto siita saatiinko kerros poistettua asetuksista
	*/
	public boolean poistaVari(int kerros) {
		if (kerros <=0 || kerros > varit.size()) {
			return false;
		}
		
		varit.remove(kerros-1);
		return true;
	}
}
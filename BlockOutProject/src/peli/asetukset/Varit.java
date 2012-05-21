package peli.asetukset;

import java.util.ArrayList;

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
	*/
	public void avaaVarit(String tallenne) {
		String[] tallennevarit = tallenne.split(" ");
		
		if (tallennevarit.length() > 0) {
			varit.clear();
		}
		
		for (int i=0; i<tallennevarit.length; i++) {
			int hashKoodi = Integer.parseInt(tallennevarit[i]);
			try {
				Color vari = new Color( hashKoodi );
				this.varit.add(vari);
			} catch (Exception e) {
				System.out.println("VŠrin luonti ei onnistunut.");
				this.varit.add(Color.GRAY);
			}
		}
	}
	
	/**
	* Luo vareista tallenteen.
	* 
	* @param Tallenne
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
			System.out.println("TŠlle kerrokselle (" + kerros + ") ei voida antaa vŠriŠ.");
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
			System.out.println("TŠlle kerrokselle (" + kerros + ") ei voida antaa vŠriŠ.");
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
		
		System.out.println("TŠlle kerrokselle (" + kerros + ") ei voida antaa vŠriŠ.");
		return false;
	}
	
	/**
	* Vaihtaa jo maaritellyn kerroksen varin.
	* 
	* @param kerros Kerros, jonka vari vaihdetaan
	* @param vari Vari, joka kerrokselle asetetaan
	*/
	private void vaihdaOlemassaOlevanKerroksenVari(int kerros, Color vari) {
		Color vaihdettavaVari = varit.get(kerros-1);
		vaihdettavaVari = vari;
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
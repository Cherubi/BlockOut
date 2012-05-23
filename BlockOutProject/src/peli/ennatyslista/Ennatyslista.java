package peli.ennatyslista;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

import java.io.Serializable;

public class Ennatyslista implements Serializable {
	private ArrayList<Ennatys> lista;
	private int pituus;
	
	private int pienempiLeveys, suurempiLeveys, syvyys;
	private String palikkasetti;
	
	/**
	* Hallinnoi ennatyslistaa, jolle voi paasta 10 parasta.
	* 
	* @param pienempiLeveys Pelin kuilun pienempi leveys ruuduissa
	* @param suurempiLeveys Pelin kuilun suurempi leveys ruuduissa
	* @param syvyys Pelin kuilun syvyys ruuduissa
	* @param palikkasetti Pelin palikkasetti
	*/
	public Ennatyslista(int pienempiLeveys, int suurempiLeveys, int syvyys, String palikkasetti) {
		this(10, pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
	}
	
	/**
	* Hallinnoi ennatyslistaa, jolle voi paasta x parasta.
	* 
	* @param pituus Listan pituus
	* @param pienempiLeveys Pelin kuilun pienempi leveys ruuduissa
	* @param suurempiLeveys Pelin kuilun suurempi leveys ruuduissa
	* @param syvyys Pelin kuilun syvyys ruuduissa
	* @param palikkasetti Pelin palikkasetti
	*/
	public Ennatyslista(int pituus, int pienempiLeveys, int suurempiLeveys, int syvyys, String palikkasetti) {
		this.pituus = pituus;
		
		this.pienempiLeveys = pienempiLeveys;
		this.suurempiLeveys = suurempiLeveys;
		this.syvyys = syvyys;
		
		this.palikkasetti = palikkasetti;
		
		luoUusiEnnatyslista();
	}
	
	/**
	* Luo uuden ennatyslistan.
	*/
	private void luoUusiEnnatyslista() {
		this.lista = new ArrayList<Ennatys>();
		for (int i=1; i<=pituus; i++) {
			lista.add( new Ennatys(0, "--") );
		}
	}
	
	/**
	* Avaa ennatyslistan.
	* 
	* @param lukija Skanneri, johon on liitetty tiedosto
	*/
	public void avaaLista(Scanner lukija) throws Exception {
		for (int i=1; i<=pituus; i++) {
			String listasija = lukija.nextLine();
			int pisteet = Integer.parseInt( listasija.substring(0, listasija.indexOf(" ")) );
			String nimi = listasija.substring(listasija.indexOf(" ") + 1, listasija.length());
			
			lisaaListalle(pisteet, nimi);
		}
	}
	
	/**
	* Luo ennatyslistasta merkkijonon tallennettavaksi.
	* 
	* @return Ennatyslista merkkijonomuodossa
	*/
	public String tallennaLista() {
		String tallenne = "";
		for (Ennatys ennatys : lista) {
			tallenne += ennatys.annaPisteet() + " " + ennatys.annaNimi() + "\n";
		}
		
		return tallenne;
	}
	
	/**
	* Selvittaa onko ennatyslistan parametrit samat kuin metodille annetut.
	* 
	* @param pienempiLeveys Pelin kuilun pienempi leveys ruuduissa
	* @param suurempiLeveys Pelin kuilun suurempi leveys ruuduissa
	* @param syvyys Pelin kuilun syvyys ruuduissa
	* @param palikkasetti Pelin palikkasetti
	*/
	public boolean onkoKokoJaSetti(int pienempiLeveys, int suurempiLeveys, int syvyys, String palikkasetti) {
		if (this.pienempiLeveys == pienempiLeveys && this.suurempiLeveys == suurempiLeveys && this.syvyys == syvyys && palikkasetti.toLowerCase().equals(this.palikkasetti.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	/**
	* Antaa ennatyslistan pienemman leveyden ruuduissa.
	* 
	* @return Pienempi leveys
	*/
	public int annaPienempiLeveys() {
		return this.pienempiLeveys;
	}
	
	/**
	* Antaa ennatyslistan suuremman leveyden ruuduissa.
	* 
	* @return Suurempi leveys
	*/
	public int annaSuurempiLeveys() {
		return this.suurempiLeveys;
	}
	
	/**
	* Antaa ennatyslistan syvyyden ruuduissa.
	* 
	* @return Syvyys
	*/
	public int annaSyvyys() {
		return this.syvyys;
	}
	
	/**
	* Antaa ennatyslistan palikkasetin merkkijono esityksessa.
	* 
	* @return Ennatyslistan palikkasetti
	*/
	public String annaPalikkasetti() {
		return this.palikkasetti;
	}
	
	/**
	* Selvittaa paaseeko tulos listalle.
	* 
	* @param pisteet Tuloksen pistemaara
	* @return Tieto siita paaseeko tulos listalle vai ei
	*/
	public boolean paaseekoListalle(int pisteet) {
		if (pisteet > getPieninPistemaara()) {
			return true;
		}
		return false;
	}
	
	/**
	* Antaa listan pienimman pistemaaran.
	* 
	* @return Pistemaara
	*/
	private int getPieninPistemaara() {
		Collections.sort(lista);
		return lista.get(lista.size()-1).annaPisteet();
	}
	
	/**
	* Lisaa listalle tuloksen jos tulos on tarpeeksi hyva.
	* 
	* @param pisteet Tuloksen pisteet
	* @param nimi Tuloksen tekijan nimi
	*/
	public void lisaaListalle(int pisteet, String nimi) {
		if (paaseekoListalle(pisteet)) {
			lista.add( new Ennatys(pisteet, nimi) );
			Collections.sort(lista);
			lista.remove(pituus);
		}
	}
	
	/**
	* Antaa ennatyslistalta listasijan.
	* 
	* @param Listasijan numero, joka halutaan
	* @return Listasijan pitajan pisteet ja nimi merkkijonona
	*/
	public String annaListanSija(int monesko) {
		return String.format("%5d", lista.get(monesko-1).annaPisteet()) + " " + lista.get(monesko-1).annaNimi();
	}
}
package peli.ennatyslista;

import java.io.Serializable;

public class Ennatys implements Comparable<Ennatys>, Serializable {
	private int pistemaara;
	private String nimi;
	
	/**
	* Hallinnoi yhta ennatysta.
	* 
	* @param pisteet Ennatyksen pisteet
	* @param nimi Ennatyksen tekijan nimi
	*/
	public Ennatys(int pisteet, String nimi) {
		this.pistemaara = pisteet;
		this.nimi = nimi;
	}
	
	/**
	* Antaa ennatyksen tekijan nimen.
	* 
	* @return Nimi
	*/
	public String annaNimi() {
		return nimi;
	}
	
	/**
	* Antaa ennatyksen pisteet.
	* 
	* @return Pisteet
	*/
	public int annaPisteet() {
		return pistemaara;
	}
	
	/**
	* Vertailee ennatysta toiseen ennatykseen jarjestelya varten pisteiden perusteella.
	* 
	* @param toinen Toinen ennatys
	* @return Jarjestyksen kertova numero
	*/
	public int compareTo(Ennatys toinen) {
		return toinen.annaPisteet() - pistemaara;
	}
}
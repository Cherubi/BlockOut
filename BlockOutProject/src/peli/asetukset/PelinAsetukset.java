package peli.asetukset;

import kayttoliittyma.BlockOut;
import valmiskomponentit.Ikkuna;

import java.util.ArrayList;

public class PelinAsetukset extends Ikkuna {
	private BlockOut kayttis;
	private String tallenneosoite;
	
	private ArrayList<Asetukset> tallennetutAsetukset;
	private int valittuAsetus;
	
	/**
	* Hallinnoi pelin asetuksia.
	* 
	* @param kayttis Kayttoliittyma, johon asetukset on kiinnitetty
	* @param tallenneosoite Tallennetiedoston osoite
	*/
	public PelinAsetukset(BlockOut kayttis, String tallenneosoite) {
		this.kayttis = kayttis;
		this.tallenneosoite = tallenneosoite;
	}
	
	/**
	* Tallentaa asetuksissa tehdyt muutokset.
	*/
	public void tallennaTallennokset() {
	
	}
}
package peli.asetukset;

import peli.logiikka.Palikkasetti;

import java.util.Scanner;
import java.io.FileWriter;

public class Asetukset {
	private PelinAsetukset pelinAsetukset;
	
	private String asetustenNimi;
	
	private Ulottuvuudet ulottuvuudet;
	private int aloitustaso;
	
	private Palikkasetti palikkasetti;
	private Nappainsetti nappainsetti;
	private Varit varit;
	
	/**
	* Hallinnoi yhta settia asetuksia. Sisaltaa seka pelin parametreja etta pelin grafiikkaan ja pelaamiseen liittyvia valintoja.
	*/
	public Asetukset(PelinAsetukset pelinAsetukset) {
		this.pelinAsetukset = pelinAsetukset;
		
		this.asetustenNimi = "";
		
		this.ulottuvuudet = new Ulottuvuudet();
		this.aloitustaso = 0;
		
		this.palikkasetti = Palikkasetti.FLAT;
		this.nappainsetti = new Nappainsetti();
		this.varit = new Varit();
	}
	
	/**
	* Avaa tallennetut asetukset skannerin antamasta syotteesta.
	* 
	* @param lukija Skanneri, jolle on annettu luettava tiedosto.
	*/
	public void avaaAsetukset(Scanner lukija) {
		asetaAsetustenNimi(lukija.nextLine());
		
		this.ulottuvuudet.avaaUlottuvuudet(lukija.nextLine());
		asetaAloitustaso( Integer.parseInt(lukija.nextLine()) );
		
		avaaPalikkasetti(lukija.nextLine());
		this.nappainsetti.avaaNappainsetti(lukija.nextLine());
		this.varit.avaaVarit(lukija.nextLine());
	}
	
	/**
	* Asettaa merkkijonon perusteella asetuksiin palikkasetin, jolla tullaan pelaamaan.
	* 
	* @param tallenne Merkkijono, jonka perusteella palikkasetti valitaan
	*/
	private void avaaPalikkasetti(String tallenne) {
		if (tallenne.equals("basic")) {
			asetaPalikkasetti(Palikkasetti.BASIC);
		}
		else if (tallenne.equals("extended")) {
			asetaPalikkasetti(Palikkasetti.EXTENDED);
		}
		
		// muussa tapauksessa jaa FLAT
	}
	
	/**
	* Tallentaa asetukset niin kuin pelaaja on ne kayttoliittymasta valinnut.
	* 
	* @param kirjuri FileWriter, johon on avattu tiedosto mihin kirjoitetaan
	*/
	public void tallennaAsetukset(FileWriter kirjuri) throws Exception {
		kirjuri.write(asetustenNimi + "\n");
		
		kirjuri.write(ulottuvuudet.tallennaUlottuvuudet() + "\n");
		kirjuri.write(aloitustaso + "\n");
		
		kirjuri.write(palikkasetti.annaNimi().toLowerCase() + "\n");
		
		kirjuri.write(nappainsetti.tallennaNappainsetti() + "\n");
		kirjuri.write(varit.tallennaVarit() + "\n");
	}
	
	//*********************************
	//
	//  setterit ja getterit
	//
	//*********************************
	
	/**
	* Antaa asetuksiin tallennetun nimen (asetusten nimi).
	* 
	* @return Asetusten nimi
	*/
	public String annaAsetustenNimi() {
		return asetustenNimi;
	}
	
	/**
	* Asettaa asetuksiin uuden nimen.
	* 
	* @param Asetusten uusi nimi
	*/
	public boolean asetaAsetustenNimi(String nimi) {
		this.asetustenNimi = nimi;
		pelinAsetukset.tallennaTallennokset();
		return true;
	}
	
	/**
	* Antaa asetuksiin tallennetut ulottuvuudet (pelin leveys, korkeus, syvyys, pelin seinamien leikkauspisteen etaisyys)
	*
	* @return Ulottuvuudet
	*/
	public Ulottuvuudet annaUlottuvuudet() {
		return this.ulottuvuudet;
	}
	
	/**
	* Antaa asetuksiin tallennetun aloitustason
	* 
	* @return Aloitustaso
	*/
	public int annaAloitustaso() {
		return this.aloitustaso;
	}
	
	/**
	* Antaa pienimman mahdollisen aloitustason pelissa.
	* 
	* @return Aloitustaso
	*/
	public int annaMinimitaso() {
		return 0;
	}
	
	/**
	* Antaa suurimman mahdollisen aloitustason pelissa.
	* 
	* @return Aloitustaso
	*/
	public int annaMaksimitaso() {
		return 10;
	}
	
	/**
	* Asettaa asetuksiin pelin aloitustason.
	* 
	* @param taso Asetettava aloitustaso
	*/
	public void asetaAloitustaso(int taso) {
		if (taso >= 0 && taso <=10) {
			this.aloitustaso = taso;
			pelinAsetukset.tallennaTallennokset();
		}
	}
	
	/**
	* Antaa asetuksien palikkasetin (FLAT, BASIC, EXTENDED)
	* 
	* @return Asetuksien palikkasetti
	*/
	public Palikkasetti annaPalikkasetti() {
		return this.palikkasetti;
	}
	
	/**
	* Asettaa asetuksiin palikkasetin.
	* 
	* @param palikkasetti Asetettava palikkasetti
	* @return Tieto onnistuiko asetus vai ei
	*/
	public boolean asetaPalikkasetti(Palikkasetti palikkasetti) {
		this.palikkasetti = palikkasetti;
		pelinAsetukset.tallennaTallennokset();
		return true;
	}
	
	/**
	* Antaa asetuksien nappainsetin eli tiedot toiminto-nappain yhteyksista
	* 
	* @return Asetuksien nappainsetti
	*/
	public Nappainsetti annaNappainsetti() {
		return this.nappainsetti;
	}
	
	/**
	* Antaa asetuksien varisetin eli tiedon siita milla vareilla mitakin kerrosta pelissa varitetaan.
	* 
	* @return Asetuksien varit
	*/
	public Varit annaVarit() {
		return this.varit;
	}
}
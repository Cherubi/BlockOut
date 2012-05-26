package peli.grafiikka;

import peli.Peli;
import peli.asetukset.Ulottuvuudet;
import peli.asetukset.Varit;
import peli.logiikka.Pala;
import peli.logiikka.Palikkasetti;
import peli.logiikka.Pistelaskija;
import peli.logiikka.TippuvaPalikka;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Piirturi {
	private Peli peli;
	private Piste3DHaku piste3DHaku;
	private Reunapiirturi reunapiirturi;
	
	private int ikkunanLeveys, ikkunanKorkeus, tietopalkinLeveys;
	
	/**
	* Hoitaa pelin piirtamisen.
	* 
	* @param peli Peli, jota piirretaan
	* @param ikkunanLeveys Kaytettavan ikkunan leveys
	* @param ikkunanKorkeus Kaytettavan ikkunan korkeus
	* @param ulottuvuudet Asetuksissa valitut ulottuvuudet kuilulle
	* @param palikkasetti Peliin valittu palikkasetti
	* @param varit Eri kerrosten piirtamiseen tarkoitetut varit
	* @param pistelaskija Pelin pistelaskija
	*/
	public Piirturi(Peli peli, int ikkunanLeveys, int ikkunanKorkeus, Ulottuvuudet ulottuvuudet, Palikkasetti palikkasetti, Varit varit, Pistelaskija pistelaskija) {
		this.peli = peli;
		
		this.ikkunanLeveys = ikkunanLeveys;
		this.tietopalkinLeveys = 130;
		this.ikkunanKorkeus = ikkunanKorkeus;
		
		luoPiste3DHaku(ulottuvuudet);
		
		luoSisaisetPiirturit();
	}
	
	private void luoPiste3DHaku(Ulottuvuudet ulottuvuudet) {
		int kentanLeveys = ulottuvuudet.annaLeveys();
		int kentanKorkeus = ulottuvuudet.annaKorkeus();
		int kentanSyvyys = ulottuvuudet.annaSyvyys();
		int leikkauspiste = ulottuvuudet.annaLeikkauspiste();
		
		this.piste3DHaku = new Piste3DHaku( this.ikkunanLeveys-this.tietopalkinLeveys*2, this.tietopalkinLeveys, this.ikkunanKorkeus, leikkauspiste, kentanLeveys, kentanKorkeus, kentanSyvyys );
	}
	
	private void luoSisaisetPiirturit() {
		this.reunapiirturi = new Reunapiirturi(this.piste3DHaku);
	}
	
	/**
	* Paivittaa nakyman.
	*/
	public void paivita() {
		peli.paivita();
	}
	
	/**
	* Piirtaa kaikki pelin komponentit.
	* 
	* @param g Graphics
	* @param kuilu Pelin kuilu/kentta
	* @param tippuvaPalikka Tippuva palikka
	* @param palojaSisaltavienKerrostenMaara Paloja sisaltavien kerrosten maara
	*/
	public void piirra(Graphics g, Pala[][][] kuilu, TippuvaPalikka tippuvaPalikka, int palojaSisaltavienKerrostenMaara) {
		this.reunapiirturi.piirra(g, kuilu);
	}
}
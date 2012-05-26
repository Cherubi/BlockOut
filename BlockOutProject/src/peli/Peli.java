package peli;

import kayttoliittyma.BlockOut;
import peli.asetukset.Asetukset;
import peli.asetukset.Ulottuvuudet;
import peli.ennatyslista.Ennatyslistaaja;
import peli.grafiikka.Piirturi;
import peli.logiikka.Kentta;
import peli.logiikka.Palikkavarasto;
import peli.logiikka.Pistelaskija;
import peli.logiikka.TippuvaPalikka;
import valmiskomponentit.Ikkuna;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Peli extends Ikkuna {
	private BlockOut kayttis;
	private Piirturi piirturi;
	private Ennatyslistaaja ennatyslistaaja;
	
	// Hallinnoitavat oliot:
	
	private Kentta kentta;
	private Palikkavarasto palikkavarasto;
	private TippuvaPalikka tippuvaPalikka;
	//private Ajastin ajastin;
	private double tiputustenVali;
	private double pohjaAika = 5.51, aikatasokerroin = 0.64;
	
	// Pelin statuksen, pisteiden ja tasojen hallinta
	
	private Pistelaskija pistelaskija;
	private int taso, pelattujaPalikoita;
	private boolean tauolla, gameOver;
	private long gameOverHetki;
	
	/**
	* Alustaa ja hallinnoi pelin osasia.
	* 
	* @param kayttis Kayttoliittyma
	* @param asetukset Kayttajan valitsemat asetukset
	* @param ennatyslistaaja Ennatyslistan hallinnoija
	*/
	public Peli(BlockOut kayttis, Asetukset asetukset, Ennatyslistaaja ennatyslistaaja) {
		this.kayttis = kayttis;
		this.ennatyslistaaja = ennatyslistaaja;
		
		asetaPeliValmiiksi(asetukset);
	}
	
	private void asetaPeliValmiiksi(Asetukset asetukset) {
		alustaTilanne(asetukset.annaAloitustaso());
		
		Ulottuvuudet ulottuvuudet = asetukset.annaUlottuvuudet();
		
		alustaLogiikka(asetukset, ulottuvuudet);
		alustaGrafiikka(asetukset, ulottuvuudet);
		
		//NappainKuuntelija nappainKuuntelija = new NappainKuuntelija( this, this.kayttis, asetukset.annaNappainsetti() );
		//this.addKeyListener(nappainKuuntelija);
	}
	
	private void alustaTilanne(int aloitustaso) {
		this.tauolla = true;
		this.gameOver = false;
		this.gameOverHetki = -1;
		
		this.taso = aloitustaso;
		this.pelattujaPalikoita = 0;
		
		this.tiputustenVali = pohjaAika * Math.pow(aikatasokerroin, taso);
	}
	
	private void alustaLogiikka(Asetukset asetukset, Ulottuvuudet ulottuvuudet) {
		this.pistelaskija = new Pistelaskija( this, ulottuvuudet.annaSyvyys(), asetukset.annaPalikkasetti() );
		
		this.kentta = new Kentta(this, pistelaskija, ulottuvuudet);
		
		this.palikkavarasto = new Palikkavarasto(asetukset.annaPalikkasetti());
	}
	
	private void alustaGrafiikka(Asetukset asetukset, Ulottuvuudet ulottuvuudet) {
		this.piirturi = new Piirturi( this, 800, 491, ulottuvuudet, asetukset.annaPalikkasetti(), asetukset.annaVarit(), this.pistelaskija );
	}
	
	/**
	* Asettaa peliin uudet asetukset. Kayttajan muuttamista asetuksista vain grafiikkaan ja nappaimiin vaikuttavat asetukset otetaan voimaan.
	*/
	public void asetaUudetAsetukset() {
	
	}
	
	//*******************************************
	//
	// Hallinnoi pelin statusta
	//
	//*******************************************
	
	/**
	* Aloittaa pelin. Peli on valmisteltu kaynnistettavaksi kun Peli-luokka on luotu.
	*/
	public void aloitaPeli() {
		haeTippuvaPalikka();
		
		this.tauolla = false;
		ajastaSeuraavaTiputus();
	}
	
	/**
	* Asettaa pelin tauolle tai tauolta pois.
	* 
	* @param tauolla Tieto siita halutaanko peli tauolle vai tauolta pois
	*/
	public void asetaPeliTauolle(boolean tauolla) {
	
	}
	
	/**
	* Lopettaa pelin.
	* 
	* @return Tieto siita paasiko pelin pistetulos ennatyslistalle
	*/
	public boolean lopetaPeli() {
		return false;
	}
	
	/**
	* 
	*/
	public int annaTaso() {
		return this.taso;
	}
	
	//*******************************************
	//
	// Hallinnoi pelin etenemista
	//
	//*******************************************
	
	private void annaTippuvaPalikka() {
		//return this.tippuvaPalikka;
	}
	
	private void haeTippuvaPalikka() {
		this.tippuvaPalikka = null;
	}
	
	private void ajastaSeuraavaTiputus() {}
	
	/**
	* 
	* 
	* @param
	* @return
	*/
	
	/**
	* 
	* 
	* @param
	* @return
	*/
	
	/**
	* 
	* 
	* @param
	* @return
	*/
	
	/**
	* 
	* 
	* @param
	* @return
	*/
	
	//*******************************************
	//
	// Hallinnoi pelin etenemista
	//
	//*******************************************
	
	/**
	* 
	* 
	* @param
	* @return
	*/
	public void paivita() {
		this.repaint();
	}
	
	/**
	* Piirtaa pelinakyman.
	* 
	* @param g Graphics
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.piirturi.piirra(g, kentta.annaKentta(), tippuvaPalikka, kentta.annaPalojaSisaltavienKerrostenMaara() );
		
		if (gameOver) {
			g.setColor(Color.BLACK);
			Font fontti = new Font("futura", Font.PLAIN, 30);
			g.setFont(fontti);
			g.drawString("Game over", 800/5*2, 491/2);
			fontti = new Font("futura", Font.PLAIN, 14);
			g.setFont(fontti);
			g.drawString("Press any key.", 800/5*2+20, 491/2+30);
			
			if (this.ennatyslistaaja.paaseekoListalle( pistelaskija.annaPisteet(), kentta.annaLeveys(), kentta.annaKorkeus(), kentta.annaSyvyys(), palikkavarasto.annaPalikkasetti() )) {
				//TODO nayta tato
			}
		}
	}
}
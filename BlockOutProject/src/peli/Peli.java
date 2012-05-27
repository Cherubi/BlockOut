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
	* Kertoo onko peli tauolla.
	* 
	* @return Tieto siita onko peli tauolla vai ei
	*/
	public boolean onkoTauolla() {
		return this.tauolla;
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
	* Antaa silla hetkella pelattavan tason.
	* 
	* @return Taso, jota pelataan
	*/
	public int annaTaso() {
		return this.taso;
	}
	
	/**
	* Lisaa pelattujen palojen maaraa ja kun pelattuja paloja tulee tarpeeksi nostaa tasoa.
	* 
	* @param maara Uusien pelattujen palojen maara
	*/
	public void lisaaPelattujenPalojenMaaraa(int maara) {
		this.pelattujaPalikoita += maara;
		
		if (this.pelattujaPalikoita >= 15*( kentta.annaLeveys() + kentta.annaKorkeus() ) * (this.taso+1) ) {
			this.taso++;
			this.tiputustenVali *= aikatasokerroin;
		}
	}
	
	//*******************************************
	//
	// Hallinnoi pelin etenemista
	//
	//*******************************************
	
	/**
	* Hakee uuden tippuvan palikan kenttaan edellisen tilalle ja lisaa sille ajastimen huolehtimaan tippumisesta.
	*/
	public void haeUusiPalikkaKenttaan() {
		haeTippuvaPalikka();
		paivita();
		
		ajastaSeuraavaTiputus();
	}
	
	private void haeTippuvaPalikka() {
		//TODO jos tippuva palikka reunojen paalla jo annettaessa niin ei saisi johtaa gameoveriin, ratkaisuehdotus: palikat annetaan niin, ettei tule tata ongelmaa
		if (!kentta.onkoKentanEdustaVapaana()) {
			this.tauolla = true;
			this.gameOver = true;
			this.gameOverHetki = System.currentTimeMillis();
			return;
		}
		
		this.tippuvaPalikka = new TippuvaPalikka(palikkavarasto.annaPalikka(), kentta, this);
	}
	
	/**
	* Antaa silla hetkella tippuvan palikan.
	* 
	* @return Tippuva palikka
	*/
	public TippuvaPalikka annaTippuvaPalikka() {
		return this.tippuvaPalikka;
	}
	
	private void ajastaSeuraavaTiputus() {}
	
	//*******************************************
	//
	// Hallinnoi pelin etenemista
	//
	//*******************************************
	
	/**
	* Paivittaa nakyman.
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
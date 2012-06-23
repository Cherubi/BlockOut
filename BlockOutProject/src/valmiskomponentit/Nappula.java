package valmiskomponentit;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JButton;

public class Nappula extends JButton {
	private Color tekstinVari, taustanVari, tekstinVariAktiivisena, taustanVariAktiivisena;
	private String fontinNimi;
	private int fontinKoko;
	
	private int nappulanKavennus;
	private int yvYlitys, yoYlitys, avYlitys, aoYlitys, vyYlitys, vaYlitys, oyYlitys, oaYlitys;
	
	/**
	* Luo JButtonin, jonka ulkonakoa on muutettu.
	* 
	* @param teksti Teksti, joka nappulaan halutaan
	*/
	public Nappula(String teksti) {
		this(teksti, Color.WHITE, Color.BLACK);
	}
	
	/**
	* Luo JButtonin, jonka ulkonakoa on muutettu. Kayttaja valitsee tekstin ja taustan varin.
	* 
	* @param teksti Teksti, joka nappulaan halutaan
	* @param tekstinVari Vari, jonka kayttaja haluaa tekstille
	* @param taustanVari Vari, jonka kayttaja haluaa nappulalle tekstin taakse
	*/
	public Nappula(String teksti, Color tekstinVari, Color taustanVari) {
		super(teksti);
		
		this.setBorderPainted(false); //WINDOWSia varten
		
		this.tekstinVari = tekstinVari;
		this.tekstinVariAktiivisena = tekstinVari;
		this.taustanVari = taustanVari;
		this.taustanVariAktiivisena = taustanVari;
		
		this.fontinNimi = "futura";
		this.fontinKoko = 12;
		
		this.nappulanKavennus = 0;
		
		arvoNappulanReunojenYlitykset();
	}
	
	private void arvoNappulanReunojenYlitykset() {
		Random arpoja = new Random();
		
		yvYlitys = arpoja.nextInt(4)+1;
		yoYlitys = arpoja.nextInt(4)+1;
		avYlitys = arpoja.nextInt(4)+1;
		aoYlitys = arpoja.nextInt(4)+1;
		vyYlitys = arpoja.nextInt(4)+1;
		vaYlitys = arpoja.nextInt(4)+1;
		oyYlitys = arpoja.nextInt(4)+1;
		oaYlitys = arpoja.nextInt(4)+1;
	}
	
	/**
	* Asettaa nappulaan uuden tekstin.
	* 
	* @param teksti Uusi teksti, joka nappulaan halutaan
	*/
	public void asetaTeksti(String teksti) {
		setText(teksti);
	}
	
	/**
	* Asettaa fontin, jolla teksti kirjoitetaan nappulaan
	* 
	* @param fontinNimi Fontin nimi
	* @param fontinKoko Fontin koko, jonka kokoinen tai jota pienempi fontin tulee olla
	*/
	public void asetaFontti(String fontinNimi, int fontinKoko) {
		this.fontinNimi = fontinNimi;
		this.fontinKoko = fontinKoko;
	}
	
	/**
	* Asettaa tekstin varin, jolla teksti kirjoitetaan
	* 
	* @param vari Asetettava vari
	*/
	public void asetaTekstinVari(Color vari) {
		if (this.tekstinVariAktiivisena == this.tekstinVari) {
			this.tekstinVari = vari;
		}
		this.tekstinVariAktiivisena = vari;
	}
	
	/**
	* Asettaa taustan varin, jolla nappula varitetaan
	* 
	* @param vari Asetettava vari
	*/
	public void asetaTaustanVari(Color vari) {
		if (this.taustanVariAktiivisena == this.taustanVari) {
			this.taustanVari = vari;
		}
		this.taustanVariAktiivisena = vari;
	}
	
	/**
	* Asettaa nappulan leveydelle kavennuksen minka verran nappulaa kavennetaan seka oikealta, etta vasemmalta
	* 
	* @param kavennus Kavennuksen maara
	*/
	public void asetaNappulanKavennus(int kavennus) {
		this.nappulanKavennus = kavennus;
	}
	
	/**
	* Asettaa nappulan aktiivisuuden. Normaaliin JButtoniin verrattuna muuttaa myos nappulan tekstin ja taustan varit tummemmiksi.
	* 
	* @param aktiivinen Nappulan uusi aktiivisuus
	*/
	@Override
	public void setEnabled(boolean aktiivinen) {
		super.setEnabled(aktiivinen);
		
		if (aktiivinen) {
			this.tekstinVari = this.tekstinVariAktiivisena;
			this.taustanVari = this.taustanVariAktiivisena;
		}
		else {
			this.tekstinVari = this.tekstinVariAktiivisena.darker().darker();
			this.taustanVari = this.taustanVariAktiivisena.darker().darker();
		}
	}
	
	/**
	* Piirtaa nappulan muunnellun nakoiseksi.
	* 
	* @param g Graphics
	*/
	@Override
	public void paintComponent(Graphics g) {
		varitaTausta(g);
		varitaReunat(g);
		piirraTeksti(g);
	}
	
	/**
	* Varittaa nappulan taustan
	* 
	* @param g Graphics
	*/
	private void varitaTausta(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0+nappulanKavennus, 0, getWidth()-2*nappulanKavennus, getHeight());
		
		g.setColor(this.taustanVari);
		g.fillRect(5+nappulanKavennus, 5, getWidth()-2*5-nappulanKavennus*2, getHeight()-2*5);
	}
	
	/**
	* Piirtaa nappulan reunat
	* 
	* @param g Graphics
	*/
	private void varitaReunat(Graphics g) {
		g.setColor(Color.WHITE);
		
		Random arpoja = new Random();
		
		//ylempi vaakaviiva
		int ekaYlitys = yvYlitys;
		int tokaYlitys = yoYlitys;
		g.drawRect(5-tokaYlitys+nappulanKavennus, 5, getWidth()-2*nappulanKavennus-2*5+ekaYlitys+tokaYlitys, 1);
		
		//alempi vaakaviiva
		ekaYlitys = avYlitys;
		tokaYlitys = aoYlitys;
		g.drawRect(5-tokaYlitys+nappulanKavennus, getHeight()-5, getWidth()-2*nappulanKavennus-2*5+ekaYlitys+tokaYlitys, 1);
		
		//vasen pystyviiva
		ekaYlitys = vyYlitys;
		tokaYlitys = vaYlitys;
		g.drawRect(nappulanKavennus+5, 5-ekaYlitys, 1, getHeight()-2*5+ekaYlitys+tokaYlitys);
		
		//oikea pystyviiva
		ekaYlitys = oyYlitys;
		tokaYlitys = oaYlitys;
		g.drawRect(getWidth()-nappulanKavennus-5, 5-ekaYlitys, 1, getHeight()-2*5+ekaYlitys+tokaYlitys);
	}
	
	/**
	* Kirjoittaa tekstin nappulaan keskittamisen jalkeen.
	* 
	* @param g Graphics
	*/
	private void piirraTeksti(Graphics g) {
		Font f = haeRuutuunSovitettuFontti(g);
		int tekstinLeveys = laskeTekstinLeveys(g);
		int tekstinKorkeus = laskeTekstinKorkeus(g);
		
		int nappulanLeveys = getWidth() - 2*nappulanKavennus - 2*5;
		int tekstinVaakakeskitys = (nappulanLeveys-tekstinLeveys)/2;
		
		int nappulanKorkeus = getHeight() - 2*5;
		int tekstinPystykeskitys = (nappulanKorkeus-tekstinKorkeus)/2;
		
		g.setColor(this.tekstinVari);
		g.drawString(getText(), nappulanKavennus+5+tekstinVaakakeskitys, 5+tekstinPystykeskitys + tekstinKorkeus);
	}
	
	/**
	* Etsii fonttikoon, jolla nappulan teksti mahtuu nappulaan.
	* 
	* @param g Graphics
	* @return Fontti, jonka fonttikoko on asetettu
	*/
	private Font haeRuutuunSovitettuFontti(Graphics g) {
		int tekstinLeveys = 0;
		int fontinSovitettuKoko = this.fontinKoko;
		
		Font f;
		do {
			f = new Font(this.fontinNimi, Font.PLAIN, fontinSovitettuKoko);
			g.setFont(f);
			tekstinLeveys = laskeTekstinLeveys(g);
			
			fontinSovitettuKoko--;
		} while (tekstinLeveys > getWidth()-2*nappulanKavennus-2*5-2);
		
		return f;
	}
	
	/**
	* Laskee tekstin leveyden grafiikalle asetetun fontin perusteella
	* 
	* @param g Graphics
	* @return Tekstin leveys
	*/
	private int laskeTekstinLeveys(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		return fm.charsWidth(getText().toCharArray(), 0, getText().length());
	}
	
	/**
	* Laskee tekstin korkeuden grafiikalle asetetun fontin perusteella
	* 
	* @param g Graphics
	* @return Tekstin korkeus
	*/
	private int laskeTekstinKorkeus(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		return fm.getMaxAscent();
	}
	
}
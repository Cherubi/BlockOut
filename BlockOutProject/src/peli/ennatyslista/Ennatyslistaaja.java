package peli.ennatyslista;

import peli.logiikka.Palikkasetti;
import valmiskomponentit.Ikkuna;
import valmiskomponentit.Nappula;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ennatyslistaaja extends Ikkuna {
	private Ennatyslistat ennatyslistat;
	private int valittuPienempiLeveys, valittuSuurempiLeveys, valittuSyvyys;
	private Palikkasetti valittuPalikkasetti;
	
	private boolean yleinenListaNakyvissa;
	
	private boolean kysytaanNimea;
	private int lisattavanPisteet;
	private String lisattavanNimi;
	
	private JPanel listapaneli, valikkopaneli, nimipaneli;
	private Saatopaneli saatopaneli;
	
	/**
	* Hallinnoi ennatyslistoja.
	*/
	public Ennatyslistaaja() {
		this.ennatyslistat = new Ennatyslistat("ennatyslistat.javafile");
		
		this.valittuPienempiLeveys = 5;
		this.valittuSuurempiLeveys = 5;
		this.valittuSyvyys = 10;
		this.valittuPalikkasetti = Palikkasetti.FLAT;
		
		this.yleinenListaNakyvissa = false;
		
		this.kysytaanNimea = false;
		this.lisattavanPisteet = -1;
		this.lisattavanNimi = ""; //max 15 merkkia //TODO FontMetrics
		
		luoJPanelinSisalto();
		
		this.addKeyListener(new NappainKuuntelija(this));
	}
	
	private void luoJPanelinSisalto() {
		this.setLayout(new BorderLayout());
		JLabel alalaita = new JLabel("-");
		alalaita.setOpaque(false);
		this.add(alalaita, BorderLayout.SOUTH);
		
		JPanel paneli = new JPanel(new GridLayout(1,2));
		paneli.setOpaque(false);
		this.add(paneli, BorderLayout.CENTER);
		
		this.listapaneli = new JPanel(new BorderLayout());
		this.listapaneli.setOpaque(false);
		paneli.add(this.listapaneli);
		luoListapanelinSisalto();
		
		this.valikkopaneli = new JPanel(new GridLayout(2,1));
		this.valikkopaneli.setOpaque(false);
		paneli.add(this.valikkopaneli);
		luoValikkopanelinSisalto();
	}
	
	private void luoListapanelinSisalto() {
		JPanel listavalitsin = new JPanel(new FlowLayout());
		listavalitsin.setOpaque(false);
		
		Nappula kuilulista = new Nappula("Kuilukohtainen");
		kuilulista.setEnabled(false);
		kuilulista.setFocusable(false);
		kuilulista.setOpaque(false);
		kuilulista.addActionListener( new Listakuuntelija(this, listavalitsin) );
		listavalitsin.add(kuilulista);
		
		Nappula yleinenlista = new Nappula("Yleinen");
		yleinenlista.setEnabled(true);
		yleinenlista.setFocusable(false);
		yleinenlista.setOpaque(false);
		yleinenlista.addActionListener( new Listakuuntelija(this, listavalitsin) );
		listavalitsin.add(yleinenlista);
		
		this.listapaneli.add(listavalitsin, BorderLayout.SOUTH);
	}
	
	private static class Listakuuntelija implements ActionListener {
		private Ennatyslistaaja ennatyslistaaja;
		private JPanel paneli;
		
		public Listakuuntelija(Ennatyslistaaja ennatyslistaaja, JPanel paneli) {
			this.ennatyslistaaja = ennatyslistaaja;
			this.paneli = paneli;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			ennatyslistaaja.vaihdaNakyvaaListaa(paneli);
		}
	}
	
	private void vaihdaNakyvaaListaa() {
		vaihdaNakyvaaListaa((JPanel)listapaneli.getComponent(0));
	}
	
	/**
	* Vaihtaa listapanelissa nakyvaa listaa kuilukohtaisen ja yleisen listan valilla.
	* 
	* @param listanappuloidenPaneli Paneli, jossa listavalintanappulat ovat
	*/
	public void vaihdaNakyvaaListaa(JPanel listanappuloidenPaneli) {
		yleinenListaNakyvissa = !yleinenListaNakyvissa;
		
		vaihdaListanappuloidenKlikattavuuksia(listanappuloidenPaneli);
		
		this.repaint();
	}
	
	private void vaihdaListanappuloidenKlikattavuuksia(JPanel listanappuloidenPaneli) {
		for (Component komponentti : listanappuloidenPaneli.getComponents()) {
			if (!(komponentti instanceof Nappula)) {
				continue;
			}
			
			Nappula nappula = (Nappula)komponentti;
			nappula.setEnabled( !nappula.isEnabled() );
		}
	}
	
	private void luoValikkopanelinSisalto() {
		this.nimipaneli = new JPanel();
		this.nimipaneli.setOpaque(false);
		this.valikkopaneli.add(nimipaneli);
		
		this.saatopaneli = new Saatopaneli(this);
		this.saatopaneli.setOpaque(false);
		this.valikkopaneli.add(saatopaneli);
	}
	
	/**
	* Kertoo paaseeko tulos ennatyslistalle.
	* 
	* @param pisteet Saadut pisteet
	* @param leveys Pelatun kuilun leveys
	* @param korkeus Pelatun kuilun korkeus
	* @param syvyys Pelatun kuilun syvyys
	* @param palikkasetti Pelatun pelin palikkasetti
	* @return Tieto siita paaseeko tulos listalle
	*/
	public boolean paaseekoListalle(int pisteet, int leveys, int korkeus, int syvyys, Palikkasetti palikkasetti) {
		int pienempiLeveys = Math.min(leveys, korkeus);
		int suurempiLeveys = Math.max(leveys, korkeus);
		return ennatyslistat.paaseekoListalle(pisteet, pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
	}
	
	/**
	* Poistaa ennatyslistaajasta nimikyselyn.
	*/
	public void poistaEnnatyslistanKysely() {
		this.kysytaanNimea = false;
		this.lisattavanPisteet = 0;
		this.lisattavanNimi = "";
		
		aktivoiSaatopanelinSisalto();
	}
	
	private void aktivoiSaatopanelinSisalto() {
		//TODO
	}
	
	private void passivoiSaatopanelinSisalto() {
		//TODO
	}
	
	/**
	* Antaa pelin pistetuloksen ennatyslistanakymalle kasiteltavaksi.
	* 
	* @param pisteet Pelin pisteet
	* @param leveys Pelin kuilun leveys ruutuina
	* @param korkeus Pelin kuilun korkeus ruutuina
	* @param syvyys Pelin kuilun syvyys ruutuina
	* @param palikkasetti Pelin palikkasetti
	*/
	public void annaEnnatyslistalleKasiteltavaksi(int pisteet, int leveys, int korkeus, int syvyys, Palikkasetti palikkasetti) {
		int pienempiLeveys = Math.min(leveys, korkeus);
		int suurempiLeveys = Math.max(leveys, korkeus);
		
		//valmistautuu oikean listan ja nimikyselyn nayttamiseen
		this.kysytaanNimea = true;
		this.lisattavanPisteet = pisteet;
		this.lisattavanNimi = "";
		
		saadaEnnatyslistanParametrit(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
		valitseKumpiEnnatyslista(pisteet);
	}
	
	/**
	* Saataa ennatyslistan parametrit tietylle peliasetukselle, jolloin voidaan nayttaa kyseisen pelin ennatyslista.
	* 
	* @param pienempiLeveys Pelin kuilun pienempi leveys ruutuina
	* @param suurempiLeveys Pelin kuilun suurempi leveys ruutuina
	* @param syvyys Pelin kuilun syvyys ruutuina
	* @param palikkasetti Pelin palikkasetti
	*/
	public void saadaEnnatyslistanParametrit(int pienempiLeveys, int suurempiLeveys, int syvyys, Palikkasetti palikkasetti) {
		this.valittuPienempiLeveys = pienempiLeveys;
		this.valittuSuurempiLeveys = suurempiLeveys;
		this.valittuSyvyys = syvyys;
		this.valittuPalikkasetti = palikkasetti;
	}
	
	private void valitseKumpiEnnatyslista(int pisteet) {
		if ( ennatyslistat.paaseekoListalle(pisteet, -1, -1, -1, Palikkasetti.VOID) ) {
			if (!yleinenListaNakyvissa) {
				vaihdaNakyvaaListaa();
			}
		}
		else {
			if (yleinenListaNakyvissa) {
				vaihdaNakyvaaListaa();
			}
		}
	}
	
	/**
	* Palauttaa tiedon siita onko ennatyslistaaja kysymassa listaennatyksen nimea.
	* 
	* @return Tieto nimen kysymisesta
	*/
	public boolean kysytaankoNimea() {
		return kysytaanNimea;
	}
	
	/**
	* Vahvistaa annetun nimen ja lisaa sen kuilukohtaiselle listalle ja yleiselle listalle jos annettu nimi ei ole tyhja.
	*/
	public void vahvistaNimiJaLisaaListalle() {
		if (lisattavanNimi.length() > 0) {
			ennatyslistat.lisaaListalle( lisattavanPisteet, lisattavanNimi, valittuPienempiLeveys, valittuSuurempiLeveys, valittuSyvyys, valittuPalikkasetti );
			
			lisaaYleiselleListalle();
		}
		
		poistaEnnatyslistanKysely();
		paivita();
	}
	
	private void lisaaYleiselleListalle() {
		if ( ennatyslistat.paaseekoListalle( lisattavanPisteet, -1, -1, -1, Palikkasetti.VOID ) ) {
			
			ennatyslistat.lisaaListalle( lisattavanPisteet, lisattavanNimi, -1, -1, -1, Palikkasetti.VOID );
			
		}
	}
	
	/**
	* Antaa listaennatyksen parametrien mukaiselle pelille.
	* 
	* @param leveys Pelin kuilun leveys
	* @param korkeus Pelin kuilun korkeus
	* @return Listan parhaimman pisteet
	*/
	public int annaListaennatys(int leveys, int korkeus, int syvyys, Palikkasetti setti) {
		String paras = ennatyslistat.annaListanSija(1, Math.min(leveys, korkeus), Math.max(leveys, korkeus), syvyys, setti);
		
		String[] erotellut = paras.trim().split(" ");
		int parhaanPisteet = Integer.parseInt( erotellut[0] );
		return parhaanPisteet;
	}
	
	/**
	* Poistaa kirjoitetusta ennatyksen tekijan nimesta merkin.
	*/
	public void poistaNimestaMerkki() {
		if (lisattavanNimi.length() > 0) {
			lisattavanNimi = lisattavanNimi.substring(0, lisattavanNimi.length()-1);
			paivita();
		}
	}
	
	/**
	* Lisaa kirjoitettavaan ennatyksen tekijan nimeen merkin.
	* 
	* @param merkki Lisattava merkki
	*/
	public void lisaaNimeenMerkki(String merkki) {
		if (lisattavanNimi.length() < 15) {
			lisattavanNimi += merkki;
			paivita();
		}
	}
	
	private void paivita() {
		this.repaint();
	}
	
	/**
	* Piirtaa ennatyslistaaja nakyman.
	* 
	* @param g Graphics
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		piirraOtsikko(g);
		if (yleinenListaNakyvissa) {
			piirraYleisetListasijat(g);
		}
		else {
			piirraKuilukohtaisetListasijat(g);
		}
		
		if (kysytaanNimea) {
			piirraNimikysely(g);
		}
	}
	
	private void piirraOtsikko(Graphics g) {
		Font isoFontti = new Font(haeFontti(), Font.PLAIN, 30);
		g.setFont(isoFontti);
		g.setColor(Color.WHITE);
		g.drawString("Ennatyslista", 35, 80);
	}
	
	private void piirraKuilukohtaisetListasijat(Graphics g) {
		piirraListasijat(g, valittuPienempiLeveys, valittuSuurempiLeveys, valittuSyvyys, valittuPalikkasetti);
	}
	
	private void piirraYleisetListasijat(Graphics g) {
		piirraListasijat(g, -1, -1, -1, Palikkasetti.VOID);
	}
	
	private void piirraListasijat(Graphics g, int pienempiLeveys, int suurempiLeveys, int syvyys, Palikkasetti palikkasetti) {
		Font fontti = new Font(haeFontti(), Font.PLAIN, 18);
		g.setFont(fontti);
		
		for (int i=1; i<=ennatyslistat.annaListanSijojenMaara(); i++) {
			String sijanPitaja = ennatyslistat.annaListanSija(i, pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
			
			g.drawString(i + ":", 40, 100 + 30*i);
			g.drawString(sijanPitaja, 70, 100 + 30*i);
		}
	}
	
	private void piirraNimikysely(Graphics g) {
		Font fontti = new Font("herculanum", Font.PLAIN, 24);
		g.setFont(fontti);
		g.drawString("Nimesi:", this.getWidth()/2, 120);
		
		Font pienempiFontti = new Font("herculanum", Font.PLAIN, 18);
		g.setFont(pienempiFontti);
		g.drawString("_____________________________", this.getWidth()/2, 150);
		g.drawString(lisattavanNimi, this.getWidth()/2+10, 145);
		
		g.drawString("Pisteet: " + lisattavanPisteet, this.getWidth()/2, 180);
	}
	
	private String haeFontti() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = environment.getAllFonts();
 
 		String[] halututFontit = {"Herculanum", "ComicSansMS", "Comic Sans MS", "ArialMT", "Arial"};
 		for (String haluttuFontti : halututFontit) {
 			for (Font font : fonts) {
	 			if (font.getFontName().equals(haluttuFontti)) {
	 				return font.getFontName();
	 			}
	 		}
		}
		
		for (Font font : fonts) {
			if (font.getFontName().toLowerCase().contains("arial")) {
				return font.getFontName();
			}
		}
		
		return "futura";
	}
}
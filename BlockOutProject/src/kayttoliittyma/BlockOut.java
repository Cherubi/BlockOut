package kayttoliittyma;

import peli.Peli;
import peli.asetukset.PelinAsetukset;
import peli.asetukset.grafiikka.Nappainpaletti;
import peli.asetukset.logiikka.Asetukset;
import peli.asetukset.logiikka.Ulottuvuudet;
import peli.ennatyslista.Ennatyslistaaja;
import valmiskomponentit.Ikkuna;

import java.util.HashMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BlockOut implements Runnable {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new BlockOut());
	}
	
	private JFrame kehys;
	private ValittuIkkuna valittuIkkuna;
	private HashMap<ValittuIkkuna, Ikkuna> ikkunat;
	private Etusivu etusivu;
	
	private Peli peli;
	private PelinAsetukset pelinAsetukset;
	//private Varipaletti varipaletti;
	private Ennatyslistaaja ennatyslistaaja;
	
	/**
	* Alustaa kayttoliittyman ja luo graafisen kayttoliittyman
	*/
	public void run() {
		luoIkkunat();
		
		this.kehys = new JFrame();
		this.kehys.addKeyListener(new NappainKuuntelija(this));
		
		luoAloitusIkkuna();
		
		this.kehys.setTitle("BlockOut 1.3.1");
		this.kehys.setSize(800, 600);
		this.kehys.setBackground(Color.BLACK);
		this.kehys.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.kehys.setVisible(true);
	}
	
	/**
	* Luo graafisen kayttoliittyman ikkunan aloitustilaan
	*/
	private void luoAloitusIkkuna() {
		this.etusivu = new Etusivu(this);
		this.kehys.add(this.etusivu, BorderLayout.SOUTH);
		
		this.kehys.add(this.ikkunat.get(ValittuIkkuna.TYHJA), BorderLayout.CENTER);
	}
	
	/**
	* Luo graafisessa kayttoliittymassa myohemmin kaytettavat ikkunat
	*/
	private void luoIkkunat() {
		this.ikkunat = new HashMap<ValittuIkkuna, Ikkuna>();
		
		this.ikkunat.put(ValittuIkkuna.TYHJA, new Ikkuna());
		
		
		pelinAsetukset = new PelinAsetukset(this, "asetukset.javafile");
		this.ikkunat.put(ValittuIkkuna.ASETUKSET, pelinAsetukset);
		
		/*
		varipaletti = new Varipaletti();
		this.ikkunat.put(ValittuIkkuna.VARIT, varipaletti);
		*/
		ennatyslistaaja = new Ennatyslistaaja();
		this.ikkunat.put(ValittuIkkuna.ENNATYSLISTA, ennatyslistaaja);
		
		this.valittuIkkuna = ValittuIkkuna.TYHJA;
	}
	
	/**
	* Antaa kayttoliittymassa parhaillaan nakyvan ikkunan
	* 
	* @return Kayttoliittymassa nakyva ikkuna
	*/
	public Ikkuna annaJPanel() {
		return this.ikkunat.get(valittuIkkuna);
	}
	
	/**
	* Vaihtaa kayttoliittymaan uuden ikkunan nakyviin vanhan tilalle
	* 
	* @param nytValittuIkkuna Uuden valittavan ikkunan tunnus
	*/
	public void vaihdaJPanel(ValittuIkkuna nytValittuIkkuna) {
		asetaPeliTarvittaessaTauolle(nytValittuIkkuna);
		if (this.valittuIkkuna == ValittuIkkuna.ASETUKSET) {
			pelinAsetukset.vaihdaAsetuksetPanelia();
		}
		
		this.valittuIkkuna = nytValittuIkkuna;
		this.kehys.getContentPane().remove(1);
		this.kehys.getContentPane().add( this.ikkunat.get(nytValittuIkkuna) );
		if (nytValittuIkkuna == ValittuIkkuna.ENNATYSLISTA) {
			asetaUusimmatAsetuksetEnnatyslistaan();
		}
		
		this.etusivu.vaihdaNappuloidenAktiivisuuksiaTasmaaviksi( nytValittuIkkuna );
		
		this.ikkunat.get(nytValittuIkkuna).revalidate();
		this.kehys.repaint();
	}
	
	private void asetaUusimmatAsetuksetEnnatyslistaan() {
		Asetukset asetukset = this.pelinAsetukset.annaValitutAsetukset();
		Ulottuvuudet ulottuvuudet = asetukset.annaUlottuvuudet();
		
		this.ennatyslistaaja.saadaEnnatyslistanParametrit( ulottuvuudet.annaLeveys(), ulottuvuudet.annaKorkeus(), ulottuvuudet.annaSyvyys(), asetukset.annaPalikkasetti() );
	}
	
	/**
	* Aloittaa pelin ja lopettaa ensin edellisen pelin jos se on viela kaynnissa. Vaihtaa ikkunaksi peli-ikkunan
	*/
	public void aloitaPeli() {
		if (onkoPeliKaynnissa()) {
			this.peli.lopetaPeli(); // jos tulisi tulos ennatyslistalle niin ehtiiko kayttaja edes nahda sita? jaako kysymaan nimea?
			this.etusivu.vaihdaTaukoNappulanTeksti("Tauko");
		}
		
		int leveys = this.ikkunat.get(valittuIkkuna).getWidth();
		int korkeus = this.ikkunat.get(valittuIkkuna).getHeight();
		
		this.ennatyslistaaja.poistaEnnatyslistanKysely();
		this.peli = new Peli(this, pelinAsetukset.annaValitutAsetukset(), ennatyslistaaja, leveys, korkeus);
		this.peli.aloitaPeli();
		this.ikkunat.put(ValittuIkkuna.PELI, this.peli);
		
		vaihdaJPanel(ValittuIkkuna.PELI);
	}
	
	/**
	* Asettaa pelin tarvittaessa tauolle. Nain voi kayda jos peli on kaynnissa ja pelaaja avaa jonkun muun ikkunan peli-ikkunan tilalle
	*
	* @param nytValittuIkkuna Ikkuna, jota ollaan vaihtamassa nykyisen ikkunan tilalle
	*/
	public void asetaPeliTarvittaessaTauolle(ValittuIkkuna nytValittuIkkuna) {
		if (!onkoPeliKaynnissa()) {
			return;
		}
		
		if (this.valittuIkkuna == ValittuIkkuna.PELI && nytValittuIkkuna != ValittuIkkuna.PELI) {
			asetaPeliTauolle(true);
		}
	}
	
	/**
	* Asettaa kaynnissa olevan pelin tauolle tai pois tauolta. Vaihtaa myos Tauko-nappulan tekstin tasmaavaksi.
	* 
	* @param tauolla Tieto siita halutaanko peli tauolle vai pois tauolta
	*/
	public void asetaPeliTauolle(boolean tauolla) {
		if (!this.ikkunat.containsKey(ValittuIkkuna.PELI)) {
			return;
		}
		
		this.peli.asetaPeliTauolle(tauolla);
		
		if (tauolla) {
			this.etusivu.vaihdaTaukoNappulanTeksti("Jatka");
		}
		else {
			this.etusivu.vaihdaTaukoNappulanTeksti("Tauko");
			kaynnistaPeliTauolta();
		}
	}
	
	/**
	* Kaynnistaa pelin tauolta ja asettaa siihen nykyiset graafiset asetukset
	*/
	private void kaynnistaPeliTauolta() {
		this.peli.asetaUudetAsetukset( this.pelinAsetukset.annaValitutAsetukset() );
		
		vaihdaJPanel(ValittuIkkuna.PELI);
	}
	
	/**
	* Lopettaa kaynnissa olevan pelin. Avaa ennatyslistan jos pelin tulos on paasemassa sille. Avaa tyhjan ikkunan jos nakymassa oli peli. Muuten ei vaihda ikkunaa.
	*/
	public void lopetaPeli() {
		this.etusivu.vaihdaTaukoNappulanTeksti("Tauko");
		
		boolean tulosPaaseeListalle = this.peli.lopetaPeli();
		this.peli = null;
		this.ikkunat.remove(ValittuIkkuna.PELI);
		if (tulosPaaseeListalle) {
			vaihdaJPanel(ValittuIkkuna.ENNATYSLISTA);
		}
		else if (this.valittuIkkuna == ValittuIkkuna.PELI) {
			vaihdaJPanel(ValittuIkkuna.TYHJA);
		}
		else {
			this.etusivu.vaihdaNappuloidenAktiivisuuksiaTasmaaviksi( valittuIkkuna );
		}
	}
	
	/**
	* Antaa tiedon siita onko peli kaynnissa.
	* 
	* @return Tieto siita onko peli kaynnissa vai ei
	*/
	public boolean onkoPeliKaynnissa() {
		return this.ikkunat.containsKey(ValittuIkkuna.PELI);
	}
}

package kayttoliittyma;

import valmiskomponentit.Nappula;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class Etusivu extends JPanel {
	private BlockOut kayttis;
	private Nappula tauko, uusiPeli, lopeta, asetukset, ennatyslista;
	
	/**
	* Hallinnoi kayttoliittyman alalaidassa olevia nappuloita, joilla pelin voi aloittaa, lopettaa ja laittaa tauolle tai ennatyslistaa tai asetuksia voi katsella.
	* 
	* @param kayttis Kayttoliittyma johon nappulat ovat kiinnittyneena
	*/
	public Etusivu(BlockOut kayttis) {
		super();
		
		this.kayttis = kayttis;
		
		this.setLayout(new GridLayout(1,3));
		luoNappulat();
	}
	
	/**
	* Luo nappulat, joilla kayttoliittymaa voidaan navigoida
	*/
	private void luoNappulat() {
		JPanel peliPaneli = new JPanel(new GridLayout(3,1));
		
		this.tauko = new Nappula("Tauko");
		this.tauko.addActionListener( new TaukoNappulaKuuntelija(this.kayttis, this.tauko) );
		this.tauko.setEnabled(false);
		this.tauko.setFocusable(false);
		peliPaneli.add(this.tauko);
		
		this.uusiPeli = new Nappula("Uusi peli");
		this.uusiPeli.addActionListener( new UusiPeliNappulaKuuntelija(this.kayttis));
		this.uusiPeli.setEnabled(true);
		this.uusiPeli.setFocusable(false);
		peliPaneli.add(this.uusiPeli);
		
		this.lopeta = new Nappula("Lopeta");
		this.lopeta.addActionListener( new LopetaNappulaKuuntelija(this.kayttis) );
		this.lopeta.setEnabled(false);
		this.lopeta.setFocusable(false);
		peliPaneli.add(this.lopeta);
		
		this.add(peliPaneli);
		
		this.asetukset = new Nappula("Asetukset");
		this.asetukset.addActionListener( new AsetuksetNappulaKuuntelija(this.kayttis) );
		this.asetukset.setEnabled(true);
		this.asetukset.setFocusable(false);
		this.add(this.asetukset);
		
		this.ennatyslista = new Nappula("Ennatyslista");
		this.ennatyslista.addActionListener( new EnnatyslistaNappulaKuuntelija(this.kayttis) );
		this.ennatyslista.setEnabled(true);
		this.ennatyslista.setFocusable(false);
		this.add(ennatyslista);
	}
	
	/**
	* Vaihtaa Tauko-nappulan tekstia. (Vaihtoehtoja Tauko ja Jatka)
	* 
	* @param uusiTeksti Tauko-nappulan uusi teksti
	*/
	public void vaihdaTaukoNappulanTeksti(String uusiTeksti) {
		this.tauko.setText(uusiTeksti);
	}
	
	/**
	* Vaihtaa nappuloiden aktiivisuuksia sen mukaisiksi mika nakyma kayttajalla on aktiivisena ja missa tilassa peli on
	*
	* @param valittuIkkuna Valitun ikkunan tunnus
	*/
	public void vaihdaNappuloidenAktiivisuuksiaTasmaaviksi( ValittuIkkuna valittuIkkuna ) {
		if (valittuIkkuna == ValittuIkkuna.PELI) {
			this.tauko.setEnabled(true);
			this.lopeta.setEnabled(true);
		}
		else if (!kayttis.onkoPeliKaynnissa()) {
			this.tauko.setEnabled(false);
			this.lopeta.setEnabled(false);
		}
		
		if (valittuIkkuna == ValittuIkkuna.ASETUKSET) {
			this.asetukset.setEnabled(false);
		} else {
			this.asetukset.setEnabled(true);
		}
		
		if (valittuIkkuna == ValittuIkkuna.ENNATYSLISTA) {
			this.ennatyslista.setEnabled(false);
		} else {
			this.ennatyslista.setEnabled(true);
		}
	}
	
	// kuuntelijat
	
	private static class TaukoNappulaKuuntelija implements ActionListener {
		private BlockOut kayttis;
		private Nappula nappula;
		
		/**
		* Tauko-nappulan kuuntelija
		* 
		* @param kayttis Kayttoliittyma, johon nappula on kiinnitetty ja jota nappulalla kaytetaan
		* @param nappula Nappula, jossa kuuntelija on kiinnittyneena
		*/
		public TaukoNappulaKuuntelija(BlockOut kayttis, Nappula nappula) {
			this.kayttis = kayttis;
			this.nappula = nappula;
		}
		
		/**
		* Selvittaa kuuntelijan saamien tapahtumien perusteella laitetaanko tauko paalle vai pois.
		* 
		* @param ae Klikkaustapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (nappula.getText().equals("Tauko")) {
				this.kayttis.asetaPeliTauolle(true);
			}
			else {
				this.kayttis.asetaPeliTauolle(false);
			}
		}
	}
	
	private static class UusiPeliNappulaKuuntelija implements ActionListener {
		private BlockOut kayttis;
		
		/**
		* Uusi peli-nappulan kuuntelija.
		* 
		* @param kayttis Kayttoliittyma, jossa nappula on ja jota halutaan nappulalla kayttaa
		*/
		public UusiPeliNappulaKuuntelija(BlockOut kayttis) {
			this.kayttis = kayttis;
		}
		
		/**
		* Tapahtuman perusteella aloittaa pelin.
		* 
		* @param ae Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			this.kayttis.aloitaPeli();
		}
	}
	
	private static class LopetaNappulaKuuntelija implements ActionListener {
		private BlockOut kayttis;
		
		/**
		* Lopeta-nappulan kuuntelija
		* 
		* @param kayttis Kayttoliittyma, johon nappula on kiinnitetty
		*/
		public LopetaNappulaKuuntelija(BlockOut kayttis) {
			this.kayttis = kayttis;
		}
		
		/**
		* Tapahtuman perusteella lopettaa pelin.
		* 
		* @param Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			this.kayttis.lopetaPeli();
		}
	}
	
	private static class AsetuksetNappulaKuuntelija implements ActionListener {
		private BlockOut kayttis;
		
		/**
		* Asetukset-nappulan kuuntelija.
		* 
		* @param kayttis Kayttoliittyma, johon nappulan on kiinnitetty
		*/
		public AsetuksetNappulaKuuntelija(BlockOut kayttis) {
			this.kayttis = kayttis;
		}
		
		/**
		* Tapahtuman perusteella vaihtaa kayttoliittyman nakyman ikkunan asetuksiksi
		* 
		* @param ae Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			this.kayttis.vaihdaJPanel(ValittuIkkuna.ASETUKSET);
		}
	}
	
	private static class EnnatyslistaNappulaKuuntelija implements ActionListener {
		private BlockOut kayttis;
		
		/**
		* Ennatyslista-nappulan kuuntelija.
		* 
		* @param kayttis Kayttoliittyma, johon nappula on kiinnitetty
		*/
		public EnnatyslistaNappulaKuuntelija(BlockOut kayttis) {
			this.kayttis = kayttis;
		}
		
		/**
		* Tapahtuman perusteella vaihtaa kayttoliittyman nakymassa ikkunaksi ennatyslista-ikkunan.
		* 
		* @param ae Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			this.kayttis.vaihdaJPanel(ValittuIkkuna.ENNATYSLISTA);
		}
	}
}
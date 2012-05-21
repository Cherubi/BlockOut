package kayttoliittyma;

import peli.Peli;
import peli.asetukset.PelinAsetukset;
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
	//private Nappainpaletti nappainpaletti;
	//private Varipaletti varipaletti;
	private Ennatyslistaaja ennatyslistaaja;
	
	public void run() {
		luoIkkunat();
		
		this.kehys = new JFrame();
		this.kehys.addKeyListener(new NappainKuuntelija(this));
		
		luoAloitusIkkuna();
		
		this.kehys.setTitle("BlockOut");
		this.kehys.setSize(800, 600);
		this.kehys.setBackground(Color.BLACK);
		this.kehys.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.kehys.setVisible(true);
	}
	
	private void luoAloitusIkkuna() {
		this.etusivu = new Etusivu(this);
		this.kehys.add(this.etusivu, BorderLayout.SOUTH);
		
		this.kehys.add(this.ikkunat.get(ValittuIkkuna.TYHJA), BorderLayout.CENTER);
	}
	
	private void luoIkkunat() {
		this.ikkunat = new HashMap<ValittuIkkuna, Ikkuna>();
		
		this.ikkunat.put(ValittuIkkuna.TYHJA, new Ikkuna());
		
		
		pelinAsetukset = new PelinAsetukset();
		this.ikkunat.put(ValittuIkkuna.ASETUKSET, pelinAsetukset);
		/*
		nappainpaletti = new Nappainpaletti();
		this.ikkunat.put(ValittuIkkuna.NAPPULAT, nappainpaletti);
		
		varipaletti = new Varipaletti();
		this.ikkunat.put(ValittuIkkuna.VARIT, varipaletti);
		*/
		ennatyslistaaja = new Ennatyslistaaja();
		this.ikkunat.put(ValittuIkkuna.ENNATYSLISTA, ennatyslistaaja);
		
		this.valittuIkkuna = ValittuIkkuna.TYHJA;
	}
	
	public Ikkuna annaJPanel() {
		return this.ikkunat.get(valittuIkkuna);
	}
	
	public void vaihdaJPanel(ValittuIkkuna nytValittuIkkuna) {
		asetaPeliTarvittaessaTauolle(nytValittuIkkuna);
		
		this.valittuIkkuna = nytValittuIkkuna;
		this.kehys.getContentPane().remove(1);
		this.kehys.getContentPane().add( this.ikkunat.get(nytValittuIkkuna) );
		
		this.etusivu.vaihdaNappuloidenAktiivisuuksiaTasmaaviksi( nytValittuIkkuna );
		
		this.ikkunat.get(nytValittuIkkuna).revalidate();
		this.kehys.repaint();
	}
	
	public void aloitaPeli() {
		if (onkoPeliKaynnissa()) {
			this.peli.lopetaPeli(); // jos tulisi tulos ennatyslistalle niin ehtiiko kayttaja edes nahda sita? jaako kysymaan nimea?
			this.etusivu.vaihdaTaukoNappulanTeksti("Tauko");
		}
		
		this.peli = new Peli();
		this.ikkunat.put(ValittuIkkuna.PELI, this.peli);
		
		vaihdaJPanel(ValittuIkkuna.PELI);
	}
	
	public void asetaPeliTarvittaessaTauolle(ValittuIkkuna nytValittuIkkuna) {
		if (!onkoPeliKaynnissa()) {
			return;
		}
		
		if (this.valittuIkkuna == ValittuIkkuna.PELI && nytValittuIkkuna != ValittuIkkuna.PELI) {
			asetaPeliTauolle(true);
		}
	}
	
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
	
	private void kaynnistaPeliTauolta() {
		this.peli.asetaUudetAsetukset(); //TODO
		
		vaihdaJPanel(ValittuIkkuna.PELI);
	}
	
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
	
	public boolean onkoPeliKaynnissa() {
		return this.ikkunat.containsKey(ValittuIkkuna.PELI);
	}
}

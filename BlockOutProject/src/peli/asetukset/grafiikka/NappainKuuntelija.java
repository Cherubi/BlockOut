package peli.asetukset.grafiikka;

import peli.asetukset.grafiikka.AsetuksetPaneli;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private KeyListener paneli;
	
	/**
	* Nappainkuuntelija, joka valittaa kuulemansa tapahtumat asetuspanelille
	* 
	* @param asetuksetPaneli Paneli, jolle kuuntelija kuuntelee nappaimistoa
	*/
	public NappainKuuntelija(KeyListener paneli) {
		this.paneli = paneli;
	}
	
	/**
	* Asettaa uuden asetuspanelin, jolle tapahtumat valitetaan.
	* 
	* @param asetuksetPaneli Uusi asetuspaneli
	*/
	public void asetaUusiKuuntelija(KeyListener paneli) {
		this.paneli = paneli;
	}
	
	/**
	* Ei tee mitaan. Ei vastaanottaisi viesteja nuolinappainten painalluksista vaikka tekisikin.
	* 
	* @param ke Nappaintapahtuma nappaimen painalluksesta
	*/
	public void keyTyped(KeyEvent ke) {}
	
	/**
	* Valittaa nappaintapahtuman asetuksetpanelille.
	* 
	* @param ke Nappaintapahtuma nappaimen pohjaan painamisesta
	*/
	public void keyPressed(KeyEvent ke) {
		paneli.keyPressed(ke);
	}
	
	/**
	* Ei tee mitaan.
	* 
	* @param ke Nappaintapahtuma nappaimen vapauttamisesta
	*/
	public void keyReleased(KeyEvent ke) {}
}
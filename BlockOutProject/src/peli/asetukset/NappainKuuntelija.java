package peli.asetukset;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private AsetuksetPaneli asetuksetPaneli;
	
	/**
	* Nappainkuuntelija, joka valittaa kuulemansa tapahtumat asetuspanelille
	* 
	* @param asetuksetPaneli Paneli, jolle kuuntelija kuuntelee nappaimistoa
	*/
	public NappainKuuntelija(AsetuksetPaneli asetuksetPaneli) {
		this.asetuksetPaneli = asetuksetPaneli;
	}
	
	/**
	* Asettaa uuden asetuspanelin, jolle tapahtumat valitetaan.
	* 
	* @param asetuksetPaneli Uusi asetuspaneli
	*/
	public void asetaUusiAsetuksetPaneli(AsetuksetPaneli asetuksetPaneli) {
		this.asetuksetPaneli = asetuksetPaneli;
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
		asetuksetPaneli.keyPressed(ke);
	}
	
	/**
	* Ei tee mitaan.
	* 
	* @param ke Nappaintapahtuma nappaimen vapauttamisesta
	*/
	public void keyReleased(KeyEvent ke) {}
}
package kayttoliittyma;

import valmiskomponentit.Ikkuna;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private BlockOut kayttis;
	
	/**
	* Nappainkuuntelija, joka valittaa saamansa tapahtumat kayttoliittyman aktiivisen ikkunan nappainkuuntelijalle.
	* 
	* @param kayttis Kayttoliittyma, jota kuuntelija kuuntelee
	*/
	public NappainKuuntelija(BlockOut kayttis) {
		this.kayttis = kayttis;
	}
	
	/**
	* Ei tee mitaan.
	* 
	* @param ke Nappaintapahtuma nappaimen painalluksesta
	*/
	//keyTyped ei lue esim. nuolinappaimia
	public void keyTyped(KeyEvent ke) {}
	
	/**
	* Antaa nappaintapahtuman edelleen kayttoliittyman aktiivisen ikkunan nappainkuuntelijalle.
	* 
	* @param ke Nappaintapahtuma nappaimen painamisesta
	*/
	public void keyPressed(KeyEvent ke) {
		Ikkuna ikkuna = this.kayttis.annaJPanel();
		KeyListener[] kuuntelijalista = ikkuna.getKeyListeners();
		
		if (kuuntelijalista.length > 0) {
			kuuntelijalista[0].keyPressed(ke);
		}
	}
	
	/**
	* Ei tee mitaan.
	* 
	* @param ke Nappaintapahtuma nappaimen vapauttamisesta
	*/
	public void keyReleased(KeyEvent ke) {}
}
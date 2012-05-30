package peli;

import kayttoliittyma.BlockOut;
import peli.asetukset.logiikka.Nappainsetti;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private Peli peli;
	private BlockOut kayttis;
	private Nappainsetti nappainsetti;
	
	/**
	* Kuuntelee nappaintapahtumia pelissa ja valittaa niita pelin kautta tippuville palikoille.
	* 
	* @param peli Peli, jolle kuunnellaan nappaintapahtumia
	* @param kayttis Kayttoliittyma, jolle voidaan kertoa pelin paattymisesta kun kayttaja on hyvaksynyt havion
	* @param nappainsetti Valitut nappaimet
	*/
	public NappainKuuntelija(Peli peli, BlockOut kayttis, Nappainsetti nappainsetti) {
		this.peli = peli;
		this.kayttis = kayttis;
		this.nappainsetti = nappainsetti;
	}
	
	/**
	* Ei tee mitaan. Eika lukisi muutenkaan nuolinappaimia.
	* 
	* @param ke Nappaintapahtuma siita kun nappain on painastu
	*/
	@Override
	public void keyTyped(KeyEvent ke) {}
	
	/**
	* Valittaa kayttajan tekemat valinnat pelille.
	* 
	* @param ke Nappaintapahtuma siita kun nappain on painettu pohjaan
	*/
	public void keyPressed(KeyEvent ke) {
		if (peli.onkoGameOver()) {
			if (peli.onkoGameOveristaSekunti()) {
				kayttis.lopetaPeli();
			}
			return;
		}
		
		if (peli.onkoTauolla()) {
			return;
		}
		
		kasittelePelisiirto(ke.getKeyCode());
	}
	
	private void kasittelePelisiirto(int nappainkoodi) {
		if (onkoSiirto(nappainkoodi)) {
			return;
		}
		
		if (onkoPyoritys(nappainkoodi)) {
			return;
		}
		
		if (onkoTiputus(nappainkoodi)) {
			return;
		}
	}
	
	private boolean onkoSiirto(int koodi) {
		if (koodi == nappainsetti.annaYlosNappain()) {
			peli.annaTippuvaPalikka().siirra(0, -1);
		}
		else if (koodi == nappainsetti.annaAlasNappain()) {
			peli.annaTippuvaPalikka().siirra(0, 1);
		}
		else if (koodi == nappainsetti.annaVasemmalleNappain()) {
			peli.annaTippuvaPalikka().siirra(-1, 0);
		}
		else if (koodi == nappainsetti.annaOikealleNappain()) {
			peli.annaTippuvaPalikka().siirra(1, 0);
		}
		
		else {
			return false;
		}
		
		return true;
	}
	
	private boolean onkoPyoritys(int koodi) {
		if (koodi == nappainsetti.annaYlapuoliEsilleNappain()) {
			peli.annaTippuvaPalikka().pyoritaSuuntaEsille(0, -1);
		}
		else if (koodi == nappainsetti.annaAlapuoliEsilleNappain()) {
			peli.annaTippuvaPalikka().pyoritaSuuntaEsille(0, 1);
		}
		else if (koodi==nappainsetti.annaVasenPuoliEsilleNappain()) {
			peli.annaTippuvaPalikka().pyoritaSuuntaEsille(-1, 0);
		}
		else if (koodi==nappainsetti.annaOikeaPuoliEsilleNappain()) {
			peli.annaTippuvaPalikka().pyoritaSuuntaEsille(1, 0);
		}
		
		else if (koodi == nappainsetti.annaKierraMyotapaivaanNappain()) {
			peli.annaTippuvaPalikka().pyoritaMyotapaivaan(true);
		}
		else if (koodi == nappainsetti.annaKierraVastapaivaanNappain()) {
			peli.annaTippuvaPalikka().pyoritaMyotapaivaan(false);
		}
		
		else {
			return false;
		}
		
		return true;
	}
	
	private boolean onkoTiputus(int koodi) {
		if (koodi == nappainsetti.annaTiputaNappain()) {
			peli.annaTippuvaPalikka().tiputaPohjalle();
			return true;
		}
		return false;
	}
	
	/**
	* Ei tee mitaan. Eika lukisi muutenkaan nuolinappaimia.
	* 
	* @param ke Nappaintapahtuma siita kun nappain on painastu
	*/
	public void keyReleased(KeyEvent ke) {}
}
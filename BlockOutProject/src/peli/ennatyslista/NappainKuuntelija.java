package peli.ennatyslista;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private Ennatyslistaaja ennatyslistaaja;
	
	/**
	* Kuuntelee ennatyslistalle saapuvia nappaintapahtumia.
	* 
	* @param ennatyslistaaja Ennatyslistaaja, jolle nappaintapahtumia kuunnellaan
	*/
	public NappainKuuntelija(Ennatyslistaaja ennatyslistaaja) {
		this.ennatyslistaaja = ennatyslistaaja;
	}
	
	/**
	* Ei tee mitaan.
	*/
	@Override
	public void keyTyped(KeyEvent ke) {}
	
	/**
	* Valittaa nappaintapahtumia ennatyslistaajan nimen kysyjalle.
	* 
	* @param ke Nappaintapahtuma
	*/
	@Override
	public void keyPressed(KeyEvent ke) {
		if (!ennatyslistaaja.kysytaankoNimea()) {
			return;
		}
		
		String merkki = ke.getKeyChar() + "";
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			ennatyslistaaja.vahvistaNimiJaLisaaListalle();
		}
		else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			ennatyslistaaja.poistaNimestaMerkki();
		}
		else if (merkki.matches("\\p{ASCII}|[ŒŠš€…§Ÿ†]")) {
			ennatyslistaaja.lisaaNimeenMerkki(merkki);
		}
	}
	
	/**
	* Ei tee mitaan.
	*/
	@Override
	public void keyReleased(KeyEvent ke) {}
}
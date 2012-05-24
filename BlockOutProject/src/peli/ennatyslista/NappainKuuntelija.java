package peli.ennatyslista;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private Ennatyslistaaja ennatyslistaaja;
	
	public NappainKuuntelija(Ennatyslistaaja ennatyslistaaja) {
		this.ennatyslistaaja = ennatyslistaaja;
	}
	
	@Override
	public void keyTyped(KeyEvent ke) {}
	
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
		else if (merkki.matches("\\p{ASCII}|[ŒŠš€…§]")) {
			ennatyslistaaja.lisaaNimeenMerkki(merkki);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent ke) {}
}
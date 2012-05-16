package kayttoliittyma;

import valmiskomponentit.Ikkuna;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NappainKuuntelija implements KeyListener {
	private BlockOut kayttis;
	
	public NappainKuuntelija(BlockOut kayttis) {
		this.kayttis = kayttis;
	}
	
	//keyTyped ei lue esim. nuolinappaimia
	public void keyTyped(KeyEvent ke) {}
	
	//nappaimen pohjassa painaminen ei luo useita tapahtumia
	public void keyPressed(KeyEvent ke) {
		Ikkuna ikkuna = this.kayttis.annaJPanel();
		KeyListener[] kuuntelijalista = ikkuna.getKeyListeners();
		
		if (kuuntelijalista.length > 0) {
			kuuntelijalista[0].keyPressed(ke);
		}
	}
	
	public void keyReleased(KeyEvent ke) {}
}
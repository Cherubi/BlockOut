package peli;

import peli.logiikka.TippuvaPalikka;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Ajastin extends Timer implements ActionListener {
	private Peli peli;
	private TippuvaPalikka tippuvaPalikka;
	private long ajastushetki;
	
	public Ajastin(Peli peli, TippuvaPalikka tippuvaPalikka, int aika, long ajastushetki) {
		super(aika, null);
		super.addActionListener(this);
		this.peli = peli;
		this.tippuvaPalikka = tippuvaPalikka;
		
		this.ajastushetki = ajastushetki;
		
		this.setDelay(aika);
		this.setRepeats(false);
		this.start();
		//System.out.println("ajastin aloitettu " + System.currentTimeMillis());
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (peli.annaTippuvaPalikka() != tippuvaPalikka) {
			return;
		}
		
		if (!peli.onkoTauolla()) {
			peli.tiputaPalikkaa(ajastushetki);
		}
	}
}
package peli.logiikka;

import peli.Peli;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class PyoritysAjastin extends Timer implements ActionListener {
	private Peli peli;
	private TippuvaPalikka tippuvaPalikka;
	
	/**
	* Ajoittaa palikoiden pyorimisen.
	* 
	* @param peli Peli, joka hallinnoi tippuvaa palikkaa ja grafiikkaa
	* @param tippuvaPalikka Palikka, jota pyoritetaan
	* @param aika Aika minka paasta pyoritetaan
	*/
	public PyoritysAjastin(Peli peli, TippuvaPalikka tippuvaPalikka, int aika) {
		super(aika, null);
		super.addActionListener(this);
		this.peli = peli;
		this.tippuvaPalikka = tippuvaPalikka;
		
		this.setDelay(aika);
		this.setRepeats(false);
		this.start();
	}
	
	/**
	* Vastaanottaa ajastimen tapahtuman. Pyorayttaa tippuvaa palikkaa oikeampaa suuntaan jos tippuva palikka on yha sama eika se ole tippunut pohjalle. Nollaa kuvan viiveen jos peli on tauolla.
	* 
	* @param ae Ajastimen tapahtuma
	*/
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (peli.annaTippuvaPalikka() != tippuvaPalikka) {
			return;
		}
		
		if (!peli.onkoTauolla()) {
			tippuvaPalikka.oikaisePyoraytysta();
		}
		else {
			tippuvaPalikka.nollaaPyoritykset();
		}
	}
}
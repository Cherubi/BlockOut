package peli.asetukset.grafiikka;

import peli.asetukset.logiikka.Asetukset;
import peli.logiikka.Palikkasetti;
import valmiskomponentit.Nappula;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PalikkasettiPaneli extends JPanel {
	private Asetukset asetukset;
	private Palikkasetti setti;
	private String fontinNimi;
	
	/**
	* Luo JPanelin, joka sisaltaa nappulat palikkasettien vaihteluun.
	*/
	public PalikkasettiPaneli(Asetukset asetukset, String fontinNimi) {
		this.asetukset = asetukset;
		this.setti = asetukset.annaPalikkasetti();
		this.fontinNimi = fontinNimi;
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		luoOtsikko();
		luoNappulat();
	}
	
	private void luoOtsikko() {
		JLabel otsikko = new JLabel("Palikkasetti");
		otsikko.setFont(new Font(fontinNimi, Font.PLAIN, 18));
		otsikko.setForeground(Color.WHITE);
		this.add(otsikko);
	}
	
	private void luoNappulat() {
		luoPalikkasettiNappula("FLAT");
		luoPalikkasettiNappula("BASIC");
		luoPalikkasettiNappula("EXTENDED");
	}
	
	private void luoPalikkasettiNappula(String teksti) {
		Nappula nappula = new Nappula(teksti);
		nappula.setFocusable(false);
		nappula.setFont(new Font(fontinNimi, Font.PLAIN, 16));
		
		Palikkasetti nappulanSetti = haeNimellaSetti(teksti);
		if (this.setti == nappulanSetti) {
			nappula.setEnabled(false);
		}
		else {
			nappula.setEnabled(true);
		}
		
		nappula.addActionListener( new PalikkasettiKuuntelija( nappulanSetti, this ) );
		
		this.add(nappula);
	}
	
	private Palikkasetti haeNimellaSetti(String teksti) {
		if (teksti.equals("BASIC")) {
			return Palikkasetti.BASIC;
		}
		else if (teksti.equals("EXTENDED")) {
			return Palikkasetti.EXTENDED;
		}
		
		return Palikkasetti.FLAT;
	}
	
	private static class PalikkasettiKuuntelija implements ActionListener {
		private Palikkasetti setti;
		private PalikkasettiPaneli kohde;
		
		/**
		* Kuuntelee palikkasettinappulaan liittyvia tapahtumia.
		* 
		* @param setti Nappulaan liittyva palikkasetti
		* @param kohde Kohde, jolle tieto palikkasetin valinnasta tulee antaa
		*/
		public PalikkasettiKuuntelija(Palikkasetti setti, PalikkasettiPaneli kohde) {
			this.setti = setti;
			this.kohde = kohde;
		}
		
		/**
		* Vastaanottaa palikkasettinappulaan liittyvia tapahtumia.
		* 
		* @param ae Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			kohde.asetaPalikkasetti(setti);
		}
	}
	
	/**
	* Asettaa uuden palikkasetin asetuksiin ja paivittaa nakymassa Palikkasettinappuloiden aktiivisuudet.
	* 
	* @param setti Uusi palikkasetti
	*/
	public void asetaPalikkasetti(Palikkasetti setti) {
		asetukset.asetaPalikkasetti(setti);
		
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			Nappula nappula = (Nappula)komponentti;
			
			if (nappula.getText().equals( setti.annaNimi().toUpperCase() )) {
				nappula.setEnabled(false);
			}
			else {
				nappula.setEnabled(true);
			}
		}
		
		this.repaint();
	}
}
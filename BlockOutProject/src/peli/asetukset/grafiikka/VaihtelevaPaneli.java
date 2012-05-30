package peli.asetukset.grafiikka;

import peli.asetukset.logiikka.Asetukset;
import valmiskomponentit.Nappula;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VaihtelevaPaneli extends JPanel {
	private AsetuksetPaneli asetuksetPaneli;
	private Asetukset asetukset;
	private String aihe;
	private String fontinNimi;
	
	/**
	* Luo vaihtelevalla aiheella valitun panelin.
	* 
	* @param kohde Kohde, joka vaihtaa panelin aihetta
	* @param asetukset Pelin valitut asetukset
	* @param aihe Panelin valittu aihe
	* @param fontinNimi Fontin nimi
	*/
	public VaihtelevaPaneli(AsetuksetPaneli asetuksetPaneli, Asetukset asetukset, String aihe, String fontinNimi) {
		this.asetuksetPaneli = asetuksetPaneli;
		this.asetukset = asetukset;
		this.aihe = aihe;
		this.fontinNimi = fontinNimi;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		
		luoOtsikko();
		luoNappulat();
	}
	
	private void luoOtsikko() {
		JLabel otsikko = new JLabel(aihe);
		otsikko.setFont(new Font(fontinNimi, Font.PLAIN, 18));
		otsikko.setForeground(Color.WHITE);
		this.add(otsikko);
	}
	
	private void luoNappulat() {
		int alaraja = annaAiheenAlaraja();
		int ylaraja = annaAiheenYlaraja();
		
		for (int i = alaraja; i <= ylaraja; i++) {
			if (aihe.equals("Taso")) {
				luoTasoNappula(i);
			}
			else {
				luoUlottuvuusNappula(aihe, i);
			}
		}
	}
	
	private int annaAiheenAlaraja() {
		if (aihe.equals("Leveys")) {
			return asetukset.annaUlottuvuudet().annaMinimiLeveys();
		}
		else if (aihe.equals("Korkeus")) {
			return asetukset.annaUlottuvuudet().annaMinimiKorkeus();
		}
		else if (aihe.equals("Syvyys")) {
			return asetukset.annaUlottuvuudet().annaMinimiSyvyys();
		}
		//leikkauspiste
		else {
			return asetukset.annaMinimitaso();
		}
	}
	
	private int annaAiheenYlaraja() {
		if (aihe.equals("Leveys")) {
			return asetukset.annaUlottuvuudet().annaMaksimiLeveys();
		}
		else if (aihe.equals("Korkeus")) {
			return asetukset.annaUlottuvuudet().annaMaksimiKorkeus();
		}
		else if (aihe.equals("Syvyys")) {
			return asetukset.annaUlottuvuudet().annaMaksimiSyvyys();
		}
		//leikkauspiste
		else {
			return asetukset.annaMaksimitaso();
		}
	}
	
	//*******************************************
	//
	// Tasonappula
	//
	//*******************************************
	
	private void luoTasoNappula(int arvo) {
		Nappula nappula = new Nappula(arvo+"");
		nappula.setFont(new Font(fontinNimi, Font.PLAIN, 16));
		nappula.setFocusable(false);
		
		if (asetukset.annaAloitustaso() == arvo) {
			nappula.setEnabled(false);
		}
		else {
			nappula.setEnabled(true);
		}
		
		nappula.addActionListener( new TasoKuuntelija(this, arvo) );
		
		this.add(nappula);
	}
	
	private static class TasoKuuntelija implements ActionListener {
		private VaihtelevaPaneli kohde;
		private int arvo;
		
		/**
		* Kuuntelee tasonappulaan liittyvia tapahtumia.
		* 
		* @param kohde Kohde, jolle tiedot tapahtumista lahetetaan
		* @param arvo Arvo, joka nappulaan liittyy
		*/
		public TasoKuuntelija(VaihtelevaPaneli kohde, int arvo) {
			this.kohde = kohde;
			this.arvo = arvo;
		}
		
		/**
		* Vastaanottaa tasonappulaan liittyvia tapahtumia.
		* 
		* @param ae Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			kohde.valitseTaso(arvo);
		}
	}
	
	/**
	* Vaihtaa asetuksista aloitustason.
	* 
	* @param arvo Haluttu taso
	*/
	public void valitseTaso(int arvo) {
		asetukset.asetaAloitustaso(arvo);
		vaihdaTasoNappuloidenAktiivisuuksia(arvo);
	}
	
	private void vaihdaTasoNappuloidenAktiivisuuksia(int arvo) {
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			Nappula nappula = (Nappula)komponentti;
			if (nappula.getText().equals(arvo+"")) {
				nappula.setEnabled(false);
			}
			else {
				nappula.setEnabled(true);
			}
		}
		
		this.repaint();
	}
	
	//*******************************************
	//
	// Ulottuvuusnappula
	//
	//*******************************************
	
	private void luoUlottuvuusNappula(String aihe, int arvo) {
		Nappula nappula = new Nappula(arvo+"");
		nappula.setFont(new Font(fontinNimi, Font.PLAIN, 16));
		nappula.setFocusable(false);
		
		nappula.setEnabled(true);
		
		nappula.addActionListener( new UlottuvuusKuuntelija(this, aihe, arvo) );
		
		this.add(nappula);
	}
	
	private static class UlottuvuusKuuntelija implements ActionListener {
		private VaihtelevaPaneli kohde;
		private String aihe;
		private int arvo;
		
		/**
		* Kuuntelee ulottuvuusnappulaan liittyvia tapahtumia.
		* 
		* @param kohde Kohde, jolle tiedot tapahtumista lahetetaan
		* @param arvo Arvo, joka nappulaan liittyy
		*/
		public UlottuvuusKuuntelija(VaihtelevaPaneli kohde, String aihe, int arvo) {
			this.kohde = kohde;
			this.aihe = aihe;
			this.arvo = arvo;
		}
		
		/**
		* Vastaanottaa nappulaan liittyvia tapahtumia.
		* 
		* @param ae
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			kohde.valitseUlottuvuus(aihe, arvo);
		}
	}
	
	/**
	* Valitsee ulottuvuuden ja asettaa sen asetuksiin.
	* 
	* @param aihe Ulottuvuuden nimi
	* @param arvo Haluttu arvo
	*/
	public void valitseUlottuvuus(String aihe, int arvo) {
		if (aihe.equals("Leveys")) {
			asetukset.annaUlottuvuudet().asetaLeveys(arvo);
		}
		else if (aihe.equals("Korkeus")) {
			asetukset.annaUlottuvuudet().asetaKorkeus(arvo);
		}
		else if (aihe.equals("Syvyys")) {
			asetukset.annaUlottuvuudet().asetaSyvyys(arvo);
		}
		
		asetuksetPaneli.asetaVaihtelevaPalstaPerustilaan(arvo);
	}
}
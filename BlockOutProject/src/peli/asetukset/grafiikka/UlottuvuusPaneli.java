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

public class UlottuvuusPaneli extends JPanel {
	private AsetuksetPaneli asetuksetPaneli;
	private Asetukset asetukset;
	private String fontinNimi;
	
	/**
	* Luo panelin, josta kasin voidaan vaihtaa pelin kuilun ulottuvuuksia.
	* @param asetuksetPaneli Paneli, jota voidaan pyytaa vaihtamaan vaihtelevan panelin nayttamaa aihetta
	* @param asetukset Pelin valitut asetukset
	* @param fontinNimi Fontin nimi
	*/
	public UlottuvuusPaneli(AsetuksetPaneli asetuksetPaneli, Asetukset asetukset, String fontinNimi) {
		this.asetuksetPaneli = asetuksetPaneli;
		this.asetukset = asetukset;
		this.fontinNimi = fontinNimi;
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		luoUlottuvuusValinta("Leveys");
		luoUlottuvuusValinta("Korkeus");
		luoUlottuvuusValinta("Syvyys");
	}
	
	private void luoUlottuvuusValinta(String aihe) {
		luoOtsikko(aihe);
		luoNappula(aihe);
	}
	
	private void luoOtsikko(String aihe) {
		JLabel otsikko = new JLabel(aihe);
		otsikko.setFont(new Font(fontinNimi, Font.PLAIN, 18));
		otsikko.setForeground(Color.WHITE);
		this.add(otsikko);
	}
	
	private void luoNappula(String aihe) {
		Nappula nappula = new Nappula(haeUlottuvuudenArvo(aihe));
		nappula.setFocusable(false);
		nappula.setEnabled(true);
		nappula.addActionListener( new UlottuvuusNappulaKuuntelija( aihe, nappula, this) );
		this.add(nappula);
	}
	
	private String haeUlottuvuudenArvo(String aihe) {
		if (aihe.equals("Leveys")) {
			return asetukset.annaUlottuvuudet().annaLeveys() + "";
		}
		else if (aihe.equals("Korkeus")) {
			return asetukset.annaUlottuvuudet().annaKorkeus() + "";
		}
		else {
			return asetukset.annaUlottuvuudet().annaSyvyys() + "";
		}
	}
	
	private static class UlottuvuusNappulaKuuntelija implements ActionListener {
		private String aihe;
		private Nappula nappula;
		private UlottuvuusPaneli kohde;
		
		/**
		* Kuuntelee ulottuvuusnappulaan liittyvia tapahtumia.
		* 
		* @param setti Nappulan aihe
		* @param kohde Kohde, jolle tieto palikkasetin valinnasta tulee antaa
		*/
		public UlottuvuusNappulaKuuntelija(String aihe, Nappula nappula, UlottuvuusPaneli kohde) {
			this.aihe = aihe;
			this.nappula = nappula;
			this.kohde = kohde;
		}
		
		/**
		* Kuuntelee ulottuvuusnappulaan liittyvia tapahtumia.
		* 
		* @param ae Tapahtuma
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			nappula.setText("");
			
			kohde.muutaUlottuvuusNappuloidenAktiivisuuksia(false);
			kohde.luoOikeaVaihtelevaPalsta(aihe);
		}
	}
	
	public void muutaUlottuvuusNappuloidenAktiivisuuksia(boolean aktiivisuus) {
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			Nappula nappula = (Nappula)komponentti;
			nappula.setEnabled(aktiivisuus);
		}
		
		this.repaint();
	}
	
	public void luoOikeaVaihtelevaPalsta(String aihe) {
		asetuksetPaneli.luoOikeaVaihtelevaPalsta(aihe);
	}
	
	public void asetaPyydettyArvo(int arvo) {
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			Nappula nappula = (Nappula)komponentti;
			if (nappula.getText().equals("")) {
				nappula.setText(arvo+"");
				break;
			}
		}
		
		muutaUlottuvuusNappuloidenAktiivisuuksia(true);
	}
}
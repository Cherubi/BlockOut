package peli.asetukset;

import peli.asetukset.logiikka.Asetukset;
import valmiskomponentit.Nappula;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class TalletetutAsetuksetPaneli extends JPanel {
	private PelinAsetukset pelinAsetukset;
	private String fontinNimi;
	
	/**
	* Hallinnoi talletettujen asetussettien nakymaa.
	* 
	* @param pelinAsetukset Pelin asetuksien hallinnoija
	* @param talletetutAsetukset Asetussetit, jotka peli sisaltaa
	*/
	public TalletetutAsetuksetPaneli (PelinAsetukset pelinAsetukset, ArrayList<Asetukset> talletetutAsetukset, int valittuAsetus) {
		this.pelinAsetukset = pelinAsetukset;
		haeFontti();
		
		this.setLayout(new GridLayout(7, 1));
		
		luoOtsikko();
		luoNappulat(talletetutAsetukset, valittuAsetus);
	}
	
	/**
	* Luo panelin otsikon
	*/
	private void luoOtsikko() {
		JTextArea otsikko = new JTextArea("   Talletetut\n   asetukset");
		otsikko.setEditable(false);
		otsikko.setFocusable(false);
		int fontinKoko = 30;
		if (fontinNimi.toLowerCase().contains("comic")) {
			fontinKoko = 20;
		}
		otsikko.setFont( new Font(fontinNimi, Font.PLAIN, fontinKoko) );
		otsikko.setForeground(Color.WHITE);
		otsikko.setOpaque(false);
		this.add(otsikko);
	}
	
	/**
	* Luo nappulat, joista eri asetussetteja voi avata.
	* 
	* @param asetukset Asetussetit
	* @param valittuAsetus Valitun asetuksetn id/jarjestysnumero
	*/
	private void luoNappulat(ArrayList<Asetukset> asetukset, int valittuAsetus) {
		int asetusId = 0;
		
		for (Asetukset asetus : asetukset) {
			luoUusiNappula(asetus.annaAsetustenNimi(), asetusId, asetusId == valittuAsetus);
			asetusId++;
			if (asetusId >= 5) {
				System.out.println("Kaikki talletetut asetussetit eivat mahtuneet nakymaan.");
				break;
			}
		}
		
		while (asetusId < 5) {
			luoUusiNappula("Tyhja", -1, false);
			asetusId++;
		}
	}
	
	/**
	* Luo uuden nappulan, joka lisataan JPaneliin.
	* 
	* @param nimi Nappulaan kirjoitettava teksti
	* @param id Nappulan tunnus, jolla kuuntelija kasittelee sita
	* @param valittu Tieto siita asetetaanko nappula valittavaksi
	*/
	private void luoUusiNappula(String nimi, int id, boolean valittu) {
		Nappula nappula = new Nappula(nimi);
		nappula.asetaNappulanKavennus(20);
		nappula.asetaFontti(fontinNimi, 14);
		
		nappula.addActionListener(new Kuuntelija(this, id));
		nappula.setEnabled(!valittu);
		nappula.setFocusable(false);
		nappula.setOpaque(false);
		this.add(nappula);
	}
	
	private static class Kuuntelija implements ActionListener {
		private TalletetutAsetuksetPaneli paneli;
		private int id;
		
		/**
		* Tapahtumakuuntelija talletettuAsetus-nappulalle.
		* 
		* @param paneli JPanel, jolle kuuntelija valittaa viestit
		* @param id Tunnus, jolla kuuntelija kutsuu nappulaansa
		*/
		public Kuuntelija(TalletetutAsetuksetPaneli paneli, int id) {
			this.paneli = paneli;
			this.id = id;
		}
		
		/**
		* Valittaa nappulan tapahtumat talletetutAsetuksetPanelille
		* 
		* @param ae Tapahtuma, joka panelille valitetaan
		*/
		@Override
		public void actionPerformed(ActionEvent ae) {
			paneli.vaihdaAsetus(id);
		}
	}
	
	/**
	* Vaihtaa valittua asetusta kayttajan valintojen mukaisesti. Jos kayttaja on valinnut entuudestaan tyhjan asetuksen luodaan uudelle asetukselle uusi nappula tyhjan nappulan tilalle.
	* 
	* @param id Sen nappulan tunnus, joka halutaan valituksi
	*/
	public void vaihdaAsetus(int id) {
		pelinAsetukset.vaihdaValittuaAsetusta(id);
		if (id == -1) {
			id = pelinAsetukset.annaAsetustenMaara() - 1;
			vaihdaNappulanTeksti("", id);
			annaNappulalleUusiKuuntelija(id);
		}
		vaihdaNappuloidenAktiivisuudet(id);
		this.repaint();
	}
	
	/**
	* Vaihtaa nappuloiden aktiivisuuksia niin, etta valittu nappula/asetus on passiivinen ja muut aktiivisia, jotta ne voidaan valita.
	* 
	* @param id Valitun nappulan tunnus
	*/
	private void vaihdaNappuloidenAktiivisuudet(int id) {
		int haetunNappulanId = 0;
		
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			Nappula nappula = (Nappula)komponentti;
			nappula.setEnabled( haetunNappulanId != id );
			
			haetunNappulanId++;
		}
	}
	
	/**
	* Vaihtaa valitun nappulan tekstin. Nain kay jos kayttaja vaihtaa tallennetun asetussetin nimea.
	* 
	* @param teksti Nappulan uusi nimi
	*/
	public void vaihdaNappulanTeksti(String teksti) {
		vaihdaNappulanTeksti(teksti, pelinAsetukset.annaValittuAsetus() );
	}
	
	/**
	* Vaihtaa maaritetyn nappulan tekstin.
	* 
	* @param teksti Nappulan uusi teksti
	* @param id Muutettavan nappulan tunnus
	*/
	private void vaihdaNappulanTeksti(String teksti, int id) {
		int haetunId = 0;
		
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			if (haetunId == id) {
				Nappula nappula = (Nappula)komponentti;
				nappula.setText(teksti);
				return;
			}
			
			haetunId++;
		}
	}
	
	/**
	* Antaa nappulalle uuden kuuntelijan. Kaytetaan kun tyhja nappula liitetaan uuteen asetussettiin.
	* 
	* @param id Uuden nappulan tunnus
	*/
	private void annaNappulalleUusiKuuntelija(int id) {
		int haetunId = 0;
		
		for (Component komponentti : this.getComponents()) {
			if (! (komponentti instanceof Nappula)) {
				continue;
			}
			
			if (haetunId == id) {
				Nappula nappula = (Nappula)komponentti;
				liitaNappulalleUusiKuuntelija(nappula, id);
				return;
			}
			
			haetunId++;
		}
	}
	
	/**
	* Poistaa tietylta nappulalta liitetyt tapahtumakuuntelijat ja lisaa sille uuden tapahtumakuuntelijan.
	* 
	* @param nappula Nappula, jonka kuuntelijoita halutaan vaihtaa
	* @param id Nappulan tunnus, jolla kuuntelija sita kasittelee
	*/
	private void liitaNappulalleUusiKuuntelija(Nappula nappula, int id) {
		for (ActionListener kuuntelija : nappula.getActionListeners()) {
			nappula.removeActionListener(kuuntelija);
		}
		
		nappula.addActionListener(new Kuuntelija(this, id));
	}
	
	
	/**
	* Hakee fontin, jonka tietokoneen kayttojarjestelma tunnistaa. Tallentaa fontin nimen luokan privaattiin muuttujaan.
	*/
	private void haeFontti() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = environment.getAllFonts();
 
 		String[] halututFontit = {"Herculanum", "ComicSansMS", "Comic Sans MS", "ArialMT", "Arial"};
 		for (String haluttuFontti : halututFontit) {
 			for (Font font : fonts) {
	 			if (font.getFontName().equals(haluttuFontti)) {
	 				fontinNimi = font.getFontName();
	 				return;
	 			}
	 		}
		}
		
		for (Font font : fonts) {
			if (font.getFontName().toLowerCase().contains("arial")) {
				fontinNimi = font.getFontName();
				return;
			}
		}
		
		fontinNimi = "futura";
	}
}
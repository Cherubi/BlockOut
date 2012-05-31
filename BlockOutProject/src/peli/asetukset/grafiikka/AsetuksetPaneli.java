package peli.asetukset.grafiikka;

import peli.asetukset.grafiikka.TalletetutAsetuksetPaneli;
import peli.asetukset.logiikka.Asetukset;
import valmiskomponentit.Nappula;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class AsetuksetPaneli extends JPanel {
	private TalletetutAsetuksetPaneli talletetutPaneli;
	private Asetukset asetukset;
	
	private JLabel asetuksenNimi;
	private boolean muokattavissa;
	
	private UlottuvuusPaneli ulottuvuusPaneli;
	
	private String fontinNimi;
	
	/**
	* Nayttaa kayttajalle lahes kaikki peliin liittyvat asetukset ja antaa kayttajan muokata niita.
	* 
	* @param talletetutPaneli JPanel, joka sisaltaa kayttajan valittavissa olevat asetussetti-JButtonit
	* @param valitutAsetukset Silla hetkella valittu asetussetti
	* @param muokattavissa Kertoo onko kaikki asetukset muokattavissa. Perusasetuksien kaikkia asetuksia ei voi muokata
	*/
	public AsetuksetPaneli (TalletetutAsetuksetPaneli talletetutPaneli, Asetukset valitutAsetukset, boolean muokattavissa) {
		this.talletetutPaneli = talletetutPaneli;
		this.asetukset = valitutAsetukset;
		this.muokattavissa = muokattavissa;
		
		haeFontti();
		
		luoKomponentit();
	}
	
	private void luoKomponentit() {
		this.setLayout(new BorderLayout());
		
		luoAsetuksenNimiPaneli();
		
		luoAsetuksetAlue();
		
		JLabel tyhja = new JLabel("-");
		tyhja.setOpaque(false);
		this.add(tyhja, BorderLayout.SOUTH);
	}
	
	private void luoAsetuksenNimiPaneli() {
		JPanel alue = new JPanel();
		alue.setOpaque(false);
		this.add(alue, BorderLayout.NORTH);
		
		Font isompiFontti = new Font(this.fontinNimi, Font.PLAIN, 24);
		
		JLabel selite = new JLabel("Nimi: ", JLabel.LEFT);
		selite.setFocusable(false);
		selite.setFont( isompiFontti );
		selite.setForeground(Color.WHITE);
		alue.add(selite);
		
		asetuksenNimi = new JLabel( asetukset.annaAsetustenNimi(), JLabel.TRAILING );
		asetuksenNimi.setFocusable(false);
		asetuksenNimi.setFont( isompiFontti );
		asetuksenNimi.setForeground(Color.WHITE);
		alue.add(asetuksenNimi);
	}
	
	/**
	* Kasittelee asetukset-nakymalle tulevat nappaintapahtumat.
	* 
	* @param ke Nappaintapahtuma nappaimen pohjaan painamisesta
	*/
	public void keyPressed(KeyEvent ke) {
		if (!muokattavissa) {
			return;
		}
		
		String nimi = asetuksenNimi.getText();
		kasitteleNappaintapahtuma(nimi, ke);
		
		asetuksenNimi.repaint();
	}
	
	private void kasitteleNappaintapahtuma(String nimi, KeyEvent ke) {
		String merkki = ke.getKeyChar() + "";
		
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			asetukset.asetaAsetustenNimi(nimi);
			talletetutPaneli.vaihdaNappulanTeksti(nimi);
		}
		else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (nimi.length() != 0) {
				asetuksenNimi.setText( nimi.substring( 0, nimi.length()-1 ) );
			}
		}
		else if (merkki.matches("\\p{ASCII}")) { //TODO erikoismerkit
			asetuksenNimi.setText( nimi + merkki );
		}
	}
	
	private void luoAsetuksetAlue() {
		JPanel asetusalue = new JPanel(new GridLayout(1,3));
		this.add(asetusalue);
		asetusalue.setOpaque(false);
		
		luoVasenPalsta(asetusalue);
		luoKeskinPalsta(asetusalue);
		luoOikeaVaihtelevaPalsta("Taso");
	}
	
	private void luoVasenPalsta(JPanel kohde) {
		JPanel vasenPalsta = new JPanel(new GridLayout(2,1));
		vasenPalsta.setOpaque(false);
		
		vasenPalsta.add(new PalikkasettiPaneli( asetukset, fontinNimi));
		
		Nappula palikatNappula = new Nappula("Palikat");
		palikatNappula.setFocusable(false);
		palikatNappula.setEnabled(false && this.muokattavissa); //TODO
		//TODO kuuntelija
		vasenPalsta.add(palikatNappula);
		
		kohde.add(vasenPalsta);
	}
	
	private void luoKeskinPalsta(JPanel kohde) {
		JPanel keskinPalsta = new JPanel(new GridLayout(2,1));
		keskinPalsta.setOpaque(false);
		
		this.ulottuvuusPaneli = new UlottuvuusPaneli(this, asetukset, fontinNimi);
		keskinPalsta.add(ulottuvuusPaneli);
		
		JPanel alapaneli = new JPanel(new GridLayout(2,1));
		
		Nappula nappainNappula = new Nappula("Nappulat");
		nappainNappula.setFocusable(false);
		nappainNappula.setEnabled(false && this.muokattavissa); //TODO
		//TODO kuuntelija
		alapaneli.add(nappainNappula);
		
		Nappula variNappula = new Nappula("Varit");
		variNappula.setFocusable(false);
		variNappula.setEnabled(false && this.muokattavissa); //TODO
		//TODO kuuntelija
		alapaneli.add(variNappula);
		
		keskinPalsta.add(alapaneli);
		
		kohde.add(keskinPalsta);
	}
	
	/**
	* Luo oikeanpuoleisimman palstan, joka sisaltaa vaihtelevan aiheen.
	* 
	* @param aihe Aiheen nimi
	*/
	public void luoOikeaVaihtelevaPalsta(String aihe) {
		JPanel asetusalue = (JPanel)this.getComponent(1);
		if (asetusalue.getComponentCount() >= 3) {
			asetusalue.remove(2);
		}
		
		JPanel vaihtelevaAlue = new JPanel(new FlowLayout());
		vaihtelevaAlue.setOpaque(false);
		vaihtelevaAlue.add(new VaihtelevaPaneli(this, asetukset, aihe, fontinNimi));
		
		asetusalue.add(vaihtelevaAlue);
		this.revalidate();
		this.repaint();
	}
	
	/**
	* Luo vaihtelevaan palstaan jalleen Taso-kyselyn ja paivittaa UlottuvuudetPanelin.
	*/
	public void asetaVaihtelevaPalstaPerustilaan(int arvo) {
		ulottuvuusPaneli.asetaPyydettyArvo(arvo);
		luoOikeaVaihtelevaPalsta("Taso");
	}
	
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
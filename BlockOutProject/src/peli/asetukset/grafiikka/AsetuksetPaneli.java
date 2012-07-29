package peli.asetukset.grafiikka;

import kayttoliittyma.BlockOut;
import peli.asetukset.PelinAsetukset;
import peli.asetukset.grafiikka.TalletetutAsetuksetPaneli;
import peli.asetukset.logiikka.Asetukset;
import valmiskomponentit.Nappula;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class AsetuksetPaneli extends JPanel implements KeyListener {
	private BlockOut kayttis;
	private PelinAsetukset pelinAsetukset;
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
	public AsetuksetPaneli (BlockOut kayttis, PelinAsetukset pelinAsetukset, TalletetutAsetuksetPaneli talletetutPaneli, Asetukset valitutAsetukset, boolean muokattavissa) {
		this.kayttis = kayttis;
		this.pelinAsetukset = pelinAsetukset;
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
		alue.setLayout(new GridLayout(1,3));
		alue.setOpaque(false);
		this.add(alue, BorderLayout.NORTH);
		
		Font isompiFontti = new Font(this.fontinNimi, Font.PLAIN, 24);
		
		JLabel selite = new JLabel("Nimi: ", JLabel.RIGHT);
		selite.setFocusable(false);
		selite.setFont( isompiFontti );
		selite.setForeground(Color.WHITE);
		alue.add(selite);
		
		asetuksenNimi = new JLabel( asetukset.annaAsetustenNimi(), JLabel.LEFT );
		asetuksenNimi.setFocusable(false);
		asetuksenNimi.setFont( isompiFontti );
		asetuksenNimi.setForeground(Color.WHITE);
		alue.add(asetuksenNimi);
		
		/*Nappula alustaSetti = new Nappula("Alusta asetussetti");
		alustaSetti.setFocusable(false);
		alue.add(alustaSetti);*/ //PelinAsetukset.java:aan
	}
	
	public void keyTyped(KeyEvent ke) {}
	
	public void keyReleased(KeyEvent ke) {}
	
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
		else if (merkki.matches("\\p{ASCII}|[ŒŠš€…§Ÿ†]")) {
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
		palikatNappula.asetaFontti(fontinNimi, 20);
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
		
		JPanel alapaneli = new JPanel(new GridLayout(3,1));
		
		Nappula nappainNappula = new Nappula("Nappulat");
		nappainNappula.setFocusable(false);
		nappainNappula.asetaFontti(fontinNimi, 20);
		nappainNappula.setEnabled(this.muokattavissa);
		nappainNappula.addActionListener(new NappainNappulaKuuntelija(pelinAsetukset));
		alapaneli.add(nappainNappula);
		
		Nappula variNappula = new Nappula("VŠrit");
		variNappula.setFocusable(false);
		variNappula.asetaFontti(fontinNimi, 20);
		variNappula.setEnabled(this.muokattavissa);
		variNappula.addActionListener(new VariNappulaKuuntelija( pelinAsetukset ));
		alapaneli.add(variNappula);
		
		Nappula aaniNappula = new Nappula("€Šnet pŠŠllŠ");
		if (!asetukset.annaAanet()) {
			aaniNappula.setText("€Šnet poissa");
		}
		aaniNappula.setFocusable(false);
		aaniNappula.asetaFontti(fontinNimi, 20);
		aaniNappula.setEnabled(true);
		aaniNappula.addActionListener(new AaniNappulaKuuntelija( aaniNappula, asetukset ));
		alapaneli.add(aaniNappula);
		
		keskinPalsta.add(alapaneli);
		
		kohde.add(keskinPalsta);
	}
	
	private static class NappainNappulaKuuntelija implements ActionListener {
		private PelinAsetukset kohde;
		
		public NappainNappulaKuuntelija(PelinAsetukset kohde) {
			this.kohde = kohde;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			kohde.naytaNappaimet();
		}
	}
	
	private static class VariNappulaKuuntelija implements ActionListener {
		private PelinAsetukset kohde;
		
		public VariNappulaKuuntelija(PelinAsetukset kohde) {
			this.kohde = kohde;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			kohde.naytaVarit();
		}
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
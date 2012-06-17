package peli.asetukset.grafiikka;

import peli.asetukset.PelinAsetukset;
import peli.asetukset.logiikka.Asetukset;
import peli.asetukset.logiikka.Nappainsetti;
import valmiskomponentit.Nappula;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Nappainpaletti extends JPanel implements KeyListener {
	private PelinAsetukset pelinAsetukset;
	private Nappainsetti nappainsetti;
	
	private ArrayList<Nappula> nappulat;
	private boolean kyselyAktivoitu;
	
	private String fontinNimi;
	
	public Nappainpaletti(PelinAsetukset pelinAsetukset) {
		this.pelinAsetukset = pelinAsetukset;
		this.nappainsetti = pelinAsetukset.annaValitutAsetukset().annaNappainsetti();
		
		haeFontti();
		
		this.nappulat = new ArrayList<Nappula>();
		luoKomponentit();
		
		this.kyselyAktivoitu = false;
	}
	
	private void luoKomponentit() {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		luoSisennys();
		JPanel kohde = new JPanel(new GridLayout(9,2));
		kohde.setOpaque(false);
		this.add(kohde, BorderLayout.CENTER);
		
		luoSiirrotJaPyoritykset();
		luoTiputuksetJaTauko();
		
		luoPaluu(kohde);
	}
	
	private void luoSisennys() {
		JLabel otsikko = new JLabel("Sisennys:");
		otsikko.setFont(new Font(fontinNimi, Font.PLAIN, 30));
		this.add(otsikko, BorderLayout.NORTH);
		
		JLabel sisennys = new JLabel("     ");
		this.add(sisennys, BorderLayout.WEST);
		
		this.add(sisennys, BorderLayout.EAST);
	}
	
	private void luoSiirrotJaPyoritykset() {
		luoPari("Siirrä ylös", "ylos", nappainsetti.annaYlosNappain());
		luoPari("Yläpuoli esille", "ylapuoli esille", nappainsetti.annaYlapuoliEsilleNappain());
		
		luoPari("Siirrä alas", "alas", nappainsetti.annaAlasNappain());
		luoPari("Alapuoli esille", "alapuoli esille", nappainsetti.annaAlapuoliEsilleNappain());
		
		luoPari("Siirrä vasemmalle", "vasemmalle", nappainsetti.annaVasemmalleNappain());
		luoPari("Vasen puoli esille", "vasen puoli esille", nappainsetti.annaVasenPuoliEsilleNappain());
		
		luoPari("Siirrä oikealle", "oikealle", nappainsetti.annaOikealleNappain());
		luoPari("Oikea puoli esille", "oikea puoli esille", nappainsetti.annaOikeaPuoliEsilleNappain());
		
		((JPanel)this.getComponent(2)).add(new JPanel());
		luoPari("Pyöritä myötäpäivään", "myotapaivaan", nappainsetti.annaKierraMyotapaivaanNappain());
		
		((JPanel)this.getComponent(2)).add(new JPanel());
		luoPari("Pyöritä vastapäivään", "vastapaivaan", nappainsetti.annaKierraVastapaivaanNappain());
	}
	
	private void luoTiputuksetJaTauko() {
		luoPari("Tiputa pohjalle", "tiputa", nappainsetti.annaTiputaNappain());
		((JPanel)this.getComponent(2)).add(new JPanel());
		
		luoPari("Tiputa yksi kerros", "tiputa yksi kerros", nappainsetti.annaTiputaYksiKerrosNappain());
		luoPari("Tauko", "tauko", nappainsetti.annaTaukoNappain());
	}
	
	private void luoPari(String teksti, String avain, int nappain) {
		JPanel center = (JPanel)this.getComponent(2);
		
		JPanel pari = new JPanel();
		pari.setLayout(new BoxLayout(pari, BoxLayout.X_AXIS));
		luoPari(pari, teksti, avain, nappain);
		
		center.add(pari);
	}
	
	private void luoPari(JPanel kohde, String teksti, String avain, int nappain) {
		Nappula nappula = new Nappula(selvitaNappaimenUlkonako(nappain));
		nappula.setName(avain);
		nappula.setFocusable(false);
		nappula.setFont(new Font(fontinNimi, Font.PLAIN, 16));
		nappula.setEnabled(true);
		nappula.addActionListener(new NappulaKuuntelija(this, nappain));
		nappula.setMaximumSize(new Dimension(40, 30));
		kohde.add(nappula);
		
		this.nappulat.add(nappula);
		
		JLabel label = new JLabel(" " + teksti);
		label.setFont(new Font(fontinNimi, Font.PLAIN, 16));
		label.setForeground(Color.WHITE);
		kohde.add(label);
	}
	
	private String selvitaNappaimenUlkonako(int koodi) {
		return koodi + "";
	}
	
	private static class NappulaKuuntelija implements ActionListener {
		private Nappainpaletti kohde;
		private int nykyinenNappain;
		
		public NappulaKuuntelija(Nappainpaletti kohde, int nykyinenNappain) {
			this.kohde = kohde;
			this.nykyinenNappain = nykyinenNappain;
		}
		
		public void vaihdaNykyistaNappainta(int uusiNappain) {
			this.nykyinenNappain = uusiNappain;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			kohde.nappulaaPainettu(this.nykyinenNappain);
		}
	}
	
	public void nappulaaPainettu(int nykyinenNappain) {
		JPanel center = (JPanel)this.getComponent(2);
		
		for (Nappula nappula : this.nappulat) {
			if (nappula.getText().equals("")) {
				nappula.setText(nykyinenNappain + "");
				aktivoiKaikki();
				kyselyAktivoitu = false;
				break;
			}
			else if (nappula.getText().equals(nykyinenNappain + "")) {
				nappula.setText("");
				deaktivoiMuut(nappula);
				kyselyAktivoitu = true;
				break;
			}
		}
	}
	
	private void aktivoiKaikki() {
		for (Nappula nappula : this.nappulat) {
			nappula.setEnabled(true);
		}
	}
	
	private void deaktivoiMuut(Nappula aktiivinenNappula) {
		for (Nappula nappula : this.nappulat) {
			if (nappula != aktiivinenNappula) {
				nappula.setEnabled(false);
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent ke) {}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		if (kyselyAktivoitu) {
			for (Nappula nappula : nappulat) {
				if (nappula.getText().equals("")) {
					asetaUusiKeyCode(nappula, ke.getKeyCode());
					return;
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent ke) {}
	
	private void asetaUusiKeyCode(Nappula nappula, int uusiKoodi) {
		String tunniste = nappula.getName();
		if (nappainsetti.asetaNappain(tunniste, uusiKoodi)) {
			nappula.setText(uusiKoodi + "");
			muutaKuuntelijaa(nappula, uusiKoodi);
			
			aktivoiKaikki();
			kyselyAktivoitu = false;
		}
	}
	
	private void muutaKuuntelijaa(Nappula nappula, int uusiKoodi) {
		for (ActionListener kuuntelija : nappula.getActionListeners()) {
			if (kuuntelija instanceof NappulaKuuntelija) {
				( (NappulaKuuntelija)kuuntelija ).vaihdaNykyistaNappainta(uusiKoodi);
			}
		}
	}
	
	private void luoPaluu(JPanel kohde) {
		kohde.add(new JPanel());
		JPanel paluu = new JPanel(new GridLayout(1,2));
		paluu.setOpaque(false);
		
		paluu.add(new JPanel());
		
		Nappula paluuNappula = new Nappula("Palaa");
		paluuNappula.setFocusable(false);
		paluuNappula.asetaFontti(fontinNimi, 20);
		paluuNappula.addActionListener(new PaluuKuuntelija(pelinAsetukset));
		paluu.add(paluuNappula);
		
		kohde.add(paluu);
	}
	
	private static class PaluuKuuntelija implements ActionListener {
		private PelinAsetukset pelinAsetukset;
		
		public PaluuKuuntelija(PelinAsetukset pelinAsetukset) {
			this.pelinAsetukset = pelinAsetukset;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			pelinAsetukset.vaihdaAsetuksetPanelia();
			pelinAsetukset.revalidate();
		}
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
package varipaletti;

import peli.asetukset.PelinAsetukset;
import peli.asetukset.logiikka.Varit;
import valmiskomponentit.Nappula;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Varipaletti extends JPanel implements KeyListener {
	private PelinAsetukset pelinAsetukset;
	private Varit varit;
	private String fontinNimi;
	
	private JPanel kerrosValikoima, variValikoima, variTalletus;
	private int valittuKerros = 0;
	
	public Varipaletti(PelinAsetukset pelinAsetukset) {
		this.pelinAsetukset = pelinAsetukset;
		this.varit = pelinAsetukset.annaValitutAsetukset().annaVarit();
		
		haeFontti();
		
		luoKomponentit();
	}
	
	
	private void luoKomponentit() {
		this.setLayout(new BorderLayout());
		JPanel sisalto = new JPanel();
		this.setOpaque(false);
		
		sisalto.setLayout(new GridLayout(1,3));
		sisalto.setOpaque(false);
		
		sisalto.add(luoPaapalkki());
		
		this.variValikoima = new JPanel();
		variValikoima.setOpaque(false);
		sisalto.add(variValikoima);
		
		this.variTalletus = new JPanel();
		variTalletus.setOpaque(false);
		sisalto.add(variTalletus);
		
		this.add(sisalto, BorderLayout.CENTER);
		this.add(luoOtsikko(), BorderLayout.NORTH);
		this.add(new JLabel("-"), BorderLayout.SOUTH);
	}
	
	private JLabel luoOtsikko() {
		JLabel otsikko = new JLabel("VŠriasetukset", JLabel.CENTER);
		otsikko.setFont(new Font(fontinNimi, Font.PLAIN, 30));
		otsikko.setForeground(Color.WHITE);
		return otsikko;
	}
	
	private JPanel luoPaapalkki() {
		JPanel paneli = new JPanel();
		paneli.setLayout(new BorderLayout());
		
		kerrosValikoima = new JPanel();
		kerrosValikoima.setLayout(new FlowLayout());
		luoOlemassaOlevatKerrokset();
		paneli.add(kerrosValikoima, BorderLayout.CENTER);
		
		Nappula palaa = new Nappula("Palaa");
		palaa.setFocusable(true);
		palaa.addActionListener(new PaluuKuuntelija(pelinAsetukset));
		paneli.add(palaa, BorderLayout.SOUTH);
		
		return paneli;
	}
	
	private void luoOlemassaOlevatKerrokset() {
		kerrosValikoima.removeAll();
		
		int kerros = 1;
		for (Color vari : varit.annaVarit()) {
			JPanel paneli = new JPanel(new GridLayout(1,3));
			
			Nappula variNappula = new Nappula(" " + kerros);
			variNappula.setFocusable(false);
			variNappula.asetaTekstinVari(Color.BLACK);
			variNappula.asetaTaustanVari(vari);
			variNappula.setPreferredSize(new Dimension(50, 29));
			variNappula.addActionListener(new KerrosKuuntelija(this, kerros));
			variNappula.addActionListener(new MuutaVariKuuntelija(this, variNappula));
			paneli.add(variNappula);
			
			Nappula lisaaNappula = new Nappula("LisŠŠ");
			lisaaNappula.setFocusable(false);
			lisaaNappula.setPreferredSize(new Dimension(50, 29));
			lisaaNappula.addActionListener(new LisayksenKuuntelija(this, -kerros));
			paneli.add(lisaaNappula);
			
			Nappula poistaNappula = new Nappula("Poista");
			poistaNappula.setFocusable(false);
			poistaNappula.setPreferredSize(new Dimension(50, 29));
			poistaNappula.addActionListener(new PoistonKuuntelija( pelinAsetukset, this, varit, kerros ));
			paneli.add(poistaNappula);
			
			kerrosValikoima.add(paneli);
			
			//Nappula lisaaNappula = new Nappula("LisŠŠ");
			//lisaaNappula.setFocusable(false);
			//kerrospaneli.add(lisaaNappula);
			
			kerros++;
		}
	}
	
	private static class KerrosKuuntelija implements ActionListener {
		private Varipaletti paletti;
		private int kerros;
		
		public KerrosKuuntelija(Varipaletti paletti, int kerros) {
			this.paletti = paletti;
			this.kerros = kerros;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			paletti.valitseKerros(kerros);
		}
	}
	
	public void valitseKerros(int valittuKerros) {
		this.valittuKerros = valittuKerros;
		
		int kerros = 1;
		for (Component komponentti : this.kerrosValikoima.getComponents()) {
			if (komponentti instanceof JPanel) {
				JPanel kohta = (JPanel)komponentti;
				Nappula varinappula = (Nappula)kohta.getComponent(0);
				varinappula.tummennaReuna(kerros == valittuKerros);
				
				kerros++;
			}
		}
		
		this.kerrosValikoima.repaint();
	}
	
	private static class MuutaVariKuuntelija implements ActionListener {
		private Varipaletti paletti;
		private Nappula nappula;
		
		public MuutaVariKuuntelija(Varipaletti paletti, Nappula nappula) {
			this.paletti = paletti;
			this.nappula = nappula;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			this.paletti.alustaVarikentat(this.nappula.getBackground());
		}
	}
	
	private static class ValitseVariKuuntelija implements ActionListener {
		private Varipaletti paletti;
		private Nappula nappula;
		
		public ValitseVariKuuntelija(Varipaletti paletti, Nappula nappula) {
			this.paletti = paletti;
			this.nappula = nappula;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			this.paletti.alustaTalletuskentta(this.nappula.getBackground());
		}
	}
	
	private static class PoistonKuuntelija implements ActionListener {
		private PelinAsetukset pelinAsetukset;
		private Varipaletti paletti;
		private Varit varit;
		private int kerros;
		
		public PoistonKuuntelija(PelinAsetukset pelinAsetukset, Varipaletti paletti, Varit varit, int kerros) {
			this.pelinAsetukset = pelinAsetukset;
			this.paletti = paletti;
			this.varit = varit;
			this.kerros = kerros;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			varit.poistaVari(kerros);
			pelinAsetukset.tallennaTallennokset();
			
			paletti.luoOlemassaOlevatKerrokset();
			paletti.tyhjennaVariKentat();
			
			
			paletti.revalidate();
			paletti.repaint();
		}
	}
	
	private static class LisayksenKuuntelija implements ActionListener {
		private Varipaletti paletti;
		private int kerros;
		
		public LisayksenKuuntelija(Varipaletti paletti, int kerros) {
			this.paletti = paletti;
			this.kerros = kerros;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			paletti.valitseKerros(kerros);
			this.paletti.alustaVarikentat(new Color(125, 125, 125));
		}
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
	
	public void alustaVarikentat(Color vari) {
		this.variValikoima.removeAll();
		alustaVariValikoima(vari);
		
		this.variValikoima.revalidate();
	}
	
	private void alustaVariValikoima(Color vari) {
		this.variValikoima.setLayout(new GridLayout(11, 6));
		
		int b = vari.getBlue();
		int g = vari.getGreen();
		int r = vari.getRed();
		
		this.variValikoima.add(luoVariNappula(r, g, b));
		
		for (int sin : haeVariarvot(b)) {
			for (int vih : haeVariarvot(g)) {
				for (int pun : haeVariarvot(r)) {
					this.variValikoima.add(luoVariNappula(pun, vih, sin));
				}
			}
		}
		
		this.variValikoima.add(luoVariNappula(vari.darker().getRed(), vari.darker().getGreen(), vari.darker().getBlue()));
	}
	
	private ArrayList<Integer> haeVariarvot(int arvo) {
		ArrayList<Integer> arvot = new ArrayList<Integer>();
		
		arvot.add(arvo/2);
		arvot.add(arvo*3/4);
		arvot.add(arvo + (255-arvo)/4);
		arvot.add(arvo + (255-arvo)/2);
		
		for (int variarvo : arvot) {
			if (variarvo < 0) {
				variarvo = 0;
			}
			else if (variarvo > 255) {
				variarvo = 255;
			}
		}
		
		return arvot;
	}
	
	private Nappula luoVariNappula(int r, int g, int b) {
		Nappula nappula = new Nappula(" ");
		nappula.setFocusable(false);
		nappula.setFocusPainted(false);
		nappula.setBorderPainted(false);
		nappula.asetaTaustanVari(new Color(r, g, b));
		
		nappula.addActionListener(new MuutaVariKuuntelija(this, nappula));
		nappula.addActionListener(new ValitseVariKuuntelija(this, nappula));
		
		return nappula;
	}
	
	private void alustaTalletuskentta(Color vari) {
		this.variTalletus.removeAll();
		this.variTalletus.setLayout(new GridLayout(3,1));
		
		Nappula nappula = new Nappula(" ");
		nappula.setFocusable(false);
		nappula.asetaTaustanVari(new Color( vari.getRed(), vari.getGreen(), vari.getBlue() ));
		nappula.asetaNappulanKavennus(20);
		this.variTalletus.add(nappula);
		
		JPanel rgbPaneli = new JPanel();
		rgbPaneli.setOpaque(false);
		this.variTalletus.add(rgbPaneli);
		
		Nappula valintaNappula = new Nappula("Valitse vŠri");
		valintaNappula.setFocusable(false);
		valintaNappula.asetaNappulanKavennus(20);
		valintaNappula.addActionListener(new ValinnanKuuntelija( this, vari ));
		this.variTalletus.add(valintaNappula);
	}
	
	private static class ValinnanKuuntelija implements ActionListener {
		private Varipaletti paletti;
		private Color vari;
		
		public ValinnanKuuntelija(Varipaletti paletti, Color vari) {
			this.paletti = paletti;
			this.vari = vari;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			paletti.vaihdaKerroksenVari(vari);
			
			paletti.tyhjennaVariKentat();
		}
	}
	
	public void vaihdaKerroksenVari(Color vari) {
		if (valittuKerros > 0 ) {
			varit.vaihdaVari(valittuKerros, vari);
		}
		else if (valittuKerros < 0) {
			varit.lisaaVari(-valittuKerros, vari);
		}
		pelinAsetukset.tallennaTallennokset();
		
		luoOlemassaOlevatKerrokset();
		this.revalidate();
	}
	
	public void tyhjennaVariKentat() {
		this.variValikoima.removeAll();
		this.variTalletus.removeAll();
		
		this.valittuKerros = 0;
		
		this.repaint();
	}
	
	
	
	@Override
	public void keyTyped(KeyEvent ke) {}
	
	@Override
	public void keyPressed(KeyEvent ke) {}
	
	@Override
	public void keyReleased(KeyEvent ke) {}
	
	
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
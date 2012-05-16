package valmiskomponentit;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JButton;

public class Nappula extends JButton {
	private Color tekstinVari, taustanVari, tekstinVariAktiivisena, taustanVariAktiivisena;
	private String fontinNimi;
	private int fontinKoko;
	
	private int nappulanKavennus;
	
	public Nappula(String teksti) {
		this(teksti, Color.WHITE, Color.BLACK);
	}
	
	public Nappula(String teksti, Color tekstinVari, Color taustanVari) {
		super(teksti);
		
		this.tekstinVari = tekstinVari;
		this.tekstinVariAktiivisena = tekstinVari;
		this.taustanVari = taustanVari;
		this.taustanVariAktiivisena = taustanVari;
		
		this.fontinNimi = "futura";
		this.fontinKoko = 12;
		
		this.nappulanKavennus = 0;
	}
	
	public void asetaTeksti(String teksti) {
		setText(teksti);
	}
	
	public void asetaFontti(String fontinNimi, int fontinKoko) {
		this.fontinNimi = fontinNimi;
		this.fontinKoko = fontinKoko;
	}
	
	public void asetaTekstinVari(Color vari) {
		if (this.tekstinVariAktiivisena == this.tekstinVari) {
			this.tekstinVari = vari;
		}
		this.tekstinVariAktiivisena = vari;
	}
	
	public void asetaTaustanVari(Color vari) {
		if (this.taustanVariAktiivisena == this.taustanVari) {
			this.taustanVari = vari;
		}
		this.taustanVariAktiivisena = vari;
	}
	
	public void asetaNappulanKavennus(int kavennus) {
		this.nappulanKavennus = kavennus;
	}
	
	@Override
	public void setEnabled(boolean aktiivinen) {
		super.setEnabled(aktiivinen);
		
		if (aktiivinen) {
			this.tekstinVari = this.tekstinVariAktiivisena;
			this.taustanVari = this.taustanVariAktiivisena;
		}
		else {
			this.tekstinVari = this.tekstinVariAktiivisena.darker().darker();
			this.taustanVari = this.taustanVariAktiivisena.darker().darker();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		varitaTausta(g);
		varitaReunat(g);
		piirraTeksti(g);
	}
	
	private void varitaTausta(Graphics g) {
		g.setColor(this.taustanVari);
		g.fillRect(5, 5, getWidth()-2*5, getHeight()-2*5);
	}
	
	private void varitaReunat(Graphics g) {
		g.setColor(Color.WHITE);
		
		Random arpoja = new Random();
		
		//ylempi vaakaviiva
		int ekaYlitys = arpoja.nextInt(4)+1;
		int tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(5-tokaYlitys+nappulanKavennus, 5, getWidth()-2*nappulanKavennus-2*5+ekaYlitys+tokaYlitys, 1);
		
		//alempi vaakaviiva
		ekaYlitys = arpoja.nextInt(4)+1;
		tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(5-tokaYlitys+nappulanKavennus, getHeight()-5, getWidth()-2*nappulanKavennus-2*5+ekaYlitys+tokaYlitys, 1);
		
		//vasen pystyviiva
		ekaYlitys = arpoja.nextInt(4)+1;
		tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(nappulanKavennus+5, 5-ekaYlitys, 1, getHeight()-2*5+ekaYlitys+tokaYlitys);
		
		//oikea pystyviiva
		ekaYlitys = arpoja.nextInt(4)+1;
		tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(getWidth()-nappulanKavennus-5, 5-ekaYlitys, 1, getHeight()-2*5+ekaYlitys+tokaYlitys);
	}
	
	private void piirraTeksti(Graphics g) {
		Font f = haeRuutuunSovitettuFontti(g);
		int tekstinLeveys = laskeTekstinLeveys(g);
		int tekstinKorkeus = laskeTekstinKorkeus(g);
		
		int nappulanLeveys = getWidth() - 2*nappulanKavennus - 2*5;
		int tekstinVaakakeskitys = (nappulanLeveys-tekstinLeveys)/2;
		
		int nappulanKorkeus = getHeight() - 2*5;
		int tekstinPystykeskitys = (nappulanKorkeus-tekstinKorkeus)/2;
		
		g.setColor(this.tekstinVari);
		g.drawString(getText(), nappulanKavennus+5+tekstinVaakakeskitys, 5+tekstinPystykeskitys + tekstinKorkeus);
	}
	
	private Font haeRuutuunSovitettuFontti(Graphics g) {
		int tekstinLeveys = 0;
		int fontinSovitettuKoko = this.fontinKoko;
		
		Font f;
		do {
			f = new Font(this.fontinNimi, Font.PLAIN, fontinSovitettuKoko);
			g.setFont(f);
			tekstinLeveys = laskeTekstinLeveys(g);
			
			fontinSovitettuKoko--;
		} while (tekstinLeveys > getWidth()-2*nappulanKavennus-2*5-2);
		
		return f;
	}
	
	private int laskeTekstinLeveys(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		return fm.charsWidth(getText().toCharArray(), 0, getText().length());
	}
	
	private int laskeTekstinKorkeus(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		return fm.getMaxAscent();
	}
	
}
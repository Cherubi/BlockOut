package peli.grafiikka;

import peli.Peli;
import peli.asetukset.logiikka.Ulottuvuudet;
import peli.asetukset.logiikka.Varit;
import peli.ennatyslista.Ennatyslistaaja;
import peli.logiikka.Pala;
import peli.logiikka.Palikkasetti;
import peli.logiikka.Pistelaskija;
import peli.logiikka.TippuvaPalikka;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class Piirturi {
	private Peli peli;
	private Piste3DHaku piste3DHaku;
	
	private Reunapiirturi reunapiirturi;
	private Palikkapiirturi palikkapiirturi;
	private TippuvaPalikkapiirturi tippuvaPalikkapiirturi;
	private Statistiikkapiirturi statistiikkapiirturi;
	
	private int ikkunanLeveys, ikkunanKorkeus, tietopalkinLeveys;
	
	/**
	* Hoitaa pelin piirtamisen.
	* 
	* @param peli Peli, jota piirretaan
	* @param ikkunanLeveys Kaytettavan ikkunan leveys
	* @param ikkunanKorkeus Kaytettavan ikkunan korkeus
	* @param ulottuvuudet Asetuksissa valitut ulottuvuudet kuilulle
	* @param palikkasetti Peliin valittu palikkasetti
	* @param varit Eri kerrosten piirtamiseen tarkoitetut varit
	* @param pistelaskija Pelin pistelaskija
	*/
	public Piirturi(Peli peli, int ikkunanLeveys, int ikkunanKorkeus, Ulottuvuudet ulottuvuudet, Palikkasetti palikkasetti, Varit varit, Pistelaskija pistelaskija, Ennatyslistaaja ennatyslistaaja) {
		this.peli = peli;
		
		this.ikkunanLeveys = ikkunanLeveys;
		this.tietopalkinLeveys = 130;
		this.ikkunanKorkeus = ikkunanKorkeus;
		
		luoPiste3DHaku(ulottuvuudet);
		
		luoSisaisetPiirturit(varit, ulottuvuudet, pistelaskija, ennatyslistaaja);
	}
	
	private void luoPiste3DHaku(Ulottuvuudet ulottuvuudet) {
		int kentanLeveys = ulottuvuudet.annaLeveys();
		int kentanKorkeus = ulottuvuudet.annaKorkeus();
		int kentanSyvyys = ulottuvuudet.annaSyvyys();
		int leikkauspiste = ulottuvuudet.annaLeikkauspiste();
		
		this.piste3DHaku = new Piste3DHaku( this.ikkunanLeveys-this.tietopalkinLeveys*2, this.tietopalkinLeveys, this.ikkunanKorkeus, leikkauspiste, kentanLeveys, kentanKorkeus, kentanSyvyys );
	}
	
	private void luoSisaisetPiirturit(Varit varit, Ulottuvuudet ulottuvuudet, Pistelaskija pistelaskija, Ennatyslistaaja ennatyslistaaja) {
		this.reunapiirturi = new Reunapiirturi(this.piste3DHaku);
		
		this.palikkapiirturi = new Palikkapiirturi(this.piste3DHaku, varit.annaVarit());
		
		this.tippuvaPalikkapiirturi = new TippuvaPalikkapiirturi( this.piste3DHaku );
		
		this.statistiikkapiirturi = new Statistiikkapiirturi( this.tietopalkinLeveys, ulottuvuudet, varit, this.peli, pistelaskija, ennatyslistaaja );
	}
	
	/**
	* Asettaa osan kayttajan pelin aikana vaihtamista asetuksista kaynnissa olevaan peliin.
	* 
	* @param leikkauspiste Kuvan leikkauspiste
	* @param varit Varit, joilla eri kerrokset halutaan varitettavan
	*/
	public void asetaUudetAsetukset(int leikkauspiste, Varit varit) {
		this.palikkapiirturi.asetaUudetVarit( varit.annaVarit() );
		this.statistiikkapiirturi.asetaUudetVarit( varit );
		
		this.piste3DHaku.asetaUusiLeikkauspiste(leikkauspiste);
	}
	
	/**
	* Paivittaa nakyman.
	*/
	public void paivita() {
		peli.paivita();
	}
	
	/**
	* Piirtaa kaikki pelin komponentit.
	* 
	* @param g Graphics
	* @param kuilu Pelin kuilu/kentta
	* @param tippuvaPalikka Tippuva palikka
	* @param palojaSisaltavienKerrostenMaara Paloja sisaltavien kerrosten maara
	*/
	public void piirra(Graphics g, Pala[][][] kuilu, TippuvaPalikka tippuvaPalikka, int palojaSisaltavienKerrostenMaara) {
		this.reunapiirturi.piirra(g, kuilu);
		this.palikkapiirturi.piirra(g, kuilu);
		
		if (!peli.onkoTauolla()) {
			this.tippuvaPalikkapiirturi.piirra(g, tippuvaPalikka);
		}
		
		this.statistiikkapiirturi.piirra(g, ikkunanLeveys, ikkunanKorkeus, palojaSisaltavienKerrostenMaara);
	}
	
	public void piirraGameOver(Graphics g, JPanel kuvanKuuntelija, boolean paaseekoListalle) {
		int ikkunanLeveys = kuvanKuuntelija.getWidth();
		int ikkunanKorkeus = kuvanKuuntelija.getHeight();
		
		g.setColor(Color.BLACK);
		Font fontti = new Font("futura", Font.PLAIN, 30);
		g.setFont(fontti);
		g.drawString("Game over", ikkunanLeveys/5*2, ikkunanKorkeus/2);
		fontti = new Font("futura", Font.PLAIN, 14);
		g.setFont(fontti);
		g.drawString("Press any key.", ikkunanLeveys/5*2+20, ikkunanKorkeus/2+30);
		
		if (paaseekoListalle) {
			piirraTato(g, kuvanKuuntelija, ikkunanLeveys, ikkunanKorkeus);
		}
	}
	
	private void piirraTato(Graphics g, JPanel kuvanKuuntelija, int ikkunanLeveys, int ikkunanKorkeus) {
		try {
			URL url = Piirturi.class.getResource("Tato.gif");
			Image tato = Toolkit.getDefaultToolkit().getImage(url);
			
			g.drawImage(tato, ikkunanLeveys/7*4, ikkunanKorkeus/3, kuvanKuuntelija);
		} catch (Exception e) {}
	}
}
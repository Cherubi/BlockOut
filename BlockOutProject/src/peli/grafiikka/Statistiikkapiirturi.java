package peli.grafiikka;

import peli.Peli;
import peli.asetukset.Ulottuvuudet;
import peli.asetukset.Varit;
import peli.ennatyslista.Ennatyslistaaja;
import peli.logiikka.Pistelaskija;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;

public class Statistiikkapiirturi {
	private Peli peli;
	private Pistelaskija pistelaskija;
	
	private ImageObserver kuvanSeuraaja;
	private Image oikeanLaidanKuva, vasemmanLaidanKuva;
	private ArrayList<Color> varit;
	
	private int tietopalkinLeveys;
	private int kuilunLeveys, kuilunKorkeus, kuilunSyvyys;
	private int listaennatys;
	
	/**
	* Piirtaa pelin statistiikan seka muuta mielenkiintoista tietoa.
	* 
	* @param tietopalkinLeveys Tiedoille varatun palkin leveys pelin kummallakin puolella
	* @param ulottuvuudet Pelin kuilun ulottuvuudet
	* @param varit Kuilun kerrosten varit
	* @param peli Peli, joka tuntee pelin tasoon liittyvat tiedot
	* @param pistelaskija Pistelaskija, joka tuntee pisteisiin liittyvat tiedot
	*/
	public Statistiikkapiirturi(int tietopalkinLeveys, Ulottuvuudet ulottuvuudet, Varit varit, Peli peli, Pistelaskija pistelaskija, Ennatyslistaaja ennatyslistaaja) {
		this.peli = peli;
		this.pistelaskija = pistelaskija;
		
		this.kuvanSeuraaja = peli;
		avaaKuvat();
		this.varit = varit.annaVarit();
		
		
		this.tietopalkinLeveys = tietopalkinLeveys;
		
		this.kuilunLeveys = ulottuvuudet.annaLeveys();
		this.kuilunKorkeus = ulottuvuudet.annaKorkeus();
		this.kuilunSyvyys = ulottuvuudet.annaSyvyys();
		
		this.listaennatys = ennatyslistaaja.annaListaennatys( kuilunLeveys, kuilunKorkeus, kuilunSyvyys, peli.annaPalikkasetti() );
	}
	
	private void avaaKuvat() {
		URL url = Statistiikkapiirturi.class.getResource("OikeaLaita.png");
		oikeanLaidanKuva = Toolkit.getDefaultToolkit().getImage(url);
		
		url = Statistiikkapiirturi.class.getResource("VasenLaita.png");
		vasemmanLaidanKuva = Toolkit.getDefaultToolkit().getImage(url);
	}
	
	/**
	* Asettaa pelin kerrosten uudet varit myos kerrosnakymaan missa naytetaan missa kuilun kerroksissa on paloja.
	* 
	* @param varit Uudet varit
	*/
	public void asetaUudetVarit(Varit varit) {
		this.varit = varit.annaVarit();
	}
	
	/**
	* Piirtaa statistiikat.
	* 
	* @param g Graphics
	* @param ikkunanLeveys Kaytettavan ikkunan leveys
	* @param ikkunanKorkeus Kaytettavan ikkunan korkeus
	* @param palojaSisaltavienKerrostenMaara Paloja sisaltavien kerrosten maara
	*/
	public void piirra(Graphics g, int ikkunanLeveys, int ikkunanKorkeus, int palojaSisaltavienKerrostenMaara) {
		piirraVasenPalkki(g, 0, tietopalkinLeveys, ikkunanKorkeus, palojaSisaltavienKerrostenMaara);
		
		piirraOikeaPalkki(g, ikkunanLeveys - tietopalkinLeveys, tietopalkinLeveys, ikkunanKorkeus);
	}
	
	private void piirraVasenPalkki(Graphics g, int x, int leveys, int korkeus, int palojaSisaltavienKerrostenMaara) {
		g.drawImage(vasemmanLaidanKuva, x+6, 0+7, leveys, korkeus-2*6, kuvanSeuraaja);
		
		g.setFont(new Font("futura", Font.PLAIN, 20));
		g.setColor(new Color(0, 255, 128));
		g.drawString(""+peli.annaTaso(), x+6+60, 0+7+75);
		
		piirraPalojaSisaltavatKerrokset(g, x, leveys, korkeus, palojaSisaltavienKerrostenMaara);
	}
	
	private void piirraPalojaSisaltavatKerrokset(Graphics g, int x, int leveys, int korkeus, int kerroksia) {
		int laatanLeveys = 50;
		int laatanKorkeus = 10;
		int laattavali = 8;
		int sisennys = (leveys-laatanLeveys)/2+20;
		int alavara = 40;
		
		piirraKerrosvalikonKehys(g, x, sisennys, korkeus, alavara, laatanKorkeus, laattavali, laatanLeveys);
		
		piirraKerrokset(g, kerroksia, x, sisennys, korkeus, alavara, laatanKorkeus, laattavali, laatanLeveys);
	}
	
	private void piirraKerrosvalikonKehys(Graphics g, int x, int sisennys, int korkeus, int alavara, int laatanKorkeus, int laattavali, int laatanLeveys) {
		int kerroslaattavalikonKorkeus = kuilunSyvyys*(laatanKorkeus+laattavali)-laattavali + 2*10;
		
		g.setColor(new Color(255, 0, 128));
		g.fillRoundRect( x+sisennys-10, korkeus - alavara+laatanKorkeus+10 - kerroslaattavalikonKorkeus, laatanLeveys+2*10, kerroslaattavalikonKorkeus, 5, 5);
		
		g.setColor(Color.BLACK);
		g.fillRoundRect( x+sisennys-10+4, korkeus - alavara+laatanKorkeus+10 - kerroslaattavalikonKorkeus, laatanLeveys+2*10-8, kerroslaattavalikonKorkeus, 5, 5);
	}
	
	private void piirraKerrokset(Graphics g, int kerroksia, int x, int sisennys, int korkeus, int alavara, int laatanKorkeus, int laattavali, int laatanLeveys) {
		int kerros = 0;
		while (true) {
			for (Color vari : varit) {
				if (kerros >= kerroksia) {
					return;
				}
				
				piirraKerroslaatta(g, x+sisennys, korkeus-alavara - kerros*(laatanKorkeus+laattavali), laatanLeveys, laatanKorkeus, vari);
				
				kerros++;
			}
		}
	}
	
	private void piirraKerroslaatta(Graphics g, int x, int y, int leveys, int korkeus, Color vari) {
		g.setColor(new Color(76, 76, 76));
		g.fillRoundRect(x, y, leveys, korkeus, 5, 5);
		
		g.setColor(vari);
		g.fillRoundRect(x+2, y+2, leveys-4, korkeus-4, 5, 5);
	}
	
	private void piirraOikeaPalkki(Graphics g, int x, int leveys, int korkeus) {
		g.drawImage(oikeanLaidanKuva, x-6, 0+7, leveys, korkeus-2*6, kuvanSeuraaja);
		
		g.setFont(new Font("futura", Font.PLAIN, 16));
		g.setColor(new Color(0, 255, 128));
		g.drawString(""+pistelaskija.annaPisteet(), x-6+46, 0+7+77);
		g.drawString(""+peli.annaPelattujenPalojenMaara(), x-6+65, 0+7+151);
		g.drawString(""+listaennatys, x-6+37, 0+7+242);
		
		g.drawString(""+kuilunLeveys, x-6+20, 0+7+370);
		g.drawString(""+kuilunKorkeus, x-6+68, 0+7+345);
		g.drawString(""+kuilunSyvyys, x-6+95, 0+7+402);
		
		String setti = peli.annaPalikkasetti().annaNimi();
		g.drawString(setti.toUpperCase(), x-6+32, 0+7+455);
	}
}
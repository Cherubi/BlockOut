package peli.grafiikka;

import peli.Koordinaatti;

public class Piste3DHaku {
	private int leveysKeskitysKorjaus, korkeusKeskitysKorjaus;
	private int ruudunLeveys;
	private int kentanLeveys, kentanKorkeus, kentanSyvyys, leikkauspiste;
	
	/**
	* Osaa selvittaa ruudukon koordinaattien perusteella ikkunakoordinaatit.
	* 
	* @param ikkunanLeveys Kaytettavan ikkunan leveys
	* @param tietopalkinLeveys Vasemman laidan sisennys koko ikkunassa
	* @param ikkunanKorkeus Kaytettavan ikkunan korkeus
	* @param leikkauspiste Kuvan leikkauspisteen etaisyys, samoissa yksikoissa kuin kentanSyvyys
	* 
	* @param kentanLeveys Kentan leveys ruuduissa
	* @param kentanKorkeus Kentan korkeus ruuduissa
	* @param kentanSyvyys Kentan syvyys ruuduissa
	*/
	public Piste3DHaku(int ikkunanLeveys, int tietopalkinLeveys, int ikkunanKorkeus, int leikkauspiste, int kentanLeveys, int kentanKorkeus, int kentanSyvyys) {
		
		this.kentanLeveys = kentanLeveys;
		this.kentanKorkeus = kentanKorkeus;
		this.kentanSyvyys = kentanSyvyys;
		this.leikkauspiste = leikkauspiste;
		
		selvitaYksittaisenRuudunLeveys(ikkunanLeveys, ikkunanKorkeus);
		
		this.leveysKeskitysKorjaus = (ikkunanLeveys - this.ruudunLeveys*kentanLeveys) / 2 + tietopalkinLeveys;
		this.korkeusKeskitysKorjaus = (ikkunanKorkeus - this.ruudunLeveys*kentanKorkeus) / 2;
	}
	
	private void selvitaYksittaisenRuudunLeveys(int ikkunanLeveys, int ikkunanKorkeus) {
		int leveys = (ikkunanLeveys-40)/this.kentanLeveys;
		int korkeus = (ikkunanKorkeus-40)/this.kentanKorkeus;
		
		this.ruudunLeveys = Math.min(leveys, korkeus);
	}
	
	/**
	* Asettaa kuvalle uuden leikkauspisteen.
	* 
	* leikkauspiste Kuvan leikkauspiste samoissa yksikoissa kuin kentanSyvyys
	*/
	public void asetaUusiLeikkauspiste(int leikkauspiste) {
		this.leikkauspiste = leikkauspiste;
	}
	
	/**
	* Palauttaa annettujen kuilukoordinaattien perusteella ikkunakoordinaatit.
	* 
	* @param x Palan x-koordinaatti kuilussa
	* @param y Palan y-koordinaatti kuilussa
	* @param z Palan z-koordinaatti kuilussa
	* @param kulmaX -1 jos piste Palan vasen kulma, 1 jos Palan oikea kulma
	* @param kulmaY -1 jos piste Palan ylakulma, 1 jos piste Palan alakulma
	* @param kulmaZ -1 jos piste Palan etukulma, 1 jos piste Palan takakulma
	*/
	public Koordinaatti koordinaatit(int x, int y, int z, int kulmaX, int kulmaY, int kulmaZ) {
		int xKoord = xKoordinaatti(x, 0, z, kulmaX, 0, kulmaZ);
		int yKoord = yKoordinaatti(0, y, z, 0, kulmaY, kulmaZ);
		return new Koordinaatti(xKoord, yKoord);
	}
	
	private int xKoordinaatti(double x, double y, double z, int kulmaX, int kulmaY, int kulmaZ) {
		//nama ruuduissa
		double xEtaisyysKeskelta = -(kentanLeveys/2.0 - x);
		if (kulmaX==-1) {
			xEtaisyysKeskelta--;
		}
		double zEtaisyysLeikkauspisteesta = leikkauspiste - z;
		if (kulmaZ==1) {
			zEtaisyysLeikkauspisteesta--;
		}
		
		//nama pikseleina
		double xKoordinaattiKeskelta = zEtaisyysLeikkauspisteesta * xEtaisyysKeskelta * ruudunLeveys / leikkauspiste;
		
		return (int) ( leveysKeskitysKorjaus + (kentanLeveys/2.0 * ruudunLeveys) + xKoordinaattiKeskelta );
	}
	
	private int yKoordinaatti(double x, double y, double z, int kulmaX, int kulmaY, int kulmaZ) {
		//nama ruuduissa
		double yEtaisyysKeskelta = -(kentanKorkeus/2.0 - y);
		if (kulmaY==-1) {
			yEtaisyysKeskelta--;
		}
		double zEtaisyysLeikkauspisteesta = leikkauspiste - z;
		if (kulmaZ==1) {
			zEtaisyysLeikkauspisteesta--;
		}
		
		//nama pikseleina
		double yKoordinaattiKeskelta = zEtaisyysLeikkauspisteesta * yEtaisyysKeskelta * ruudunLeveys / leikkauspiste;
		
		return (int) ( korkeusKeskitysKorjaus + (kentanKorkeus/2.0 * ruudunLeveys) + yKoordinaattiKeskelta );
	}
	
	/**
	* Palauttaa annettujen kuilukoordinaattien perusteella ikkunakoordinaatit. Mahdollistaa myos pisteen pyorityksen kappaleen keskipisteen ympari.
	* 
	* @param x Palan x-koordinaatti kuilussa
	* @param y Palan y-koordinaatti kuilussa
	* @param z Palan z-koordinaatti kuilussa
	* @param
	*/
	public Koordinaatti koordinaatit(double x, double y, double z, int keskiX, int keskiY, int keskiZ, int kulmaIJ, int kulmaJK, int kulmaIK) {
		// nakyy etta toimii, mutta miksi?
		int kulmaX = -1;
		int kulmaY = -1;
		int kulmaZ = -1;
		
		DoubleKoordinaatti xy = kaannaOikeaanKulmaan(x, y, kulmaX, kulmaY, keskiX, keskiY, kulmaIJ);
		x = xy.annaX();
		y = xy.annaY();
		
		DoubleKoordinaatti yz = kaannaOikeaanKulmaan(z, y, kulmaZ, kulmaY, keskiZ, keskiY, kulmaJK);
		z = yz.annaX();
		y = yz.annaY();
		
		DoubleKoordinaatti xz = kaannaOikeaanKulmaan(z, x, kulmaZ, kulmaX, keskiZ, keskiX, kulmaIK);
		z = xz.annaX();
		x = xz.annaY();
		
		int ikkunaX = xKoordinaatti(x, y, z, kulmaX, kulmaY, kulmaZ);
		int ikkunaY = yKoordinaatti(x, y, z, kulmaX, kulmaY, kulmaZ);
		return new Koordinaatti(ikkunaX, ikkunaY);
	}
	
	private DoubleKoordinaatti kaannaOikeaanKulmaan(double x, double y, int kulmaX, int kulmaY, int keskiX, int keskiY, int kulma) {
		double xEtaisyys = x + kulmaX/2.0 - keskiX;
		double yEtaisyys = y + kulmaY/2.0 - keskiY;
		double etaisyysKeskipisteesta = Math.pow(Math.pow( xEtaisyys, 2) + Math.pow( yEtaisyys, 2), 0.5);
		
		double alkuperainenKulma = Math.acos(xEtaisyys / etaisyysKeskipisteesta);
		if (yEtaisyys > 0) {
			alkuperainenKulma *= -1;
		}
		
		double uusiKulma = alkuperainenKulma + 1.0*kulma/180*Math.PI;
		
		double uusiX = Math.cos(uusiKulma)*etaisyysKeskipisteesta + keskiX - kulmaX/2.0;
		double uusiY = -Math.sin(uusiKulma)*etaisyysKeskipisteesta + keskiY - kulmaX/2.0;
		return new DoubleKoordinaatti(uusiX, uusiY);
	}
}
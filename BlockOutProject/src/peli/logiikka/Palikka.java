package peli.logiikka;

import peli.Koordinaatti;

import java.util.ArrayList;
import java.util.HashMap;

public class Palikka {
	private Pala[][][] palikka;
	private int alapisteet, ylapisteet, palojenMaara;
	private HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat;
	
	public Palikka(int alapisteet, int ylapisteet) {
		this.palikka = new Pala[5][5][5];
		palikanTyhjaksiAlustus(this.palikka);
		
		this.alapisteet = alapisteet;
		this.ylapisteet = ylapisteet;
		this.palojenMaara = 0;
		
		this.sarmat = new HashMap<Koordinaatti, ArrayList<Koordinaatti>>();
	}
	
	private void palikanTyhjaksiAlustus(Pala[][][] palikka) {
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				for (int k=0; k<5; k++) {
					palikka[i][j][k] = Pala.TYHJA;
				}
			}
		}
	}
	
	public void lisaaPala(int x, int y, int z) {
		this.palikka[x-1][y-1][z-1] = Pala.TIPPUVA;
		this.palojenMaara++;
		
		Kulmahaku kulmahaku = new Kulmahaku(this.palikka);
		this.sarmat = kulmahaku.haeSarmat();	
	}
	
	/**
	* Kopioi alkuperaisen palikan niin, etta alkuperaista palikkaa ei pyoriteta pelin aikana.
	* 
	* @return Kopioitu palikka
	*/
	public Palikka kopioi() {
		Palikka kopioituPalikka = new Palikka( this.alapisteet, this.ylapisteet );
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				for (int k=0; k<5; k++) {
					if (palikka[i][j][k] == Pala.TIPPUVA) {
						kopioituPalikka.lisaaPala(i+1,j+1,k+1);;
					}
				}
			}
		}
		
		return kopioituPalikka;
	}
	
	/**
	* Antaa Palikan taulukon.
	* 
	* @return Palikka taulukkomuodossa
	*/
	public Pala[][][] annaPalikka() {
		return this.palikka;
	}
	
	/**
	* Antaa Palikkaan liittyvan minimitiputuksen pistelaskukertoimen.
	* 
	* @return pistekerroin
	*/
	public int annaAlapisteet() {
		return this.alapisteet;
	}
	
	/**
	* Antaa Palikkaan liittyvan maksimitiputuksen pistelaskukertoimen.
	* 
	* @return pistekerroin
	*/
	public int annaYlapisteet() {
		return this.ylapisteet;
	}
	
	/**
	* Antaa palikan sisaltamien palojen maaran.
	* 
	* @return Palojen maara
	*/
	public int annaPalojenMaara() {
		return palojenMaara;
	}
	
	/**
	* Antaa palikan piirtoon tarvittavat suorat.
	* 
	* @return Alkupiste-loppupiste parit
	*/
	public HashMap<Koordinaatti, ArrayList<Koordinaatti>> annaSuorat() {
		return this.sarmat;
	}
	
	// Pyorittelyt
	
	/**
	* Pyorittaa jonkun sivutahkoista esille.
	* 
	* @param x Haluttu tahko on vasen (x=-1) tai oikea (x=1), jos muu tahko niin 0
	* @param y Haluttu tahko on ylapuoli (y=-1) tai alapuoli (y=1), jos muu tahko niin 0
	*/
	public void pyoritaSuuntaEsille(int x, int y) {
		Pala[][][] uusi = new Pala[5][5][5];
		palikanTyhjaksiAlustus(uusi);
		
		PalikkaPyorayttaja pyorayttaja = new PalikkaPyorayttaja( this.palikka );
		this.palikka = pyorayttaja.pyoritaSuuntaEsille(uusi, x, y);
		
		Kulmahaku kulmahaku = new Kulmahaku(this.palikka);
		this.sarmat = kulmahaku.haeSarmat();
	}
	
	/**
	* Pyorittaa palikkaa myotapaivaan tai vastapaivaan.
	* 
	* @param myotapaivaan Tieto siita pyoritetaanko myotapaivaan vai vastakkaiseen suuntaan
	*/
	public void pyoritaMyotapaivaan(boolean myotapaivaan) {
		Pala[][][] uusi = new Pala[5][5][5];
		palikanTyhjaksiAlustus(uusi);
		
		PalikkaPyorayttaja pyorayttaja = new PalikkaPyorayttaja( this.palikka );
		this.palikka = pyorayttaja.pyoritaMyotapaivaan(uusi, myotapaivaan);
		
		Kulmahaku kulmahaku = new Kulmahaku(this.palikka);
		this.sarmat = kulmahaku.haeSarmat();
	}
}
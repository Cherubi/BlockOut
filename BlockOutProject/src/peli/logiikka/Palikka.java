package peli.logiikka;

import peli.Koordinaatti;

import java.util.ArrayList;
import java.util.HashMap;

public class Palikka {
	private Pala[][][] palikka;
	private int koko;
	private int alapisteet, ylapisteet, palojenMaara;
	private HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat;
	
	/**
	* Hallinnoi yhden palikan sisaista rakennetta ja palikkaan liittyvia peruspisteita. Voidaan luoda 3x3x3 palikoita.
	* 
	* @param alapisteet Alemmat peruspisteet
	* @param ylapisteet Ylemmat peruspisteet
	*/
	public Palikka(int alapisteet, int ylapisteet) {
		this(5, alapisteet, ylapisteet);
	}
	
	/**
	* Hallinnoi yhden palikan sisaista rakennetta ja palikkaan liittyvia peruspisteita.
	* 
	* @param koko Palikan leveys/korkeus/syvyys
	* @param alapisteet Alemmat peruspisteet
	* @param ylapisteet Ylemmat peruspisteet
	*/
	public Palikka(int koko, int alapisteet, int ylapisteet) {
		if (koko%2 == 0) {
			koko++;
		}
		this.koko = koko;
		this.palikka = new Pala[this.koko][this.koko][this.koko];
		palikanTyhjaksiAlustus(this.palikka);
		
		this.alapisteet = alapisteet;
		this.ylapisteet = ylapisteet;
		this.palojenMaara = 0;
		
		this.sarmat = new HashMap<Koordinaatti, ArrayList<Koordinaatti>>();
	}
	
	private void palikanTyhjaksiAlustus(Pala[][][] palikka) {
		for (int i=0; i<koko; i++) {
			for (int j=0; j<koko; j++) {
				for (int k=0; k<koko; k++) {
					palikka[i][j][k] = Pala.TYHJA;
				}
			}
		}
	}
	
	//012
	//01234
	//0123456
	
	/**
	* Lisaa palikkaan palan.
	* 
	* @param x Palan x-koordinaatti
	* @param y Palan y-koordinaatti
	* @param z Palan z-koordinaatti
	* @return Tieto siita onnistuiko palikan lisaaminen vai ei
	*/
	public boolean lisaaPala(int x, int y, int z) {
		if (this.palikka[x-1][y-1][z-1] == Pala.TIPPUVA) {
			return false;
		}
		
		this.palikka[x-1][y-1][z-1] = Pala.TIPPUVA;
		this.palojenMaara++;
		
		Kulmahaku kulmahaku = new Kulmahaku(this.palikka);
		this.sarmat = kulmahaku.haeSarmat();
		
		return true;
	}
	
	/**
	* Kopioi alkuperaisen palikan niin, etta alkuperaista palikkaa ei pyoriteta pelin aikana.
	* 
	* @return Kopioitu palikka
	*/
	public Palikka kopioi() {
		Palikka kopioituPalikka = new Palikka( koko, this.alapisteet, this.ylapisteet );
		
		for (int i=0; i<koko; i++) {
			for (int j=0; j<koko; j++) {
				for (int k=0; k<koko; k++) {
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
	* Antaa palikan keskipisteen koordinaatin. Keskipisteen koordinaatti on kaikista suunnista samassa kohdassa.
	* 
	* @return Palikan keskipiste
	*/
	public int annaKeskipiste() {
		return (koko-1)/2;
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
		Pala[][][] uusi = new Pala[koko][koko][koko];
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
		Pala[][][] uusi = new Pala[koko][koko][koko];
		palikanTyhjaksiAlustus(uusi);
		
		PalikkaPyorayttaja pyorayttaja = new PalikkaPyorayttaja( this.palikka );
		this.palikka = pyorayttaja.pyoritaMyotapaivaan(uusi, myotapaivaan);
		
		Kulmahaku kulmahaku = new Kulmahaku(this.palikka);
		this.sarmat = kulmahaku.haeSarmat();
	}
}
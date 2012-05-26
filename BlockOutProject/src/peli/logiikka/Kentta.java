package peli.logiikka;

import peli.Peli;
import peli.asetukset.Ulottuvuudet;

public class Kentta {
	private Peli peli;
	private Pistelaskija pistelaskija;
	
	private Pala[][][] kentta;
	private int leveys, korkeus, syvyys;
	
	/**
	* Hallinnoi peli kentan jahmetettyja paloja ja reunoja.
	* 
	* @param peli Pelin hallinnoija, joka vastaa nakyman paivityksesta
	* @param pistelaskija Pelin pistelaskija
	* @param ulottuvuudet Pelin kuilun ulottuvuudet
	*/
	public Kentta(Peli peli, Pistelaskija pistelaskija, Ulottuvuudet ulottuvuudet) {
		alustaKentta(ulottuvuudet);
		
		this.peli = peli;
		this.pistelaskija = pistelaskija;
	}
	
	private void alustaKentta(Ulottuvuudet ulottuvuudet) {
		this.leveys = ulottuvuudet.annaLeveys();
		this.korkeus = ulottuvuudet.annaKorkeus();
		this.syvyys = ulottuvuudet.annaSyvyys();
		
		this.kentta = new Pala[leveys+2][korkeus+2][syvyys+1];
		
		for (int k=0; k<syvyys+1; k++) {
			for (int j=0; j<korkeus+2; j++) {
				for (int i=0; i<leveys+2; i++) {
					if (k == syvyys+1-1 || j==0 || i==0 || j == korkeus+2-1 || i == leveys+2-1) {
						kentta[i][j][k] = Pala.REUNA;
					}
					else {
						kentta[i][j][k] = Pala.TYHJA;
					}
				}
			}
		}
	}
	
	/**
	* Antaa kolmiulotteisen kentan.
	* 
	* @return Kolmiulotteinen Pala-taulukko
	*/
	public Pala[][][] annaKentta() {
		return this.kentta;
	}
	
	/**
	* Antaa kentan leveyden
	* 
	* @return Leveys ruuduissa
	*/
	public int annaLeveys() {
		return this.leveys;
	}
	
	/**
	* Antaa kentan korkeuden
	* 
	* @return Korkeus ruuduissa
	*/
	public int annaKorkeus() {
		return this.korkeus;
	}
	
	/**
	* Antaa kentan syvyyden
	* 
	* @return Syvyys ruuduissa
	*/
	public int annaSyvyys() {
		return this.syvyys;
	}
	
	/**
	* Selvittaa onko kentan etummaisin kohta vapaana.
	* 
	* @return Tieto siita oliko tyhja vai ei
	*/
	public boolean onkoKentanEdustaVapaana() {
		if (kentta[(leveys+2)/2][(korkeus+2)/2][0] == Pala.TYHJA) {
			return true;
		}
		return false;
	}
	
	/**
	* Jahmettaa tippuneen palikan varatuiksi paloiksi pohjalle.
	* 
	* @param annettuPalikka Palikka, joka jahmetetaan
	* @param x Palikan x-koordinaatti
	* @param y Palikan y-koordinaatti
	* @param z Palikan z-koordinaatti
	* @param tiputusKorkeus Matka mita Palikkaa tiputettiin tauotta
	*/
	public void jahmetaPalikka(Palikka annettuPalikka, int x, int y, int z, int tiputusKorkeus) {
		muutaPalatTippuvistaVaratuiksi(annettuPalikka, x, y, z);
		
		int tuhottujaKerroksia = tuhoaValmiitKerrokset();
		boolean tyhja = onkoKenttaTyhja();
		
		pistelaskija.annaPisteitaTiputuksesta(annettuPalikka, tiputusKorkeus, tuhottujaKerroksia, tyhja);
		
		peli.lisaaPelattujenPalojenMaaraa( annettuPalikka.annaPalojenMaara() );
	}
	
	private void muutaPalatTippuvistaVaratuiksi(Palikka annettuPalikka, int x, int y, int z) {
		Pala[][][] palikka = annettuPalikka.annaPalikka();
		
		for (int k=0; k<palikka[0][0].length; k++) {
			for (int j=0; j<palikka[0].length; j++) {
				for (int i=0; i<palikka.length; i++) {
					
					if (palikka[i][j][k]==Pala.TIPPUVA && k-2+z>=0) {
						kentta[i-2+x][j-2+y][k-2+z] = Pala.VARATTU;
					}
					
				}
			}
		}
	}
	
	private int tuhoaValmiitKerrokset() {
		int kerrostenMaara = 0;
		
		for (int k=0; k<=this.syvyys-1; k++) {
			if (kokoKerrosTaytetty(k)) {
				kerrostenMaara++;
				tiputaKerroksia(k);
			}
		}
		
		return kerrostenMaara;
	}
	
	private boolean kokoKerrosTaytetty(int kerros) {
		for (int i=1; i<this.leveys+1; i++) {
			for (int j=1; j<this.korkeus+1; j++) {
				if (kentta[i][j][kerros] != Pala.VARATTU) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void tiputaKerroksia(int tuhottuKerros) {
		for (int k=tuhottuKerros; k>=0; k--) {
			for (int i=1; i<this.leveys+1; i++) {
				for (int j=1; j<this.korkeus+1; j++) {
					
					if (k==0) {
						kentta[i][j][k] = Pala.TYHJA;
					}
					else {
						kentta[i][j][k] = kentta[i][j][k-1];
					}
					
				}
			}
		}
	}
	
	private boolean onkoKenttaTyhja() {
		for (int k=0; k<this.syvyys; k++) {
			for (int j=1; j<this.korkeus+1; j++) {
				for (int i=1; i<this.leveys+1; i++) {
					if (kentta[i][j][k] != Pala.TYHJA) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	* Selvittaako mahtuuko yksittainen pala kenttaan. Jos pala on kuilun sivujen ulkopuolella palauttaa false. Jos pala on kuilun edessa, mutta sivujen sisapuolella palauttaa true.
	* 
	* @param i Palan x-koordinaatti
	* @param j Palan y-koordinaatti
	* @param k Palan z-koordinaatti
	* @return Tieto siita mahtuuko pala kenttaan vai ei
	*/
	public boolean mahtuukoPalaKenttaan(int i, int j, int k) {
		try {
			if( kentta[i][j][k] == Pala.TYHJA ) {
				return true;
			}
			else {
				return false;
			}
		
		//kentŠn edessŠ olevat palaset
		} catch (IndexOutOfBoundsException e) {
			if (i<1 || j<1 || i>this.leveys || j>this.korkeus) {
				return false;
			}
			return true;
		}
	}
	
	/**
	* Kertoo kuinka monessa kerroksessa on paloja.
	* 
	* @return Kerrosten maara
	*/
	public int annaPalojaSisaltavienKerrostenMaara() {
		int kerrostenMaara = 0;
		
		for (int k=this.syvyys-1; k>=0; k--) {
			if (kokoKerrosTyhja(k)) {
				break;
			}
			kerrostenMaara++;
		}
		
		return kerrostenMaara;
	}
	
	private boolean kokoKerrosTyhja(int kerros) {
		for (int i=1; i<this.leveys+1; i++) {
			for (int j=1; j<this.korkeus+1; j++) {
				if (kentta[i][j][kerros] == Pala.VARATTU) {
					return false;
				}
			}
		}
		
		return true;
	}
}
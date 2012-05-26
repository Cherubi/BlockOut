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
	
	//TODO
	
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
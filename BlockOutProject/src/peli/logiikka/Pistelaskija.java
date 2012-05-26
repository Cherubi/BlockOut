package peli.logiikka;

import peli.Peli;

public class Pistelaskija {
	private int pisteet;
	
	private double[] palikkaTasokerroin = {0.066990 , 0.139195 , 0.219800 , 0.308444 , 0.403897 , 0.507822 , 0.619062 , 0.738630 , 0.865802 , 1.000000 , 1.133333};
	private double[] kerrosTasokerroin = {0.096478, 0.163873, 0.242913, 0.328261, 0.422329, 0.518394, 0.630405, 0.747501, 0.867087, 1.000000, 1.131653};
	private double[] kerrosmaarakerroin = {0.0 , 1.000000, 3.703372, 8.104827, 14.188325, 22.144941};
	private double[] syvyyskerroin = {0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 1.557692, 1.367521, 1.217949, 1.100427, 1.000000, 0.918803, 0.852564, 0.788996, 0.737714, 0.691774, 0.651709, 0.614850, 0.583868};
	
	private Peli peli;
	private int kentanSyvyys;
	private Palikkasetti palikkasetti;
	
	/**
	* Hallinnoi pelin pistelaskua.
	* 
	* @param peli Pelin hallinnoija, joka tuntee pelin tason
	* @param kentanSyvyys Kentan syvyys, vaikuttaa pistelaskuun
	* @param setti Pelattava palikkasetti, vaikuttaa pistelaskuun
	*/
	public Pistelaskija(Peli peli, int kentanSyvyys, Palikkasetti setti) {
		this.pisteet = 0;
		
		this.peli = peli;
		this.kentanSyvyys = kentanSyvyys;
		this.palikkasetti = setti;
	}
	
	/**
	* Antaa lasketut pisteet.
	* 
	* @return Pisteet
	*/
	public int annaPisteet() {
		return this.pisteet;
	}
	
	/**
	* Pisteyttaa tiputuksen ja kirjaa saadut pisteet ylos.
	* 
	* @param palikka Tiputettu palikka
	* @param korkeusPohjasta Korkeus mista palikka tiputettiin
	* @param kerrostenMaara Taytettyjen kerrosten maara (jotka siis on myos tuhottu tayttymisen jalkeen)
	* @param kuiluTyhja Tieto siita onko kuilu tyhjentynyt vai ei
	*/
	public void annaPisteitaTiputuksesta(Palikka palikka, int korkeusPohjasta, int kerrostenMaara, boolean kuiluTyhja) {
		double palikkaPisteet = annaPisteitaPalikasta(palikka, korkeusPohjasta);
		double kerrosPisteet = annaPisteitaKerroksista( kerrostenMaara );
		double tyhjennysPisteet = annaPisteitaTyhjennyksesta( kuiluTyhja );
		
		int tiputuksenKokonaisPisteet = (int) (( palikkaPisteet + kerrosPisteet + tyhjennysPisteet ) * syvyyskerroin[kentanSyvyys]);
		if (tiputuksenKokonaisPisteet < 1) {
			tiputuksenKokonaisPisteet = 1;
		}
		
		this.pisteet += tiputuksenKokonaisPisteet;
	}
	
	private double annaPisteitaPalikasta(Palikka palikka, int korkeusPohjasta) {
		double sijainti = 1.0 * korkeusPohjasta / (kentanSyvyys-1);
		double alapisteet = 1.0*palikka.annaAlapisteet();
		double ylapisteet = 1.0*palikka.annaYlapisteet();
		
		return (alapisteet + (ylapisteet-alapisteet)*sijainti) * palikkaTasokerroin[peli.annaTaso()];
	}
	
	private double annaPisteitaKerroksista(int kerrostenMaara) {
		double kerroksenPohjapisteet = 0;
		if (palikkasetti == Palikkasetti.FLAT) {
			kerroksenPohjapisteet = 762.5;
		}
		else if (palikkasetti == Palikkasetti.BASIC) {
			kerroksenPohjapisteet = 875.5;
		}
		else if (palikkasetti == Palikkasetti.EXTENDED) {
			kerroksenPohjapisteet = 2886.25;
		}
		
		return kerroksenPohjapisteet * kerrosTasokerroin[peli.annaTaso()] * kerrosmaarakerroin[kerrostenMaara];
	}
	
	private double annaPisteitaTyhjennyksesta(boolean kuiluTyhja) {
		if (!kuiluTyhja) {
			return 0.0;
		}
		
		return annaPisteitaKerroksista(2);
	}
}
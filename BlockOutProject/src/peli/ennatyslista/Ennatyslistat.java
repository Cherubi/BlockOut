package peli.ennatyslista;

import peli.logiikka.Palikkasetti;

import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInput;
//import java.io.ObjectOutput;

import java.io.FileNotFoundException;

public class Ennatyslistat {
	private ArrayList<Ennatyslista> listat;
	private String listojenOsoite;
	private int listojenPituus;
	
	/**
	* Hallinnoi eri ennatyslistoja
	*/
	public Ennatyslistat(String osoite) {
		this.listojenOsoite = osoite;
		this.listojenPituus = 10;
		avaaListat();
		
		/*lisaaListalle(53892, "jänis", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(52692, "jänis", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(22570, "sari", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(16567, "martin", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(10986, "mauri", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(2951, "JARMO", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(1924, "Laura", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(1175, "Antti", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(784, "turjakas", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(704, "mika 11", 5, 5, 10, Palikkasetti.FLAT);
		*/
		
	}
	
	/**
	* Avaa ennatyslistat tiedostosta.
	*/
	private void avaaListat() {
		this.listat = new ArrayList<Ennatyslista>();
		
		//tarkistaEnnatyslistanOikeellisuus(new Scanner(listojenOsoite));
		
		Scanner lukija = null;
		try {
			lukija = new Scanner(new File(listojenOsoite));
			
			int listojenMaara = Integer.parseInt(lukija.nextLine());
			if (!lukija.nextLine().equals("")) {
				throw new Exception();
			}
			
			lueTiedostostaYksittaisetEnnatyslistat(lukija, listojenMaara);
		} catch (Exception e) {
			this.listat = new ArrayList<Ennatyslista>();
			
			System.out.println("Tiedoston 'ennatyslista' avaus ei onnistunut.");
			e.printStackTrace();
		} finally {
			try {
				lukija.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	* Lukee tiedostosta vuorotellen jokaisen talletetun ennatyslistan.
	* 
	* @param lukija Skanneri, jolle on asetettu luettava tiedosto
	* @param listojenMaara Luettavien ennatyslistojen maara
	*/
	private void lueTiedostostaYksittaisetEnnatyslistat(Scanner lukija, int listojenMaara) throws Exception {
		for (int i = 0; i < listojenMaara; i++) {
			String[] ennatyslistanMaareet = lukija.nextLine().split(" ");
			
			int pienempiLeveys = Integer.parseInt( ennatyslistanMaareet[0] );
			int suurempiLeveys = Integer.parseInt( ennatyslistanMaareet[1] );
			int syvyys = Integer.parseInt( ennatyslistanMaareet[2] );
			String palikkasetti = ennatyslistanMaareet[3];
			
			if (!tallenteenMaareetSopivat(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti)) {
				throw new Exception();
			}
			
			if (!lukija.nextLine().equals(":")) {
				throw new Exception();
			}
					
			Ennatyslista lista = new Ennatyslista(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
			lista.luoUusiEnnatyslista();
			lista.avaaLista(lukija);
			this.listat.add(lista);
		}
	}
	
	/**
	* Selvittaa onko tallenteesta luetut ennatyslistan maareet peliin sopivat.
	* 
	* @param pienempiLeveys Pelin kuilun pienempi leveys ruutuina
	* @param suurempiLeveys Pelin kuilun suurempi leveys ruutuina
	* @param syvyys Pelin kuilun syvyys ruutuina
	* @param palikkasetti Pelin palikkasetti
	* @return Tieto siita oliko maareet sopivia
	*/
	private boolean tallenteenMaareetSopivat(int pienempiLeveys, int suurempiLeveys, int syvyys, String palikkasetti) {
		if (pienempiLeveys > suurempiLeveys) {
			System.out.println("1");
			return false;
		}
		
		if (pienempiLeveys < 3 || pienempiLeveys > 7 || suurempiLeveys < 3 || suurempiLeveys > 7 || syvyys < 6 || syvyys > 18) {
			System.out.println("2");
			return false;
		}
		
		if (palikkasetti.toLowerCase().equals("flat") || palikkasetti.toLowerCase().equals("basic") || palikkasetti.toLowerCase().equals("extended")) {
			return true;
		}
		else {
			System.out.println("3");
			return false;
		}
	}
	
	/**
	* Tallentaa ennatyslistat tiedostoon.
	*/
	private void tallennaListat() {
		FileWriter kirjuri = null;
		try {
			kirjuri = new FileWriter(new File(listojenOsoite));
			
			kirjuri.write(this.listat.size() + "\n\n");
			tallennaKukinEnnatyslista(kirjuri);
			///ObjectOutput out = new ObjectOutputStream( new FileOutputStream(listojenOsoite) );
			//out.writeObject(listat);
			//out.close();
		} catch (Exception e) {
			System.out.println("Tiedoston tallennus ei onnistunut");
			e.printStackTrace();
		} finally {
			try {
				kirjuri.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	* Tallentaa kunkin ennatyslistan.
	* 
	* @param FileWriter, johon on liitetty tiedosto.
	*/
	private void tallennaKukinEnnatyslista(FileWriter kirjuri) throws Exception {
		//TODO tarkista onko tallennettava tiedosto pateva
		
		for (Ennatyslista lista : this.listat) {
			kirjuri.write( lista.annaPienempiLeveys() + " " + lista.annaSuurempiLeveys() + " " + lista.annaSyvyys() + " " + lista.annaPalikkasetti() + "\n");
			
			kirjuri.write(":\n");
			kirjuri.write( lista.tallennaLista() );
		}
		
		kirjuri.write("\n-1 -1 -1 -1");
	}
	
	/**
	* Etsii ennatyslistojen joukosta ennatyslistan, joka tasmaa parametreja. Jos kyseisilla parametreilla ei loydy ennatyslistaa luodaan uusi ennatyslista.
	* 
	* @param pienempiLeveys Pelatun kuilun pienempi leveys
	* @param suurempiLeveys Pelatun kuilun suurempi leveys
	* @param syvyys Pelatun kuilun syvyys
	* @param palikkasetti Pelatun pelin palikkasetti
	* @return Parametreja tasmaava ennatyslista
	*/
	private Ennatyslista etsiOikeaEnnatyslista(int pienempiLeveys, int suurempiLeveys, int syvyys, Palikkasetti palikkasetti) {
		for (Ennatyslista lista : listat) {
			if (lista.onkoKokoJaSetti(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti.annaNimi())) {
				return lista;
			}
		}
		
		Ennatyslista uusiLista = new Ennatyslista(listojenPituus, pienempiLeveys, suurempiLeveys, syvyys, palikkasetti.annaNimi());
		listat.add(uusiLista);
		System.out.println("Luotu uusi ennatyslista talle koolle.");
		uusiLista.luoUusiEnnatyslista();
		return uusiLista;
	}
	
	/**
	* Selvittaa paaseeko tulos listalle, jolla on pelatun pelin parametrit.
	* 
	* @param pisteet Pelatun pelin pisteet
	* @param pienempiLeveys Pelin kuilun pienempi leveys
	* @param suurempiLeveys Pelin kuilun suurempi leveys
	* @param syvyys Pelin kuilun syvyys
	* @param palikkasetti Pelatun pelin palikkasetti
	* @return Tieto siita paaseeko tulos listalle vai ei
	*/
	public boolean paaseekoListalle(int pisteet, int pienempiLeveys, int suurempiLeveys, int syvyys, Palikkasetti palikkasetti) {
		Ennatyslista kyseinenLista = etsiOikeaEnnatyslista(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
		return kyseinenLista.paaseekoListalle(pisteet);
	}
	
	/**
	* Lisaa tuloksen listalle, jolla on pelatun pelin parametrit.
	* 
	* @param pisteet Pelatun pelin pisteet
	* @param nimi Pelaajan nimi
	* @param pienempiLeveys Pelin kuilun pienempi leveys
	* @param suurempiLeveys Pelin kuilun suurempi leveys
	* @param syvyys Pelin kuilun syvyys
	* @param palikkasetti Pelatun pelin palikkasetti
	*/
	public void lisaaListalle(int pisteet, String nimi, int pienempiLeveys, int suurempiLeveys, int syvyys, Palikkasetti palikkasetti) {
		Ennatyslista kyseinenLista = etsiOikeaEnnatyslista(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
		kyseinenLista.lisaaListalle(pisteet, nimi);
		tallennaListat();
	}
	
	/**
	* Antaa listojen pituuden eli kuinka monta tulosta listalle mahtuu.
	* 
	* @return Listan maksimipituus
	*/
	public int annaListanSijojenMaara() {
		return this.listojenPituus;
	}
	
	/**
	* Antaa ennatyslistasta yhden listasijan.
	* 
	* @param monesko Haluttu listasija
	* @param pienempiLeveys Pelin kuilun pienempi leveys
	* @param suurempiLeveys Pelin kuilun suurempi leveys
	* @param syvyys Pelin kuilun syvyys
	* @param palikkasetti Pelatun pelin palikkasetti
	* @return Listasijan tiedot Stringissa. Pistemaara
	*/
	public String annaListanSija(int monesko, int pienempiLeveys, int suurempiLeveys, int syvyys, Palikkasetti palikkasetti) {
		Ennatyslista kyseinenLista = etsiOikeaEnnatyslista(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
		return kyseinenLista.annaListanSija(monesko);
	}
}
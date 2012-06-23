package peli.ennatyslista;

import peli.logiikka.Palikkasetti;

import java.util.ArrayList;
import java.math.BigInteger;

import java.io.IOException;
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
import java.beans.PropertyVetoException;
import java.beans.PropertyChangeEvent;

public class Ennatyslistat {
	private ArrayList<Ennatyslista> listat;
	private String listojenOsoite;
	private int listojenPituus;
	private boolean yleinenListaOlemassa;
	
	/**
	* Hallinnoi eri ennatyslistoja
	*/
	public Ennatyslistat(String osoite) {
		this.listojenOsoite = osoite;
		this.listojenPituus = 10;
		this.yleinenListaOlemassa = false;
		avaaListat();
		
		/*lisaaListalle(53892, "janis", 5, 5, 10, Palikkasetti.FLAT);
		lisaaListalle(52692, "janis", 5, 5, 10, Palikkasetti.FLAT);
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
		
		Scanner lukija = null;
		try {
			if (!tarkistaEnnatyslistanOikeellisuus(listojenOsoite)) {
				throw new Exception();
			}
			
			lukija = new Scanner(new File(listojenOsoite));
			
			int listojenMaara = Integer.parseInt(lukija.nextLine());
			if (!lukija.nextLine().equals("")) {
				throw new Exception();
			}
			
			lueTiedostostaYksittaisetEnnatyslistat(lukija, listojenMaara);
		} catch (IOException ioe) {
			this.listat = new ArrayList<Ennatyslista>();
			
			System.out.println("Tiedoston 'ennatyslista' avaus ei onnistunut.");
			ioe.printStackTrace();
		} catch (NumberFormatException ne) {
			this.listat = new ArrayList<Ennatyslista>();
			
			System.out.println("Tiedostoon 'ennatyslista' oli kajottu. (NumberFormatException)");
		} catch (Exception e) {
			this.listat = new ArrayList<Ennatyslista>();
			
			System.out.println("Tiedoston 'ennatyslista' oikeellisuutta ei voitu tarkistaa tai ennatyslistaan oli kajottu.");
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
				System.out.println("EnnŠtyslistaan oli tallennettu kentŠn tiedot vŠŠrŠssŠ muodossa.");
				throw new Exception();
			}
			
			if (!lukija.nextLine().equals(":")) {
				throw new Exception();
			}
					
			Ennatyslista lista = new Ennatyslista(pienempiLeveys, suurempiLeveys, syvyys, palikkasetti);
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
		if (!yleinenListaOlemassa && pienempiLeveys==-1 && suurempiLeveys==-1 && syvyys==-1 && palikkasetti.toLowerCase().equals("void")) {
			yleinenListaOlemassa = true;
			return true;
		}
		
		if (pienempiLeveys > suurempiLeveys) {
			return false;
		}
		
		if (pienempiLeveys < 3 || pienempiLeveys > 7 || suurempiLeveys < 3 || suurempiLeveys > 7 || syvyys < 6 || syvyys > 18) {
			return false;
		}
		
		if (palikkasetti.toLowerCase().equals("flat") || palikkasetti.toLowerCase().equals("basic") || palikkasetti.toLowerCase().equals("extended")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean tarkistaEnnatyslistanOikeellisuus(String listojenOsoite) throws Exception {
		boolean oikeellisuus = true;
		
		Scanner lukija = null;
		try {
			lukija = new Scanner(new File(listojenOsoite));
			
			tarkistaOikeellisuusRiveittain(lukija, true);
		} catch (IOException ioe) {
			System.out.println("Tiedoston 'ennatyslista' avaus ei onnistunut kun yritettiin selvittŠŠ ennŠtyslistan oikeellisuutta.");
			ioe.printStackTrace();
			
			oikeellisuus = false;
		} catch (PropertyVetoException pve) {
			System.out.println("Tiedostoa 'ennatyslista' ei avattu, koska sen tarkistusrivi ei tŠsmŠnnyt tiedoston muuta sisŠltšŠ.");
			pve.printStackTrace();
			
			oikeellisuus = false;
		} catch (Exception e) {
			System.out.println("Virhe oikeellisuuden tarkastuksessa, johon ei osattu varautua.");
			oikeellisuus = false;
		} finally {
			try {
				lukija.close();
			} catch (Exception e) {}
		}
		
		return oikeellisuus;
	}
	
	private String tarkistaOikeellisuusRiveittain(Scanner lukija, boolean avataan) throws Exception {
		BigInteger summa = BigInteger.ZERO, pistesumma = BigInteger.ZERO, juoksevaPistesumma = BigInteger.ZERO;
		int edellinenMerkki = 'a';
		int edellisestaListanAlusta = 1, moneskoMerkki = 0, moneskoEnnatys = 1;
		
		String rivi = "";
		while (lukija.hasNextLine()) {
			rivi = lukija.nextLine();
			if (!lukija.hasNextLine() && avataan) {
				break;
			}
			
			for (char merkki : rivi.toCharArray()) {
				summa = summa.add( new BigInteger("" + merkki*edellinenMerkki * edellisestaListanAlusta));
				edellinenMerkki = merkki;
			}
			
			if (rivi.equals(":")) {
				edellisestaListanAlusta = 0;
			}
			else if (rivi.split(" ").length == 2) {
				int pistemaara = Integer.parseInt(rivi.split(" ")[0]);
				
				pistesumma = pistesumma.add(new BigInteger("" + pistemaara % moneskoEnnatys));
				
				if (pistemaara != 0) {
					moneskoMerkki = selvitaMoneskoMerkki(moneskoMerkki, pistemaara);
					juoksevaPistesumma = juoksevaPistesumma.add(new BigInteger(("" + pistemaara).charAt(moneskoMerkki) + ""));
				}
				moneskoEnnatys++;
			}
			edellisestaListanAlusta++;
		}
		
		String tarkistusrivi = summa + " " + pistesumma + " " + juoksevaPistesumma;
		System.out.println(tarkistusrivi);
		
		if (avataan) {
			if (!tarkistusrivi.equals(rivi)) {
				throw new PropertyVetoException("Tiedostoon oli kajottu.", new PropertyChangeEvent(this, "tarkistusrivi", tarkistusrivi, rivi));
			}
		}
		return tarkistusrivi;
	}
	
	private int selvitaMoneskoMerkki(int edellinenMoneskoMerkki, int pistemaara) {
		int moneskoMerkki = edellinenMoneskoMerkki + 1;
		
		String pisteet = "" + pistemaara;
		if (pisteet.length()-1 >= moneskoMerkki) {
			return moneskoMerkki;
		}
		else {
			return 0;
		}
	}
	
	private void kirjoitaTarkistusrivi(String listojenOsoite) {
		FileWriter kirjuri = null;
		Scanner lukija = null;
		try {
			lukija = new Scanner(new File(listojenOsoite));
			
			String tarkistusrivi = tarkistaOikeellisuusRiveittain( lukija, false );
			System.out.println("Haluttu tarkistusrivi: " + tarkistusrivi);
			
			kirjuri = new FileWriter(new File(listojenOsoite), true);
			kirjuri.write("\n" + tarkistusrivi);
		} catch (Exception e) {
			System.out.println("Tarkistusrivin kirjoituksessa meni jokin pieleen.");
			e.printStackTrace();
		} finally {
			try {
				kirjuri.close();
			} catch (Exception e) {}
			try {
				lukija.close();
			} catch (Exception e) {}
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
		} catch (Exception e) {
			System.out.println("Tiedoston tallennus ei onnistunut");
			e.printStackTrace();
		} finally {
			try {
				kirjuri.close();
			} catch (Exception e) {}
		}
		
		kirjoitaTarkistusrivi(listojenOsoite);
	}
	
	/**
	* Tallentaa kunkin ennatyslistan.
	* 
	* @param FileWriter, johon on liitetty tiedosto.
	*/
	private void tallennaKukinEnnatyslista(FileWriter kirjuri) throws Exception {
		for (Ennatyslista lista : this.listat) {
			kirjuri.write( lista.annaPienempiLeveys() + " " + lista.annaSuurempiLeveys() + " " + lista.annaSyvyys() + " " + lista.annaPalikkasetti() + "\n");
			
			kirjuri.write(":\n");
			kirjuri.write( lista.tallennaLista() );
		}
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
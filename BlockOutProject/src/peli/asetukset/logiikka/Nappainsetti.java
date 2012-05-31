package peli.asetukset.logiikka;

import java.util.HashMap;
import java.awt.event.KeyEvent;

public class Nappainsetti {
	private HashMap<String, Integer> nappaimet;
	
	/**
	* Hallinnoi peliin liittyvaa nappainsettia
	*/
	public Nappainsetti() {
		nappaimet = new HashMap<String, Integer>();
		
		asetaPerusnappaimet();
	}
	
	/**
	* Asettaa peliin liittyvat perusnappaimet nappainsettiin
	*/
	private void asetaPerusnappaimet() {
		nappaimet.put("ylos", KeyEvent.VK_UP);
		nappaimet.put("alas", KeyEvent.VK_DOWN);
		nappaimet.put("vasemmalle", KeyEvent.VK_LEFT);
		nappaimet.put("oikealle", KeyEvent.VK_RIGHT);
		
		nappaimet.put("ylapuoli esille", KeyEvent.VK_A);
		nappaimet.put("alapuoli esille", KeyEvent.VK_Q);
		nappaimet.put("vasen puoli esille", KeyEvent.VK_W);
		nappaimet.put("oikea puoli esille", KeyEvent.VK_S);
		nappaimet.put("myotapaivaan", KeyEvent.VK_D);
		nappaimet.put("vastapaivaan", KeyEvent.VK_E);
		
		nappaimet.put("tiputa", KeyEvent.VK_SPACE);
		nappaimet.put("tiputa yksi kerros", KeyEvent.VK_MINUS); //mac tunnisti sen plussaksi o.O, sama muualla? TODO
		
		nappaimet.put("tauko", KeyEvent.VK_P);
	}
	
	/**
	* Avaa nappainsetin tallenteesta.
	* 
	* @param tallenne Tallenne
	*/
	public void avaaNappainsetti(String tallenne) {
		String[] osaset = tallenne.split(" ");
		
		this.nappaimet.clear();
		
		try {
			asetaTallenteenSiirtonappaimet(osaset);
			asetaTallenteenKiertonappaimet(osaset);
			
			asetaTiputaNappain( Integer.parseInt(osaset[10]) );
			asetaTiputaYksiKerrosNappain( Integer.parseInt(osaset[11]) );
			
			asetaTaukoNappain( Integer.parseInt(osaset[12]) );
		} catch (Exception e) {
			System.out.println("Tallennettu nappainsetti oli virheellinen. Luodaan uusi. Jos olet juuri paivittanyt ohjelman on tama aivan odotettua eika poista mitaan mita olisit voinut aiemmin tallentaa.");
			this.nappaimet.clear();
			asetaPerusnappaimet();
		}
	}
	
	/**
	* Avaa tallenteesta palikan siirtelyyn liittyvat nappaimet
	* 
	* @param Nappainten KeyEvent-koodit
	*/
	private void asetaTallenteenSiirtonappaimet(String[] nappaimet) {
		asetaYlosNappain( Integer.parseInt(nappaimet[0]) );
		asetaAlasNappain( Integer.parseInt(nappaimet[1]) );
		asetaVasemmalleNappain( Integer.parseInt(nappaimet[2]) );
		asetaOikealleNappain( Integer.parseInt(nappaimet[3]) );
	}
	
	/**
	* Asettaa tallenteesta palikan kiertamiseen liittyvat nappaimet
	* 
	* @param Nappainten KeyEvent-koodit
	*/
	private void asetaTallenteenKiertonappaimet(String[] nappaimet) {
		asetaYlapuoliEsilleNappain( Integer.parseInt(nappaimet[4]) );
		asetaAlapuoliEsilleNappain( Integer.parseInt(nappaimet[5]) );
		asetaVasenPuoliEsilleNappain( Integer.parseInt(nappaimet[6]) );
		asetaOikeaPuoliEsilleNappain( Integer.parseInt(nappaimet[7]) );
			
		asetaKierraMyotapaivaanNappain( Integer.parseInt(nappaimet[8]) );
		asetaKierraVastapaivaanNappain( Integer.parseInt(nappaimet[9]) );
	}
	
	/**
	* Luo nappainsetista tallenteen.
	* 
	* @return Tallenne
	*/
	public String tallennaNappainsetti() {
		String tallenne = annaYlosNappain() + " " + annaAlasNappain() + " " + annaVasemmalleNappain() + " " + annaOikealleNappain() + " ";
		
		tallenne += annaYlapuoliEsilleNappain() + " " + annaAlapuoliEsilleNappain() + " " + annaVasenPuoliEsilleNappain() + " " + annaOikeaPuoliEsilleNappain() + " ";
		
		tallenne += annaKierraMyotapaivaanNappain() + " " + annaKierraVastapaivaanNappain() + " ";
		
		tallenne += annaTiputaNappain() + " " + annaTiputaYksiKerrosNappain() + " ";
		
		tallenne += annaTaukoNappain();
		
		return tallenne;
	}
	
	//*********************************
	//
	//  setterit ja getterit
	//
	//*********************************
	
	/**
	* Antaa ylos-nappaimen KeyEvent-koodin
	* 
	* @return Ylos-nappaimen KeyEvent-koodi
	*/
	public int annaYlosNappain() {
		return this.nappaimet.get("ylos");
	}
	
	/**
	* Asettaa Ylos-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaYlosNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("ylos", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa alas-nappaimen KeyEvent-koodin
	* 
	* @return Alas-nappaimen KeyEvent-koodi
	*/
	public int annaAlasNappain() {
		return this.nappaimet.get("alas");
	}
	
	/**
	* Asettaa Alas-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaAlasNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("alas", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa vasemmalle-nappaimen KeyEvent-koodin
	* 
	* @return Vasemmalle-nappaimen KeyEvent-koodi
	*/
	public int annaVasemmalleNappain() {
		return this.nappaimet.get("vasemmalle");
	}
	
	/**
	* Asettaa Vasemmalle-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaVasemmalleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("vasemmalle", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa oikealle-nappaimen KeyEvent-koodin
	* 
	* @return Oikealle-nappaimen KeyEvent-koodi
	*/
	public int annaOikealleNappain() {
		return this.nappaimet.get("oikealle");
	}
	
	/**
	* Asettaa Oikealle-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaOikealleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("oikealle", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa ylapuoli esille-nappaimen KeyEvent-koodin
	* 
	* @return Ylapuoli esille-nappaimen KeyEvent-koodi
	*/
	public int annaYlapuoliEsilleNappain() {
		return this.nappaimet.get("ylapuoli esille");
	}
	
	/**
	* Asettaa Ylapuoli esille-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaYlapuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("ylapuoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa alapuoli esille-nappaimen KeyEvent-koodin
	* 
	* @return Alapuoli esille-nappaimen KeyEvent-koodi
	*/
	public int annaAlapuoliEsilleNappain() {
		return this.nappaimet.get("alapuoli esille");
	}
	
	/**
	* Asettaa Alapuoli esille-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaAlapuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("alapuoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa vasenpuoli esille-nappaimen KeyEvent-koodin
	* 
	* @return Vasenpuoli esille-nappaimen KeyEvent-koodi
	*/
	public int annaVasenPuoliEsilleNappain() {
		return this.nappaimet.get("vasen puoli esille");
	}
	
	/**
	* Asettaa Vasenpuoli esille-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaVasenPuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("vasen puoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa oikeapuoli esille-nappaimen KeyEvent-koodin
	* 
	* @return Oikeapuoli esille-nappaimen KeyEvent-koodi
	*/
	public int annaOikeaPuoliEsilleNappain() {
		return this.nappaimet.get("oikea puoli esille");
	}
	
	/**
	* Asettaa Oikeapuoli esille-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaOikeaPuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("oikea puoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa kierra myotapaivaan-nappaimen KeyEvent-koodin
	* 
	* @return Kierra myotapaivaan-nappaimen KeyEvent-koodi
	*/
	public int annaKierraMyotapaivaanNappain() {
		return this.nappaimet.get("myotapaivaan");
	}
	
	/**
	* Asettaa Kierra myotapaivaan-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaKierraMyotapaivaanNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("myotapaivaan", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa kierra vastapaivaan-nappaimen KeyEvent-koodin
	* 
	* @return Kierra vastapaivaan-nappaimen KeyEvent-koodi
	*/
	public int annaKierraVastapaivaanNappain() {
		return this.nappaimet.get("vastapaivaan");
	}
	
	/**
	* Asettaa Kierra vastapaivaan-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaKierraVastapaivaanNappain(int uusiKoodi)  {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("vastapaivaan", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa tiputa-nappaimen KeyEvent-koodin
	* 
	* @return Tiputa-nappaimen KeyEvent-koodi
	*/
	public int annaTiputaNappain() {
		return this.nappaimet.get("tiputa");
	}
	
	/**
	* Asettaa Tiputa-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaTiputaNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("tiputa", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa tiputa yksi kerros-nappaimen KeyEvent-koodin
	* 
	* @return Tiputa yksi kerros-nappaimen KeyEvent-koodi
	*/
	public int annaTiputaYksiKerrosNappain() {
		return this.nappaimet.get("tiputa yksi kerros");
	}
	
	/**
	* Asettaa Tiputa yksi kerros-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaTiputaYksiKerrosNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("tiputa yksi kerros", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Antaa tauko-nappaimen KeyEvent-koodin
	* 
	* @return Tauko-nappaimen KeyEvent-koodi
	*/
	public int annaTaukoNappain() {
		return this.nappaimet.get("tauko");
	}
	
	/**
	* Asettaa Tauko-nappaimen KeyEvent-koodin perusteella
	* 
	* @param uusiKoodi Haluttu KeyEvent-koodi
	* @return Tieto siita asetettiinko nappain vai estettiinko asettaminen
	*/
	public boolean asetaTaukoNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("tauko", uusiKoodi);
			return true;
		}
		return false;
	}
	
	/**
	* Selvittaa onko KeyEvent-koodi jo jonkun toisen toiminnon kaytossa.
	* 
	* @param koodi KeyEvent-koodi, jolle etsitaan muita toimintoja
	* @return Tieto siita onko KeyEvent-koodi jo varattu toiselle toiminnolle
	*/
	private boolean onkoNappainVarattu(int koodi) {
		for (int varattuKoodi : this.nappaimet.values()) {
			if (varattuKoodi == koodi) {
				return true;
			}
		}
		
		return false;
	}
}
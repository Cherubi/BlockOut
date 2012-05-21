package peli.asetukset;

import java.util.HashMap;
import java.awt.event.KeyEvent;

public class Nappainsetti {
	private HashMap<String, Integer> nappaimet;
	
	public Nappainsetti() {
		nappaimet = new HashMap<String, Integer>();
		
		asetaPerusnappaimet();
	}
	
	private void asetaPerusnappaimet() {
		nappaimet.put("ylos", KeyEvent.VK_UP);
		nappaimet.put("alas", KeyEvent.VK_DOWN);
		nappaimet.put("vasemmalle", KeyEvent.VK_LEFT);
		nappaimet.put("oikealle", KeyEvent.VK_RIGHT);
		
		nappaimet.put("ylapuoli esille", KeyEvent.VK_Q);
		nappaimet.put("alapuoli esille", KeyEvent.VK_A);
		nappaimet.put("vasen puoli esille", KeyEvent.VK_W);
		nappaimet.put("oikea puoli esille", KeyEvent.VK_S);
		nappaimet.put("myotapaivaan", KeyEvent.VK_D);
		nappaimet.put("vastapaivaan", KeyEvent.VK_E);
		
		nappaimet.put("tiputa", KeyEvent.VK_SPACE);
		nappaimet.put("tiputa yksi kerros", KeyEvent.VK_MINUS);
		
		nappaimet.put("tauko", KeyEvent.VK_P);
	}
	
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
			this.nappaimet.clear();
			asetaPerusnappaimet();
		}
	}
	
	private void asetaTallenteenSiirtonappaimet(String[] nappaimet) {
		asetaYlosNappain( Integer.parseInt(nappaimet[0]) );
		asetaAlasNappain( Integer.parseInt(nappaimet[1]) );
		asetaVasemmalleNappain( Integer.parseInt(nappaimet[2]) );
		asetaOikealleNappain( Integer.parseInt(nappaimet[3]) );
	}
	
	private void asetaTallenteenKiertonappaimet(String[] nappaimet) {
		asetaYlapuoliEsilleNappain( Integer.parseInt(nappaimet[4]) );
		asetaAlapuoliEsilleNappain( Integer.parseInt(nappaimet[5]) );
		asetaVasenPuoliEsilleNappain( Integer.parseInt(nappaimet[6]) );
		asetaOikeaPuoliEsilleNappain( Integer.parseInt(nappaimet[7]) );
			
		asetaKierraMyotapaivaanNappain( Integer.parseInt(nappaimet[8]) );
		asetaKierraVastapaivaanNappain( Integer.parseInt(nappaimet[9]) );
	}
	
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
	
	public int annaYlosNappain() {
		return this.nappaimet.get("ylos");
	}
	
	public boolean asetaYlosNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("ylos", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaAlasNappain() {
		return this.nappaimet.get("alas");
	}
	
	public boolean asetaAlasNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("alas", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaVasemmalleNappain() {
		return this.nappaimet.get("vasemmalle");
	}
	
	public boolean asetaVasemmalleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("vasemmalle", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaOikealleNappain() {
		return this.nappaimet.get("oikealle");
	}
	
	public boolean asetaOikealleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("oikealle", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaYlapuoliEsilleNappain() {
		return this.nappaimet.get("ylapuoli esille");
	}
	
	public boolean asetaYlapuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("ylapuoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaAlapuoliEsilleNappain() {
		return this.nappaimet.get("alapuoli esille");
	}
	
	public boolean asetaAlapuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("alapuoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaVasenPuoliEsilleNappain() {
		return this.nappaimet.get("vasen puoli esille");
	}
	
	public boolean asetaVasenPuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("vasen puoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaOikeaPuoliEsilleNappain() {
		return this.nappaimet.get("oikea puoli esille");
	}
	
	public boolean asetaOikeaPuoliEsilleNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("oikea puoli esille", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaKierraMyotapaivaanNappain() {
		return this.nappaimet.get("myotapaivaan");
	}
	
	public boolean asetaKierraMyotapaivaanNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("myotapaivaan", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaKierraVastapaivaanNappain() {
		return this.nappaimet.get("vastapaivaan");
	}
	
	public boolean asetaKierraVastapaivaanNappain(int uusiKoodi)  {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("vastapaivaan", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaTiputaNappain() {
		return this.nappaimet.get("tiputa");
	}
	
	public boolean asetaTiputaNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("tiputa", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaTiputaYksiKerrosNappain() {
		return this.nappaimet.get("tiputa yksi kerros");
	}
	
	public boolean asetaTiputaYksiKerrosNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("tiputa yksi kerros", uusiKoodi);
			return true;
		}
		return false;
	}
	
	public int annaTaukoNappain() {
		return this.nappaimet.get("tauko");
	}
	
	public boolean asetaTaukoNappain(int uusiKoodi) {
		if (!onkoNappainVarattu(uusiKoodi)) {
			this.nappaimet.put("tauko", uusiKoodi);
			return true;
		}
		return false;
	}
	
	private boolean onkoNappainVarattu(int koodi) {
		for (int varattuKoodi : this.nappaimet.values()) {
			if (varattuKoodi == koodi) {
				return true;
			}
		}
		
		return false;
	}
}
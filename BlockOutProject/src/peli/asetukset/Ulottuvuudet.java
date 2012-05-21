package peli.asetukset;

public class Ulottuvuudet {
	private int ruutujaLeveyssuunnassa, ruutujaKorkeussuunnassa, ruutujaSyvyyssuunnassa;
	private int leikkauspisteenEtaisyys; //mitattu ruuduissa
	
	public Ulottuvuudet() {
		this.ruutujaLeveyssuunnassa = 5;
		this.ruutujaKorkeussuunnassa = 5;
		this.ruutujaSyvyyssuunnassa = 10;
		
		this.leikkauspisteenEtaisyys = 18;
	}
	
	//TODO testit
	public void avaaUlottuvuudet(String tallenne) {
		String[] ominaisuudet = tallenne.split(" ");
		
		try {
			asetaLeveys( Integer.parseInt(ominaisuudet[0]) );
			asetaKorkeus( Integer.parseInt(ominaisuudet[1]) );
			asetaSyvyys( Integer.parseInt(ominaisuudet[2]) );
			
			asetaLeikkauspiste( Integer.parseInt(ominaisuudet[3]) );
		} catch(Exception e) {
			System.out.println("Ulottuvuus-tallennetta avatessa tormattiin ongelmaan.");
			e.printStackTrace();
		}
	}
	
	public String tallennaUlottuvuudet() {
		String tallenne = "";
		
		tallenne += annaLeveys() + " ";
		tallenne += annaKorkeus() + " ";
		tallenne += annaSyvyys() + " ";
		
		tallenne += annaLeikkauspiste();
		
		return tallenne;
	}
	
	public int annaLeveys() {
		return this.ruutujaLeveyssuunnassa;
	}
	
	public int annaMinimiLeveys() {
		return 3;
	}
	
	public int annaMaksimiLeveys() {
		return 7;
	}
	
	public boolean asetaLeveys(int leveys) {
		if (leveys >= annaMinimiLeveys() && leveys <= annaMaksimiLeveys()) {
			this.ruutujaLeveyssuunnassa = leveys;
			return true;
		}
		return false;
	}
	
	public int annaKorkeus() {
		return this.ruutujaKorkeussuunnassa;
	}
	
	public int annaMinimiKorkeus() {
		return 3;
	}
	
	public int annaMaksimiKorkeus() {
		return 7;
	}
	
	public boolean asetaKorkeus(int korkeus) {
		if (korkeus >= annaMinimiKorkeus() && korkeus <= annaMaksimiKorkeus()) {
			this.ruutujaKorkeussuunnassa = korkeus;
			return true;
		}
		return false;
	}
	
	public int annaSyvyys() {
		return this.ruutujaSyvyyssuunnassa;
	}
	
	public int annaMinimiSyvyys() {
		return 6;
	}
	
	public int annaMaksimiSyvyys() {
		return 18;
	}
	
	//TODO testit
	public boolean asetaSyvyys(int syvyys) {
		if (syvyys >= annaMinimiSyvyys() && syvyys <= annaMaksimiSyvyys()) {
			
			this.ruutujaSyvyyssuunnassa = syvyys;
			
			if (this.leikkauspisteenEtaisyys < syvyys) {
				asetaLeikkauspiste(syvyys+4);
			}
			
		}
		return false;
	}
	
	public int annaLeikkauspiste() {
		return this.leikkauspisteenEtaisyys;
	}
	
	public boolean asetaLeikkauspiste(int leikkauspiste) {
		if (leikkauspiste > annaSyvyys()) {
			this.leikkauspisteenEtaisyys = leikkauspiste;
			return true;
		}
		return false;
	}
}
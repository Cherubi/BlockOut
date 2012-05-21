package peli.asetukset;

public class Ulottuvuudet {
	private int ruutujaLeveyssuunnassa, ruutujaKorkeussuunnassa, ruutujaSyvyyssuunnassa;
	private int leikkauspisteenEtaisyys; //mitattu ruuduissa
	
	/**
	* Hallinnoi pelin ulottuvuuksia.
	*/
	public Ulottuvuudet() {
		this.ruutujaLeveyssuunnassa = 5;
		this.ruutujaKorkeussuunnassa = 5;
		this.ruutujaSyvyyssuunnassa = 10;
		
		this.leikkauspisteenEtaisyys = 18;
	}
	
	/**
	* Avaa tallennetut ulottuvuudet.
	* 
	* @param Tallenne
	*/
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
	
	/**
	* Tallentaa ulottuvuudet tiedostoon.
	* 
	* @return Tallenne
	*/
	public String tallennaUlottuvuudet() {
		String tallenne = "";
		
		tallenne += annaLeveys() + " ";
		tallenne += annaKorkeus() + " ";
		tallenne += annaSyvyys() + " ";
		
		tallenne += annaLeikkauspiste();
		
		return tallenne;
	}
	
	/**
	* Antaa kuilun leveyden.
	* 
	* @return Kuilun leveys ruutuina
	*/
	public int annaLeveys() {
		return this.ruutujaLeveyssuunnassa;
	}
	
	/**
	* Antaa kuilun minimileveyden.
	* 
	* @return Minimileveys ruutuina
	*/
	public int annaMinimiLeveys() {
		return 3;
	}
	
	/**
	* Antaa kuilun maksimileveyden.
	* 
	* @return Maksimileveys ruutuina
	*/
	public int annaMaksimiLeveys() {
		return 7;
	}
	
	/**
	* Asettaa kuilun leveyden jos se on sallituissa rajoissa.
	* 
	* @param leveys Haluttu leveys ruutuina
	* @return Tieto siita saatiinko haluttu leveys asetettua
	*/
	public boolean asetaLeveys(int leveys) {
		if (leveys >= annaMinimiLeveys() && leveys <= annaMaksimiLeveys()) {
			this.ruutujaLeveyssuunnassa = leveys;
			return true;
		}
		return false;
	}
	
	/**
	* Antaa kuilun korkeuden.
	* 
	* @return Korkeus ruutuina
	*/
	public int annaKorkeus() {
		return this.ruutujaKorkeussuunnassa;
	}
	
	/**
	* Antaa kuilun minimikorkeuden.
	* 
	* @return Minimikorkeus ruutuina
	*/
	public int annaMinimiKorkeus() {
		return 3;
	}
	
	/**
	* Antaa kuilun maksimikorkeuden.
	* 
	* @return Maksimikorkeus ruutuina
	*/
	public int annaMaksimiKorkeus() {
		return 7;
	}
	
	/**
	* Asettaa kuilun korkeuden jos se on sallituissa rajoissa.
	* 
	* @param korkeus Haluttu korkeus ruutuina
	* @return Tieto siita saatiinko haluttu korkeus asetettua
	*/
	public boolean asetaKorkeus(int korkeus) {
		if (korkeus >= annaMinimiKorkeus() && korkeus <= annaMaksimiKorkeus()) {
			this.ruutujaKorkeussuunnassa = korkeus;
			return true;
		}
		return false;
	}
	
	/**
	* Antaa kuilun syvyyden.
	* 
	* @return Syvyys ruuduissa
	*/
	public int annaSyvyys() {
		return this.ruutujaSyvyyssuunnassa;
	}
	
	/**
	* Antaa kuilun minimisyvyyden.
	* 
	* @return Minisyvyys ruuduissa
	*/
	public int annaMinimiSyvyys() {
		return 6;
	}
	
	/**
	* Antaa kuilun maksimisyvyyden.
	* 
	* @return Maksimisyvyys ruuduissa
	*/
	public int annaMaksimiSyvyys() {
		return 18;
	}
	
	/**
	* Asettaa kuilun syvyyden jos se on sallituissa rajoissa.
	* 
	* @param syvyys Asetettava syvyys ruutuina
	* @return Tieto siita saatiinko syvyys asetettua
	*/
	public boolean asetaSyvyys(int syvyys) {
		if (syvyys >= annaMinimiSyvyys() && syvyys <= annaMaksimiSyvyys()) {
			
			this.ruutujaSyvyyssuunnassa = syvyys;
			
			if (this.leikkauspisteenEtaisyys < syvyys) {
				asetaLeikkauspiste(syvyys+4);
			}
			
		}
		return false;
	}
	
	/**
	* Antaa kuilun seinamien leikkauspisteen.
	* 
	* @return Leikkauspisteen etaisyys ikkunan etuosasta ruuduissa
	*/
	public int annaLeikkauspiste() {
		return this.leikkauspisteenEtaisyys;
	}
	
	/**
	* Asettaa kuilun seinamien leikkauspisteen jos se on kuilun pohjan takana.
	* 
	* @param leikkauspiste Leikkauspisteen etaisyys ikkunan etuosasta ruutuina
	* @return Tieto siita saatiinko leikkauspiste asetettua
	*/
	public boolean asetaLeikkauspiste(int leikkauspiste) {
		if (leikkauspiste > annaSyvyys()) {
			this.leikkauspisteenEtaisyys = leikkauspiste;
			return true;
		}
		return false;
	}
}
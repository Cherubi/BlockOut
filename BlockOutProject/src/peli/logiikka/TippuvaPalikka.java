package peli.logiikka;

import peli.Peli;

public class TippuvaPalikka {
	private Palikka palikka;
	private int x, y, z;
	private int dAlfaXY, dAlfaYZ, dAlfaXZ; // vastapaivaan positiivinen, 0 astetta idassa
	private Kentta kentta;
	private Peli peli;
	
	/**
	* Huolehtii palikan sijainnista ja siirtelysta.
	* 
	* @param palikka Palikka, jonka sijainnista ja siirtelysta huolehditaan
	* @param kentta Kentta, johon palikka on tippumassa ja johon sen tulee mahtua
	* @param peli Peli, joka paivittaa nakymaa ja joka sisaltaa palikan
	*/
	public TippuvaPalikka(Palikka palikka, Kentta kentta, Peli peli) {
		this.palikka = palikka;
		
		this.x = (kentta.annaLeveys()+2) / 2;
		this.y = (kentta.annaKorkeus()+2) / 2;
		this.z = 0;
		
		if (palikka.annaPalikka().length > kentta.annaLeveys() || palikka.annaPalikka()[0].length > kentta.annaKorkeus()) {
		//	pyoritaSuuntaEsille(1, 0);
			System.out.println("liian iso");
		}
		nollaaPyoritykset();
		
		this.kentta = kentta;
		this.peli = peli;
	}
	
	/**
	* Antaa tippuvan palikan keskipisteen x-koordinaatin
	* 
	* @return X-koordinaatti ruuduissa
	*/
	public int annaX() {
		return this.x;
	}
	
	/**
	* Antaa tippuvan palikan keskipisteen y-koordinaatin
	* 
	* @return Y-koordinaatti ruuduissa
	*/
	public int annaY() {
		return this.y;
	}
	
	/**
	* Antaa tippuvan palikan keskipisteen z-koordinaatin
	* 
	* @return Z-koordinaatti ruuduissa
	*/
	public int annaZ() {
		return this.z;
	}
	
	/**
	* Antaa tippuvan palikan ilman tippumisen tai pyorimisen tietoja.
	* 
	* @return Palikka
	*/
	public Palikka annaPalikka() {
		return this.palikka;
	}
	
	//*******************************************
	//
	// Tippuvan palikan siirtaminen
	//
	//*******************************************
	
	/**
	* Siirtaa kayttajan toimien perusteella tippuvaa palikkaa kun se on mahdollista.
	* 
	* @param dx Siirrettava matka x-suunnassa
	* @param dy Siirrettava matka y-suunnassa
	*/
	//vain siirrot, kentta hoitaa tippuvien palikoiden jahmettamisen
	public void siirra(int dx, int dy) {
		siirra(dx, dy, 0);
	}
	
	/**
	* Siirtaa kayttajan tai pelin perusteella tippuvaa palikkaa kun se on mahdollista.
	* 
	* @param dx Siirrettava matka x-suunnassa
	* @param dy Siirrettava matka y-suunnassa
	* @param dz Siirrettava matka z-suunnassa
	* 
	* @return Tieto siita pystyttiinko palikkaa siirtamaan.
	*/
	public boolean siirra(int dx, int dy, int dz) {
		if (!mahtuukoPalikkaKenttaan(dx, dy, dz)) {
			return false;
		}
		
		this.x += dx;
		this.y += dy;
		this.z += dz;
		
		this.peli.paivita();
		
		return true;
	}
	
	/**
	* Tiputtaa palikan VARATTUjen palojen paalle tai pohjalle.
	*/
	public void tiputaPohjalle() {
		int tiputusKorkeus = 0; //TODO vai oikeasta pohjasta eika siita mihin pystyy tippumaan
		
		while (siirra(0, 0, 1)) {
			tiputusKorkeus++;
		}
		
		kentta.jahmetaPalikka(this.palikka, this.x, this.y, this.z, tiputusKorkeus);
		this.peli.haeUusiPalikkaKenttaan();
	}
	
	//*******************************************
	//
	// Tippuvan palikan pyorittaminen
	//
	//*******************************************
	
	/**
	* Kayttajan pyoritettya palikkaa se siirtyy valittomasti minka takia piirtaja on toteutettu toimimaan viiveella. Kulmat dAlfaXY, dAlfaYZ ja dAlfaXZ kertovat kulman minka verran kuva on jaljessa todellisuutta missakin tasossa. Tama metodi oikaisee kuvaa hieman todellisuuden suuntaan aina kun sita kutsutaan.
	*/
	public void oikaisePyoraytysta() {
		//TODO jos lyhyempi matka toiseen suuntaan niin vaihda suunta
		
		if (this.dAlfaXY != 0) {
			this.dAlfaXY += -10*(dAlfaXY/Math.abs(dAlfaXY));
		}
		if (this.dAlfaYZ != 0) {
			this.dAlfaYZ += -10*(dAlfaYZ/Math.abs(dAlfaYZ));
		}
		if (this.dAlfaXZ != 0) {
			this.dAlfaXZ += -10*(dAlfaXZ/Math.abs(dAlfaXZ));
		}
		
		this.peli.paivita();
		if (this.dAlfaXY != 0 || this.dAlfaYZ != 0 || this.dAlfaXZ != 0) {
			new PyoritysAjastin(peli, this, 20);
		}
	}
	
	/**
	* Kertoo minka kulman verran todellisuutta jaljessa kuva on xy-tasossa.
	* 
	* @return Kulma asteina
	*/
	public int annaXYKulma() {
		return this.dAlfaXY;
	}
	
	/**
	* Kertoo minka kulman verran todellisuutta jaljessa kuva on yz-tasossa.
	* 
	* @return Kulma asteina
	*/
	public int annaYZKulma() {
		return this.dAlfaYZ;
	}
	
	/**
	* Kertoo minka kulman verran todellisuutta jaljessa kuva on xz-tasossa.
	* 
	* @return Kulma asteina
	*/
	public int annaXZKulma() {
		return this.dAlfaXZ;
	}
	
	/**
	* Nollaa tippuvan palikan kuvan viiveen.
	*/
	public void nollaaPyoritykset() {
		this.dAlfaXY = 0;
		this.dAlfaYZ = 0;
		this.dAlfaXZ = 0;
	}
	
	/**
	* Pyorittaa palikasta jonkun suunnan tahkon esille.
	* 
	* @param x 1 pyorittaa oikean tahkon esille, -1 vasemman
	* @param y 1 pyorittaa alatahkon esille, -1 ylatahkon
	*/
	public boolean pyoritaSuuntaEsille(int x, int y) {
		this.palikka.pyoritaSuuntaEsille(x, y);
		
		if (mahtuukoPalikkaKenttaan(0, 0, 0)) {
			selvitaPyorityksenAnimointi(x, y);
			return true;
		}
		
		if (siirtelyMahdollistaaPyorityksen()) {
			selvitaPyorityksenAnimointi(x, y);
			return true;
		}
		else {
			this.palikka.pyoritaSuuntaEsille(-x, -y);
			return false;
		}
	}
	
	private void selvitaPyorityksenAnimointi(int x, int y) {
		if (x != 0) {
			dAlfaXZ += 90*x;
		}
		else if (y != 0) {
			dAlfaYZ += -90*y;
		}
		this.peli.paivita();
		
		if (Math.abs(dAlfaXY) + Math.abs(dAlfaYZ) + Math.abs(dAlfaXZ) == 90) {
			new PyoritysAjastin(peli, this, 20);
		}
	}
	
	/**
	* Pyorittaa palikkaa myota-tai vastapaivaan.
	* 
	* @param myotapaivaan Tieto siita pyoritetaanko myotapaivaan vai vastakkaiseen suuntaan
	*/
	public boolean pyoritaMyotapaivaan(boolean myotapaivaan) {
		this.palikka.pyoritaMyotapaivaan(myotapaivaan);
		
		if (mahtuukoPalikkaKenttaan(0, 0, 0)) {
			selvitaPyorityksenAnimointi(myotapaivaan);
			return true;
		}
		
		if (siirtelyMahdollistaaPyorityksen()) {
			selvitaPyorityksenAnimointi(myotapaivaan);
			return true;
		}
		else {
			this.palikka.pyoritaMyotapaivaan(!myotapaivaan);
			return false;
		}
	}
	
	private void selvitaPyorityksenAnimointi(boolean myotapaivaan) {
		if (myotapaivaan) {
			dAlfaXY += 90;
		}
		else {
			dAlfaXY += -90;
		}
		this.peli.paivita();
		
		if (Math.abs(dAlfaXY) + Math.abs(dAlfaYZ) + Math.abs(dAlfaXZ) == 90) {
			new PyoritysAjastin(peli, this, 20);
		}
	}
	
	//*******************************************
	//
	// siirtelyita pyorittamisen mahdollistamiseksi
	//
	//*******************************************
	
	private boolean siirtelyMahdollistaaPyorityksen() {
		if (yksinkertainenSiirtelyMahdollistaaPyorityksen()) {
			return true;
		}
		if (lyhytKulmasiirtelyMahdollistaaPyorityksen()) {
			return true;
		}
		if (kahdenVerranSiirtelyMahdollistaaPyorityksen()) {
			return true;
		}
		return false;
	}
	
	private boolean yksinkertainenSiirtelyMahdollistaaPyorityksen() {
		for (int dx = -1; dx <= 1; dx += 2) {
			if (mahtuukoPalikkaKenttaan(dx, 0, 0)) {
				this.x += dx;
				return true;
			}
		}
		for (int dy = -1; dy <= 1; dy += 2) {
			if (mahtuukoPalikkaKenttaan(0, dy, 0)) {
				this.y += dy;
				return true;
			}
		}
		
		return false;
	}
	
	private boolean lyhytKulmasiirtelyMahdollistaaPyorityksen() {
		for (int dx = -1; dx <= 1; dx += 2) {
			for (int dy = -1; dy <= 1; dy += 2) {
				
				if (mahtuukoPalikkaKenttaan(dx, dy, 0)) {
					this.x += dx;
					this.y += dy;
					return true;
				}
			
			}
		}
		
		return false;
	}
	
	private boolean kahdenVerranSiirtelyMahdollistaaPyorityksen() {
		for (int dx = -2; dx <= 2; dx += 4) {
			if (mahtuukoPalikkaKenttaan(dx, 0, 0)) {
				this.x += dx;
				return true;
			}
		}
		for (int dy = -2; dy <= 2; dy += 4) {
			if (mahtuukoPalikkaKenttaan(0, dy, 0)) {
				this.y += dy;
				return true;
			}
		}
		
		return false;
	}
	
	// kenttaan mahtuminen
	
	private boolean mahtuukoPalikkaKenttaan(int dx, int dy, int dz) {
		Pala[][][] palikka = this.palikka.annaPalikka();
		int keskipiste = this.palikka.annaKeskipiste();
		
		for (int k=0; k<palikka[0][0].length; k++) {
			for (int j=0; j<palikka[0].length; j++) {
				for (int i=0; i<palikka.length; i++) {
					
					if (palikka[i][j][k] == Pala.TIPPUVA) {
						if (!kentta.mahtuukoPalaKenttaan( i-keskipiste+x+dx, j-keskipiste+y+dy, k-keskipiste+z+dz)) {
							return false;
						}
					}
					
				}
			}
		}
		
		return true;
	}
}
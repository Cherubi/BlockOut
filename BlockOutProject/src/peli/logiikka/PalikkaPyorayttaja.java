package peli.logiikka;

public class PalikkaPyorayttaja {
	private Pala[][][] palikka;
	private int koko;
	
	/**
	* Osaa pyoraytella 3D-taulukoita, jotka on rakennettu Paloista.
	* 
	* @param palikka Pyoraytettava palikka
	*/
	public PalikkaPyorayttaja(Pala[][][] palikka) {
		this.palikka = palikka;
		this.koko = palikka.length; //kaikkiin suuntiin saman kokoinen
	}
	
	/**
	* Pyorittaa palikaa niin, etta jokin sivutahkoista tulee esille.
	* 
	* @param uusi Uusi tyhja 3D-taulukko, johon pyoraytetty palikka muodostetaan
	* @param x Jos annettu arvo on -1 pyoritetaan vasen puoli esille, jos se on 1 pyoritetaan oikea puoli esille, jos nolla niin ei pyoriteta y-akselin ympari
	* @param y Jos annettu arvo on -1 pyoritetaan ylapuoli esille, jos jos se on 1 pyoritetaan alapuoli esille, jos nolla niin ei pyoriteta x-akselin ympari
	* 
	* @return Luotu uusi 3D-taulukko
	*/
	public Pala[][][] pyoritaSuuntaEsille(Pala[][][] uusi, int x, int y) {
		if (y == 1) {
			return pyoritaAlapuoliEsille(uusi);
		}
		else if (y == -1) {
			return pyoritaYlapuoliEsille(uusi);
		}
		
		else if (x == -1) {
			return pyoritaVasenPuoliEsille(uusi);
		}
		else if (x == 1) {
			return pyoritaOikeaPuoliEsille(uusi);
		}
		
		return this.palikka;
	}
	
	private Pala[][][] pyoritaYlapuoliEsille(Pala[][][] uusi) {
		//System.out.println("alapuoli");
		for (int k=0; k<koko; k++) {
			
			for (int i=0; i<koko; i++) {
				for (int j=0; j<koko; j++) {
					uusi[i][j][k] = palikka[i][koko-1-k][j];
				}
			}
			
		}
		
		return uusi;
	}
	
	private Pala[][][] pyoritaAlapuoliEsille(Pala[][][] uusi) {
		//System.out.println("ylapuoli");
		for (int k=0; k<koko; k++) {
			
			for (int i=0; i<koko; i++) {
				for (int j=0; j<koko; j++) {
					uusi[i][j][k] = palikka[i][k][koko-1-j];
				}
			}
			
		}
		
		return uusi;
	}
	
	private Pala[][][] pyoritaOikeaPuoliEsille(Pala[][][] uusi) {
		//System.out.println("vasen puoli");
		for (int k=0; k<koko; k++) {
			
			for (int i=0; i<koko; i++) {
				for (int j=0; j<koko; j++) {
					uusi[i][j][k] = palikka[koko-1-k][j][i];
				}
			}
			
		}
		
		return uusi;
	}
	
	private Pala[][][] pyoritaVasenPuoliEsille(Pala[][][] uusi) {
		//System.out.println("oikea puoli");
		for (int k=0; k<koko; k++) {
			
			for (int i=0; i<koko; i++) {
				for (int j=0; j<koko; j++) {
					uusi[i][j][k] = palikka[k][j][koko-1-i];
				}
			}
			
		}
		
		return uusi;
	}
	
	/**
	* Pyorittaa palikkaa myota-ja vastapaivaan.
	* 
	* @param uusi Uusi 3D-taulukko, johon pyoraytetty palikka muodostetaan
	* @param myotapaivaan Tieto siita pyoritetaanko myotapaivaan vai vastakkaiseen suuntaan
	* 
	* @return Luotu uusi 3D-taulukko
	*/
	public Pala[][][] pyoritaMyotapaivaan(Pala[][][] uusi, boolean myotapaivaan) {
		if (myotapaivaan) {
			return pyoritaMyotapaivaan(uusi);
		}
		else {
			return pyoritaVastapaivaan(uusi);
		}
	}
	
	private Pala[][][] pyoritaVastapaivaan(Pala[][][] uusi) {
		//System.out.println("myotapaivaan");
		for (int k=0; k<koko; k++) {
			
			for (int i=0; i<koko; i++) {
				for (int j=0; j<koko; j++) {
					uusi[i][j][k] = palikka[koko-1-j][i][k];
				}
			}
			
		}
		
		return uusi;
	}
	
	private Pala[][][] pyoritaMyotapaivaan(Pala[][][] uusi) {
		//System.out.println("vastapaivaan");
		for (int k=0; k<koko; k++) {
			
			for (int i=0; i<koko; i++) {
				for (int j=0; j<koko; j++) {
					uusi[i][j][k] = palikka[j][koko-1-i][k];
				}
			}
			
		}
		
		return uusi;
	}
}
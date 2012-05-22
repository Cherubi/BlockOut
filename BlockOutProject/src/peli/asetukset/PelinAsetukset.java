package peli.asetukset;

import kayttoliittyma.BlockOut;
import valmiskomponentit.Ikkuna;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PelinAsetukset extends Ikkuna {
	private BlockOut kayttis;
	private String tallenneosoite;
	private boolean tallenteenAvausKaynnissa;
	
	private ArrayList<Asetukset> tallennetutAsetukset;
	private int valittuAsetus;
	
	private TalletetutAsetuksetPaneli talletetutPaneli;
	private AsetuksetPaneli asetuksetPaneli;
	private NappainKuuntelija nappainKuuntelija;
	
	/**
	* Hallinnoi pelin asetuksia.
	* 
	* @param kayttis Kayttoliittyma, johon asetukset on kiinnitetty
	* @param tallenneosoite Tallennetiedoston osoite
	*/
	public PelinAsetukset(BlockOut kayttis, String tallenneosoite) {
		this.kayttis = kayttis;
		this.tallenneosoite = tallenneosoite;
		
		avaaTallennokset();
		
		luoKomponentit();
	}
	
	/**
	* Avaa tallennokset tiedostosta. Avauksen ollessa kaynnissa ohjelma ei tallenna asetuksia aina kun jotain arvoa asetetaan.
	*/
	private void avaaTallennokset() {
		this.tallennetutAsetukset = new ArrayList<Asetukset>();
		this.valittuAsetus = 0;
		tallenteenAvausKaynnissa = true;
		
		Scanner lukija = null;
		try {
			lukija = new Scanner(new File(tallenneosoite));
			lueTallennokset(lukija);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Tiedoston: asetukset avaaminen ei onnistunut. (Olemassaolevan tiedoston tulee olla samassa kansiossa jar:n kanssa.) Luodaan uusi, joka tallennetaan kun muutoksia tehdŠŠn.");
			alustaAsetukset();
		} finally {
			try {
				lukija.close();
			} catch (Exception e) {}
		}
		
		tallenteenAvausKaynnissa = false;
	}
	
	/**
	* Alustaa asetukset.
	* 
	* @param
	* @return
	*/
	private void alustaAsetukset() {
		Asetukset perusasetukset = new Asetukset(this);
		perusasetukset.asetaAsetustenNimi("Perusasetukset");
		tallennetutAsetukset.add(perusasetukset);
	}
	
	/**
	* Lukee tallennetut asetukset skannerilla.
	* 
	* @param lukija Skanneri, johon on asetettu luettava tiedosto
	*/
	private void lueTallennokset(Scanner lukija) throws Exception {
		int tallennettujaAsetuksia = 0;
		
		if (lukija.hasNextLine()) {
			String[] tallennostenTila = lukija.nextLine().split("/");
			
			this.valittuAsetus = Integer.parseInt(tallennostenTila[0]);
			tallennettujaAsetuksia = Integer.parseInt(tallennostenTila[1]);
		}
		
		for (int i=1; i<=tallennettujaAsetuksia; i++) {
			Asetukset asetukset = new Asetukset(this);
			asetukset.avaaAsetukset(lukija);
			
			this.tallennetutAsetukset.add(asetukset);
		}
	}
	
	/**
	* Tallentaa asetuksissa tehdyt muutokset tiedostoon.
	*/
	public void tallennaTallennokset() {
		if (tallenteenAvausKaynnissa) {
			return;
		}
		
		FileWriter kirjuri = null;
		try {
			kirjuri = new FileWriter(new File(tallenneosoite));
			kirjoitaTallennokset(kirjuri);
		} catch (Exception e) {
			System.out.println("Asetusten tallennuksessa ilmeni ongelma.");
			e.printStackTrace();
		} finally {
			try {
				kirjuri.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	* Kirjoittaa asetukset FileWriterilla
	* 
	* @param kirjuri FileWriter, joka on kiinnitetty luotavaan tiedostoon
	*/
	private void kirjoitaTallennokset(FileWriter kirjuri) throws Exception {
		int tallennettujaAsetuksia = tallennetutAsetukset.size();
		
		kirjuri.write(valittuAsetus + "/" + tallennettujaAsetuksia + "\n");
		
		for (Asetukset asetus : tallennetutAsetukset) {
			asetus.tallennaAsetukset(kirjuri);
		}
	}
	
	/**
	* Luo PelinAsetukset JPanelin komponentit.
	*/
	public void luoKomponentit() {
		this.setLayout(new BorderLayout());
		
		luoOtsikko();
		
		this.talletetutPaneli = new TalletetutAsetuksetPaneli( this, tallennetutAsetukset, valittuAsetus );
		this.add(talletetutPaneli, BorderLayout.WEST);
		
		this.asetuksetPaneli = new AsetuksetPaneli( talletetutPaneli, annaValitutAsetukset(), valittuAsetus != 0 );
		this.asetuksetPaneli.setOpaque(false);
		this.add(asetuksetPaneli, BorderLayout.CENTER);
		
		this.nappainKuuntelija = new NappainKuuntelija(asetuksetPaneli);
		this.addKeyListener(this.nappainKuuntelija);
	}
	
	/**
	* Luo PelinAsetukset JPanelin otsikko-komponentin.
	*/
	public void luoOtsikko() {
		JLabel otsikko = new JLabel("Asetukset", JLabel.CENTER);
		String fontinNimi = haeFontti();
		int fontinKoko = 48;
		if (fontinNimi.toLowerCase().contains("comic")) {
			fontinKoko = 40;
		}
		otsikko.setFont( new Font(fontinNimi, Font.PLAIN, fontinKoko) );
		otsikko.setForeground(Color.WHITE);
		this.add(otsikko, BorderLayout.NORTH);
	}
	
	/**
	* Vaihtaa valittua asetusta. (Asetuksia voi olla useita mm. eri kayttajille.)
	* 
	* @param id Valittavan asetukset id/jarjestysnumero
	*/
	public void vaihdaValittuaAsetusta(int id) {
		if (id == -1) {
			luoUusiAsetus();
		}
		else {
			valittuAsetus = id;
		}
		tallennaTallennokset();
		
		vaihdaAsetuksetPanelia();
		this.revalidate();
	}
	
	/**
	* Luo uuden asetuksen asetus-listaan.
	*/
	private void luoUusiAsetus() {
		tallennetutAsetukset.add(new Asetukset(this));
		valittuAsetus = annaAsetustenMaara() - 1;
	}
	
	/**
	* Vaihtaa nakymassa asetuksetPanelin valittua asetusta vastaavaksi.
	*/
	private void vaihdaAsetuksetPanelia() {
		this.asetuksetPaneli = new AsetuksetPaneli( talletetutPaneli, annaValitutAsetukset(), valittuAsetus != 0 );
		this.asetuksetPaneli.setOpaque(false);
		
		this.remove(2);
		this.add(asetuksetPaneli, BorderLayout.CENTER);
		
		this.nappainKuuntelija.asetaUusiAsetuksetPaneli( asetuksetPaneli );
	}
	
	/**
	* Antaa talletettujen asetusten maaran.
	* @return Talletettujen asetusten maara
	*/
	public int annaAsetustenMaara() {
		return tallennetutAsetukset.size();
	}
	
	/**
	* Antaa silla hetkella valitut asetukset.
	* 
	* @return Silla hetkella valitut asetukset
	*/
	public Asetukset annaValitutAsetukset() {
		return tallennetutAsetukset.get(valittuAsetus);
	}
	
	/**
	* Antaa valitun asetuksen id:n / jarjestysnumeron.
	* 
	* @return Asetuksen id
	*/
	public int annaValittuAsetus() {
		return valittuAsetus;
	}
	
	/**
	* Antaa varin asetuksille jos Varipaletti ei nyt sitten anna sita suoraan vareille.
	* 
	* @param vari, joka halutaan antaa asetuksille
	*/
	//public void annaVariAsetuksille(Color vari) {
	//	//TODO
	//}
	
	/**
	* Etsii fontin, jonka tietokoneen kayttojarjestelma ymmartaa.
	* 
	* @return Fontin nimi
	*/
	private String haeFontti() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = environment.getAllFonts();
 
 		String[] halututFontit = {"Herculanum", "ComicSansMS", "Comic Sans MS", "ArialMT", "Arial"};
 		for (String haluttuFontti : halututFontit) {
 			//System.out.println(haluttuFontti);
 			for (Font font : fonts) {
	 			//System.out.println("-" + font.getFontName());
	 			if (font.getFontName().equals(haluttuFontti)) {
	 				return font.getFontName();
	 			}
	 		}
		}
		
		for (Font font : fonts) {
			if (font.getFontName().toLowerCase().contains("arial")) {
				return font.getFontName();
			}
		}
		
		return "futura";
	}
	
	/**
	* 
	* 
	* @param
	* @return
	*/
	
	/**
	* 
	* 
	* @param
	* @return
	*/
}
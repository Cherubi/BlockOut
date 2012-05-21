/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.asetukset;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Usagi-chan
 */
public class UlottuvuudetTest {
	private Ulottuvuudet ulottuvuudet;
	
	/**
	 * Testaa ulottuvuuksia.
	 */
	public UlottuvuudetTest() {
		ulottuvuudet = new Ulottuvuudet();
	}

	/*@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}*/
	
	
	
	/**
	 * Käyttäjä ei saa voida asettaa kuilun leveydeksi liian pientä arvoa.
	 * Arvo pysyy sinä mikä se ennen asetusta oli.
	 */
	@Test
	public void liianPientaLeveyttaEiVoiAsettaa() {
		int alkuperainenLeveys = ulottuvuudet.annaLeveys();
		ulottuvuudet.asetaLeveys(ulottuvuudet.annaMinimiLeveys()-1);
		assertTrue("Ulottuvuudet sallii liian pienen leveyden asettamisen.",  ulottuvuudet.annaLeveys() == alkuperainenLeveys);
	}
	
	/**
	 * Käyttäjä ei saa voida asettaa kuilun leveydeksi liian suurta arvoa.
	 * Arvo pysyy sinä mikä se ennen asetusta oli.
	 */
	@Test
	public void liianSuurtaLeveyttaEiVoiAsettaa() {
		int alkuperainenLeveys = ulottuvuudet.annaLeveys();
		ulottuvuudet.asetaLeveys(ulottuvuudet.annaMaksimiLeveys()+1);
		assertTrue("Ulottuvuudet sallii liian suuren leveyden asettamisen.",  ulottuvuudet.annaLeveys() == alkuperainenLeveys);
	}
	
	/**
	 * Käyttäjän tulee voida asettaa sallittu kuilun leveys.
	 */
	@Test
	public void sopivanLeveydenVoiAsettaa() {
		for (int leveys = ulottuvuudet.annaMinimiLeveys(); leveys <= ulottuvuudet.annaMaksimiLeveys(); leveys++) {
			ulottuvuudet.asetaLeveys(leveys);
			assertTrue("Ulottuvuudet ei salli sallittavan leveyden asettamista.",  ulottuvuudet.annaLeveys() == leveys);
		}
	}
	
	/**
	 * Käyttäjän ei tule voida asettaa liian pientä kuilun korkeutta.
	 * Arvo pysyy sinä mikä se ennen asetusta oli.
	 */
	@Test
	public void liianPientaKorkeuttaEiVoiAsettaa() {
		int alkuperainenKorkeus = ulottuvuudet.annaKorkeus();
		ulottuvuudet.asetaKorkeus(ulottuvuudet.annaMinimiKorkeus()-1);
		assertTrue("Ulottuvuudet sallii liian pienen korkeuden asettamisen.",  ulottuvuudet.annaKorkeus() == alkuperainenKorkeus);
	}
	
	/**
	 * Käyttäjän ei tule voida asettaa liian suurta kuilun korkeutta.
	 * Arvo pysyy sinä mikä se ennen asetusta oli.
	 */
	@Test
	public void liianSuurtaKorkeuttaEiVoiAsettaa() {
		int alkuperainenKorkeus = ulottuvuudet.annaKorkeus();
		ulottuvuudet.asetaKorkeus(ulottuvuudet.annaMaksimiKorkeus()+1);
		assertTrue("Ulottuvuudet sallii liian suuren korkeuden asettamisen.",  ulottuvuudet.annaKorkeus() == alkuperainenKorkeus);
	}
	
	/**
	 * Käyttäjän tulee voida asettaa sallittu kuilun korkeus.
	 */
	@Test
	public void sopivanKorkeudenVoiAsettaa() {
		for (int korkeus = ulottuvuudet.annaMinimiKorkeus(); korkeus <= ulottuvuudet.annaMaksimiKorkeus(); korkeus++) {
			ulottuvuudet.asetaKorkeus(korkeus);
			assertTrue("Ulottuvuudet ei salli sallittavan korkeuden asettamista.",  ulottuvuudet.annaKorkeus() == korkeus);
		}
	}
	
	/**
	 * Käyttäjän ei tule voida asettaa liian pientä kuilun syvyyttä.
	 */
	@Test
	public void liianPientaSyvyyttaEiVoiAsettaa() {
		int alkuperainenSyvyys = ulottuvuudet.annaSyvyys();
		ulottuvuudet.asetaSyvyys(ulottuvuudet.annaMinimiSyvyys()-1);
		assertTrue("Ulottuvuudet sallii liian pienen syvyyden asettamisen.",  ulottuvuudet.annaSyvyys() == alkuperainenSyvyys);
	}
	
	/**
	 * Käyttäjän ei tule voida asettaa liian suurta kuilun syvyyttä.
	 * Arvo pysyy sinä mikä se ennen asetusta oli.
	 */
	@Test
	public void liianSuurtaSyvyyttaEiVoiAsettaa() {
		int alkuperainenSyvyys = ulottuvuudet.annaSyvyys();
		ulottuvuudet.asetaSyvyys(ulottuvuudet.annaMaksimiSyvyys()+1);
		assertTrue("Ulottuvuudet sallii liian suuren syvyyden asettamisen.",  ulottuvuudet.annaSyvyys() == alkuperainenSyvyys);
	}
	
	/**
	 * Käyttäjän tulee voida asettaa sallittu kuilun syvyys.
	 */
	@Test
	public void sopivanSyvyydenVoiAsettaa() {
		for (int syvyys = ulottuvuudet.annaMinimiSyvyys(); syvyys <= ulottuvuudet.annaMaksimiSyvyys(); syvyys++) {
			ulottuvuudet.asetaSyvyys(syvyys);
			assertTrue("Ulottuvuudet ei salli sallittavan leveyden asettamista.",  ulottuvuudet.annaSyvyys() == syvyys);
		}
	}
	
	/**
	 * Käyttäjän ei tule voida asettaa kuilun seinien leikkauspistettä kuilun pohjan etupuolelle.
	 */
	@Test
	public void leikkauspistettaEiVoiAsettaaKuilunPohjanEtupuolelle() {
		int kuilunSyvyys = ulottuvuudet.annaMaksimiSyvyys();
		ulottuvuudet.asetaSyvyys( kuilunSyvyys );
		assertFalse("Leikkauspistettä ei saisi voida asettaa kuilun pohjan etupuolelle.", ulottuvuudet.asetaLeikkauspiste( kuilunSyvyys-5 ));
	}
	
	/**
	 * Käyttäjän ei tule voida asettaa kuilun seinien leikkauspistettä kuilun pohjan kohdalle.
	 */
	@Test
	public void leikkauspistetaEiVoiAsettaaKuilunPohjanKohdalle() {
		int kuilunSyvyys = ulottuvuudet.annaMaksimiSyvyys();
		ulottuvuudet.asetaSyvyys( kuilunSyvyys );
		assertFalse("Leikkauspistettä ei saisi voida asettaa kuilun pohjan kohdalle.", ulottuvuudet.asetaLeikkauspiste( kuilunSyvyys ));
	}
	
	/**
	 * Käyttäjän tulee voida asettaa kuilun seinien leikkauspiste kuilun pohjan taakse.
	 */
	@Test
	public void leikkauspisteenVoiAsettaaKuilunPohjanTaakse() {
		int kuilunSyvyys = ulottuvuudet.annaSyvyys();
		assertTrue("Leikkauspisteen pitäisi pystyä asettamaan kuilun pohjan taakse.", ulottuvuudet.asetaLeikkauspiste(kuilunSyvyys+1));
	}
	
	/**
	 * Käyttäjän siirrettyä kuilun pohjaa leikkauspisteen taakse, leikkauspisteen tulee siirtyä automaattisesti kuilun pohjan taakse.
	 */
	@Test
	public void leikkauspisteEtaantyyKunSyvyyttaKasvatetaanLeikkauspistettaKauemmas() {
		ulottuvuudet.asetaLeikkauspiste( ulottuvuudet.annaSyvyys()+1 );
		ulottuvuudet.asetaSyvyys( ulottuvuudet.annaSyvyys()+2 );
		assertTrue("Leikkauspisteen tulisi siirtyä kuilun pohjan taakse kun kuilua syvennetään.", ulottuvuudet.annaLeikkauspiste() > ulottuvuudet.annaSyvyys());
	}
	
	/**
	 * Ulottuvuustallenne tulee olla avattavissa ja sen tulee asettaa ulottuvuudet sen mukaiseksi.
	 */
	@Test
	public void patevaTallenneOsataanAvata() {
		int leveys = ulottuvuudet.annaMinimiLeveys();
		int korkeus = ulottuvuudet.annaMinimiKorkeus();
		int syvyys = ulottuvuudet.annaMinimiSyvyys();
		int leikkauspiste = syvyys + 4;
		
		String testitallenne = leveys + " " + korkeus + " " + syvyys + " " + leikkauspiste;
		ulottuvuudet.avaaUlottuvuudet(testitallenne);
		
		assertTrue("Tallennuksen avauksen tulisi muuttaa kuilun leveyttä tallennuksen mukaiseksi.", ulottuvuudet.annaLeveys() == leveys);
		assertTrue("Tallennuksen avauksen tulisi muuttaa kuilun korkeutta tallennuksen mukaiseksi.", ulottuvuudet.annaKorkeus() == korkeus);
		assertTrue("Tallennuksen avauksen tulisi muuttaa kuilun syvyyttä tallennuksen mukaiseksi.", ulottuvuudet.annaSyvyys() == syvyys);
		
		assertTrue("Tallennuksen avauksen tulisi muuttaa kuvan leikkauspistettä tallennuksen mukaiseksi.", ulottuvuudet.annaLeikkauspiste() == leikkauspiste);
	}
	
	/**
	 * Tallennetta avattaessa ulottuvuuksia ei tule asettaa liian suuriksi tai liian pieniksi. (Kullakin ulottuvuuden parametrilla on omat ylä- ja alarajansa.)
	 */
	@Test
	public void epapatevaTallenneOsataanAvata() {
		int leveys = ulottuvuudet.annaMinimiLeveys() - 1;
		int korkeus = ulottuvuudet.annaMaksimiKorkeus() + 1;
		int syvyys = ulottuvuudet.annaMinimiSyvyys() - 1;
		int leikkauspiste = syvyys-1;
		
		String testitallenne = leveys + " " + korkeus + " " + syvyys + " " + leikkauspiste;
		ulottuvuudet.avaaUlottuvuudet(testitallenne);
		
		assertTrue("Tallennuksen missä on epäpätevä leveys ei pitäisi muuttaa leveyttä.", ulottuvuudet.annaLeveys() == 5);
		assertTrue("Tallennuksen missä on epäpätevä korkeus ei pitäisi muuttaa korkeutta.", ulottuvuudet.annaKorkeus() == 5);
		assertTrue("Tallennuksen missä on epäpätevä syvyys ei pitäisi muuttaa syvyyttä.", ulottuvuudet.annaSyvyys() == 10);
		assertTrue("Tallennuksen missä on epäpätevä leikkauspiste ei pitäisi muuttaa leikkauspistettä.", ulottuvuudet.annaLeikkauspiste() == 18);
	}
	
	/**
	 * Tallenne, joka sisältää huonossa formaatissa olevaa tietoa, ei saa kaataa ohjelmaa vaan siihen tulee varautua.
	 */
	@Test
	public void taysinEpapatevaanTallenteeseenOsataanVarautua() {
		try {
			ulottuvuudet.avaaUlottuvuudet("huono tiedosto");
		} catch (Exception e) {
			fail("Epäpäteviin Ulottuvuus-tallenteisiin tulee varautua.");
		}
	}
	
	/**
	 * Tallenteisiin, joista puuttuu lukuarvoja, tulee varautua eivätkä ne saa kaataa ohjelmaa.
	 */
	@Test
	public void hiemanEpapatevaanTallenteeseenOsataanVarautua() {
		try {
			ulottuvuudet.avaaUlottuvuudet("3 4 5");
		} catch (Exception e) {
			fail("Ulottuvuus-tallenteisiin, joista puuttuu määritteitä tulee varautua.");
		}
	}
	
	/**
	 * Ohjelmassa luotavan tallenteen muotoilun tulee olla oikea.
	 */
	@Test
	public void tallenneOsataanLuoda() {
		ulottuvuudet.asetaLeveys(4);
		ulottuvuudet.asetaKorkeus(4);
		ulottuvuudet.asetaSyvyys(8);
		
		ulottuvuudet.asetaLeikkauspiste(12);
		
		assertTrue("Luotavan tallenteen muotoilu on väärä. Sen tuli olla \"4 4 8 12\", mutta se oli " + ulottuvuudet.tallennaUlottuvuudet(), ulottuvuudet.tallennaUlottuvuudet().equals("4 4 8 12"));
	}
}

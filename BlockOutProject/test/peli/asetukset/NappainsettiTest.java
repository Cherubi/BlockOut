package peli.asetukset;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import peli.asetukset.logiikka.Nappainsetti;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Usagi-chan
 */
public class NappainsettiTest {
	private Nappainsetti nappainsetti;
	
	public NappainsettiTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		this.nappainsetti = new Nappainsetti();
	}
	
	@After
	public void tearDown() {
	}
	
	
	
	/**
	 * Testataan onnistuuko tallennetun nappainsetin avaaminen.
	 */
	@Test
	public void nappainsetinAvausOnnistuu() {
		this.nappainsetti.avaaNappainsetti("11 12 13 14 21 22 23 24 31 32 51 52 100");
		
		assertTrue("Tiedostoa avattaessa ylös-näppäin ei asettunut", nappainsetti.annaYlosNappain() == 11);
		assertTrue("Tiedostoa avattaessa alas-näppäin ei asettunut", nappainsetti.annaAlasNappain() == 12);
		assertTrue("Tiedostoa avattaessa vasemmalle-näppäin ei asettunut", nappainsetti.annaVasemmalleNappain() == 13);
		assertTrue("Tiedostoa avattaessa oikealle-näppäin ei asettunut", nappainsetti.annaOikealleNappain() == 14);
		
		assertTrue("Tiedostoa avattaessa yläpuoliEsille-näppäin ei asettunut", nappainsetti.annaYlapuoliEsilleNappain() == 21);
		assertTrue("Tiedostoa avattaessa alapuoliEsille-näppäin ei asettunut", nappainsetti.annaAlapuoliEsilleNappain() == 22);
		assertTrue("Tiedostoa avattaessa vasenPuoliEsille-näppäin ei asettunut", nappainsetti.annaVasenPuoliEsilleNappain() == 23);
		assertTrue("Tiedostoa avattaessa oikeaPuoliEsille-näppäin ei asettunut", nappainsetti.annaOikeaPuoliEsilleNappain() == 24);
		assertTrue("Tiedostoa avattaessa kierraMyotapaivaan-näppäin ei asettunut", nappainsetti.annaKierraMyotapaivaanNappain() == 31);
		assertTrue("Tiedostoa avattaessa kierraVastapaivaan-näppäin ei asettunut", nappainsetti.annaKierraVastapaivaanNappain() == 32);
		
		assertTrue("Tiedostoa avattaessa tiputa-näppäin ei asettunut", nappainsetti.annaTiputaNappain() == 51);
		assertTrue("Tiedostoa avattaessa tiputaYksiKerros-näppäin ei asettunut", nappainsetti.annaTiputaYksiKerrosNappain() == 52);
		
		assertTrue("Tiedostoa avattaessa tauko-näppäin ei asettunut", nappainsetti.annaTaukoNappain() == 100);
	}
	
	/**
	 * Testataan epäonnistuuko virheellisen (numero puuttuu) tallenteen avaus.
	 * Tällöin ohjelman tulee asettaa alkuperäiset näppäimet vaikka osa tallenteesta olisikin jo avattu ongelmitta.
	 */
	@Test
	public void virheellisenNappainsetinAvausEiOnnistu() {
		this.nappainsetti.avaaNappainsetti("11 12 13 14 21 22 23 24");
		
		assertFalse("Tiedostoa avattaessa ylös-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaYlosNappain() == 11);
		assertFalse("Tiedostoa avattaessa alas-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaAlasNappain() == 12);
		assertFalse("Tiedostoa avattaessa vasemmalle-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaVasemmalleNappain() == 13);
		assertFalse("Tiedostoa avattaessa oikealle-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaOikealleNappain() == 14);
		
		assertFalse("Tiedostoa avattaessa yläpuoliEsille-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaYlapuoliEsilleNappain() == 21);
		assertFalse("Tiedostoa avattaessa alapuoliEsille-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaAlapuoliEsilleNappain() == 22);
		assertFalse("Tiedostoa avattaessa vasenPuoliEsille-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaVasenPuoliEsilleNappain() == 23);
		assertFalse("Tiedostoa avattaessa oikeaPuoliEsille-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaOikeaPuoliEsilleNappain() == 24);
		assertFalse("Tiedostoa avattaessa kierraMyotapaivaan-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaKierraMyotapaivaanNappain() == 31);
		assertFalse("Tiedostoa avattaessa kierraVastapaivaan-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaKierraVastapaivaanNappain() == 32);
		
		assertFalse("Tiedostoa avattaessa tiputa-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaTiputaNappain() == 51);
		assertFalse("Tiedostoa avattaessa tiputaYksiKerros-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaTiputaYksiKerrosNappain() == 52);
		
		assertFalse("Tiedostoa avattaessa tauko-näppäin asettui vaikka tiedosto oli viallinen", nappainsetti.annaTaukoNappain() == 100);
	}
	
	/**
	 * Testataan varautuuko nappainsettitallenteen avaaja virheisiin tallenteen muotoilussa.
	 * Tässä tapauksessa testataan miten merkkijono syötteet käyttäytyvät kun niiden kohdilla tuli olla numeroita.
	 */
	@Test
	public void nappainsetinAvausValmistautuuVirheisiinParseIntinSuhteen() {
		try {
			this.nappainsetti.avaaNappainsetti("1 2 3 v i r h e");
		} catch (Exception e) {
			fail("Nappainsetin tulee varautua tallenteisiin, joissa on numeroiden lisäksi tekstiä");
		}
	}
	
	/**
	 * Ohjelman tulee tallentaa nappainsetti oikein, jotta sita voidaan myohemmin lukea.
	 */
	@Test
	public void nappainSetinTallennusLuoOikeanlaisenMerkkijononTallennukseen() {
		String toimivaTallenne = "11 12 13 14 21 22 23 24 31 32 51 52 100";
		this.nappainsetti.avaaNappainsetti(toimivaTallenne);
		
		String luettuTallenne = this.nappainsetti.tallennaNappainsetti();
		assertTrue("Nappainsetin tallennus ei toimi.\nTallenteen olisi tullut olla: " + toimivaTallenne + "\nMutta se oli: " + luettuTallenne, luettuTallenne.equals(toimivaTallenne) );
	}
	
	/**
	 * Ohjelman tulee asettaa alkuperäiset näppäimet jos nappainsettitallenteen avauksessa ilmeni ongelmia.
	 */
	@Test
	public void virheellisestiToiminutTallenneJohtaaPerussettiin() {
		String alkuperainenNappainsetti = this.nappainsetti.tallennaNappainsetti();
		
		this.nappainsetti.avaaNappainsetti("3 4 5 6 7 8 c 0 d sdf");
		String uusiNappainsetti = this.nappainsetti.tallennaNappainsetti();
		assertTrue("Nappainsetin olisi tullut pysya perussettina kun virheellinen tallenne koitettiin lukea.", uusiNappainsetti.equals( alkuperainenNappainsetti ) );
	}
	
	/**
	 * Käyttäjä ei saa voida asettaa kahta toimintoa samalle näppäimelle.
	 */
	@Test
	public void kahtaNappaintaEiVoiAsettaaKahdelle() {
		this.nappainsetti.asetaAlasNappain(-50);
		assertTrue("Kahta toimintoa ei kuuluisi voida asettaa samalle nappaimelle.", this.nappainsetti.asetaYlosNappain(-50) == false);
	}
	
	/**
	 * Käyttäjän tulee voida asettaa toiminto näppäimelle, jolle ei ole vielä asetettu muita toimintoja.
	 */
	@Test
	public void nappaimenVoiAsettaaAiemminVapaallePaikalle() {
		String varatutNappaimet =this.nappainsetti.tallennaNappainsetti();
		
		int kokeiltavaVapaaKoodi = 0;
		while (true) {
			if ( !varatutNappaimet.contains(""+kokeiltavaVapaaKoodi) ) {
				break;
			}
			kokeiltavaVapaaKoodi++;
		}
		
		assertTrue("Pitaisi olla mahdollista asettaa toiminto nappaimelle, jota ei ole entuudestaan varattu.", this.nappainsetti.asetaYlosNappain(kokeiltavaVapaaKoodi));
	}
}

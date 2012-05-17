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
	
	
	@Test
	public void liianPientaLeveyttaEiVoiAsettaa() {
		int alkuperainenLeveys = ulottuvuudet.annaLeveys();
		ulottuvuudet.asetaLeveys(ulottuvuudet.annaMinimiLeveys()-1);
		assertTrue("Ulottuvuudet sallii liian pienen leveyden asettamisen.",  ulottuvuudet.annaLeveys() == alkuperainenLeveys);
	}
	
	@Test
	public void liianSuurtaLeveyttaEiVoiAsettaa() {
		int alkuperainenLeveys = ulottuvuudet.annaLeveys();
		ulottuvuudet.asetaLeveys(ulottuvuudet.annaMaksimiLeveys()+1);
		assertTrue("Ulottuvuudet sallii liian suuren leveyden asettamisen.",  ulottuvuudet.annaLeveys() == alkuperainenLeveys);
	}
	
	@Test
	public void sopivanLeveydenVoiAsettaa() {
		for (int leveys = ulottuvuudet.annaMinimiLeveys(); leveys <= ulottuvuudet.annaMaksimiLeveys(); leveys++) {
			ulottuvuudet.asetaLeveys(leveys);
			assertTrue("Ulottuvuudet ei salli sallittavan leveyden asettamista.",  ulottuvuudet.annaLeveys() == leveys);
		}
	}
	
	@Test
	public void liianPientaKorkeuttaEiVoiAsettaa() {
		int alkuperainenKorkeus = ulottuvuudet.annaKorkeus();
		ulottuvuudet.asetaKorkeus(ulottuvuudet.annaMinimiKorkeus()-1);
		assertTrue("Ulottuvuudet sallii liian pienen korkeuden asettamisen.",  ulottuvuudet.annaKorkeus() == alkuperainenKorkeus);
	}
	
	@Test
	public void liianSuurtaKorkeuttaEiVoiAsettaa() {
		int alkuperainenKorkeus = ulottuvuudet.annaKorkeus();
		ulottuvuudet.asetaKorkeus(ulottuvuudet.annaMaksimiKorkeus()+1);
		assertTrue("Ulottuvuudet sallii liian suuren korkeuden asettamisen.",  ulottuvuudet.annaKorkeus() == alkuperainenKorkeus);
	}
	
	@Test
	public void sopivanKorkeudenVoiAsettaa() {
		for (int korkeus = ulottuvuudet.annaMinimiKorkeus(); korkeus <= ulottuvuudet.annaMaksimiKorkeus(); korkeus++) {
			ulottuvuudet.asetaKorkeus(korkeus);
			assertTrue("Ulottuvuudet ei salli sallittavan korkeuden asettamista.",  ulottuvuudet.annaKorkeus() == korkeus);
		}
	}
	
	@Test
	public void liianPientaSyvyyttaEiVoiAsettaa() {
		int alkuperainenSyvyys = ulottuvuudet.annaSyvyys();
		ulottuvuudet.asetaSyvyys(ulottuvuudet.annaMinimiSyvyys()-1);
		assertTrue("Ulottuvuudet sallii liian pienen syvyyden asettamisen.",  ulottuvuudet.annaSyvyys() == alkuperainenSyvyys);
	}
	
	@Test
	public void liianSuurtaSyvyyttaEiVoiAsettaa() {
		int alkuperainenSyvyys = ulottuvuudet.annaSyvyys();
		ulottuvuudet.asetaSyvyys(ulottuvuudet.annaMaksimiSyvyys()+1);
		assertTrue("Ulottuvuudet sallii liian suuren syvyyden asettamisen.",  ulottuvuudet.annaSyvyys() == alkuperainenSyvyys);
	}
	
	@Test
	public void sopivanSyvyydenVoiAsettaa() {
		for (int syvyys = ulottuvuudet.annaMinimiSyvyys(); syvyys <= ulottuvuudet.annaMaksimiSyvyys(); syvyys++) {
			ulottuvuudet.asetaSyvyys(syvyys);
			assertTrue("Ulottuvuudet ei salli sallittavan leveyden asettamista.",  ulottuvuudet.annaSyvyys() == syvyys);
		}
	}
	
	@Test
	public void leikkauspistettaEiVoiAsettaaKuilunPohjanEtupuolelle() {
		int kuilunSyvyys = ulottuvuudet.annaMaksimiSyvyys();
		ulottuvuudet.asetaSyvyys( kuilunSyvyys );
		assertFalse("Leikkauspistettä ei saisi voida asettaa kuilun pohjan etupuolelle.", ulottuvuudet.asetaLeikkauspiste( kuilunSyvyys-5 ));
	}
	
	@Test
	public void leikkauspisteenVoiAsettaaKuilunPohjanTaakse() {
		int kuilunSyvyys = ulottuvuudet.annaSyvyys();
		assertTrue("Leikkauspisteen pitäisi pystyä asettamaan kuilun pohjan taakse.", ulottuvuudet.asetaLeikkauspiste(kuilunSyvyys+1));
	}
}

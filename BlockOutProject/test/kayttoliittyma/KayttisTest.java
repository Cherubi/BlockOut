package kayttoliittyma;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.*;
import static org.junit.Assert.*;

import javax.swing.JFrame;

import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;

import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.launcher.ApplicationLauncher;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;

import kayttoliittyma.BlockOut;

/**
 *
 * @author Usagi-chan
 */
public class KayttisTest extends FestSwingJUnitTestCase {
	
	FrameFixture frame;
	
	/**
	 * Ohjelmasta tulee löytyä JFrame.
	 */
	@Override
	protected void onSetUp() {
		ApplicationLauncher.application(BlockOut.class).start();
		try{
			robot().settings().delayBetweenEvents(100);
		} catch(Exception e) {
			robot().settings().delayBetweenEvents(500);
		}
		
		try {
			frame = WindowFinder.findFrame(JFrame.class).using(robot());
		} catch(Exception e) {
			fail("Ei löytynyt JFramea. Olethan luonut sen ja asettanut sen näkyväksi.");
		}
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
	 * JFramesta tulee löytyä aluksi nappulat: Tauko, Uusi peli, Lopeta, Asetukset ja Ennatyslista
	 */
	@Test
	public void onkoEtusivunNappulat() {
		String[] nappienNimet = {"Tauko", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		for (String napinNimi : nappienNimet) {
			assertTrue("Olethan lisännyt JFrameen \"" + napinNimi + "\"-JButtonin", loytyykoNappi(napinNimi));
		}
	}
	
	/**
	 * Etsii JFramesta JButtonia.
	 * 
	 * @param teksti Teksti, joka JButtonilla tulee olla.
	 * @return Tieto siitä löytyikö nappi vai ei.
	 */
	public boolean loytyykoNappi(String teksti) {
		try {
			frame.button(JButtonMatcher.withText(teksti));
			return true;
		} catch (ComponentLookupException e) {
			return false;	
		}
	}
	
	/**
	 * Testaa onko nappuloiden aktiivisuudet aluksi oikein. (Jokainen nappula on aktiivinen vain silloin kun siitä saa voida painaa.)
	 */
	@Test
	public void onkoNappuloidenAktiivisuudetAluksiOikein() {
		String[] nappienNimet = {"Tauko", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		boolean[] nappienAktiivisuudet = {false, true, false, true, true};
		for (int i=0; i<nappienNimet.length; i++) {
			assertTrue("Nappulan \"" + nappienNimet[i] + "\" aktiivisuuden tulisi olla " + nappienAktiivisuudet[i] + ", mutta se oli " + !nappienAktiivisuudet[i], annaNappula(nappienNimet[i]).target.isEnabled() == nappienAktiivisuudet[i]);
		}
	}
	
	/**
	 * Hakee JFramesta JButtonin.
	 * 
	 * @param teksti Teksti, joka haetulla JButtonilla halutaan olevan.
	 * @return JButton, jolla on haettu teksti.
	 */
	public JButtonFixture annaNappula(String teksti) {
		try {
			return frame.button(JButtonMatcher.withText(teksti));
		} catch(ComponentLookupException e) {
			return null;
		}
	}
	
	/**
	 * Painaa JFramesta löytyvää nappulaa.
	 * 
	 * @param teksti Teksti, joka painettavalla nappulalla halutaan olevan.
	 */
	public void painaNappia(String teksti) {
		try {
			JButtonFixture nappula = annaNappula(teksti);
			nappula.focus();
			nappula.click();
		} catch(Exception e) {
			fail(teksti + "-JButtonia ei löydetty. Tämä viittaa virheelliseen nappulan aktiivisuuteen.");
		}
	}
	
	/**
	 * Testaa onko JFramessa olevien JButtonien aktiivisuudet (isEnabled) haluttuja
	 * 
	 * @param aktiivisuudet JFramen JButtonien halutut aktiivisuudet
	 */
	public void onkoNappuloidenAktiivisuudetOikein(boolean... aktiivisuudet) {
		String[] nappienNimet = {"Tauko", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		for (int i=0; i<nappienNimet.length; i++) {
			assertTrue("Nappulan \"" + nappienNimet[i] + "\" aktiivisuuden tulisi olla " + aktiivisuudet[i] + ", mutta se oli " + annaNappula(nappienNimet[i]).target.isEnabled(), annaNappula(nappienNimet[i]).target.isEnabled() == aktiivisuudet[i]);
		}
	}
	
	/**
	 * Testaa onko JButtonien aktiivisuudet oikein kun Asetukset-näppäintä on klikattu
	 */
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinAsetusNapaytyksenJalkeen() {
		painaNappia("Asetukset");
		onkoNappuloidenAktiivisuudetOikein(false, true, false, false, true);
	}
	
	/**
	 * Testaa onko JButtonien aktiivisuudet oikein kun Ennatys-näppiäntä on klikattu
	 */
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinEnnatyslistaNapaytyksenJalkeen() {
		painaNappia("Ennatyslista");
		onkoNappuloidenAktiivisuudetOikein(false, true, false, true, false);
	}
	
	/**
	 * Testaa onko JButtonien aktiivisuudet oikein kun Uusi peli-näppäintä on klikattu
	 */
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinUudenPelinAloituksenJalkeen() {
		painaNappia("Uusi peli");
		onkoNappuloidenAktiivisuudetOikein(true, true, true, true, true);
	}
	
	/**
	 * Testaa onko JButtonien aktiivisuudet oikein kun peli on laitettu tauolle Tauko-näppäintä klikkaamalla
	 */
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinTauonAsetuksenJalkeen() {
		painaNappia("Uusi peli");
		painaNappia("Tauko");
		
		String[] nappienNimet = {"Jatka", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		boolean[] aktiivisuudet = {true, true, true, true, true};
		for (int i=0; i<nappienNimet.length; i++) {
			assertTrue("Nappulan \"" + nappienNimet[i] + "\" aktiivisuuden tulisi olla " + aktiivisuudet[i] + ", mutta se oli " + annaNappula(nappienNimet[i]).target.isEnabled(), annaNappula(nappienNimet[i]).target.isEnabled() == aktiivisuudet[i]);
		}
	}
	
	/**
	 * Testaa onko JButtonien aktiivisuudet oikein kun peli on lopetettu Lopeta-näppäintä klikkaamalla
	 */
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinPelinLopetuksenJalkeen() {
		painaNappia("Uusi peli");
		painaNappia("Lopeta");
		onkoNappuloidenAktiivisuudetOikein(false, true, false, true, true);
	}
	
	/**
	 * Testaa muuttuuko Tauko-näppäin Jatka-näppäimeksi kun peli on aloitettu ja Asetukset-näppäintä klikataan
	 */
	@Test
	public void muuttuukoTaukoNappulaJatkaksiKunPelinAikanaAvataanAsetukset() {
		painaNappia("Uusi peli");
		painaNappia("Asetukset");
		assertTrue("Muuttuuhan tauko-nappulan tekstiksi \"Jatka\" kun Asetukset avataan pelin aikana", loytyykoNappi("Jatka"));
	}
	
	/**
	 * Testaa muuttuuko Tauko-näppäin Jatka-näppäimeksi kun peli on aloitettu ja Ennatyslista-näppäintä klikataan
	 */
	@Test
	public void muuttuukoTaukoNappulaJatkaksiKunPelinAikanaAvataanEnnatyslista() {
		painaNappia("Uusi peli");
		painaNappia("Ennatyslista");
		assertTrue("Muuttuuhan tauko-nappulan tekstiksi \"Jatka\" kun Ennatyslista avataan pelin aikana", loytyykoNappi("Jatka"));
	}
	
	/**
	 * Testaa onko Tauko-näppäin yhä Tauko-näppäin kun peli on aloitettu ja se lopetetaan
	 */
	@Test
	public void pysyykoTaukoNappulaTaukonaKunPeliLopetetaan() {
		painaNappia("Uusi peli");
		painaNappia("Lopeta");
		assertTrue("Onhan tauko-nappulan teksti \"Tauko\" pelin lopetuksen jälkeen", loytyykoNappi("Tauko"));
	}
	
	/**
	 * Testaa onko Tauko-näppäin yhä Tauko-näppäin kun pelin aikana klikataan Uusi peli-näppäintä
	 */
	@Test
	public void onkoTaukonappulaYhaTaukoJosPelinAikanaAloitetaanUusiPeli() {
		painaNappia("Uusi peli");
		painaNappia("Uusi peli");
		assertTrue("Pysyyhän tauko-nappulan tekstinä \"Tauko\"-kun pelin aikana aloitetaan uusi peli", loytyykoNappi("Tauko"));
	}
	
	/**
	 * Testaa onko Jatka-näppäin jälleen Tauko-näppäin jos tauon aikana klikataan Uusi peli-näppäintä
	 */
	@Test
	public void onkoTaukoNappulaJalleenTaukoJosPelinTauonAikanaAloitetaanUusiPeli() {
		painaNappia("Uusi peli");
		painaNappia("Tauko");
		painaNappia("Uusi peli");
		assertTrue("Onhan tauko-nappulan teksti jälleen \"Tauko\" kun pelin tauon aikana aloitetaan uusi peli", loytyykoNappi("Tauko"));
	}
}

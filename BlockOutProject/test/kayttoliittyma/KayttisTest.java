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
	
	
	
	@Test
	public void onkoEtusivunNapit() {
		String[] nappienNimet = {"Tauko", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		for (String napinNimi : nappienNimet) {
			assertTrue("Olethan lisännyt JFrameen \"" + napinNimi + "\"-JButtonin", loytyykoNappi(napinNimi));
		}
	}
	
	public boolean loytyykoNappi(String teksti) {
		try {
			frame.button(JButtonMatcher.withText(teksti));
			return true;
		} catch (ComponentLookupException e) {
			return false;	
		}
	}
	
	@Test
	public void onkoNappuloidenAktiivisuudetAluksiOikein() {
		String[] nappienNimet = {"Tauko", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		boolean[] nappienAktiivisuudet = {false, true, false, true, true};
		for (int i=0; i<nappienNimet.length; i++) {
			assertTrue("Nappulan \"" + nappienNimet[i] + "\" aktiivisuuden tulisi olla " + nappienAktiivisuudet[i] + ", mutta se oli " + !nappienAktiivisuudet[i], annaNappi(nappienNimet[i]).target.isEnabled() == nappienAktiivisuudet[i]);
		}
	}
	
	public JButtonFixture annaNappi(String teksti) {
		try {
			return frame.button(JButtonMatcher.withText(teksti));
		} catch(ComponentLookupException e) {
			return null;
		}
	}
	
	public void painaNappia(String teksti) {
		try {
			JButtonFixture nappula = annaNappi(teksti);
			nappula.focus();
			nappula.click();
		} catch(Exception e) {
			fail(teksti + "-JButtonia ei löydetty. Tämä viittaa virheelliseen nappulan aktiivisuuteen.");
		}
	}
	
	public void onkoNappuloidenAktiivisuudetOikein(boolean... aktiivisuudet) {
		String[] nappienNimet = {"Tauko", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		for (int i=0; i<nappienNimet.length; i++) {
			assertTrue("Nappulan \"" + nappienNimet[i] + "\" aktiivisuuden tulisi olla " + aktiivisuudet[i] + ", mutta se oli " + annaNappi(nappienNimet[i]).target.isEnabled(), annaNappi(nappienNimet[i]).target.isEnabled() == aktiivisuudet[i]);
		}
	}
	
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinAsetusNapaytyksenJalkeen() {
		painaNappia("Asetukset");
		onkoNappuloidenAktiivisuudetOikein(false, true, false, false, true);
	}
	
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinEnnatyslistaNapaytyksenJalkeen() {
		painaNappia("Ennatyslista");
		onkoNappuloidenAktiivisuudetOikein(false, true, false, true, false);
	}
	
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinUudenPelinAloituksenJalkeen() {
		painaNappia("Uusi peli");
		onkoNappuloidenAktiivisuudetOikein(true, true, true, true, true);
	}
	
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinTauonAsetuksenJalkeen() {
		painaNappia("Uusi peli");
		painaNappia("Tauko");
		
		String[] nappienNimet = {"Jatka", "Uusi peli", "Lopeta", "Asetukset", "Ennatyslista"};
		boolean[] aktiivisuudet = {true, true, true, true, true};
		for (int i=0; i<nappienNimet.length; i++) {
			assertTrue("Nappulan \"" + nappienNimet[i] + "\" aktiivisuuden tulisi olla " + aktiivisuudet[i] + ", mutta se oli " + annaNappi(nappienNimet[i]).target.isEnabled(), annaNappi(nappienNimet[i]).target.isEnabled() == aktiivisuudet[i]);
		}
	}
	
	@Test
	public void onkoNappuloidenAktiivisuudetOikeinPelinLopetuksenJalkeen() {
		painaNappia("Uusi peli");
		painaNappia("Lopeta");
		onkoNappuloidenAktiivisuudetOikein(false, true, false, true, true);
	}
	
	@Test
	public void muuttuukoTaukoNappulaJatkaksiKunPelinAikanaAvataanAsetukset() {
		painaNappia("Uusi peli");
		painaNappia("Asetukset");
		assertTrue("Muuttuuhan tauko-nappulan tekstiksi \"Jatka\" kun Asetukset avataan pelin aikana", loytyykoNappi("Jatka"));
	}
	
	@Test
	public void muuttuukoTaukoNappulaJatkaksiKunPelinAikanaAvataanEnnatyslista() {
		painaNappia("Uusi peli");
		painaNappia("Ennatyslista");
		assertTrue("Muuttuuhan tauko-nappulan tekstiksi \"Jatka\" kun Ennatyslista avataan pelin aikana", loytyykoNappi("Jatka"));
	}
	
	@Test
	public void pysyykoTaukoNappulaTaukonaKunPeliLopetetaan() {
		painaNappia("Uusi peli");
		painaNappia("Lopeta");
		assertTrue("Onhan tauko-nappulan teksti \"Tauko\" pelin lopetuksen jälkeen", loytyykoNappi("Tauko"));
	}
	
	@Test
	public void onkoTaukonappulaYhaTaukoJosPelinAikanaAloitetaanUusiPeli() {
		painaNappia("Uusi peli");
		painaNappia("Uusi peli");
		assertTrue("Pysyyhän tauko-nappulan tekstinä \"Tauko\"-kun pelin aikana aloitetaan uusi peli", loytyykoNappi("Tauko"));
	}
	
	@Test
	public void onkoTaukoNappulaJalleenTaukoJosPelinTauonAikanaAloitetaanUusiPeli() {
		painaNappia("Uusi peli");
		painaNappia("Tauko");
		painaNappia("Uusi peli");
		assertTrue("Onhan tauko-nappulan teksti jälleen \"Tauko\" kun pelin tauon aikana aloitetaan uusi peli", loytyykoNappi("Tauko"));
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.asetukset;

import java.awt.Color;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Usagi-chan
 */
public class VaritTest {
	private Varit varit;
	
	public VaritTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		this.varit = new Varit();
	}
	
	
	
	/**
	 * Testaa, etta varit voidaan avata tiedostosta kun tiedosto on pateva.
	 */
	@Test
	public void varienAvausTiedostostaToimii() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		
		this.varit.avaaVarit(testitallenne);
		ArrayList<Color> asetetutVarit = this.varit.annaVarit();
		
		assertTrue("Varien avaus nayttaa luovan enemman vareja kuin tallenteessa on ollut", asetetutVarit.size() == 3);
		
		assertTrue("Varien avaus ei nayta asettavan vareja. Ensimmainen vari oli " + asetetutVarit.get(0) + " kun sen haluttiin olevan RED", asetetutVarit.get(0).getRGB() == Color.RED.getRGB());
		assertTrue("Varien avaus ei nayta asettavan vareja.", asetetutVarit.get(1).getRGB() == Color.BLUE.getRGB());
		assertTrue("Varien avaus ei nayta asettavan vareja.", asetetutVarit.get(2).getRGB() == Color.YELLOW.getRGB());
	}
	
	/**
	 * 
	 */
	@Test
	public void ohjelmaVarautuuEpapatevaanTallenteeseenJossaOnMerkkijonoja() {
		try {
			this.varit.avaaVarit("v a r e ja");
		} catch (NumberFormatException e) {
			fail("Ohjelman olisi pitanyt varautua vareja avatessa merkkijonosyotteeseen.");
		}
	}
	
	/**
	 * Epapateva tallenne luo tallenteessa maaritellyn maaran verran harmaita.
	 */
	@Test
	public void epapatevaTallenneLuoListanHarmaita() {
		String testitallenne = "a b c d";
		this.varit.avaaVarit(testitallenne);
		ArrayList<Color> asetetutVarit = this.varit.annaVarit();
		
		assertTrue("Epapatevan tallenteen tulisi joka tapauksessa luoda yhta paljon vareja asetuksiin kuin tallenteen valilyonnilla splittaamalla saadaan alkioita", asetetutVarit.size() == 4);
		
		for (Color vari : asetetutVarit) {
			assertTrue("Taysin epapatevan tallenteen luomien varien olisi tullut olla harmaita", vari.getRGB() == Color.GRAY.getRGB());
		}
	}
	
	/**
	 * Testaan osaako ohjelma luoda avattavan tallenteen.
	 */
	@Test
	public void ohjelmaOsaaLuodaLuettavanTallenteen() {
		String testisyote = "-16777176 -16777166 -16777156";
		this.varit.avaaVarit(testisyote);
		
		String testitallenne = this.varit.tallennaVarit();
		assertTrue("Ohjelman luoma testitallenne ei ollut testisyote.\nTestisyote: " + testisyote + "\nTestitallenne: " + testitallenne, testitallenne.equals(testisyote));
	}
	
	/**
	 * Ohjelma antaa varit oikeassa jarjestyksessa kerros kerrallaan niin etta listan ensimmainen vari tulee olemaan myos kerros 1.
	 */
	@Test
	public void ohjelmaOsaaAntaaAlimmanKerroksenVari() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		this.varit.avaaVarit(testitallenne);
		
		assertTrue("Testin tulisi antaa ensimmaiseksi variksi punainen kun se on asetettu tallenteessa ensimmaiseksi.", this.varit.annaVari(1).getRGB() == Color.RED.getRGB());
	}
	
	/**
	 * Negatiivisen tai nollannen kerroksen varia pyydettaessa ohjelman tulisi palauttaa harmaa.
	 */
	@Test
	public void variksiAnnetaanHarmaaJosPyydetaanNegatiivisenTaiNollannenKerroksenVaria() {
		assertTrue("Varin tulisi olla harmaa kun pyydetaan nollannen kerroksen varia.", this.varit.annaVari(0).getRGB() == Color.GRAY.getRGB());
		assertTrue("Varin tulisi olla harmaa kun pyydetaan negatiivisen kerroksen varia.", this.varit.annaVari(-1).getRGB() == Color.GRAY.getRGB());
	}
	
	/**
	 * Pyydettaessa sellaisen kerroksen varia, jonka kerrosnumero on suurempi kuin varilistan koko kaydaan lista lapi niin monta kertaa, etta oikea kerros loydetaan.
	 * Esimerkiksi listan ollessa 3 pitka ja kysytaan neljatta kerrosta tulisi ohjelman palauttaa listan ensimmainen vari.
	 */
	@Test
	public void variOsataanAntaaKunKerrosnumeroOnSuurempiKuinVarilistanKoko() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		this.varit.avaaVarit(testitallenne);
		
		assertTrue("Varin tulisi olla listan ensimmainen kun pyydetaan neljatta varia ja varilistan pituus on kolme.", this.varit.annaVari(4).getRGB() == Color.RED.getRGB());
		assertTrue("Varin tulisi olla listan toinen kun pyydetaan viidetta varia ja varilistan pituus on kolme.", this.varit.annaVari(5).getRGB() == Color.BLUE.getRGB());
		assertTrue("Varin tulisi olla listan ensimmainen kun pyydetaan seitsematta varia ja varilistan pituus on kolme.", this.varit.annaVari(7).getRGB() == Color.RED.getRGB());
	}
	
	/**
	 * Ohjelman tulee osata vaihtaa tietyn kerroksen vari.
	 */
	@Test
	public void kerroksenVariOsataanVaihtaa() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		this.varit.avaaVarit(testitallenne);
		this.varit.vaihdaVari(3, Color.GREEN);
		ArrayList<Color> asetetutVarit = this.varit.annaVarit();
		
		assertTrue("Varien avaus nayttaa luovan enemman vareja kuin tallenteessa on ollut", asetetutVarit.size() == 3);
		
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(0).getRGB() == Color.RED.getRGB());
		assertTrue("Varien avaus ei nayta vaihtavan varia.", asetetutVarit.get(1).getRGB() == Color.BLUE.getRGB());
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(2).getRGB() == Color.GREEN.getRGB());
	}
	
	/**
	 * Ohjelman ei tule muuttaa mitaan jos yritetaan vaihtaa negatiivisen tai nollannen kerroksen varia.
	 */
	@Test
	public void negatiivisenTaiNollannenKerroksenVarinVaihtamisenYritysEiMuutaMitaan() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		this.varit.avaaVarit(testitallenne);
		this.varit.vaihdaVari(0, Color.GREEN);
		this.varit.vaihdaVari(-1, Color.MAGENTA);
		ArrayList<Color> asetetutVarit = this.varit.annaVarit();
		
		assertTrue("Varien avaus nayttaa luovan enemman vareja kuin tallenteessa on ollut", asetetutVarit.size() == 3);
		
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(0).getRGB() == Color.RED.getRGB());
		assertTrue("Varien avaus ei nayta vaihtavan varia.", asetetutVarit.get(1).getRGB() == Color.BLUE.getRGB());
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(2).getRGB() == Color.YELLOW.getRGB());
	}
	
	/**
	 * Ohjelman tulee osata lisata listaan vari jos vaihdetaan sen kerroksen vari, joka on listan viimeisen varin jalkeen.
	 */
	@Test
	public void varejaOsataanLisataListalle() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		this.varit.avaaVarit(testitallenne);
		this.varit.vaihdaVari(4, Color.GREEN);
		ArrayList<Color> asetetutVarit = this.varit.annaVarit();
		
		assertTrue("Varien avaus nayttaa luovan enemman vareja kuin tallenteeseen on laitettu", asetetutVarit.size() == 4);
		
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(0).getRGB() == Color.RED.getRGB());
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(1).getRGB() == Color.BLUE.getRGB());
		assertTrue("Varin vaihto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(2).getRGB() == Color.YELLOW.getRGB());
		assertTrue("Varin lisaaminen vaihtamalla ensimmaista listasta puuttuvaa alkiota ei nayta toimivan.", asetetutVarit.get(3).getRGB() == Color.GREEN.getRGB());
	}
	
	/**
	 * 
	 */
	@Test
	public void variOsataanPoistaaListalta() {
		int punainen = Color.RED.hashCode();
		int sininen = Color.BLUE.hashCode();
		int keltainen = Color.YELLOW.hashCode();
		
		String testitallenne = punainen + " " + sininen + " " + keltainen;
		this.varit.avaaVarit(testitallenne);
		this.varit.poistaVari(2);
		ArrayList<Color> asetetutVarit = this.varit.annaVarit();
		
		assertTrue("Varien avaus nayttaa luovan enemman vareja kuin tallenteessa tulisi olla", asetetutVarit.size() == 2);
		
		assertTrue("Varin poisto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(0).getRGB() == Color.RED.getRGB());
		assertTrue("Varin poisto nayttaa hajottavan muidenkin kerrosten varit.", asetetutVarit.get(1).getRGB() == Color.YELLOW.getRGB());
	}
}

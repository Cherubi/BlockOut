/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.ennatyslista;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import peli.logiikka.Palikkasetti;

/**
 *
 * @author Usagi-chan
 */
public class EnnatyslistaTest {
	private Ennatyslista ennatyslista;
	
	public EnnatyslistaTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		this.ennatyslista = new Ennatyslista(10, 5, 5, 8, "Flat");
	}
	
	
	
	/**
	 * Testaa onko uusi juuri luotu ennatyslista tallennettavissa oikein.
	 */
	@Test
	public void tallentuukoTyhjaListaOikein() {
		String tallenne = this.ennatyslista.tallennaLista();
		assertTrue("", tallenne.equals("0 --\n0 --\n0 --\n0 --\n0 --\n0 --\n0 --\n0 --\n0 --\n0 --\n"));
	}
	
	/**
	 * Testaa tunnistaako ennatyslista omat parametrinsa.
	 */
	@Test
	public void tunnistaakoEnnatyslistaOmatParametrinsa() {
		assertTrue("onkoKokoJaSetti ei tunnista oman luokkansa parametreja", this.ennatyslista.onkoKokoJaSetti(5, 5, 8, "Flat"));
	}
	
	/**
	 * Testaa tunnistaako ennatyslista muut kuin omat parametrinsa erilaisiksi.
	 */
	@Test
	public void tunnistaakoEnnatyslistaEriParametritErilaisiksi() {
		assertFalse("Ennatyslista tunnistaa omiksi parametreikseen parametrit missa on eri pienempi leveys.", this.ennatyslista.onkoKokoJaSetti(4, 5, 8, "Flat"));
		assertFalse("Ennatyslista tunnistaa omiksi parametreikseen parametrit missa on eri suurempi leveys.", this.ennatyslista.onkoKokoJaSetti(5, 4, 8, "Flat"));
		assertFalse("Ennatyslista tunnistaa omiksi parametreikseen parametrit missa on eri syvyys.", this.ennatyslista.onkoKokoJaSetti(5, 5, 7, "Flat"));
		assertFalse("Ennatyslista tunnistaa omiksi parametreikseen parametrit missa on eri palikkasetti.", this.ennatyslista.onkoKokoJaSetti(5, 5, 8, "Basic"));
	}
	
	/**
	 * Testaa tunnistaako ennatyslista pienella kirjoitetun palikkasetin omakseen.
	 */
	@Test
	public void tunnistaakoEnnatyslistaPienellaKirjoitetunPalikkasetinOmakseen() {
		assertTrue("Ennatyslista ei tunnista omakseen Flat-palikkasettia kun se on kirjoitettu pienella.", this.ennatyslista.onkoKokoJaSetti(5, 5, 8, "flat"));
	}
	
	/**
	 * Testaa paaseeko listalle kun listalle tulisi voida paasta.
	 */
	@Test
	public void paaseekoTarpeeksiHyvaTulosListalle() {
		assertTrue("Ennatyslista ei paastanyt tarpeeksi hyvaa tulosta listalle", this.ennatyslista.paaseekoListalle(10));
	}
	
	/**
	 * Testaa kielletaanko liian huono tulos listalta.
	 */
	@Test
	public void evataankoLiianHuonoltaTulokseltaListallePaasy() {
		assertFalse("Ennatyslista olisi paastanyt listalle liian huonon tuloksen.", this.ennatyslista.paaseekoListalle(-1));
	}
	
	/**
	 * Testaa kielletaanko sellaiselta tulokselta listalle paasy, jolla on yhta hyva tulos kuin listan viimeisella listasijalla
	 */
	@Test
	public void evataankoViimeisenListasijanTuloksenSaaneenPaasyListalle() {
		assertFalse("Ennatyslistan ei olisi tullut paastaa listalle tulosta, jolla on sama tulos viimeisen listasijan kanssa.", this.ennatyslista.paaseekoListalle(-1));
	}
	
	/**
	 * Listalle lisaaminen onnistuu kun tulos on tarpeeksi hyva.
	 */
	@Test
	public void listalleLisaaminenOnnistuu() {
		this.ennatyslista.lisaaListalle(10, "Testaaja");
		
		int loydettyjenMaara = 0;
		for (int i = 1; i <= 10; i++) {
			String listasija = this.ennatyslista.annaListanSija(i);
			if (listasija.contains("10") && listasija.contains("Testaaja")) {
				loydettyjenMaara++;
			}
		}
		
		assertTrue("Listalle lisaaminen ei onnistunut tarpeeksi hyvalla tuloksella.", loydettyjenMaara == 1);
	}
	
	/**
	 * Listalle lisaaminen lisaa tuloksen oikeaan kohtaan.
	 */
	@Test
	public void listalleLisataanOikeaanKohtaan() {
		this.ennatyslista.lisaaListalle(20, "Testaaja");
		
		String listasija = this.ennatyslista.annaListanSija(1);
		
		assertTrue("Listalle lisatessa tulos meni vaaraan kohtaan.", listasija.contains("20") && listasija.contains("Testaaja"));
	}
	
	/**
	 * Testaa osataanko lista jarjestaa niin, etta getPieninPistemaara toimii oikein ja niin ei esta muiden kuin pieninta pienempien tai yhta suurien tulosten listalle lisaamisen.
	 */
	@Test
	public void getPieninPistemaaraPalauttaaPienimmanListalta() {
		this.ennatyslista.lisaaListalle(140, "Testaaja140");
		this.ennatyslista.lisaaListalle(20, "Testaaja20");
		this.ennatyslista.lisaaListalle(110, "Testaaja110");
		this.ennatyslista.lisaaListalle(40, "Testaaja40");
		this.ennatyslista.lisaaListalle(50, "Testaaja50");
		this.ennatyslista.lisaaListalle(120, "Testaaja120");
		this.ennatyslista.lisaaListalle(30, "Testaaja30");
		this.ennatyslista.lisaaListalle(10, "Testaaja10");
		this.ennatyslista.lisaaListalle(150, "Testaaja150");
		this.ennatyslista.lisaaListalle(130, "Testaaja130");
		
		assertTrue("Listan pienin tulos oli 10 ja uusi tulos oli 15, mutta paasy listalle evattiin.", this.ennatyslista.paaseekoListalle(15));
		assertFalse("Listan pienin tulos oli 10 ja uusi tulos oli 10, mutta paasy listalle sallittiin.", this.ennatyslista.paaseekoListalle(10));
		assertFalse("Listan pienin tulos oli 10 ja uusi tulos oli 5, mutta paasy listalle sallittiin.", this.ennatyslista.paaseekoListalle(5));
	}
}

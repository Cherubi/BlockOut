/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.logiikka;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import peli.asetukset.logiikka.Ulottuvuudet;

/**
 *
 * @author Usagi-chan
 */
public class KenttaTest {
	private Kentta kentta;
	
	public KenttaTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		Ulottuvuudet ulottuvuudet = new Ulottuvuudet();
		this.kentta = new Kentta(null, null, ulottuvuudet);
	}
	
	
	
	/**
	 * Aluksi luotu kentta on tyhja, joten sen edusta on vapaana.
	 */
	@Test
	public void kentanEdustaAlussaOnTyhja() {
		assertTrue("Kentan edustan tulisi alussa olla tyhja.", kentta.onkoKentanEdustaVapaana());
	}
	
	/**
	 * Palikan jahmettaminen kentan eteen johtaisi ei-vapaaseen kentan edustaan.
	 */
	@Test
	public void kentanEdustaEiOleTyhjaKunSiihenOnJahmetettyPalikka() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		
		try {
			kentta.jahmetaPalikka(p, 3, 3, 0, 0);
		} catch (Exception e) {}
		
		assertFalse("Kentan edustan ei tulisi olla tyhja kun siihen on jahmetetty palikka.", kentta.onkoKentanEdustaVapaana());
	}
	
	/**
	 * Kentan edusta on tyhja kun edustan tayttanyt palikka on ollut koko kentan levyinen ja korkuinen ja kerros on tuhottu.
	 */
	@Test
	public void kentanEdustaTyhjaKunYlinKerrosTuhottu() {
		Palikka p = new Palikka(0, 0);
		for (int i=1; i<=5; i++) {
			for (int j=1; j<=5; j++) {
				p.lisaaPala(i, j, 3);
			}
		}
		
		try {
			kentta.jahmetaPalikka(p, 3, 3, 0, 0);
		} catch (Exception e) {}
		
		assertTrue("Kentan edustan tuli olla tyhja kun siihen on jahmetetty koko kentan levyinen ja korkuinen palikka ja se on tuhottu.", kentta.onkoKentanEdustaVapaana());
	}
	
	/**
	 * Mahtuuko pala tyhjaan kenttaan.
	 */
	public void mahtuukoPalaTyhjaanKenttaan() {
		assertTrue("Palikan olisi pitanyt mahtua tyhjaan kenttaan.", kentta.mahtuukoPalaKenttaan(1, 1, 7));
	}
	
	/**
	 * Mahtuuko pala kenttaan, jossa on eri kohdassa yksi pala.
	 */
	public void mahtuukoPalaMelkeinTyhjaanKenttaanVapaallePaikalle() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		
		try {
			kentta.jahmetaPalikka(p, 3, 3, 6, 0);
		} catch (Exception e) {}
		
		assertTrue("Palan olisi pitanyt mahtua kenttaan vaikka siella oli jo yksi pala.", kentta.mahtuukoPalaKenttaan(3, 3, 5));
	}
	
	/**
	 * Eihan pala mahdu kenttaan jo jahmetetyn palan paikalle.
	 */
	@Test
	public void eihanPalaMahduToisenPalanPaikalle() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		
		try {
			kentta.jahmetaPalikka(p, 3, 3, 6, 0);
		} catch (Exception e) {}
		
		assertFalse("Palan olisi pitanyt mahtua kenttaan vaikka siella oli jo yksi pala.", kentta.mahtuukoPalaKenttaan(3, 3, 6));
	}
	
	/**
	 * Aluksi luotu kentta on tyhja, joten varitettavia kerroksia on nolla.
	 */
	@Test
	public void aluksiVaritettaviaKerroksiaEiOle() {
		assertTrue("Aluksi varitettavia kerroksia ei olisi pitanyt olla.", kentta.annaPalojaSisaltavienKerrostenMaara() == 0);
	}
	
	/**
	 * Kun yksi pala on tiputettu pohjalle varitettavia kerroksia on yksi.
	 */
	public void varitattaviaKerroksiaYksiKunYksiTiputettuPohjalle() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		
		try {
			kentta.jahmetaPalikka(p, 3, 3, kentta.annaSyvyys()-1, 0);
		} catch (Exception e) {}
		
		assertTrue("Varitettavia kerroksia olisi pitanyt olla yksi.", kentta.annaPalojaSisaltavienKerrostenMaara() == 1);
	}
	
	/**
	 * Kun yksi kolmen korkea pala on tiputettu pohjalle varitettavia kerroksia on kolme.
	 */
	public void varitattaviaKerroksiaKolmeKunYksiKorkeaTiputettuPohjalle() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		p.lisaaPala(3, 3, 2);
		p.lisaaPala(3, 3, 1);
		
		try {
			kentta.jahmetaPalikka(p, 3, 3, kentta.annaSyvyys()-1, 0);
		} catch (Exception e) {}
		
		assertTrue("Varitettavia kerroksia olisi pitanyt olla kolme.", kentta.annaPalojaSisaltavienKerrostenMaara() == 3);
	}
}

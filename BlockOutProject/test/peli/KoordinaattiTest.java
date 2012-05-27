/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Usagi-chan
 */
public class KoordinaattiTest {
	private Koordinaatti koordinaatti1;
	private Koordinaatti koordinaatti2;
	private Koordinaatti koordinaatti2sama;
	
	public KoordinaattiTest() {
		this.koordinaatti1 = new Koordinaatti(6, 9, 2);
		
		this.koordinaatti2 = new Koordinaatti(5, 8, 1);
		this.koordinaatti2sama = new Koordinaatti(5, 8, 1);
		
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	
	
	/**
	 * Samojen koordinaattien tulee johtaa samaan hashKoodiin.
	 */
	@Test
	public void antaakoKaksiSamaaKoordinaattiaSamanHashkoodin() {
		assertTrue("Samojen koordinaattien olisi pitanyt johtaa samaan hashkoodiin.", koordinaatti2.hashCode() == koordinaatti2sama.hashCode());
	}
	
	/**
	 * Eri koordinaattien tulee johtaa eri hashKoodeihin
	 */
	@Test
	public void antaakoKaksiEriKoordinaattiaEriHashkoodin() {
		assertFalse("Samojen koordinaattien ei olisi pitanyt johtaa samaan hashkoodiin.", koordinaatti1.hashCode() == koordinaatti2.hashCode());
	}
	
	/**
	 * Kun toista samoista koordinaateista siirretaan eivat ne enaa saa samaa hashKoodia.
	 */
	@Test
	public void antaakoSamastaPisteestaSiirretytKoordinaatitEriHashKoodin() {
		koordinaatti2sama.asetaX(60);
		assertFalse("Kun samassa pisteessa olleista koordinaateista toista siirretaan tulee niille tulla eri hashKoodi.", koordinaatti2.hashCode() == koordinaatti2sama.hashCode());
	}
}

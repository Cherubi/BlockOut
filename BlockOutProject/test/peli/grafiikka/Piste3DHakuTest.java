/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.grafiikka;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import peli.Koordinaatti;

/**
 *
 * @author Usagi-chan
 */
public class Piste3DHakuTest {
	private Piste3DHaku piste;
	private int ikkunanLeveys, ikkunanKorkeus;
	
	public Piste3DHakuTest() {
		this.ikkunanLeveys = 400;
		this.ikkunanKorkeus = 300;
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		this.piste = new Piste3DHaku(ikkunanLeveys, 0, ikkunanKorkeus, 20, 5, 5, 10);
	}
	
	
	
	/**
	 * Testaa onko kaikki ehdotetut ikkunakoordinaatit jarkevalla valilla.
	 */
	@Test
	public void onkoEhdotetutIkkunakoordinaatitJarkevallaValilla() {
		Koordinaatti ikkunakoordinaatti = piste.koordinaatit(3, 4, 2, -1, -1, -1);
		
		assertTrue("Annetun x-ikkunakoordinaatin olisi pitanyt olla ikkunan sisalla.", ikkunakoordinaatti.annaX() > 0 && ikkunakoordinaatti.annaX() < ikkunanLeveys);
		assertTrue("Annetun y-ikkunakoordinaatin olisi pitanyt olla ikkunan sisalla.", ikkunakoordinaatti.annaY() > 0 && ikkunakoordinaatti.annaY() < ikkunanKorkeus);
	}
	
	/**
	 * Testaa onko syvemmalta ruudukosta pyydetyn ikkunakoordinaatit lahempaa ikkunan keskipistetta.
	 */
	@Test
	public void onkoSyvemmaltaRuudukostaPyydetynIkkunakoordinaatitLahempaaIkkunanKeskipistettaMuttaSamallaPuolellaSita() {
		Koordinaatti ik1 = piste.koordinaatit(3, 4, 2, -1, -1, -1);
		Koordinaatti ik2 = piste.koordinaatit(3, 4, 3, -1, -1, -1);
		
		assertTrue("Syvempaa haetun pisteen olisi pitanyt olla lahempana ikkunan keskipistetta (x).", Math.abs(ikkunanLeveys/2 - ik1.annaX()) > Math.abs(ikkunanLeveys/2 - ik2.annaX()) );
		assertTrue("Syvempaa haetun pisteen olisi pitanyt olla lahempana ikkunan keskipistetta (y).", Math.abs(ikkunanKorkeus/2 - ik1.annaY()) > Math.abs(ikkunanKorkeus/2 - ik2.annaY()) );
		
		assertTrue("Syvempaa haetun pisteen olisi pitanyt olla samalla puolella ikkunaa (x).", (ikkunanLeveys/2 - ik1.annaX()) * (ikkunanLeveys/2 - ik2.annaX()) > 0);
		assertTrue("Syvempaa haetun pisteen olisi pitanyt olla samalla puolella ikkunaa (y).", (ikkunanKorkeus/2 - ik1.annaY()) * (ikkunanKorkeus/2 - ik2.annaY()) > 0);
	}
	
	/**
	 * Testaa onko ruudun toisesta laidasta pyydetyn kulman ikkunakoordinaatti oikeassa suunnassa toiseen kulmaan nahden.
	 */
	@Test
	public void osaakoAntaaEriKulmatRuudusta() {
		Koordinaatti ik1 = piste.koordinaatit(3, 4, 2, -1, -1, -1);
		Koordinaatti ik2 = piste.koordinaatit(3, 4, 2, 1, -1, -1);
		
		assertTrue("Vasemmasta laidasta oikeaan laitaan siirryttaessa ikkunan x-koordinaatin olisi pitanyt kasvaa.", ik1.annaX() < ik2.annaX());
		assertTrue("Vasemmasta laidasta oikeaan laitaan siirryttaessa ikkunan y-koordinaatin ei olisi pitanyt muuttua.", ik1.annaY() == ik2.annaY());
		
		
		ik1 = piste.koordinaatit(3, 4, 2, -1, -1, -1);
		ik2 = piste.koordinaatit(3, 4, 2, -1, 1, -1);
		
		assertTrue("Ylalaidasta alalaitaan siirryttaessa ikkunan x-koordinaatin ei olisi pitanyt muuttua.", ik1.annaX() == ik2.annaX());
		assertTrue("Ylalaidasta alalaitaan siirryttaessa ikkunan y-koordinaatin olisi pitanyt kasvaa.", ik1.annaY() < ik2.annaY());
		
		
		ik1 = piste.koordinaatit(3, 4, 2, -1, -1, -1);
		ik2 = piste.koordinaatit(3, 4, 2, -1, -1, 1);
		
		assertTrue("Etulaidasta takalaitaan siirryttaessa ikkunan x-koordinaatin olisi pitanyt siirtya lahemmas ikkunan keskipistetta.", Math.abs(ikkunanLeveys/2 - ik1.annaX()) > Math.abs(ikkunanLeveys/2 - ik2.annaX()) );
		assertTrue("Etulaidasta takalaitaan siirryttaessa ikkunan y-koordinaatin olisi pitanyt siirtya lahemmas ikkunan keskipistetta.", Math.abs(ikkunanKorkeus/2 - ik1.annaY()) > Math.abs(ikkunanKorkeus/2 - ik2.annaY()) );
	}
	
	//Pyorityksia tarkastellessa testit saa nayttamaan oikein ihan vaan paattamalla, etta oli ajatellut pyorimissuunnan vaarinpain, joten testit ei oikeastaan testaisi mitaan.
}

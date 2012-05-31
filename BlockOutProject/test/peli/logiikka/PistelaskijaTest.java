/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.logiikka;

import kayttoliittyma.BlockOut;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import peli.Peli;
import peli.asetukset.logiikka.Asetukset;
import peli.asetukset.PelinAsetukset;
import peli.ennatyslista.Ennatyslistaaja;

/**
 *
 * @author Usagi-chan
 */
public class PistelaskijaTest {
	private Pistelaskija pistelaskija;
	
	public PistelaskijaTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		Peli peli = null;
		try {
			BlockOut blockOut = new BlockOut();
			peli = new Peli(blockOut, new Asetukset(new PelinAsetukset(blockOut, "tiedosto")), new Ennatyslistaaja(), 0, 0);
		} catch (Exception e) {}
		this.pistelaskija = new Pistelaskija(peli, 6, Palikkasetti.FLAT);
	}
	
	
	
	/**
	 * Testaa ettei annetut pisteet ole negatiivisia.
	 */
	@Test
	public void annetutPisteetEivatOleNegatiivisia() {
		Palikkavarasto palikkavarasto = new Palikkavarasto(Palikkasetti.FLAT);
		Palikka p = palikkavarasto.annaPalikka();
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 0, 0, false);
		} catch (Exception e) {}
		
		assertTrue("Annettujen pisteiden ei olisi pitanyt olla negatiivisia vaikka suoritus olikin huono. Palikan alapisteet olivat " + p.annaAlapisteet() + " ja annetut pisteet olivat " + pistelaskija.annaPisteet(), pistelaskija.annaPisteet() > 0);
	}
	
	/**
	 * Testaa, etta ylempaa tiputtaminen antaa enemman pisteita.
	 */
	@Test
	public void ylempaaTiputtaminenAntaaEnemmanPisteita() {
		Palikkavarasto palikkavarasto = new Palikkavarasto(Palikkasetti.FLAT);
		Palikka p = palikkavarasto.annaPalikka();
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 0, 0, false);
		} catch (Exception e) {}
		
		int ekatPisteet = pistelaskija.annaPisteet();
		
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 2, 0, false);
		} catch (Exception e) {}
		
		int tokatPisteet = pistelaskija.annaPisteet() - ekatPisteet;
		
		assertTrue("Ylempaa tiputtamisesta olisi pitanyt tulla enemman pisteita.", tokatPisteet > ekatPisteet);
	}
	
	/**
	 * Kerroksen tayttamisesta pitaisi tulla pisteita.
	 */
	@Test
	public void kerroksestaAnnetuttujenPisteidenPitaaOllaSuurempiaKuinNollaTaiPalikastaAnnettujenPisteiden() {
		Palikkavarasto palikkavarasto = new Palikkavarasto(Palikkasetti.FLAT);
		Palikka p = palikkavarasto.annaPalikka();
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 0, 0, false);
		} catch (Exception e) {}
		
		int kerroksetonTulos = pistelaskija.annaPisteet();
		
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 0, 1, false);
		} catch (Exception e) {}
		
		int kerroksenTulos = pistelaskija.annaPisteet() - 2*kerroksetonTulos;
		
		assertTrue("Kerroksesta olisi pitanyt tulla pisteita.", kerroksenTulos > 0);
		assertTrue("Kerroksesta pitaisi tulla enemman pisteita kuin palikasta", kerroksenTulos > kerroksetonTulos);
	}
	
	/**
	 * Tyhjasta kentasta pitaisi tulla enemman pisteita kuin nolla.
	 */
	@Test
	public void tyhjastaKentastaTuleePisteita() {
		Palikkavarasto palikkavarasto = new Palikkavarasto(Palikkasetti.FLAT);
		Palikka p = palikkavarasto.annaPalikka();
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 0, 0, false);
		} catch (Exception e) {}
		
		int normaaliTulos = pistelaskija.annaPisteet();
		
		try {
			pistelaskija.annaPisteitaTiputuksesta(p, 0, 0, true);
		} catch (Exception e) {}
		
		int tyhjaTulos = pistelaskija.annaPisteet() - 2*normaaliTulos;
		
		assertTrue("Tyhjasta kentasta olisi pitanyt saada pisteita.", tyhjaTulos > 0);
	}
	
}

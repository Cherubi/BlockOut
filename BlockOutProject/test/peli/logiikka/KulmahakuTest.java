/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.logiikka;

import java.util.ArrayList;
import java.util.HashMap;
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
public class KulmahakuTest {
	private Kulmahaku kulma;
	
	public KulmahakuTest() {
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
	 * Yksinkertaisessa kuutiossa tulisi olla 12 sarmaa.
	 */
	@Test
	public void yksinkertaisessaKuutiossaTulisiOlla12Sarmaa() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		this.kulma = new Kulmahaku(p.annaPalikka());
		
		HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat = kulma.haeSarmat();
		assertTrue("Kulmahaku antoi vaaran maaran sarmia kuutiolle.", suorienMaara(sarmat) == 12);
	}
	
	/**
	 * Yksinkertaisessa isommassa kuutiossa tulisi olla 12 sarmaa.
	 */
	@Test
	public void yksinkertaisessaIsommassaKuutiossaTulisiOlla12Sarmaa() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		p.lisaaPala(3, 4, 3);
		p.lisaaPala(4, 3, 3);
		p.lisaaPala(4, 4, 3);
		this.kulma = new Kulmahaku(p.annaPalikka());
		
		HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat = kulma.haeSarmat();
		assertTrue("Kulmahaku antoi vaaran maaran sarmia isommalle kuutiolle.", suorienMaara(sarmat) == 12);
	}
	
	/**
	 * L:ssa tulisi olla 18 sarmaa.
	 */
	@Test
	public void lKuutiossaTulisiOlla18Sarmaa() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		p.lisaaPala(4, 3, 3);
		p.lisaaPala(5, 3, 3);
		p.lisaaPala(5, 4, 3);
		this.kulma = new Kulmahaku(p.annaPalikka());
		
		HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat = kulma.haeSarmat();
		assertTrue("Kulmahaku antoi vaaran maaran sarmia l-kuutiolle. Annettuja sarmia oli " + suorienMaara(sarmat), suorienMaara(sarmat) == 18);
	}
	
	/**
	 * Tayspitkassa suorassa on 12 sarmaa.
	 */
	@Test
	public void yksinkertaisessaPitkassaKuutiossaTulisiOlla12Sarmaa() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		p.lisaaPala(4, 3, 3);
		p.lisaaPala(5, 3, 3);
		p.lisaaPala(2, 3, 3);
		p.lisaaPala(1, 3, 3);
		this.kulma = new Kulmahaku(p.annaPalikka());
		
		HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat = kulma.haeSarmat();
		assertTrue("Kulmahaku antoi vaaran maaran sarmia tayspitkalle kuutiolle.", suorienMaara(sarmat) == 12);
	}
	
	/**
	 * Laskee sarma-hashMapin perusteella sarmien maaran. HashMapissa on alkupisteet yhdistettyina listaan loppupisteita.
	 * 
	 * @param sarmat Annetut sarmat
	 * @return Sarmien maara
	 */
	public int suorienMaara(HashMap<Koordinaatti, ArrayList<Koordinaatti>> sarmat) {
		int maara = 0;
		for (ArrayList<Koordinaatti> loppupisteet : sarmat.values()) {
			maara += loppupisteet.size();
		}
		
		return maara;
	}
}

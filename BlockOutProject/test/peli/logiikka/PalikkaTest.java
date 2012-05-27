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
public class PalikkaTest {
	
	public PalikkaTest() {
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
	 * Palan lisaaminen palikkaan lisaa palan.
	 */
	@Test
	public void palanLisaaminenPalikkaanLisaaPalan() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		
		Pala[][][] luotuPalikka = p.annaPalikka();
		
		Pala[] tyhjaRivi = {Pala.TYHJA, Pala.TYHJA, Pala.TYHJA, Pala.TYHJA, Pala.TYHJA};
		Pala[][] tyhjaKerros = {tyhjaRivi, tyhjaRivi, tyhjaRivi, tyhjaRivi, tyhjaRivi};
		Pala[][][] vertailuPalikka = {tyhjaKerros, tyhjaKerros, {tyhjaRivi, tyhjaRivi, {Pala.TYHJA, Pala.TYHJA, Pala.TIPPUVA, Pala.TYHJA, Pala.TYHJA}, tyhjaRivi, tyhjaRivi}, tyhjaKerros, tyhjaKerros};
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k < 5; k++) {
				
					if (luotuPalikka[i][j][k] != vertailuPalikka[i][j][k]) {
						fail("Luotu palikka ei ollut halutunlainen.");
					}
				
				}
			}
		}
	}
	
	/**
	 * Palikan kopioiminen luo oikeanlaisen palikan.
	 */
	@Test
	public void palikanKopioiminenLuoSamanlaisenPalikan() {
		Palikka p = new Palikka(0, 0);
		p.lisaaPala(3, 3, 3);
		
		Palikka pKopio = p.kopioi();
		Pala[][][] luotuPalikka = pKopio.annaPalikka();
		
		
		Pala[] tyhjaRivi = {Pala.TYHJA, Pala.TYHJA, Pala.TYHJA, Pala.TYHJA, Pala.TYHJA};
		Pala[][] tyhjaKerros = {tyhjaRivi, tyhjaRivi, tyhjaRivi, tyhjaRivi, tyhjaRivi};
		Pala[][][] vertailuPalikka = {tyhjaKerros, tyhjaKerros, {tyhjaRivi, tyhjaRivi, {Pala.TYHJA, Pala.TYHJA, Pala.TIPPUVA, Pala.TYHJA, Pala.TYHJA}, tyhjaRivi, tyhjaRivi}, tyhjaKerros, tyhjaKerros};
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k < 5; k++) {
				
					if (luotuPalikka[i][j][k] != vertailuPalikka[i][j][k]) {
						fail("Kopioitu palikka ei ollut halutunlainen.");
					}
				
				}
			}
		}
	}
	
	/**
	 * Palikka tietaa sisaltamiensa palojen maaran.
	 */
	@Test
	public void palikkaTietaaSisaltamiensaPalojenMaaran() {
		Palikka p = new Palikka(0, 0);
		
		assertTrue("Alussa palikassa ei olisi pitanyt olla yhtaan palaa.", p.annaPalojenMaara() == 0);
		
		p.lisaaPala(3, 3, 3);
		assertTrue("Lisayksen jalkeen palikassa olisi pitanyt olla yksi pala.", p.annaPalojenMaara() == 1);
		
		p.lisaaPala(3, 3, 2);
		assertTrue("Kahden lisayksen jalkeen palikassa olisi pitanyt olla kaksi palaa.", p.annaPalojenMaara() == 2);
		
		p.lisaaPala(3, 3, 3);
		assertTrue("Palojen maaran ei olisi pitanyt kasvaa kun jo olemassa olevan palan paalle lisattiin toinen pala.", p.annaPalojenMaara() == 2);
	}
	
	/**
	 * Palikan tulee antaa oikea maara sarmia sen piirtamiseen.
	 */
	@Test
	public void palikkaTietaaSisaltamiensaSarmienMaaran() {
		Palikka p = new Palikka(0, 0);
		
		assertTrue("Alkuun palikassa ei tulisi olla yhtaan sarmaa.", suorienMaara(p.annaSuorat()) == 0);
		
		p.lisaaPala(3, 3, 3);
		assertTrue("Palan lisayksen jalkeen olisi pitanyt olla 12 sarmaa.", suorienMaara(p.annaSuorat()) == 12);
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
	
	//Palan pyorittaminen menee turhan hankalaksi. Testataan graafisesti.
}

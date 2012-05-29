/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.asetukset;

import peli.asetukset.logiikka.Asetukset;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Usagi-chan
 */
public class AsetuksetTest {
	private Asetukset asetukset;
	
	public AsetuksetTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		PelinAsetukset pelinAsetukset = new PelinAsetukset(null, "testiasetukset.javafile");
		this.asetukset = new Asetukset(pelinAsetukset);
	}
	
	
	
	// Luokka oli niin taynna gettereita, settereita ja oikeiden tiedostojen kanssa pyorittelemista ettei sita ollut mielekasta testata.
	
}

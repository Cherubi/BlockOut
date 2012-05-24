/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli.ennatyslista;

import java.io.File;
import org.junit.*;
import static org.junit.Assert.*;
import peli.logiikka.Palikkasetti;

/**
 *
 * @author Usagi-chan
 */
public class EnnatyslistatTest {
	private Ennatyslistat ennatyslistat;
	
	public EnnatyslistatTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		try {
			this.ennatyslistat = new Ennatyslistat("osoite");
		} catch (Exception e) {
			//tiedosto ei loydy
		}
	}
	
	@After
	public void tearDown() {
		File tiedosto = new File("osoite");
		if (tiedosto.exists()) {
			tiedosto.delete();
		}
	}
	
	
	
	/**
	 * Listalle sallitaan hyva tulos.
	 */
	@Test
	public void listalleSallitaanHyvaTulos() {
		String lista = "";
		for (int i = 1; i <= 10; i++) {
			lista += this.ennatyslistat.annaListanSija(i, 5, 5, 8, Palikkasetti.FLAT) + "\n";
		}
		
		assertTrue("Tuloksen olisi pitanyt paatya listalle.\n" + lista , this.ennatyslistat.paaseekoListalle(10, 5, 5, 8, Palikkasetti.FLAT));
		
		assertTrue("Toinen lista olisi kieltanyt paasyn, mutta talla pistelistalla paasy olisi tullut sallia tulokselle.", this.ennatyslistat.paaseekoListalle(10, 3, 3, 6, Palikkasetti.FLAT));
	}
	
	/**
	 * Listalle ei sallita huonoa tulosta.
	 */
	@Test
	public void listalleEiSallitaHuonoaTulosta() {
		assertFalse("Listalle ei olisi pitanyt sallia tulosta, joka oli huono.", this.ennatyslistat.paaseekoListalle(-10, 5, 5, 8, Palikkasetti.FLAT));
	}
	
	/**
	 * Listalle lisaaminen onnistuu.
	 */
	@Test
	public void listalleLisaaminenOnnistuu() {
		for (int i = 1; i <= this.ennatyslistat.annaListanSijojenMaara(); i++) {
			this.ennatyslistat.lisaaListalle(10, "Nimi", 5, 5, 8, Palikkasetti.FLAT);
		}
		
		assertFalse("Listalla tuli olla listan verran parempia tuloksia, joten huonomman tuloksen ei olisi tullut paasta listalle.", this.ennatyslistat.paaseekoListalle(5, 5, 5, 8, Palikkasetti.FLAT));
	}
	
	/**
	 * Lista osaa antaa listasijan.
	 */
	@Test
	public void listaOsaaAntaaListasijan() {
		int sijoja = this.ennatyslistat.annaListanSijojenMaara();
		
		for (int i = 1; i <= sijoja; i++) {
			this.ennatyslistat.lisaaListalle(i*10, "Nimi", 5, 5, 8, Palikkasetti.FLAT);
		}
		
		for (int i = 1; i <= sijoja; i++) {
			String listasija = this.ennatyslistat.annaListanSija(i, 5, 5, 8, Palikkasetti.FLAT);
			assertTrue("Listasijaa ei osattu antaa.", listasija.contains( (sijoja+1-i)*10 + "" ) && listasija.contains("Nimi") );
		}
	}
}

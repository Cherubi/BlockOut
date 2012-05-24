package peli.ennatyslista;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class Saatopaneli extends JPanel {
	private Ennatyslistaaja ennatyslistaaja;
	private JPanel parametrivalinnat, arvovalinnat;
	
	/**
	* Saataa ennatyslistanakymassa nakyvaa kuilukohtaista ennatyslistaa sen mukaisesti minka kokoisen ja palikkasettisen kuilun ennatyslistaa kayttajaa haluaa katsella.
	* 
	* @param ennatyslistaaja Ennatyslistaaja, jonka nayttamia ennatyslistoja vaihdellaan
	*/
	public Saatopaneli(Ennatyslistaaja ennatyslistaaja) {
		
		this.ennatyslistaaja = ennatyslistaaja;
		
		luoKomponentit();
	}
	
	private void luoKomponentit() {
		this.setLayout(new GridLayout(1,2));
		
		this.parametrivalinnat = new JPanel();
		parametrivalinnat.setOpaque(false);
		this.add(parametrivalinnat);
		
		
		this.arvovalinnat = new JPanel();
		arvovalinnat.setOpaque(false);
		this.add(arvovalinnat);
	}
}
package valmiskomponentit;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class Ikkuna extends JPanel {
	/**
	* JPanel, jolla on valkoiset reunukset
	*/
	public Ikkuna() {
		super();
	}
	
	/**
	* JPanel, jolla on valkoiset reunukset ja valmiiksi maaritetty asettelu
	* 
	* @param layout JPanelin asettelu
	*/
	public Ikkuna(LayoutManager layout) {
		super(layout);
	}
	
	/**
	* Piirtaa komponentin ja sille valkoiset reunat
	* 
	* @param g Grafiikka
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		varitaReunat(g);
	}
	
	/**
	* Varittaa JPanelin reunat
	* 
	* @param g Grafiikka
	*/
	private void varitaReunat(Graphics g) {
		g.setColor(Color.WHITE);
		
		Random arpoja = new Random();
		
		//ylempi vaakaviiva
		int ekaYlitys = arpoja.nextInt(4)+1;
		int tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(5-tokaYlitys, 5, getWidth()-2*5+ekaYlitys+tokaYlitys, 1);
		
		//alempi vaakaviiva
		ekaYlitys = arpoja.nextInt(4)+1;
		tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(5-tokaYlitys, getHeight()-5, getWidth()-2*5+ekaYlitys+tokaYlitys, 1);
		
		//vasen pystyviiva
		ekaYlitys = arpoja.nextInt(4)+1;
		tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(5, 5-ekaYlitys, 1, getHeight()-2*5+ekaYlitys+tokaYlitys);
		
		//oikea pystyviiva
		ekaYlitys = arpoja.nextInt(4)+1;
		tokaYlitys = arpoja.nextInt(4)+1;
		g.drawRect(getWidth()-5, 5-ekaYlitys, 1, getHeight()-2*5+ekaYlitys+tokaYlitys);
	}
}
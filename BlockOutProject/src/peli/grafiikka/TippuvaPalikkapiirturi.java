package peli.grafiikka;

import peli.Koordinaatti;
import peli.logiikka.TippuvaPalikka;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Graphics;

public class TippuvaPalikkapiirturi {
	private Piste3DHaku piste;
	
	/**
	* Osaa piirtaa tippuvan palikan. Osaa piirtaa sen myos sen pyoriessa.
	* 
	* @param piste3DHaku Kuilun koordinaattien ikkunakoordinaateiksi muuntaja
	*/
	public TippuvaPalikkapiirturi(Piste3DHaku piste3DHaku) {
		this.piste = piste3DHaku;
	}
	
	/**
	* Piirtaa tippuvan palikan aariviivat.
	* 
	* @param g Graphics
	* @param tippuvaPalikka Piirrettava palikka, joka tietaa sijaintinsa
	*/
	public void piirra(Graphics g, TippuvaPalikka tippuvaPalikka) {
		g.setColor(Color.WHITE);
		
		HashMap<Koordinaatti, ArrayList<Koordinaatti>> suorat = tippuvaPalikka.annaPalikka().annaSuorat();
		
		for (Koordinaatti lahtopiste : suorat.keySet()) {
			for (Koordinaatti paatepiste : suorat.get(lahtopiste)) {
				piirraSuora(g, tippuvaPalikka, lahtopiste, paatepiste);
			}
		}
	}
	
	private void piirraSuora(Graphics g, TippuvaPalikka tippuvaPalikka, Koordinaatti palikanLahtopiste, Koordinaatti palikanPaatepiste) {
		
		Koordinaatti ikkunanLahtopiste = selvitaIkkunaKoordinaatti( palikanLahtopiste, tippuvaPalikka );
		Koordinaatti ikkunanPaatepiste = selvitaIkkunaKoordinaatti( palikanPaatepiste, tippuvaPalikka );
		
		g.drawLine( ikkunanLahtopiste.annaX(), ikkunanLahtopiste.annaY(), ikkunanPaatepiste.annaX(), ikkunanPaatepiste.annaY() );
	}
	
	private Koordinaatti selvitaIkkunaKoordinaatti(Koordinaatti kuiluKoordinaatti, TippuvaPalikka tippuvaPalikka) {
		int palikanX = tippuvaPalikka.annaX();
		int palikanY = tippuvaPalikka.annaY();
		int palikanZ = tippuvaPalikka.annaZ();
		
		//TODO korjaa?
		int keskipiste = tippuvaPalikka.annaPalikka().annaKeskipiste();
		int x = kuiluKoordinaatti.annaX() + palikanX - keskipiste;
		int y = kuiluKoordinaatti.annaY() + palikanY - keskipiste;
		int z = kuiluKoordinaatti.annaZ() + palikanZ - keskipiste;
		
		int dAlfaXY = tippuvaPalikka.annaXYKulma();
		int dAlfaYZ = tippuvaPalikka.annaYZKulma();
		int dAlfaXZ = tippuvaPalikka.annaXZKulma();
		
		return piste.koordinaatit(x, y, z, palikanX, palikanY, palikanZ, dAlfaXY, dAlfaYZ, dAlfaXZ);
	}
}
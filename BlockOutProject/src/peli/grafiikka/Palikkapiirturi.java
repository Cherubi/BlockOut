package peli.grafiikka;

import peli.Koordinaatti;
import peli.logiikka.Pala;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Palikkapiirturi{
	private Piste3DHaku piste;
	private ArrayList<Color> varit;
	private Color kerroksenVari;
	
	/**
	* Piirtaa kuilun pohjalle tippuneet palikat.
	* 
	* @param piste3DHaku Kuilun koordinaattien perusteella ikkunakoordinaatit antava luokka
	* @param varit Lista vareja, joilla eri kerrokset halutaan varittaa
	*/
	public Palikkapiirturi(Piste3DHaku piste3DHaku, ArrayList<Color> varit) {
		this.piste = piste3DHaku;
		this.varit = varit;
	}
	
	/**
	* Asettaa kayttajan valitsemat uudet varit kerroksille
	* 
	* @param uudetVarit Uusi lista vareja, joilla eri kerrokset halutaan varittaa
	*/
	public void asetaUudetVarit(ArrayList<Color> uudetVarit) {
		this.varit = uudetVarit;
	}
	
	/**
	* Piirtaa kuilun pohjalle tippuneet palikat.
	* 
	* @param g Graphics
	* @param kentta Piirrettava kentta
	*/
	public void piirra(Graphics g, Pala[][][] kentta) {
		int leveys = kentta.length;
		int korkeus = kentta[0].length;
		int syvyys = kentta[0][0].length;
		
		int kerros = 1;
		for (int k = syvyys-2; k >= 0; k--) {
			valitseVariKerrokselle(kerros);
			
			//TODO joskus: olisi ollut fiksumpi, etta taulukosta voisi ottaa syvyyden kentta[k], mutta nyt tuo antaisi pystyslicen tietylta leveydelta
			piirraKerroksenPalojenSivutahkotLaidoistaKeskelle(g, leveys, korkeus, k, kentta);
			piirraKerroksenPalojenPaallitahkot(g, leveys, korkeus, k, kentta);
			
			kerros++;
		}
	}
	
	private void valitseVariKerrokselle(int kerros) {
		while (kerros > varit.size()) {
			kerros = kerros - varit.size();
		}
		
		kerroksenVari = varit.get(kerros-1);
	}
	
	//jarjestys niin, ettei mitaan piirreta paallemman paalle
	private void piirraKerroksenPalojenSivutahkotLaidoistaKeskelle( Graphics g, int leveys, int korkeus, int k, Pala[][][] kentta) {
		for (int j = 1; j < korkeus/2-0.5; j++) {
			piirraKerroksenPalojenSivutahkotSivulaidoistaKeskelle( g, leveys, j, k, kentta );
		}
		for (int j = korkeus-2; j >= korkeus/2-0.5; j--) {
			piirraKerroksenPalojenSivutahkotSivulaidoistaKeskelle( g, leveys, j, k, kentta );
		}
	}
	
	//jarjestys niin, ettei mitaan piirreta paallemman paalle
	private void piirraKerroksenPalojenSivutahkotSivulaidoistaKeskelle( Graphics g, int leveys, int j, int k, Pala[][][] kentta ) {
		for (int i = leveys-2; i > leveys/2; i--) {
			if (kentta[i][j][k] == Pala.VARATTU) {
				piirraSivutahkot(g, i, j, k, leveys, kentta[0].length);
			}
		}
		for (int i = 1; i <= leveys/2; i++) {
			if (kentta[i][j][k] == Pala.VARATTU) {
				piirraSivutahkot(g, i, j, k, leveys, kentta[0].length);
			}
		}
	}
	
	private void piirraKerroksenPalojenPaallitahkot(Graphics g, int leveys, int korkeus, int k, Pala[][][] kentta) {
		for (int j = 1; j < korkeus-1; j++) {
			for (int i = 1; i < leveys-1; i++) {
				
				if (kentta[i][j][k] == Pala.VARATTU) {
					piirraTahko(g, i, j, k, 0, 0, -1);
				}
				
			}
		}
	}
	
	//valinnat niin, etta vain mahdollisesti nakyvat tahkot piirretaan
	private void piirraSivutahkot(Graphics g, int i, int j, int k, int leveys, int korkeus) {
		if (i < leveys/2.0) {
			piirraTahko(g, i, j, k, 1, 0, 0);
		}
		else {
			piirraTahko(g, i, j, k, -1, 0, 0);
		}
		
		if (j < korkeus/2.0-1) {
			piirraTahko(g, i, j, k, 0, 1, 0);
		}
		else if (j >= korkeus/2.0) {
			piirraTahko(g, i, j, k, 0, -1, 0);
		}
	}
	
	private void piirraTahko(Graphics g, int i, int j, int k, int xSivu, int ySivu, int zSivu) {
		ArrayList<Koordinaatti> kulmat = haeTahkonKulmat(i, j, k, xSivu, ySivu, zSivu);
		
		Polygon pol = new Polygon();
		pol.addPoint(kulmat.get(0).annaX(), kulmat.get(0).annaY());
		pol.addPoint(kulmat.get(1).annaX(), kulmat.get(1).annaY());
		pol.addPoint(kulmat.get(3).annaX(), kulmat.get(3).annaY());
		pol.addPoint(kulmat.get(2).annaX(), kulmat.get(2).annaY());
		
		g.setColor(kerroksenVari);
		g.fillPolygon(pol);
		g.setColor(Color.BLACK);
		g.drawPolygon(pol);
	}
	
	private ArrayList<Koordinaatti> haeTahkonKulmat(int i, int j, int k, int xSivu, int ySivu, int zSivu) {
		if (xSivu != 0) {
			return haeTahkonVasemmanTaiOikeanPuolenKulmat(i, j, k, xSivu);
		}
		else if (ySivu != 0) {
			return haeTahkonYlaTaiAlapuolenKulmat(i, j, k, ySivu);
		}
		else if (zSivu != 0) {
			return haeTahkonEtuTaiTakapuolenKulmat(i, j, k, zSivu);
		}
		
		return null;
	}
	
	private ArrayList<Koordinaatti> haeTahkonVasemmanTaiOikeanPuolenKulmat(int i, int j, int k, int xSivu) {
		ArrayList<Koordinaatti> kulmat = new ArrayList<Koordinaatti>();
		
		for (int ySivu = 1; ySivu >= -1; ySivu = ySivu-2) {
			for (int zSivu = -1; zSivu <= 1; zSivu = zSivu+2) {
				kulmat.add( piste.koordinaatit(i, j, k, xSivu, ySivu, zSivu) );
			}
		}
		
		return kulmat;
	}
	
	private ArrayList<Koordinaatti> haeTahkonYlaTaiAlapuolenKulmat(int i, int j, int k, int ySivu) {
		ArrayList<Koordinaatti> kulmat = new ArrayList<Koordinaatti>();
		
		for (int xSivu = 1; xSivu >= -1; xSivu = xSivu-2) {
			for (int zSivu = -1; zSivu <= 1; zSivu = zSivu+2) {
				kulmat.add( piste.koordinaatit(i, j, k, xSivu, ySivu, zSivu) );
			}
		}
		
		return kulmat;
	}
	
	private ArrayList<Koordinaatti> haeTahkonEtuTaiTakapuolenKulmat(int i, int j, int k, int zSivu) {
		ArrayList<Koordinaatti> kulmat = new ArrayList<Koordinaatti>();
		
		for (int xSivu = 1; xSivu >= -1; xSivu = xSivu-2) {
			for (int ySivu = 1; ySivu >= -1; ySivu = ySivu-2) {
				kulmat.add( piste.koordinaatit(i, j, k, xSivu, ySivu, zSivu) );
			}
		}
		
		return kulmat;
	}
}
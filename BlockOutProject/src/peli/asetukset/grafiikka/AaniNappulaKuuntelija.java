package peli.asetukset.grafiikka;

import peli.asetukset.logiikka.Asetukset;
import valmiskomponentit.Nappula;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AaniNappulaKuuntelija implements ActionListener {
	private Nappula nappula;
	private Asetukset asetukset;
	
	public AaniNappulaKuuntelija (Nappula nappula, Asetukset asetukset) {
		this.nappula = nappula;
		this.asetukset = asetukset;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (nappula.getText().contains("p��ll�")) {
			asetukset.asetaAanet(false);
			nappula.setText("��net poissa");
		}
		else {
			asetukset.asetaAanet(true);
			nappula.setText("��net p��ll�");
		}
	}
}
package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private int maxYears;
	private int maxHours;
	private List<PowerOutage> outagesByNerc;
	List<PowerOutage> powerOutagesMax;
	private int affettiMax;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
		
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public int getMaxYears() {
		return maxYears;
	}

	public void setMaxYears(int maxYears) {
		this.maxYears = maxYears;
	}

	public int getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(int maxHours) {
		this.maxHours = maxHours;
	}
	
	public List<PowerOutage> getPowerOutagesMax(int maxHours, int maxYears, Nerc nerc){
		
		outagesByNerc = new ArrayList<>(this.podao.getOutagesByNerc(nerc));
		Collections.sort(outagesByNerc);
		int contaOre = 0; 
		affettiMax = 0;
		powerOutagesMax = new ArrayList<>();
		List<PowerOutage> parziale = new ArrayList<>();
		ricorsione(maxHours, maxYears, contaOre, parziale);
		
		return this.powerOutagesMax;	
	}
	
	
	private void ricorsione(int maxHours, int maxYears, int contaOre, List<PowerOutage> parziale){
		
		
		if(parziale.size() == outagesByNerc.size()) {
			int affetti = this.calcolaAffetti(parziale);
			if(contaOre <= maxHours*60 && affetti > affettiMax) {
				affettiMax = affetti;
				this.powerOutagesMax = new ArrayList<>(parziale);
				return;
			}
		}
		if(contaOre >= maxHours*60) {
			int affetti = this.calcolaAffetti(parziale);
			if(contaOre > maxHours*60)
				return;
			else if(contaOre == maxHours*60 && affetti > affettiMax) {
				affettiMax = affetti;
				this.powerOutagesMax = new ArrayList<>(parziale);
				return;
			}
		}
		if(contaOre <= maxHours*60 && this.calcolaAffetti(parziale) > affettiMax) {
			affettiMax = this.calcolaAffetti(parziale);
			this.powerOutagesMax = new ArrayList<>(parziale);
		}
		else {
			for(PowerOutage po: outagesByNerc) {
				if(!parziale.contains(po)) {	
					parziale.add(po);
					contaOre += po.getDurata();
					if(this.controllaAnno(parziale, po, maxYears)) {
						ricorsione(maxHours, maxYears, contaOre, parziale);
					}
					parziale.remove(po);
					contaOre -= po.getDurata();
				}
			}
		}	
		return;
	}

	private boolean controllaAnno(List<PowerOutage> parziale, PowerOutage po, int maxYears) {
	
		if(parziale.size()>=2) { //controlla differenza più recente - più vecchio
			int recente = po.getYear();
			int vecchio = parziale.get(0).getYear();
			if((recente - vecchio) <= maxYears) { // va bene
				return true;	
			}
			else 
				return false;
		}
		
		return true;
	}

	public int calcolaOreTotali(List<PowerOutage> parziale) {
		int ore = 0;
		for(PowerOutage po: parziale) {
			ore += po.getDurata();
		}
		return ore;
	}
	
	public int calcolaAffetti(List<PowerOutage> parziale) {
		int affetti = 0;
		for(PowerOutage po: parziale) {
			affetti += po.getCustomersAffected();
		}
		return affetti;
	}	
}


package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.setMaxHours(100);
		model.setMaxYears(4);
		//System.out.println("Totale clienti coinvolti: " + model.calcolaAffetti(model.powerOutagesMax));
		//System.out.println("Totale ore blackout: " + model.calcolaOreTotali(model.powerOutagesMax));
		System.out.println(model.getNercList());

	}

}

package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage implements Comparable<PowerOutage>{

	private int id;
	private Nerc nerc;
	private int customersAffected;
	private LocalDateTime outageStart;
	private LocalDateTime outageEnd;
	private int year;
	
	public PowerOutage(int id, Nerc nerc, int customersAffected, LocalDateTime outageStart, LocalDateTime outageEnd) {
		this.id = id;
		this.nerc = nerc;
		this.customersAffected = customersAffected;
		this.outageStart = outageStart;
		this.outageEnd = outageEnd;
		this.year = outageStart.getYear();
	}
	
	public long getDurata() {
		return LocalDateTime.from(outageStart).until(outageEnd, ChronoUnit.MINUTES);
	}
	
	
	public int getYear() {
		return this.year;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Nerc getNerc() {
		return nerc;
	}
	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}
	public int getCustomersAffected() {
		return customersAffected;
	}
	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}
	public LocalDateTime getOutageStart() {
		return outageStart;
	}
	public void setOutageStart(LocalDateTime outageStart) {
		this.outageStart = outageStart;
	}
	public LocalDateTime getOutageEnd() {
		return outageEnd;
	}
	public void setOutageEnd(LocalDateTime outageEnd) {
		this.outageEnd = outageEnd;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	@Override
	public int compareTo(PowerOutage arg0) {
		return this.getOutageStart().compareTo(arg0.getOutageStart());
	}

	@Override
	public String toString() {
		return "" + this.getYear() +" "+outageStart.toString()+" "+outageEnd.toString()+" "+this.getDurata()+ "min "+this.customersAffected;
	}
	
	
	
}

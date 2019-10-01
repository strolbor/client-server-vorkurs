package com.jds.vorkurs.shared;

import de.urs.game.schereSteinPapier.Dinge;

public class Player {
	private String id="0";
	private String name="Namenlos";
	Dinge auswahl = Dinge.Error;
	
	public Dinge getAuswahl() {
		return auswahl;
	}
	public void setAuswahl(Dinge auswahl) {
		this.auswahl = auswahl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

package main;

import java.awt.event.KeyEvent;

public class Player {
	
	public String name = new String();
	public int spiele;
	public int gewonnen;
	public int verloren;
	public int schere;
	public int stein;
	public int papier;
	//Hier müssen noch alle Attribute aus der Datenbank zugeweisen werden. Die Attribute müssen alle im Konstruktor festgelegt werden.
	
	
	public Player(String name) {
		this.name = name;
		spiele = 0;
		gewonnen = 0;
		verloren = 0;
		schere = 0;
		stein = 0;
		papier = 0;
	}
	
	public String getName() {
		return name;
	}
}

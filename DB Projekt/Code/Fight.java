package main;

import java.awt.event.KeyEvent;

public class Fight {
	
	public KeyHandler keyH;
	public MySQL sql;
	
	public Player epg1, epg2, winner;
		
	public int win1, win2;
	public int schere1,stein1,papier1,schere2,stein2,papier2;
	
	public boolean ww = true;
	
	public String input1 = "", input2 = "";
	
	public Fight(KeyHandler keyH, Player epg1, Player epg2, MySQL sql) {
		this.keyH = keyH;
		this.epg1 = epg1;
		this.epg2 = epg2;
		win1 = 0;
		win2 = 0;
		this.sql = sql;
	}
	
	public void epischeSchlacht() {
		if(keyH.IP != 0 && keyH.IR != 0) {
			switch (keyH.IP) {
			case 1:
				input1 = "Schere";
				schere1++;
				break;
			case 2:
				input1 = "Stein";
				stein1++;
				break;
			case 3:
				input1 = "Papier";
				papier1++;
				break;
			default:
				input1 = "";
				break;
			}
			switch (keyH.IR) {
			case 1:
				input2 = "Schere";
				schere2++;
				break;
			case 2:
				input2 = "Stein";
				stein2++;
				break;
			case 3:
				input2 = "Papier";
				papier2++;
				break;
			default:
				input2 = "";
				break;
			}
		}
		epischeSchlachtHilf(input1, input2);
	}
	
	public void epischeSchlachtHilf(String input1, String input2) {
		if(input1 == "Schere") {
			if(input2 == "Stein") {
				win2++;
				winner = epg2;
				ww = true;
			}
			if(input2 == "Papier") {
				win1++;
				winner = epg1;
				ww = true;
			}if(input2 == "Schere") {
				ww = false;
			}
		}
		if(input1 == "Stein") {
			if(input2 == "Schere") {
				win1++;
				winner = epg1;
				ww = true;
			}
			if(input2 == "Papier") {
				win2++;
				winner = epg2;
				ww = true;
			}if(input2 == "Stein") {
				ww = false;
			}
		}
		if(input1 == "Papier") {
			if(input2 == "Stein") {
				win1++;
				winner = epg1;
				ww = true;
			}
			if(input2 == "Schere") {
				win2++;
				winner = epg2;
				ww = true;
			}if(input2 == "Papier") {
				ww = false;
			}
		}
		input1 = "";
		input2 = "";
		 
	}
	
	public Player determineW() {
		if(win1 > win2) {
			return epg1;
		}
		return epg2;
	}
	
	
	public void updateStats(Player p) {
		// Hier muss die Einbindung der DB sein um halt die Attribute anzupassen
		String check = p.getName();
		String spiele = "UPDATE Tabelle1 SET Spiele = Spiele + 1 WHERE Name ='" + p.getName() + "'";
		
		String scherep1 = null;
		String scherep2 = null;
		String steinp1 = null;
		String steinp2 = null;
		String papierp1 = null;
		String papierp2 = null;
		String game = null;
		
		
		if(p.getName() == epg1.getName()) {
			scherep1 = "UPDATE Tabelle1 SET Schere = Schere + " + schere1 + " WHERE Name = '" + p.getName() + "'";
			steinp1 = "UPDATE Tabelle1 SET Stein = Stein + " + stein1 + " WHERE Name = '" + p.getName() + "'";
			papierp1 = "UPDATE Tabelle1 SET Papier = Papier + " + papier1 + " WHERE Name = '" + p.getName() + "'";
			if(determineW() == p) {
				game = "UPDATE Tabelle1 SET Gewonnen = Gewonnen + 1 WHERE Name = '" + p.getName() + "'";
			}else {
				game = "UPDATE Tabelle1 SET Verloren = Verloren + 1 WHERE Name = '" + p.getName() + "'";
			}
			try {
				MySQL.exeState(scherep1);
				MySQL.exeState(steinp1);
				MySQL.exeState(papierp1);
				MySQL.exeState(game);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(p.getName() == epg2.getName()) {
			scherep2 = "UPDATE Tabelle1 SET Schere = Schere + " + schere2 + " WHERE Name = '" + p.getName() + "'";
			steinp2 = "UPDATE Tabelle1 SET Stein = Stein + " + stein2 + " WHERE Name = '" + p.getName() + "'";
			papierp2 = "UPDATE Tabelle1 SET Papier = Papier + " + papier2 + " WHERE Name = '" + p.getName() + "'";
			if(determineW() == p) {
				game = "UPDATE Tabelle1 SET Gewonnen = Gewonnen + 1 WHERE Name = '" + p.getName() + "'";
			}else {
				game = "UPDATE Tabelle1 SET Verloren = Verloren + 1 WHERE Name = '" + p.getName() + "'";
			}
			try {
				MySQL.exeState(scherep2);
				MySQL.exeState(steinp2);
				MySQL.exeState(papierp2);
				MySQL.exeState(game);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			MySQL.exeState(spiele);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String z = "SELECT * FROM Tabelle1";
//		try {
//			MySQL.epicL(z);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(p == epg1) {
			
			
			schere1 = 0; 
			stein1 = 0;
			papier1 = 0;
			input1 = "";
		}
		if(p == epg2) {
			epg2 = null;
			epg1 = null;
			win2 = 0;
			win1 = 0;
			schere2 = 0;  stein2 = 0;  papier2 = 0;		
			input2 = "";		
		}
		ww = true;
		winner = null;
	}
	
}

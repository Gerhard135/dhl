package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, Serializable {
	
	final int originalTileSize = 32; // 32x32 tile
	final int scale = 2;
	
	public int FPS = 200;
	public boolean printed = false;
	public boolean printed2 = false;
	
	public boolean pl1 = false;
	public boolean pl2 = false;
	
	public String currentP1 = "", currentP2 = "";
	
	public int gameStage = 0;
	// 0: No Match is being played
	// 1: A Match has started and the first player is being selected
	// 2: A Match has started and the second player is being selected
	// 3: The first round of the fight starts
	// 4: The second round of the fight starts
	// 5: The third round of the fight starts
	
	public final int tileSize = originalTileSize * scale; //64x64 tile
	public final int maxScreenCol = 28;
	public final int maxSreenRow = 14;
	final int screenWidth = tileSize * maxScreenCol; //1024 pixels
	final int screenHeight = tileSize * maxSreenRow; //768 pixels
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	MySQL sql;
	
	Fight fight = new Fight(keyH, null, null, sql);
	
	public List players = new List();
	// Hier sollen alle Player aus der Datenbank gespeichert werden
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		// Die sind nur zum Testen da und müssen später entfernt werden.
		players.append(new Player("a"));
		players.append(new Player("b"));
		
	}

	@Override
	public void run() {
		try {
			MySQL.connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS; // 0.0167 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				switch(gameStage) {
				case 0:
					if(!printed) {
						System.out.println();
						System.out.println();
						System.out.println("Press ENTER to start a match!");
						System.out.println();
						System.out.println("If you want to see playerstats press SPACE");
						keyH.space = false;
						printed = true;
					}
					
					currentP1 = "";
					currentP2 = "";
					
					
					if(keyH.start) {
						keyH.np = 0;
						keyH.start = false;
						gameStage = 1;
						printed = false;
					}
					if(keyH.space) {
						String z = "SELECT * FROM Tabelle1";
						try {
							MySQL.epicL(z);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						keyH.space = false;
					}
					break;
				case 1:
					if(!printed) {
//						String s = "INSERT INTO Tabelle1 (Name, Spiele, Gewonnen, Verloren, Schere, Stein, Papier) VALUES ('Jannis', 0, 0, 0, 0, 0, 0)";
//						String t = "INSERT INTO Tabelle1 (Name, Spiele, Gewonnen, Verloren, Schere, Stein, Papier) VALUES ('Gerhard', 0, 0, 0, 0, 0, 0)";

//						String s = "SELECT * FROM Tabelle1";
//						String s = "UPDATE Tabelle1 SET Spiele = 0  WHERE Name = 'Jannis' OR Name = 'Gerhard'";
//						String s = "DELETE * FROM Tabelle1 WHERE Name = 'Jannis'";
//						String t = "DELETE * FROM Tabelle1 WHERE Name = 'Gedeon'";

						
						
//						try {
//							MySQL.exeState(s);
////							MySQL.exeState(t);
//						} catch (ClassNotFoundException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						keyH.nameChar = '\u0000';
						
						System.out.println();
						System.out.println("Match has started!");
						System.out.println("Please select your first player!");
						System.out.println("Type in your name and finish by pressing ENTER!");
						printed = true;
					}
					
					if(!keyH.start) {
						if(keyH.nameChar != '\u0000' && keyH.nameChar != 0) {
							currentP1 = currentP1 + keyH.nameChar;
							System.out.print(keyH.nameChar);
						}
						keyH.nameChar = '\u0000';
					}else {
						if(selectPlayer(currentP1) == null) {
							if(!printed2) {
								System.out.println();
								System.out.println("Selected player doesn't exist");
								System.out.println();
								System.out.println();
								System.out.println("If you want to create a new player, press 1");
								System.out.println("If you want to restart, press 2");
								printed2 = true;
							}
							
							if(keyH.np == 1) {
								String neuP1 = "INSERT INTO Tabelle1 (Name, Spiele, Gewonnen, Verloren, Schere, Stein, Papier) VALUES('" + currentP1 + "',0,0,0,0,0,0)";
								try {
									MySQL.exeState(neuP1);
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								keyH.np = 0;							
							}
							if(keyH.np == 2) {
								
								gameStage = 0;
								keyH.np = 0;
								keyH.start = false;
								printed = false;
								printed2 = false;
								break;
							}
						}else {
							System.out.println();
							System.out.println("First player is selected!");
							System.out.println(currentP1);
							
							
							gameStage = 2;
							keyH.start = false;
							printed = false;
							printed2 = false;
						}									
					}
					break;
				case 2:
					if(!printed) {
						System.out.println();
						System.out.println("Please select your second player!");
						System.out.println("Type in your name and finish by pressing ENTER!");
						printed = true;
					}
					
					if(!keyH.start) {
						if(keyH.nameChar != '\u0000') {
							currentP2 = currentP2 + keyH.nameChar;
							System.out.print(keyH.nameChar);
						}
						keyH.nameChar = '\u0000';
					}else {
						if(selectPlayer(currentP2) == null) {
							if(!printed2) {
								System.out.println();
								System.out.println("Selected player doesn't exist");
								System.out.println();
								System.out.println();
								System.out.println("If you want to create a new player, press 1");
								System.out.println("If you want to restart, press 2");
								printed2 = true;
							}
							
							if(keyH.np == 1) {
								String neuP2 = "INSERT INTO Tabelle1 (Name, Spiele, Gewonnen, Verloren, Schere, Stein, Papier) VALUES('" + currentP2 + "',0,0,0,0,0,0)";
								try {
									MySQL.exeState(neuP2);
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								keyH.np = 0;							
							}
							if(keyH.np == 2) {
								
								gameStage = 0;
								keyH.np = 0;
								keyH.start = false;
								printed = false;
								printed2 = false;
								break;
							}
						}else {
							System.out.println();
							System.out.println("Second player is selected!");
							System.out.println(currentP2);
							
							
							gameStage = 3;
							keyH.start = false;
							printed = false;
							printed2 = false;
						}					
					}
					break;
				case 3:
					
					if(selectPlayer(currentP1) == null || selectPlayer(currentP2) == null) {
						System.out.println();
						System.out.println("Couldnt find selected players!");
						System.out.println("Match ended due to an error!");
						keyH.start = false;
						printed = false;
						gameStage = 0;
						break;
					}
					
					if(!printed) {
						keyH.IP = 0;
						keyH.IR = 0;
						
						System.out.println();
						System.out.println("Round 1 begins!");
						System.out.println("Please select your Input by pressing one of these buttons!");
						System.out.println("Player 1:");
						System.out.println("A: Schere");
						System.out.println("S: Stein");
						System.out.println("D: Papier");
						
						System.out.println("Player 2:");
						System.out.println("LEFT: Schere");
						System.out.println("DOWN: Stein");
						System.out.println("RIGHT: Papier");
						printed = true;
					}
				
					fight.epg1 = selectPlayer(currentP1);
					fight.epg2 = selectPlayer(currentP2);
					
					if(keyH.IP != 0 && !pl1) {
						System.out.println("Player 1 has selected their input!");
						pl1 = true;
					}
					
					if(keyH.IR != 0 && !pl2) {
						System.out.println("Player 2 has selected their input!");
						pl2 = true;
					}
					
					if(keyH.IP != 0 && keyH.IR != 0) {
						System.out.println("The fight begins!");
						fight.epischeSchlacht();
						gameStage = 4;
						keyH.start = false;
						printed = false;
						pl1 = false;
						pl2 = false;
						
						if(fight.ww) {
							String w1 = fight.winner.name;
							
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("The winner of Round 1 is: " + w1);
							System.out.println("");
						}else {
							
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("Its a Draw!");
							System.out.println("");
							gameStage = 3;
						}
					}
					
					
					
					
					break;
				case 4:					
					if(selectPlayer(currentP1) == null || selectPlayer(currentP2) == null) {
						System.out.println();
						System.out.println("Couldnt find selected players!");
						System.out.println("Match ended due to an error!");
						keyH.start = false;
						printed = false;
						gameStage = 0;
						break;
					}
					
					if(!printed) {
						keyH.IP = 0;
						keyH.IR = 0;
						
						System.out.println();
						System.out.println("Round 2 begins!");
						System.out.println("Please select your Input by pressing one of these buttons!");
						System.out.println("Player 1:");
						System.out.println("A: Schere");
						System.out.println("S: Stein");
						System.out.println("D: Papier");
						
						System.out.println("Player 2:");
						System.out.println("LEFT: Schere");
						System.out.println("DOWN: Stein");
						System.out.println("RIGHT: Papier");
						printed = true;
					}
				
					
					
//					fight = new Fight(keyH, selectPlayer(currentP1), selectPlayer(currentP2));;
					
					if(keyH.IP != 0 && !pl1) {
						System.out.println("Player 1 has selected their input!");
						pl1 = true;
					}
					
					if(keyH.IR != 0 && !pl2) {
						System.out.println("Player 2 has selected their input!");
						pl2 = true;
					}
					
					if(keyH.IP != 0 && keyH.IR != 0) {
						System.out.println("The fight begins!");
						fight.epischeSchlacht();
						gameStage = 5;
						keyH.start = false;
						printed = false;
						pl1 = false;
						pl2 = false;
						
						if(fight.ww) {
							String w2 = fight.winner.name;
							
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("The winner of Round 2 is: " + w2);
							System.out.println("");
						}else {
							
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("Its a Draw!");
							System.out.println("");
							gameStage = 4;
						}
					}
					break;
				case 5:
					
					if(selectPlayer(currentP1) == null || selectPlayer(currentP2) == null) {
						System.out.println();
						System.out.println("Couldnt find selected players!");
						System.out.println("Match ended due to an error!");
						keyH.start = false;
						printed = false;
						gameStage = 0;
						break;
					}
					
					if(!printed) {
						keyH.IP = 0;
						keyH.IR = 0;
						
						System.out.println();
						System.out.println("Round 3 begins!");
						System.out.println("Please select your Input by pressing one of these buttons!");
						System.out.println("Player 1:");
						System.out.println("A: Schere");
						System.out.println("S: Stein");
						System.out.println("D: Papier");
						
						System.out.println("Player 2:");
						System.out.println("LEFT: Schere");
						System.out.println("DOWN: Stein");
						System.out.println("RIGHT: Papier");
						printed = true;
					}
					
				
					
//					fight = new Fight(keyH, selectPlayer(currentP1), selectPlayer(currentP2));;
					
					if(keyH.IP != 0 && !pl1) {
						System.out.println("Player 1 has selected their input!");
						pl1 = true;
					}
					
					if(keyH.IR != 0 && !pl2) {
						System.out.println("Player 2 has selected their input!");
						pl2 = true;
					}
					
					if(keyH.IP != 0 && keyH.IR != 0) {
						System.out.println("The fight begins!");
						fight.epischeSchlacht();
						gameStage = 0;
						keyH.start = false;
						printed = false;
						pl1 = false;
						pl2 = false;
						
						if(fight.ww) {
							String w3 = fight.winner.name;
							
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("The winner of Round 3 is: " + w3);
							System.out.println("");
							
							String wO = fight.determineW().name;
							
							System.out.println("");
							System.out.println("");
							System.out.println("The overall Winner is: " + wO);
							System.out.println("");
							
							fight.updateStats(fight.epg1);
							fight.updateStats(fight.epg2);
							gameStage = 0;
							
						}else {
							
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("Its a Draw!");
							System.out.println("");
							gameStage = 5;
						}
					}
					
					
					
					
					
					break;
				default:
					gameStage = 0;
					System.out.println("An unknown error has occured!");
					break;
				}
				
				delta--;
			}
		}
		
	
		
		
		
	}

	public void startGameThread() {
		// TODO Auto-generated method stub
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	public Player selectPlayerNeu(String pla) {
		players.toFirst();
		while(players.hasAccess()) {
			Player p = (Player) players.getObject();
			if(p.getName().equals(pla)) {
				return p;
			}
			players.next();
		}
		return null;
	}
	
	public Player selectPlayer(String pla) {
		String s = "SELECT Name FROM Tabelle1";
		try {
			return MySQL.epicW(s, pla);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
		}
	
	public void playerAusgeben() {
		players.toFirst();
		while(players.hasAccess()) {
			Player pla = (Player) players.getObject();
			System.out.println(pla.name);
			players.next();
		}
	}
	
	
}

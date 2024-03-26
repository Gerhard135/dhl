package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	private static Connection con;
	
	public static void connect() throws ClassNotFoundException {
			try {
//				Class.forName("com.mysql.jdbc.Driver");
			
			
				con = DriverManager.getConnection("jdbc:ucanaccess://Database2.accdb");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	
}
	
	public static void exeState(String sql) throws ClassNotFoundException {
		try {
		connect();
		
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void epicL(String sql) throws ClassNotFoundException{
		try {
			connect();
			
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				
				while(rs.next()) {
					for(int i = 1; i < 8; i++) {
						switch(i) {
						case 1:
							System.out.print("Name: " + rs.getString(i)+ ", ");
							break;
						case 2:
							System.out.print("Games: " + rs.getString(i)+ ", ");
							break;
						case 3:
							System.out.print("Wins: " + rs.getString(i)+ ", ");
							break;
						case 4:
							System.out.print("Losses: " + rs.getString(i)+ ", ");
							break;
						case 5:
							System.out.print("Scissors: " + rs.getString(i)+ ", ");
							break;
						case 6:
							System.out.print("Rock: " + rs.getString(i)+ ", ");
							break;
						case 7:
							System.out.print("Paper: " + rs.getString(i)+ ", ");
							break;
						}
						
//						System.out.print(rs.getString(i)+ ", ");
					}
					System.out.println();
				}
				statement.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static Player epicW(String sql, String s) throws ClassNotFoundException{
		try {
			connect();
			
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					if(rs.getString(1).equals(s)) {
						Player p = new Player(rs.getString(1));
//						p.spiele = Integer.parseInt(rs.getString(2));
//						p.gewonnen = Integer.parseInt(rs.getString(3));
//						p.verloren = Integer.parseInt(rs.getString(4));
//						p.schere = Integer.parseInt(rs.getString(5));
//						p.stein = Integer.parseInt(rs.getString(6));
//						p.papier = Integer.parseInt(rs.getString(7));
						return p;
					}	
					
				}
				statement.close();
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	
	public void nameB(String sql, String att) throws SQLException {
		try {
			connect();
			
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, att);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
                // Verarbeitung der Daten
                System.out.println(rs.getString("name"));
            }

            // Ressourcen schließen
            rs.close();
            statement.close();
            rs.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}



















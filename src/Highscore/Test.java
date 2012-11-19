package Highscore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Highscore.saveHighscore("Test", 1000);
		Highscore.saveHighscore("Stephan", 1337);
		ResultSet rs = Highscore.readHighscores();
		
		try {			
			while(rs.next()) {
				System.out.println(rs.getString(1) + " --> " + rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}

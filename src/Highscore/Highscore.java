package Highscore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Highscore {

	private static final String TABLE_NAME = "highscore";
	private static final String COLUMN_NAMES = "name,score";

	public static void saveHighscore(String name, int score) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection verbindung = DriverManager
					.getConnection("jdbc:sqlite:highscore.db");
			Statement query = verbindung.createStatement();

			query.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
					+ "(" + COLUMN_NAMES + ");");

			PreparedStatement statement = verbindung
					.prepareStatement("INSERT INTO " + TABLE_NAME
							+ " VALUES(?,?);");
			statement.setString(1, name);
			statement.setInt(2, score);

			statement.executeUpdate();

			statement.close();
			query.close();
			verbindung.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet readHighscores() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection verbindung;
		try {
			verbindung = DriverManager
					.getConnection("jdbc:sqlite:highscore.db");
			Statement query = verbindung.createStatement();
			ResultSet ergebnis = query.executeQuery("SELECT EXISTS(SELECT * FROM " + TABLE_NAME + ");");
			
			if(ergebnis.next()) {
				return ergebnis;
			}else {
				return null;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

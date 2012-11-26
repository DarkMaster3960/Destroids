package Game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ViewBean {
	
	private static Graphics2D spielfeldGraphics;

	private static Spaceship player1;

	private static Spaceship player2;
	
	private static ArrayList<Shot> shots;
	
	private static final int FRAMERATE = 20;

	private static Dimension auflösung = new Dimension();
	
	public ArrayList<Shot> getShots() {
		return shots;
	}
	
	public void addShot(Shot shot) {
		shots.add(shot);
	}

	public static Dimension getAuflösung() {
		return auflösung;
	}

	public static void setAuflösung(Dimension aAuflösung) {
		auflösung = aAuflösung;
	}

	public static Spaceship getPlayer1() {
		return player1;
	}

	public static void setPlayer1(Spaceship aPlayer1) {
		player1 = aPlayer1;
	}

	public static Spaceship getPlayer2() {
		return player2;
	}

	public static void setPlayer2(Spaceship aPlayer2) {
		player2 = aPlayer2;
	}

	public static void setSpielfeldGraphics(Graphics2D spielfeldGraphics) {
		ViewBean.spielfeldGraphics = spielfeldGraphics;
	}

	public static Graphics2D getSpielfeldGraphics() {
		return spielfeldGraphics;
	}

	public static int getFramerate() {
		return FRAMERATE;
	}
}

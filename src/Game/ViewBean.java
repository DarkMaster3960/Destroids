package Game;

import java.awt.Dimension;
import java.awt.Graphics;

public class ViewBean {
	
	private static Graphics spielfeldGraphics;

	private static Spaceship player1;

	private static Spaceship player2;

	private static Dimension aufl�sung = new Dimension();

	public static Dimension getAufl�sung() {
		return aufl�sung;
	}

	public static void setAufl�sung(Dimension aAufl�sung) {
		aufl�sung = aAufl�sung;
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

	public static void setSpielfeldGraphics(Graphics spielfeldGraphics) {
		ViewBean.spielfeldGraphics = spielfeldGraphics;
	}

	public static Graphics getSpielfeldGraphics() {
		return spielfeldGraphics;
	}
}

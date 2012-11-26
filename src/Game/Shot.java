package Game;

import java.awt.Graphics2D;

/**
 * @author Maik
 */
public class Shot {
	private int x;
	private int y;
	private int degree;
	
	public Shot(int x, int y, int degree) {
		this.x = x;
		this.y = y;
		this.degree = degree;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public void paint() {
		if (ViewBean.getSpielfeldGraphics() != null) {
			Graphics2D spielfeldGraphic = ViewBean.getSpielfeldGraphics();
			if (spielfeldGraphic != null) {
				
				
			}
		}
	}
}

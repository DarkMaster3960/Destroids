package Game;

import java.awt.Graphics2D;

import MovableObject.MovableObject;

/**
 * @author Maik
 */
public class Shot extends MovableObject {
	private double x;
	private double y;
	private final static int length = 3;
	private int degree;
	private static int lifetime = 10;
	
	public Shot(double x, double y, int degree) {
		this.x = x;
		this.y = y;
		this.degree = degree;
	}
	
	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
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
	
	public int getLifetime() {
		return lifetime;
	}
	
	public void aktualisiereBewegung() {
		x += length * Math.cos(Math.toRadians(degree));
		y += length * Math.cos(Math.toRadians(degree));
	}

	public void paint() {
		if (ViewBean.getSpielfeldGraphics() != null) {
			Graphics2D spielfeldGraphic = ViewBean.getSpielfeldGraphics();
			if (spielfeldGraphic != null) {
				spielfeldGraphic.fillOval((int)(x-0.5), (int)(y-0.5), length, length);
				
			}
		}
	}
}

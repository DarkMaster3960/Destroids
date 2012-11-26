package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import MovableObject.MovableObject;

public class Spaceship extends MovableObject {

	private static final int FRAMERATE = ViewBean.getFramerate();
	private static final double BESCHLEUNIGUNGSRATE = FRAMERATE / 10;
	private static final double BESCHLEUNIGUNGSSCHRITTE = FRAMERATE / 4;
	private static final int ROTATIONSRATE = FRAMERATE / 6;
	public boolean links, rechts, vorwaerts, rueckwarts;
	private BufferedImage ship;

	public double[] beschleunigungen = new double[360];
	private int aktRotation = 0;
	private double x = ViewBean.getAuflösung().width / 2;
	private double y = ViewBean.getAuflösung().height / 2;

	public Spaceship(int player) {
		try {
			if (player == 1) {
				ship = ImageIO.read(new File("Images/SkyPodRed.jpg"));
			} else {
				ship = ImageIO.read(new File("Images/SkyPodGreen.jpg"));
			}
			ship = getScaledImage(ship, 5);
			ship = setTranslucentColor(ship, Color.black);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	private void speedUp() {
		if ((beschleunigungen[aktRotation] + BESCHLEUNIGUNGSRATE) > 100) {
			beschleunigungen[aktRotation] = 100;
		} else {
			beschleunigungen[aktRotation] += BESCHLEUNIGUNGSRATE;
		}

	}

	private void speedDown() {
		if ((beschleunigungen[aktRotation] - BESCHLEUNIGUNGSRATE) < -100) {
			beschleunigungen[aktRotation] = -100;
		} else {
			beschleunigungen[aktRotation] -= BESCHLEUNIGUNGSRATE;
		}
	}

	private void setRotation(int aRotation) {
		this.aktRotation += aRotation;
		if (aktRotation == 360) {
			aktRotation = 0;
		} else if (aktRotation < 0) {
			aktRotation += 360;
		}
	}

	/**
	 * berechnet aus der geschwindigkeit und der Rotation die neuen x und y
	 * Werte
	 */
	private void ermittelXundY() {
		// ermittelt mit hilfe von der Winkelfunktionen in einem Dreieck die
		// neue Position in abhängigkeit von der Geschwindigkeit und der
		// Rotation
		double xPlus = 0;
		double yPlus = 0;
		for (int i = 0; i < beschleunigungen.length; i++) {
			xPlus += BESCHLEUNIGUNGSSCHRITTE / 100 * beschleunigungen[i]
					* Math.sin(i / 180.0 * Math.PI)
					/ Math.sin(90.0 / 180.0 * Math.PI);
			yPlus += BESCHLEUNIGUNGSSCHRITTE / 100 * beschleunigungen[i]
					* Math.sin((180.0 - 90.0 - i) / 180.0 * Math.PI)
					/ Math.sin(90.0 / 180.0 * Math.PI);
		}

		x += xPlus;
		y -= yPlus;
	}

	public void aktualisiereBewegung() {
		ermittelXundY();
		if (vorwaerts && !rueckwarts) {
			speedUp();
		}
		if (rueckwarts && !vorwaerts) {
			speedDown();
		}
		if (links && !rechts) {
			setRotation(-ROTATIONSRATE);
		}
		if (rechts && !links) {
			setRotation(ROTATIONSRATE);
		}
		beschleunigungen[aktRotation] = beschleunigungen[aktRotation];

		// verringere Geschwindigkeit bei keiner Beschleunigung

		for (int i = 0; i < beschleunigungen.length; i++) {
			if (i == aktRotation) {
				if (!vorwaerts && !rueckwarts) {
					if (beschleunigungen[i] > 0) {
						beschleunigungen[i] -= 0.5;
					} else if (beschleunigungen[i] < 0) {
						beschleunigungen[i] += 0.5;
					}
				}
			} else {
				if (beschleunigungen[i] > 0) {
					beschleunigungen[i] -= 0.5;
				} else if (beschleunigungen[i] < 0) {
					beschleunigungen[i] += 0.5;
				}
			}
		}
	}

	/**
	 * zeichnet den nichtgezeigten teil beim durchfliegen einer Wand an der
	 * gegeüberliegendenseite
	 * 
	 * @param bild
	 */
	private void zeichne(BufferedImage bild) {// benennen
		double w = ViewBean.getAuflösung().getWidth();
		double h = ViewBean.getAuflösung().getHeight();
		double duplikatX = x;
		double duplikatY = y;
		if ((y - bild.getHeight() / 2) < 0) {// links rausgeflogen
			duplikatY = (y - bild.getHeight() / 2 + h);
			duplikatX = x - bild.getWidth() / 2;
		} else if ((y + bild.getHeight() / 2) > h) {// rechts rausgeflogen
			duplikatY = (y - bild.getHeight() / 2 - h);
			duplikatX = x - bild.getWidth() / 2;
		} else if ((x - bild.getWidth() / 2) < 0) {// oben rausgeflogen
			duplikatX = (x - bild.getWidth() / 2 + w);
			duplikatY = y - bild.getHeight() / 2;
		} else if ((x + bild.getWidth() / 2) > w) {// unten rausgeflogen
			duplikatX = (x - bild.getWidth() / 2 - w);
			duplikatY = y - bild.getHeight() / 2;
		}

		// fals das Spaceship rausfliegt wird es an die gegenüberliegende
		// Position gesetzt
		if (x + bild.getWidth() / 2 < 0) {// links rausgeflogen
			x += w;
		} else if (x - bild.getWidth() / 2 > w) {// rechts rausgeflogen
			x -= w;
		} else if (y + bild.getHeight() / 2 < 0) {// oben rausgeflogen
			y += h;
		} else if (y - bild.getHeight() / 2 > h) {// unten rausgeflogen
			y -= h;
		}

		// malt das duplikat, falls ein duplikat nötig ist
		if (x != duplikatX || y != duplikatY) {
			ViewBean.getSpielfeldGraphics().drawImage(bild, (int) duplikatX,
					(int) duplikatY, null);
		}

	}

	public void paint() {
		if (ViewBean.getSpielfeldGraphics() != null) {
			Graphics2D spielfeldGraphic = ViewBean.getSpielfeldGraphics();
			if (spielfeldGraphic != null) {
				BufferedImage gedrehtesBild = getRotateImage(ship, aktRotation);
				zeichne(gedrehtesBild);

				spielfeldGraphic.drawImage(gedrehtesBild, (int) x
						- gedrehtesBild.getWidth() / 2, (int) y
						- gedrehtesBild.getHeight() / 2, null);
			}
		}
	}
}

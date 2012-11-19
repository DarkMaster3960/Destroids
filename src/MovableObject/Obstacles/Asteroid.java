package MovableObject.Obstacles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Game.ViewBean;
import MovableObject.MovableObject;

public class Asteroid extends MovableObject{

	private static final int FRAMERATE = ViewBean.getFramerate();
	private static final double BESCHLEUNIGUNGSRATE = FRAMERATE / 10;
	private static final double BESCHLEUNIGUNGSSCHRITTE = FRAMERATE / 4;
	private static final int ROTATIONSRATE = FRAMERATE / 6;
	private BufferedImage asteroid;
	
	private int aktRotation = 0;
	private double x = ViewBean.getAuflösung().width / 2;
	private double y = ViewBean.getAuflösung().height / 2;
	
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

		/*
		 * Falls der Asteroid rausfliegt wird er an den anderen Rand gesetzt
		 */
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
	
	@Override
	public void paint() {
		if (ViewBean.getSpielfeldGraphics() != null) {
			Graphics2D spielfeldGraphic = ViewBean.getSpielfeldGraphics();
			if (spielfeldGraphic != null) {
				BufferedImage gedrehtesBild = getRotateImage(asteroid, aktRotation);
				zeichne(gedrehtesBild);

				spielfeldGraphic.drawImage(gedrehtesBild, (int) x
						- gedrehtesBild.getWidth() / 2, (int) y
						- gedrehtesBild.getHeight() / 2, null);
			}
		}
	}

}

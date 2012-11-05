package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spaceship {
	private Image ship;
	private int x = 0;
	private int y = 0;

	public Spaceship() {
		try {
			ship = ImageIO.read(new File("Images/SkyPod.jpg"));
			int w = (int) (ViewBean.getAuflösung().getWidth() / 20);
			int h = (int) (ViewBean.getAuflösung().getHeight() / 20);
			ship = ship.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void move(int aX, int aY) {
		x = aX;
		y = aY;
	}

	public void paint() {
		if (ViewBean.getSpielfeldGraphics() != null) {
			Graphics spielfeldGraphic = ViewBean.getSpielfeldGraphics();
			if (spielfeldGraphic != null) {
				spielfeldGraphic.drawImage(ship, x, y, null);
			}
		}
	}
}

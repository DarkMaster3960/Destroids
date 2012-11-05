package Game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DestroidsGame extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */

	Image spielfeld;
	Graphics spielGraphics;
	long startTime, endTime;
	private static final long serialVersionUID = 2940828202128306011L;
	Thread th;

	public void init() {
		if (getWidth() == 0 || getWidth() == 0) {
			setSize(1024, 768);
		}
		setIgnoreRepaint(true);
		ViewBean.setAuflösung(getSize());
		spielfeld = createImage(getWidth(), getHeight());
		spielGraphics = spielfeld.getGraphics();
		ViewBean.setSpielfeldGraphics(spielGraphics);
		ViewBean.setPlayer1(new Spaceship());
		addKeyListener(this);
		updateScreen();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		Spaceship spaceship = ViewBean.getPlayer1();
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			spaceship.move(0, -2);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			spaceship.move(0, 2);
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			spaceship.move(-2, 0);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			spaceship.move(+2, 0);
		}
	}

	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}

	public void drawBufferedImage() {
		spielGraphics.setColor(Color.BLACK);
		spielGraphics.fillRect(0, 0, (int) ViewBean.getAuflösung().getWidth(),
				(int) ViewBean.getAuflösung().getHeight());
		ViewBean.getPlayer1().paint();
	}

	public void updateScreen() {
		drawBufferedImage();
		Graphics g = getGraphics();
		if (g != null) {
			if (spielfeld != null) {
				g.drawImage(spielfeld, 0, 0, null);
			}
		}
	}

	public void run() {
		while (true) {
			startTime = System.currentTimeMillis();
			moveObjects();
			updateScreen();
	
			try {
				endTime = System.currentTimeMillis();
				long restzeit = 25 - (endTime - startTime);
				if (restzeit > 0) {
					Thread.sleep(restzeit);
				}
			} catch (InterruptedException ex) {
			}
		}
	}

	private void moveObjects() {
	}
}

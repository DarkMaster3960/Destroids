package Game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DestroidsGame extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	// Schalter für Spielvorhergang
	boolean continuing;

	BufferedImage spielfeld;
	Graphics2D spielGraphics;
	long startTime, endTime;
	private static final long serialVersionUID = 2940828202128306011L;
	Thread th;

	public void init() {
		continuing = true;
		if (getWidth() == 0 || getWidth() == 0) {
			setSize(1024, 768);
		}
		setIgnoreRepaint(true);
		ViewBean.setAuflösung(getSize());
		spielfeld = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		spielGraphics = (Graphics2D) spielfeld.getGraphics();
		ViewBean.setSpielfeldGraphics(spielGraphics);
		ViewBean.setPlayer1(new Spaceship(1,ViewBean.getAuflösung().width / 2,ViewBean.getAuflösung().height / 2));
		ViewBean.setPlayer2(new Spaceship(2,ViewBean.getAuflösung().width / 3,ViewBean.getAuflösung().height / 3+20));
		addKeyListener(this);
		updateScreen();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		Spaceship spaceship1 = ViewBean.getPlayer1();
		Spaceship spaceship2 = ViewBean.getPlayer2();
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			spaceship1.vorwaerts = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			spaceship1.rueckwarts = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			spaceship1.links = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			spaceship1.rechts = false;
		}
		if (spaceship2 != null) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				spaceship2.vorwaerts = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				spaceship2.rueckwarts = false;
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				spaceship2.links = false;
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				spaceship2.rechts = false;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			ViewBean.addShot(new Shot(spaceship1.getX(), spaceship1.getY(),
					spaceship1.getAktRotation()));
		}
	}

	public void keyPressed(KeyEvent e) {
		Spaceship spaceship1 = ViewBean.getPlayer1();
		Spaceship spaceship2 = ViewBean.getPlayer2();
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			spaceship1.vorwaerts = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			spaceship1.rueckwarts = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			spaceship1.links = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			spaceship1.rechts = true;
		}
		if (spaceship2 != null) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				spaceship2.vorwaerts = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				spaceship2.rueckwarts = true;
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				spaceship2.links = true;
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				spaceship2.rechts = true;
			}
		}

	}

	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}

	public void drawBufferedImage() {
		spielGraphics.setColor(Color.black);
		spielGraphics.fillRect(0, 0, (int) ViewBean.getAuflösung().getWidth(),
				(int) ViewBean.getAuflösung().getHeight());
		if (ViewBean.getPlayer1() != null) {
			ViewBean.getPlayer1().paint();
		}
		if (ViewBean.getPlayer2() != null) {
			ViewBean.getPlayer2().paint();
		}
		List<Shot> shots = ViewBean.getShots();
		if (!shots.isEmpty()) {
			System.out.println("A");
			for (Shot shot : shots) {
				System.out.println("B");
				shot.paint();
			}
		}
	}

	public void updateScreen() {
		drawBufferedImage();
		Graphics2D g = (Graphics2D) getGraphics();
		if (g != null) {
			if (spielfeld != null) {
				g.drawImage(spielfeld, 0, 0, null);
			}
		}
	}

	public void run() {
		while (continuing) {
			startTime = System.currentTimeMillis();
			// 1. Repaint the graphics
			updateScreen();
			// 2. Move Objects to next Position
			moveObjects();
			// 3. Check collision
			//detectCollision();

			try {
				endTime = System.currentTimeMillis();
				long restzeit = ViewBean.getFramerate() - (endTime - startTime);
				if (restzeit > 0) {
					Thread.sleep(restzeit);
				}
			} catch (InterruptedException ex) {
			}
		}
	}

	private void detectCollision() {
		Spaceship player1 = ViewBean.getPlayer1();
		Graphics2D player1graphics = player1.getObjectGraphics();
		Spaceship player2 = ViewBean.getPlayer2();
		Graphics2D player2graphics = player2.getObjectGraphics();
		Shape player2shape = player2graphics.getClip();
		Rectangle player2rec = player2graphics.getClipBounds();
		if(player1graphics.hit( null,null , true)){
			continuing = false;
			addExplosion(player1.getX(),player1.getY());
			addExplosion(player2.getX(),player2.getY());
		}

	}
	private void addExplosion(double d, double e){
		
	}

	private void moveObjects() {
		if (ViewBean.getPlayer1() != null) {
			ViewBean.getPlayer1().aktualisiereBewegung();
		}
		if (ViewBean.getPlayer2() != null) {
			ViewBean.getPlayer2().aktualisiereBewegung();
		}
		List<Shot> shots = ViewBean.getShots();
		if (!shots.isEmpty()) {
			for (Shot shot : shots) {
				shot.aktualisiereBewegung();
			}
		}
	}
}

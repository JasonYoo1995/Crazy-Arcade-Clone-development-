package play;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

import skeleton.ImageManager;

public class Balloon extends JLabel implements Runnable{
	Image img;
	int power;
	int group;
	int posX, posY;
	final int UP = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	final int RIGHT = 4;
	Thread streamThread;
	ImageManager im;
	PlayGround pg;
	Stream st[];
	int M = 40;
	
	Balloon(int p, ImageManager im, int posX, int posY, PlayGround pg){
		this.power = p;
		this.img = im.ball.getImage();
		this.posX = posX;
		this.posY = posY;
		this.setOpaque(true);
		this.setVisible(true);
		this.im = im;
		this.pg = pg;
		this.streamThread = new Thread(this);
		
		st = new Stream[61];
		(st[0] = new Stream(im, posX, posY, 1)).setVisible(false);;
		pg.add(st[0]);
		for (int i = 1; i <= power; i++) {
			(st[4*i-3] = new Stream(im, posX, posY-i, UP)).setVisible(false);;
			(st[4*i-2] = new Stream(im, posX, posY+i, DOWN)).setVisible(false);;
			(st[4*i-1] = new Stream(im, posX-i, posY, LEFT)).setVisible(false);;
			(st[4*i] = new Stream(im, posX+i, posY, RIGHT)).setVisible(false);;
			pg.add(st[4*i-3]);
			pg.add(st[4*i-2]);
			pg.add(st[4*i-1]);
			pg.add(st[4*i]);
		}
	}
	
	public void run() {
		showStream(im, pg);
	}
	
	public void showStream(ImageManager im, PlayGround pg) {
		for (int i = 0; i < power*4+1; i++) {
			st[i].setVisible(true);
			try{ Thread.sleep(3); }
			catch(InterruptedException e) { return; }
		}
		try{ pg.repaint();}
		catch(Exception e) { };
		try{ Thread.sleep(400); }
		catch(InterruptedException e) { return; }
		
		// ���ٱ� ����
		for (int i = 0; i < power*4+1; i++) {
			st[i].setVisible(false);;
		}
		try{ pg.repaint();}
		catch(Exception e) { };
	}
	
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, M, M, this);
	}
}

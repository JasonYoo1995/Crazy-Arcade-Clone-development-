package play;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

import skeleton.ImageManager;

public class Stream extends JLabel{
	Image img;
	int power;
	int group;
	final int M = 40;
	final int UP = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	final int RIGHT = 4;
	
	public Stream(ImageManager im, int posX, int posY, int dir){
		switch(dir) {
		case UP:
			this.img = im.upStream.getImage();
			break;
		case DOWN:
			this.img = im.downStream.getImage();
			break;
		case LEFT:
			this.img = im.leftStream.getImage();
			break;
		case RIGHT:
			this.img = im.rightStream.getImage();
			break;
		}
		this.setBounds(posX*M, posY*M, M, M);
		this.setOpaque(true);
		this.setVisible(true);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, M, M, this);
	}
}

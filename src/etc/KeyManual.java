package etc;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

import skeleton.ImageManager;

public class KeyManual extends JLabel{
	Image key;
	int width = 360;
	int height = 170;
	public KeyManual(ImageManager im){
		this.key = im.key.getImage();
		this.setBounds(30, 570, width, height);
		this.setOpaque(true);
		this.setVisible(true);
	}
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(key, 0, 0, width, height, this);
	}
}

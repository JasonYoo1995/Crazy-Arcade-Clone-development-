package skeleton;

import java.awt.Color;

import javax.swing.JFrame;

import etc.KeyManual;
import etc.ResultLabel;
import play.PlayGround;

public class Frame extends JFrame{
	ImageManager im;
	PlayGround ground;
	public ResultLabel rl;
	Frame(ImageManager im){
		this.im = im;
		init();
	}
	void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(40, 45, 230));
		this.setTitle("크레이지 아케이드");
		this.setSize(680,800);
		this.setVisible(true);
		this.setLocation(370, 20);
		this.setLayout(null);
		this.setFocusable(false);
		
		this.add(new KeyManual(im));
		this.rl = new ResultLabel();
		this.add(rl);
	}
	void setGround(PlayGround pg) {
		this.ground = pg;
		this.add(pg);
		pg.requestFocus();
	}
}
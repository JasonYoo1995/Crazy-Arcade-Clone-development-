package skeleton;

import play.PlayGround;
import play.Player;

public class Controller {
	ImageManager im;
	Frame frame;
	void init() {
		im = new ImageManager();
		frame = new Frame(im);
		
		PlayGround pg = new PlayGround(frame, im);
		pg.km.pg = pg;
		addPlayer(pg, 1);
		addPlayer(pg, 2);
		
		frame.setGround(pg);
		frame.repaint();
	}
	
	void addPlayer(PlayGround pg, int n) {
		Player p = null;
		switch(n) {
		case 1:
			p = new Player(1, 11, 4);
			break;
		case 2:
			p = new Player(2, 4, 8);
			break;
		default:
			break;
		}
		pg.km.setPlayer(p);
		p.setImage(im);
		pg.addPlayer(p);
		p.setPlayGround(pg);
	}
	
}

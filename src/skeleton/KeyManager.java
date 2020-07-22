package skeleton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import play.PlayGround;
import play.Player;

public class KeyManager extends KeyAdapter{
	Player p1, p2;
	final int UP = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	final int RIGHT = 4;
	PlayGround pg;

	public KeyManager(){
		
	}
	void setPlayer(Player p) {
		if(p.id==1) p1 = p;
		else p2 = p;
	}
	public void makeFalse(int n) {
		Player p;
		if(n==1) p = p1;
		else p = p2;
		p.moveUp = false;
		p.moveDown = false;
		p.moveLeft = false;
		p.moveRight = false;
	}
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
		    case KeyEvent.VK_UP:
				if(p1.moveUp==false)
				{
			    	makeFalse(1);
					if(p1.alive) p1.dir=UP;
					p1.moveUp=true;
				}
				else return;
		        break;
		    case KeyEvent.VK_DOWN:
				if(p1.moveDown==false)
				{
			    	makeFalse(1);
			    	if(p1.alive) p1.dir=DOWN;
					p1.moveDown=true;
				}
				else return;
		        break;
		    case KeyEvent.VK_RIGHT:
				if(p1.moveRight==false)
				{
			    	makeFalse(1);
			    	if(p1.alive) p1.dir=RIGHT;
					p1.moveRight=true;
				}
				else return;
		        break;
		    case KeyEvent.VK_LEFT:
				if(p1.moveLeft==false)
				{
			    	makeFalse(1);
			    	if(p1.alive) p1.dir=LEFT;
					p1.moveLeft=true;
				}
				else return;
		    	break;
		    case KeyEvent.VK_SLASH:
		    	if(!pg.p1.alive) break;
				pg.putBall(p1);
		    	break;
		    	
		    case KeyEvent.VK_R:
				if(p2.moveUp==false)
				{
			    	makeFalse(2);
			    	if(p2.alive) p2.dir=UP;
					p2.moveUp=true;
				}
				else return;
		        break;
		    case KeyEvent.VK_F:
				if(p2.moveDown==false)
				{
			    	makeFalse(2);
			    	if(p2.alive) p2.dir=DOWN;
					p2.moveDown=true;
				}
				else return;
		        break;
		    case KeyEvent.VK_G:
				if(p2.moveRight==false)
				{
			    	makeFalse(2);
			    	if(p2.alive) p2.dir=RIGHT;
					p2.moveRight=true;
				}
				else return;
		        break;
		    case KeyEvent.VK_D:
				if(p2.moveLeft==false)
				{
			    	makeFalse(2);
			    	if(p2.alive) p2.dir=LEFT;
					p2.moveLeft=true;
				}
				else return;
		    	break;
		    case KeyEvent.VK_A:
		    	if(!pg.p2.alive) break;
		    	pg.putBall(p2);
		    	break;
		    	
		    default:
		        break;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		    case KeyEvent.VK_UP:
		    	p1.moveUp=false;
		        break;
		    case KeyEvent.VK_DOWN:
		    	p1.moveDown=false;
		        break;
		    case KeyEvent.VK_RIGHT:
		    	p1.moveRight=false;
		        break;
		    case KeyEvent.VK_LEFT:
		    	p1.moveLeft=false;
		    	break;
		    	
		    case KeyEvent.VK_R:
		    	p2.moveUp=false;
		        break;
		    case KeyEvent.VK_F:
		    	p2.moveDown=false;
		        break;
		    case KeyEvent.VK_G:
		    	p2.moveRight=false;
		        break;
		    case KeyEvent.VK_D:
		    	p2.moveLeft=false;
		    	break;
		    	
		    default:
		        break;
		}
	}
}

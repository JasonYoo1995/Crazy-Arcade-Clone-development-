package play;

import java.util.ArrayList;

public class BallGroup implements Runnable{
	int group;
	PlayGround pg;
	ArrayList<Balloon> balls;
	Thread burstThread;
	
	BallGroup(int num, PlayGround pg){
		this.group = num;
		this.pg = pg;
		this.balls = new ArrayList<Balloon>();
		this.burstThread = new Thread(this);
		this.burstThread.start();
	}
	
	public void run() {
		try { Thread.sleep(4000); } // default : 4000
		catch (InterruptedException e) { e.printStackTrace(); }
		pg.reach(pg.p1, this); pg.reach(pg.p2, this);
		int size = balls.size();
		for (int i = 0; i < size; i++) { // 그룹 내 물풍선 제거
			Balloon b = balls.get(0);
			pg.setBlock(b.posX, b.posY, 1, false);
			pg.setBlock(b.posX, b.posY, 2, false);
			pg.remove(b); // PlayGround GUI 상에서 제거
			balls.remove(0); // 그룹에서 제거
			pg.p1.onBallList.remove(b); // player1에게서 제거
			pg.p2.onBallList.remove(b); // player2에게서 제거
			try{ b.streamThread.start();}
			catch(Exception e) {};
		}
		try{ pg.repaint();}
		catch(Exception e) {};
		pg.group.remove(this); // 그룹 제거
	}
}

package play;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import skeleton.Frame;
import skeleton.ImageManager;
import skeleton.KeyManager;

public class PlayGround extends JPanel {
	int groupNum; // 새로운 판이 시작될 때마다 0으로 초기화해줘야 함 (오버플로우)
	ArrayList<BallGroup> group;
	public Player p1;
	public Player p2;
	Player dead;
	Player alive;
	public KeyManager km;
	ImageManager im;
	int blocked[][];
	Frame frame;
	int M = 40;
	int w = 15;
	int h = 13;
	
	public PlayGround(Frame frame, ImageManager im){
		this.im = im;
		this.frame = frame;
		init();
	}
	
	void init() {
		this.groupNum = 0;
		this.blocked = new int[M*(w-1)+1][M*(h-1)+1];
		this.group = new ArrayList<BallGroup>();
		this.setSize(M*w, M*h);
		this.setLocation(30, 30);
		this.setLayout(null);
		this.setBackground(new Color(144, 225, 255));
		this.setFocusable(true);

		km = new KeyManager();
		this.addKeyListener(km);
	}
	
	void reach(Player p, BallGroup bg) { // 위치가 (x, y)인 Player에게 물줄기가 닿으면 die
		int x = p.x;
		int y = p.y;
		for (int j = 0; j < bg.balls.size(); j++) {
			Balloon b = bg.balls.get(j);
			if((x >= (b.posX-b.power-1)*M+25 && x < (b.posX+b.power+1)*M-20 && y >= (b.posY-1)*M+10 && y < (b.posY+1)*M-35 )
			|| (y >= (b.posY-b.power-1)*M+10 && y < (b.posY+b.power+1)*M-35 && x >= (b.posX-1)*M+25 && x < (b.posX+1)*M-20 ) ) {
				p.die();
				dead = p;
				if(p==p1) alive = p2;
				else alive = p1;
			}
		}
	}
	
	public void disappear() {
		this.remove(dead);
		dead = null;
		alive.alive = false;
		alive.dir = 2;
		frame.rl.setResult(alive.id);
		frame.rl.setVisible(true);
		this.repaint();
	}
	
	public void draw(){
		frame.rl.setResult(0);
		frame.rl.setVisible(true);
		this.repaint();
	}
	
	void bindGroup(Balloon ball) {
		ArrayList<BallGroup> candidateGroups = new ArrayList<BallGroup>(); // 이동할 그룹 후보
		for (int i = 0; i < this.group.size(); i++) {
			BallGroup bg = this.group.get(i);
			for (int j = 0; j < bg.balls.size(); j++) {
				Balloon b = bg.balls.get(j);
				if((ball.posX >= b.posX-b.power && ball.posX <= b.posX+b.power && ball.posY==b.posY)
				|| (ball.posY >= b.posY-b.power && ball.posY <= b.posY+b.power && ball.posX==b.posX)) {
					candidateGroups.add(bg);
					break;
				}
			}
		}

		if(candidateGroups.size()!=0) { // 기존 그룹과 새 풍선 바인드
			BallGroup firstGroup = candidateGroups.get(0); // 가장 먼저 터질 그룹 추출
			for (int i = 1; i < candidateGroups.size(); i++) {
				if(firstGroup.group > candidateGroups.get(i).group) {
					firstGroup = candidateGroups.get(i);
				}
			}
			
			// 새 풍선 바인드
			firstGroup.balls.add(ball);
			ball.group = firstGroup.group;
			
			// 다른 그룹 바인드
			for (int i = 0; i < candidateGroups.size(); i++) {
				BallGroup bg = candidateGroups.get(i);
				if(bg.equals(firstGroup)) continue;
				for (int j = 0; j < bg.balls.size(); j++) {
					Balloon b = bg.balls.get(j);
					firstGroup.balls.add(b);
					b.group = firstGroup.group;
				}
				this.group.remove(bg); // 그룹 제거
			}
			return;
		}
		
		// 생성된 그룹과 새 풍선 바인드
		BallGroup bg = new BallGroup(groupNum++, this);
		this.group.add(bg);
		bg.balls.add(ball);
		ball.group = bg.group;
	}
	
	public void putBall(Player p) {
		int posX = (p.x+20)/M;
		int posY = (p.y+20)/M;
		if(blocked[posX*M][posY*M]!=0) return;
		
		Balloon ball = new Balloon(p.power, im, posX, posY, this); // 풍선 생성
		bindGroup(ball); // 그룹 부여
		ball.setBounds(posX*M, posY*M, M, M);
		this.add(ball);
		try{ this.repaint();}
		catch(Exception e) { };

		p.onBallList.add(ball);
		int N=30;
		if(p.id==1) {
			if(p2.x > ball.posX*M-N && p2.x < ball.posX*M+N
			&& p2.y > ball.posY*M-N && p2.y < ball.posY*M+N) {
				p2.onBallList.add(ball);
			}
			else setBlock(posX, posY, 2, true);
		}
		else {
			if(p1.x > ball.posX*M-N && p1.x < ball.posX*M+N
			&& p1.y > ball.posY*M-N && p1.y < ball.posY*M+N) {
				p1.onBallList.add(ball);
			}
			else setBlock(posX, posY, 1, true);
		}
	}
	
	int checkBlocked(int x, int y) {
		if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) return 3;
		return blocked[x][y];
	}
	
	void setHalfBlocked(int posX, int posY, int num) { // 수정해야 함
		for (int i = -19; i < 20; i++) {
			for (int j = -19; j < 20; j++) {
				int x = posX*M+i;
				int y = posY*M+j;
				if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) continue;
				this.blocked[x][y] |= num;
			}
		}
	}
	
	void setHalfBlocked(int posX, int posY, int num, boolean blk) { // block=0인 곳만 변경
		for (int i = -19; i < 20; i++) {
			for (int j = -19; j < 20; j++) {
				int x = posX*M+i;
				int y = posY*M+j;
				if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) continue;
				if(blk) { // player<num>을 block 하기
					this.blocked[x][y] |= num;
				}
				else { // player<num>의 block 풀기
					if(this.blocked[x][y]!=0) {
						if(num==1) this.blocked[x][y] &= 2;
						else this.blocked[x][y] &= 1;	
					}
				}
			}
		}
	}
	
	void setBlock(int posX, int posY, int num, boolean blk) { // block=0인 곳만 변경
		for (int i = -39; i < M; i++) {
			for (int j = -39; j < M; j++) {
				int x = posX*M+i;
				int y = posY*M+j;
				if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) continue;
				if(blk) { // player<num>을 block 하기
					this.blocked[x][y] |= num;
				}
				else { // player<num>의 block 풀기
					if(this.blocked[x][y]!=0) {
						if(num==1) this.blocked[x][y] &= 2;
						else this.blocked[x][y] &= 1;	
					}
				}
			}
		}
	}
	
	public void addPlayer(Player p) {
		if(p.id==1) p1 = p;
		else p2 = p;
		this.add(p);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.white);
		
		// position check line ↓
		for(int i=1; i<16; i++)
		{
			g.drawLine(i*M,0,i*M,M*w);
		}
		for(int j=1; j<13; j++)
		{
			g.drawLine(0,j*M,M*w,j*M);
		}
		// position check line ↑
	}
}

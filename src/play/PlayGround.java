package play;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import skeleton.Frame;
import skeleton.ImageManager;
import skeleton.KeyManager;

public class PlayGround extends JPanel {
	int groupNum; // ���ο� ���� ���۵� ������ 0���� �ʱ�ȭ����� �� (�����÷ο�)
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
	
	void reach(Player p, BallGroup bg) { // ��ġ�� (x, y)�� Player���� ���ٱⰡ ������ die
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
		ArrayList<BallGroup> candidateGroups = new ArrayList<BallGroup>(); // �̵��� �׷� �ĺ�
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

		if(candidateGroups.size()!=0) { // ���� �׷�� �� ǳ�� ���ε�
			BallGroup firstGroup = candidateGroups.get(0); // ���� ���� ���� �׷� ����
			for (int i = 1; i < candidateGroups.size(); i++) {
				if(firstGroup.group > candidateGroups.get(i).group) {
					firstGroup = candidateGroups.get(i);
				}
			}
			
			// �� ǳ�� ���ε�
			firstGroup.balls.add(ball);
			ball.group = firstGroup.group;
			
			// �ٸ� �׷� ���ε�
			for (int i = 0; i < candidateGroups.size(); i++) {
				BallGroup bg = candidateGroups.get(i);
				if(bg.equals(firstGroup)) continue;
				for (int j = 0; j < bg.balls.size(); j++) {
					Balloon b = bg.balls.get(j);
					firstGroup.balls.add(b);
					b.group = firstGroup.group;
				}
				this.group.remove(bg); // �׷� ����
			}
			return;
		}
		
		// ������ �׷�� �� ǳ�� ���ε�
		BallGroup bg = new BallGroup(groupNum++, this);
		this.group.add(bg);
		bg.balls.add(ball);
		ball.group = bg.group;
	}
	
	public void putBall(Player p) {
		int posX = (p.x+20)/M;
		int posY = (p.y+20)/M;
		if(blocked[posX*M][posY*M]!=0) return;
		
		Balloon ball = new Balloon(p.power, im, posX, posY, this); // ǳ�� ����
		bindGroup(ball); // �׷� �ο�
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
	
	void setHalfBlocked(int posX, int posY, int num) { // �����ؾ� ��
		for (int i = -19; i < 20; i++) {
			for (int j = -19; j < 20; j++) {
				int x = posX*M+i;
				int y = posY*M+j;
				if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) continue;
				this.blocked[x][y] |= num;
			}
		}
	}
	
	void setHalfBlocked(int posX, int posY, int num, boolean blk) { // block=0�� ���� ����
		for (int i = -19; i < 20; i++) {
			for (int j = -19; j < 20; j++) {
				int x = posX*M+i;
				int y = posY*M+j;
				if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) continue;
				if(blk) { // player<num>�� block �ϱ�
					this.blocked[x][y] |= num;
				}
				else { // player<num>�� block Ǯ��
					if(this.blocked[x][y]!=0) {
						if(num==1) this.blocked[x][y] &= 2;
						else this.blocked[x][y] &= 1;	
					}
				}
			}
		}
	}
	
	void setBlock(int posX, int posY, int num, boolean blk) { // block=0�� ���� ����
		for (int i = -39; i < M; i++) {
			for (int j = -39; j < M; j++) {
				int x = posX*M+i;
				int y = posY*M+j;
				if(x<0 || x>M*(w-1) || y<0 || y>M*(h-1)) continue;
				if(blk) { // player<num>�� block �ϱ�
					this.blocked[x][y] |= num;
				}
				else { // player<num>�� block Ǯ��
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
		
		// position check line ��
		for(int i=1; i<16; i++)
		{
			g.drawLine(i*M,0,i*M,M*w);
		}
		for(int j=1; j<13; j++)
		{
			g.drawLine(0,j*M,M*w,j*M);
		}
		// position check line ��
	}
}

package play;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JLabel;

import skeleton.ImageManager;

public class Player extends JLabel implements Runnable {
    final int M = 40;
    final int DIE = 0;
    final int UP = 1;
    final int DOWN = 2;
    final int LEFT = 3;
    final int RIGHT = 4;
    String name;
    public int speed;
    public int ballNum;
    public int power; // 15���� Ŭ �� ����
    public int id;
    int x, y;
    public int dir;
    Image up, down, right, left, die;
    public boolean moveUp;
    public boolean moveDown;
    public boolean moveLeft;
    public boolean moveRight;
    PlayGround pg;
    Thread moveThread;
    ArrayList<Balloon> onBallList;
    public boolean alive;
    String character;

    public Player(int id, int x, int y, String character) {
        this.id = id;
        this.speed = 1;
        this.ballNum = 1; // ���� ����
        this.power = 9;
        this.dir = DOWN;
        this.x = x * M;
        this.y = y * M;
        this.setBounds(this.x, this.y, M, M);
        this.setFocusable(false);
        this.moveThread = new Thread(this);
        this.onBallList = new ArrayList<Balloon>();
        this.alive = true;
        this.character = character;
    }

    public void setPlayGround(PlayGround pg) {
        this.pg = pg;
        this.moveThread.start();
    }

    public void setImage(ImageManager im) {
        if (character.equals("배찌")) { // 배찌
            die = im.playerDead.getImage();
            up = im.playerUp.getImage();
            down = im.playerDown.getImage();
            left = im.playerLeft.getImage();
            right = im.playerRight.getImage();
        } else { // 다오
            die = im.daoPlayerDead.getImage();
            up = im.daoPlayerUp.getImage();
            down = im.daoPlayerDown.getImage();
            left = im.daoPlayerLeft.getImage();
            right = im.daoPlayerRight.getImage();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dir == UP)
            g.drawImage(up, 0, 0, M, M, this);
        else if (dir == DOWN)
            g.drawImage(down, 0, 0, M, M, this);
        else if (dir == LEFT)
            g.drawImage(left, 0, 0, M, M, this);
        else if (dir == RIGHT)
            g.drawImage(right, 0, 0, M, M, this);
        else if (dir == DIE)
            g.drawImage(die, 0, 0, M, M, this);
    }

    public void run() {
        while (alive) {
            if (moveUp == true) moveUp();
            else if (moveDown == true) moveDown();
            else if (moveRight == true) moveRight();
            else if (moveLeft == true) moveLeft();
            this.requestFocus();
            if (pg.p1 != null && pg.p2 != null && !(pg.p1.alive && pg.p2.alive)) {
                if (pg.p1.alive == false && pg.p2.alive == false) {
                    pg.draw();
                } else if (checkOverlap(pg.p1, pg.p2)) {
                    pg.disappear();
                }
            }
        }
        pg.km.makeFalse(this.id);
    }

    boolean checkOverlap(Player p1, Player p2) { // �� Player�� ��ġ���� Ȯ��
        if (Math.abs(p1.x - p2.x) < M && Math.abs(p1.y - p2.y) < M) return true;
        return false;
    }

    public void die() {
        dir = DIE;
        this.alive = false;
    }

    void checkOnBall() {
        if (this.onBallList.size() != 0) {
            Balloon onBall = onBallList.get(0);
            int posX = onBall.posX;
            int posY = onBall.posY;

            // ��ǳ���� ������ ���
            if (this.x <= posX * M - M || this.x > posX * M + M || this.y <= posY * M - M || this.y > posY * M + M) {
                pg.setBlock(posX, posY, this.id, true);
                this.onBallList.remove(0);
            }
            // ��ǳ���� ���ݸ� ����� ���
            else if (this.x <= posX * M - 20 || this.x > posX * M + 20 || this.y <= posY * M - 20 || this.y > posY * M + 20) {
                pg.setHalfBlocked(posX, posY, this.id, true);
            }
        }
    }

    void moveUp() {
        while (true) {
            try {
                Thread.sleep(speed);
                int block = pg.checkBlocked(x, y - 1);
                if ((block & this.id) == 0) setLocation(x, --y);
                else { // ��ֹ��� 3���� 1�� ���� �ε���
                    int blk = pg.checkBlocked((x + 20) / M * M, y - 1);
                    if ((blk & this.id) == 0) {
                        if (x % M < 20) setLocation(--x, y);
                        else setLocation(++x, y);
                    }
                }
                checkOnBall();
            } catch (InterruptedException e) {
                return;
            }
            if (moveUp == false) break;
        }
    }

    void moveDown() {
        while (true) {
            try {
                Thread.sleep(speed);
                int block = pg.checkBlocked(x, y + 1);
                if ((block & this.id) == 0) setLocation(x, ++y);
                else { // ��ֹ��� 3���� 1�� ���� �ε���
                    int blk = pg.checkBlocked((x + 20) / M * M, y + 1);
                    if ((blk & this.id) == 0) {
                        if (x % M < 20) setLocation(--x, y);
                        else setLocation(++x, y);
                    }
                }
                checkOnBall();
            } catch (InterruptedException e) {
                return;
            }
            if (moveDown == false) break;
        }
    }

    void moveRight() {
        while (true) {
            try {
                Thread.sleep(speed);
                int block = pg.checkBlocked(x + 1, y);
                if ((block & this.id) == 0) setLocation(++x, y);
                else { // ��ֹ��� 3���� 1�� ���� �ε���
                    int blk = pg.checkBlocked(x + 1, (y + 20) / M * M);
                    if ((blk & this.id) == 0) {
                        if (y % M > 20) setLocation(x, ++y);
                        else setLocation(x, --y);
                    }
                }
                checkOnBall();
            } catch (InterruptedException e) {
                return;
            }
            if (moveRight == false) break;
        }
    }

    void moveLeft() {
        while (true) {
            try {
                Thread.sleep(speed);
                int block = pg.checkBlocked(x - 1, y);
                if ((block & this.id) == 0) setLocation(--x, y);
                else { // ��ֹ��� 3���� 1�� ���� �ε���
                    int blk = pg.checkBlocked(x - 1, (y + 20) / M * M);
                    if ((blk & this.id) == 0) {
                        if (y % M > 20) setLocation(x, ++y);
                        else setLocation(x, --y);
                    }
                }
                checkOnBall();
            } catch (InterruptedException e) {
                return;
            }
            if (moveLeft == false) break;
        }
    }
}

package main;

import java.awt.Graphics2D;
import main.Projectile;
import main.GamePanel;
import main.KeyHandler;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player {

    private int x;
    private int y;
    private int speedUp;
    private int speedLeft;
    private int speedDown;
    private int speedRight;
    private int health;
    private boolean isInMap;
    private Projectile[] projectiles;
    private int projectileCount;
    private int projectileTimer;
    private int projectileInc;
    private int size;
    private int damage;
    private int bulletSpeed;
    private Color bulletColor;

    public Player(int x, int y, GamePanel gp) {
        this.x = x;
        this.y = y;
        speedUp = 6;
        speedLeft = 6;
        speedDown = 6;
        speedRight = 6;
        projectileTimer = 45;
        projectileInc = 0;
        damage = 50;
        bulletSpeed = 9;
        bulletColor = Color.ORANGE;

        isInMap = true;

        health = 500;

        size = gp.tileSize;

        projectiles = new Projectile[50];
        projectileCount = 0;
    }

    public int getX() {
        return x;
    }

    public Projectile[] getProjectiles() {
        return projectiles;
    }

    public int getDamage() {
        return damage;
    }

    public void damage(int d) {
        health -= d;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }

    public void moveUp() {
        y -= speedUp;
    }

    public void moveDown() {
        y += speedDown;
    }

    public void moveLeft() {
        x -= speedLeft;
    }

    public void moveRight() {
        x += speedRight;
    }

    public void setSpeedRight(int newSpeed){
        speedRight = newSpeed;
    }
    public void setSpeedLeft(int newSpeed){
        speedLeft = newSpeed;
    }

    public void decSpeed(int dec) {
        speedUp -= dec;
        speedLeft -= dec;
        speedRight -= dec;
        speedDown -= dec;
    }

    public void incSpeed(int inc) {
        speedUp += inc;
        speedLeft += inc;
        speedRight += inc;
        speedDown += inc;

    }

    public void spawnProjectile(GamePanel gp, int leftRight) {
        projectileInc++;
        if (projectileInc >= projectileTimer) {
            if (projectileCount < 20) {
                if (leftRight == 1)
                    projectiles[projectileCount] = new Projectile(x + gp.tileSize,y + gp.tileSize/2 - 5,10, bulletSpeed, 0);
                else
                    projectiles[projectileCount] = new Projectile(x,y + gp.tileSize/2 - 5,10, bulletSpeed, 0);
                projectileCount++;
            }
            if (projectileCount>=20) {
                projectileCount = 1;
                if (leftRight == 1)
                    projectiles[0] = new Projectile(x + gp.tileSize,y + gp.tileSize/2 - 5,10, bulletSpeed, 0);
                else
                    projectiles[0] = new Projectile(x,y + gp.tileSize/2 - 5,10, bulletSpeed, 0);
            }
            projectileInc = 0;
        }
    }

    public void removeProjectile(int i) {
        projectiles[i] = null;
    }

    public void drawPlayer(Graphics2D g2, Color c) {
        g2.setColor(c);
        g2.fillOval(x, y, size, size);

        g2.setColor(bulletColor);
        for (int i = 0; i < projectiles.length; i++) {
            if (projectiles[i] != null)
                g2.fillOval(projectiles[i].getX(), projectiles[i].getY(), projectiles[i].getSize(), projectiles[i].getSize());
        }
    }

    public void checkInMap (GamePanel gp) {
        if ((x < -30 || x > gp.screenWidth - 30) || (y < -10 || y > gp.screenWidth - 520)) {
            isInMap = false;
        }
        else
            isInMap = true;
    }
    public void each(int leftRight) {
        if (!isInMap) {
            health--;
        }
        for (int i = 0; i < projectiles.length; i++) {
            if (projectiles[i] != null)
                projectiles[i].move(leftRight);
        }
    }
    public void checkIsMiddle(GamePanel gp, int num){
        if(num == 1){
            if(x + gp.tileSize > gp.screenWidth/2){
                setSpeedRight(0);
            } else {
                setSpeedRight(6);
            }
        }
        else if(num == -1){
            if(x < gp.screenWidth/2){
                setSpeedLeft(0);
            } else {
                setSpeedLeft(6);
            }
        }
    }

    public void powerUp (int type) {


        if (type == 1) {

            speedLeft += 2;
            speedRight += 2;
            speedUp += 2;
            speedDown += 2;

        }

        if (type == 2) {
            bulletSpeed+=2;
        }

        if (type == 3) {
            health += 150;
            if (health > 500) {
                health = 500;
            }
        }

        if (type == 4) {
            damage += 30;
            if (damage > 50)
                bulletColor = Color.RED;
            if (damage > 89)
                bulletColor = Color.MAGENTA;

        }

        if (type == 5) {
            projectileTimer/=2;
        }
    }

    public void reset (int xI, int yI, GamePanel gp) {
        x = xI;
        y = yI;
        speedUp = 6;
        speedLeft = 6;
        speedDown = 6;
        speedRight = 6;
        projectileTimer = 45;
        projectileInc = 0;
        damage = 50;
        bulletSpeed = 9;
        bulletColor = Color.ORANGE;

        isInMap = true;

        health = 500;

        size = gp.tileSize;

        projectiles = new Projectile[50];
        projectileCount = 0;

    }
}
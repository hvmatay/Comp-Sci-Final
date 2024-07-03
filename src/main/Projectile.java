package main;

public class Projectile {

    private int x;
    private int y;

    private int size;

    private int speedX;
    private int speedY;

    public Projectile (int x, int y, int size, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void move (int leftRight) {
        if (leftRight == -1) {
            x -= speedX;
        }
        if (leftRight == 1) {
            x += speedX;
        }
        y -= speedY;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSize() {
        return size;
    }
}
package main;

import main.GamePanel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt. *;

public class PowerUp {
    private double x;
    private double y;
    private int r;

    private int type;
    private Color color1;

    // 1 = Double Speed
    // 2 = 3x Bullet Size
    // 3 = +150 Health
    // 4 = Double Damage
    // 5 = Rapid fire

    public PowerUp(int type, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;

        if (type == 1) {
            color1 = Color.BLUE;
            r = 12;
        }
        if (type == 2) {
            color1 = Color.MAGENTA;
            r = 12;
        }
        if (type == 3) {
            color1 = Color.GREEN;
            r = 12;
        }
        if (type == 4) {
            color1 = Color.RED;
            r = 12;
        }
        if (type == 5) {
            color1 = Color.CYAN;
            r = 12;
        }
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public double getr() {
        return r;
    }

    public int getType() {
        return type;
    }

    public boolean update(GamePanel gp) {
        y += 2;
        if (y > gp.screenHeight + r) {
            return true;
        }
        return false;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color1);
        g2.fillRect((int) (x-r), (int) (y-r), 2 * r, 2 * r);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(color1.darker());
        g2.drawRect((int) (x-r), (int) (y-r), 2 * r, 2 * r);
        g2.setStroke(new BasicStroke(1));

    }
}
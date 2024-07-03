package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed1, downPressed1, leftPressed1, rightPressed1, upPressed2, downPressed2, leftPressed2,
            rightPressed2, escapePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed1 = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed1 = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed1 = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed1 = true;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed2 = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed2 = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed2 = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed2 = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escapePressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed1 = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed1 = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed1 = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed1 = false;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed2 = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed2 = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed2 = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed2 = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escapePressed = false;
        }
    }
}

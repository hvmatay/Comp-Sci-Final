package main;

//GAME LOOP, JFRAME, and KEY INPUT KNOWLEDGE FROM RYI SNOW ON YOUTUBE
//EVERYTHING ELSE IS DONE BY US
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import main.PowerUp;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 24;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // INITIALIZE PLAYERS
    Player player1 = new Player (100, screenHeight/2-tileSize/2, this);
    Player player2 = new Player (screenWidth-100 - tileSize, screenHeight/2-tileSize/2, this);

    ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
    int interval = (int)(Math.random()* 200 + 400);
    int powerUpCounter = 0;


    boolean gameOver = false;
    boolean player1Win = false;
    boolean player2Win = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread.isAlive()) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        powerUpCounter++;

        if (powerUpCounter >= interval) {
            powerups.add(new PowerUp((int)(Math.random() * 5 + 1), (int)(Math.random() * (screenWidth/2 - 100) + 50), (int)(Math.random() * -200)));
            powerups.add(new PowerUp((int)(Math.random() * 5 + 1), (int)(Math.random() * (screenWidth/2 - 100) + screenWidth/2 + 50), (int)(Math.random() * -200)));
            powerUpCounter = 0;
        }

        player1.spawnProjectile(this, 1);
        player2.spawnProjectile(this, -1);

        if (player1.getHealth() <= 0 && !gameOver) {
            gameOver = true;
            player2Win = true;
        }
        if (player2.getHealth() <= 0 && !gameOver) {
            gameOver = true;
            player1Win = true;
        }

        player1.checkIsMiddle(this,1);
        player2.checkIsMiddle(this,-1);

        //powerup update
        if (powerups.size() > 0)
            for (int i = 0; i < powerups.size(); i++) {
                boolean remove = powerups.get(i).update(this);
                if (remove) {
                    powerups.remove(i);
                    i--;
                }
            }


        // DIAGONALS
        boolean isDiagonal1 = false;
        boolean isDiagonal2 = false;
        if ((keyH.upPressed1 || keyH.downPressed1) && (keyH.leftPressed1 || keyH.rightPressed1)) {
            isDiagonal1 = true;
        }
        if ((keyH.upPressed2 || keyH.downPressed2) && (keyH.leftPressed2 || keyH.rightPressed2)) {
            isDiagonal2 = true;
        }

        if (isDiagonal1) {
            player1.decSpeed(2);
        }
        if (isDiagonal2) {
            player2.decSpeed(2);
        }



        // PLAYER 1 MOVEMENT

        if (keyH.upPressed1) {
            player1.moveUp();
        }
        if (keyH.downPressed1) {
            player1.moveDown();
        }
        if (keyH.leftPressed1) {
            player1.moveLeft();
        }
        if (keyH.rightPressed1) {
            player1.moveRight();
        }

        // PLAYER 2 MOVEMENT

        if (keyH.upPressed2) {
            player2.moveUp();
        }
        if (keyH.downPressed2) {
            player2.moveDown();
        }
        if (keyH.leftPressed2) {
            player2.moveLeft();
        }
        if (keyH.rightPressed2) {
            player2.moveRight();
        }

        if (isDiagonal1) {
            player1.incSpeed(2);
        }
        if (isDiagonal2) {
            player2.incSpeed(2);
        }

        //PLAYER COLLISION
        // PLAYER 1
        Projectile[] projectiles1 = player1.getProjectiles();
        Projectile[] projectiles2 = player2.getProjectiles();
        for (int i = 0; i < projectiles2.length; i++) {
            if (projectiles2[i] != null) {
                if (projectiles2[i].getX() <= player1.getX() + tileSize && projectiles2[i].getX() >= player1.getX()
                        && projectiles2[i].getY() + projectiles2[i].getSize() >= player1.getY() && projectiles2[i].getY() <= player1.getY() + tileSize) {
                    player1.damage(player2.getDamage());
                    player2.removeProjectile(i);
                }
            }
        }
        // PLAYER 2
        for (int i = 0; i < projectiles1.length; i++) {
            if (projectiles1[i] != null) {
                if (projectiles1[i].getX() <= player2.getX() + tileSize && projectiles1[i].getX() >= player2.getX()
                        && projectiles1[i].getY() + projectiles1[i].getSize() >= player2.getY() && projectiles1[i].getY() <= player2.getY() + tileSize) {
                    player2.damage(player1.getDamage());
                    player1.removeProjectile(i);
                }
            }
        }
        // PLAYER 1
        for (int i = 0; i < powerups.size(); i++) {
            if (powerups.get(i).getx() <= player1.getX() + tileSize && powerups.get(i).getx() >= player1.getX()
                    && powerups.get(i).gety() + 6 >= player1.getY() && powerups.get(i).gety() <= player1.getY() + tileSize) {
                player1.powerUp(powerups.remove(i).getType());
            }
        }
        // PLAYER 2
        for (int i = 0; i < powerups.size(); i++) {
            if (powerups.get(i).getx() <= player2.getX() + tileSize && powerups.get(i).getx() >= player2.getX()
                    && powerups.get(i).gety() + 6 >= player2.getY() && powerups.get(i).gety() <= player2.getY() + tileSize) {
                player2.powerUp(powerups.remove(i).getType());
            }
        }

        if (keyH.escapePressed) {
            player1.reset(100, screenHeight/2-tileSize/2, this);
            player2.reset(screenWidth-100 - tileSize, screenHeight/2-tileSize/2, this);
            gameOver = false;
            player1Win = false;
            player2Win = false;
        }


        if (!gameOver) {
            player1.checkInMap(this);
            player2.checkInMap(this);
            player1.each(1);
            player2.each(-1);
        }
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.GRAY);
        if (!gameOver)
            g2.fillRect(screenWidth/2-1, 0, 2, screenHeight);

        if (powerups.size() > 0)
            for (int i = 0; i < powerups.size(); i++) {
                powerups.get(i).draw(g2);
            }

        player1.drawPlayer(g2, Color.BLUE);
        player2.drawPlayer(g2, Color.RED);

        g2.setColor(Color.WHITE);
        g2.fillRect(27, 17, 522, 30);
        g2.fillRect(603, 17, 522, 30);

        g2.setColor(Color.BLACK);
        g2.fillRect(38, 22, 500, 20);
        g2.fillRect(614, 22, 500, 20);

        g2.setColor(Color.GREEN);
        g2.fillRect(38, 22, player1.getHealth(), 20);
        g2.fillRect(614, 22, player2.getHealth(), 20);

        if (gameOver) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Bold", Font.BOLD, 48));
            g2.drawString("GAME OVER", screenWidth/2 - 150, screenHeight/2);
            if (player1Win) {
                g2.setColor(Color.BLUE);
                g2.drawString("BLUE WINS", screenWidth/2-140, screenHeight/2 + 50);
            }
            if (player2Win) {
                g2.setColor(Color.red);
                g2.drawString("RED WINS", screenWidth/2-120, screenHeight/2 + 50);
            }
        }

        g2.dispose();

    }
}
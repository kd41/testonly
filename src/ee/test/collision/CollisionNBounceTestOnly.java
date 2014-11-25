package ee.test.collision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class CollisionNBounceTestOnly extends JPanel {
    private static final long serialVersionUID = 1L;
    List<Ball> ballsL = new ArrayList<Ball>();
    private GameField gameField;
    private DrawCanvas canvas;
    private int canvasWidth;
    private int canvasHeight;
    final MoveBall superBall;

    public CollisionNBounceTestOnly(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;

        // init balls
        // ballsL.add(new Ball(100, 410, 25, 3, 34, Color.YELLOW));
        // ballsL.add(new Ball(80, 350, 25, 2, -114, Color.YELLOW));
        // ballsL.add(new Ball(530, 400, 30, 3, 14, Color.GREEN));
        // ballsL.add(new Ball(400, 400, 30, 3, 14, Color.GREEN));
        // ballsL.add(new Ball(400, 50, 35, 1, -47, Color.PINK));
        // ballsL.add(new Ball(480, 320, 35, 4, 47, Color.PINK));
        // ballsL.add(new Ball(80, 150, 40, 1, -114, Color.GRAY));
        // ballsL.add(new Ball(100, 240, 40, 2, 60, Color.ORANGE));
        // ballsL.add(new Ball(250, 380, 50, 3, -42, Color.BLUE));
        // ballsL.add(new Ball(200, 80, 70, 6, -84, Color.CYAN));
        // ballsL.add(new Ball(500, 170, 90, 6, -42, Color.BLUE));
        // ballsL.add(new Ball(100, 410, 25, 3, 34, Color.YELLOW));
        // ballsL.add(new Ball(80, 350, 25, 2, -114, Color.YELLOW));
        // ballsL.add(new Ball(530, 400, 30, 3, 14, Color.GREEN));
        // ballsL.add(new Ball(400, 400, 30, 3, 14, Color.GREEN));
        // ballsL.add(new Ball(400, 50, 35, 1, -47, Color.PINK));
        // ballsL.add(new Ball(480, 320, 35, 4, 47, Color.PINK));
        // ballsL.add(new Ball(80, 150, 40, 1, -114, Color.GRAY));
        // ballsL.add(new Ball(100, 240, 40, 2, 60, Color.ORANGE));
        // ballsL.add(new Ball(250, 380, 50, 3, -42, Color.BLUE));
        // ballsL.add(new Ball(200, 80, 70, 22, -84, Color.CYAN));
        // ballsL.add(new Ball(500, 170, 90, 6, -42, Color.BLUE));
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            float radius = 10;
            float x = random.nextFloat() * width + 1 - radius;
            float y = random.nextFloat() * height + 1 - radius;
            float speed = random.nextFloat() * 30;
            float angle = random.nextFloat() * 360 - 180;
            ballsL.add(new Ball(x, y, radius, speed, angle, Color.YELLOW));
        }


        superBall = new MoveBall(width / 2, height / 2, 20, 0, 0, Color.BLUE, 1f);
        ballsL.add(superBall);
        gameField = new GameField(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);

        canvas = new DrawCanvas();

        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        // Handling window resize. Adjust container box to fill the screen.

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                Dimension dimension = c.getSize();
                gameField.set(0, 0, dimension.width, dimension.height);
            }
        });
        // Start the ball bouncing
        gameStart();
    }

    /** Start the ball bouncing. */
    public void gameStart() {
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    gameUpdate();
                    repaint();
                    // Delay and give other thread a chance
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        gameThread.start();
    }

    public void gameUpdate() {
        // Check collision between the balls and the box
        for (int i = 0; i < ballsL.size(); i++) {
            PhysicsUtils.collisionWithWall(
                    new Rectangle(gameField.minX, gameField.minY, gameField.maxX, gameField.maxY), ballsL.get(i));
        }
        // Check collision between two balls
        for (int i = 0; i < ballsL.size(); i++) {
            for (int j = 0; j < ballsL.size(); j++) {
                if (i < j) {
                    PhysicsUtils.intersect(ballsL.get(i), ballsL.get(j));
                }
            }
        }
        // update positions increments
        for (int i = 0; i < ballsL.size(); i++) {
            ballsL.get(i).update();
        }

        // remove collision balls
        for (int i = ballsL.size() - 1; i >= 0; i--) {
            if (ballsL.get(i).getCollisionCount() > 300) {
                ballsL.remove(i);
            }
        }

    }

    @SuppressWarnings("static-access")
    public void processKeyUp(KeyEvent e) {
        if (e.getKeyCode() == e.VK_DOWN) {
            superBall.setMoveDown(false);
        } else if (e.getKeyCode() == e.VK_UP) {
            superBall.setMoveUp(false);
        } else if (e.getKeyCode() == e.VK_LEFT) {
            superBall.setMoveLeft(false);
        } else if (e.getKeyCode() == e.VK_RIGHT) {
            superBall.setMoveRight(false);
        }
    }

    @SuppressWarnings("static-access")
    public void processKeyDown(KeyEvent e) {
        if (e.getKeyCode() == e.VK_DOWN) {
            superBall.setMoveDown(true);
        } else if (e.getKeyCode() == e.VK_UP) {
            superBall.setMoveUp(true);
        } else if (e.getKeyCode() == e.VK_LEFT) {
            superBall.setMoveLeft(true);
        } else if (e.getKeyCode() == e.VK_RIGHT) {
            superBall.setMoveRight(true);
        }
    }

    /** The custom drawing panel for the bouncing ball (inner class). */
    private class DrawCanvas extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) {
            // Draw the balls and field
            gameField.draw(g);
            for (Ball ball : ballsL) {
                ball.draw(g);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }
}

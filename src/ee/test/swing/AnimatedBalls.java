package ee.test.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AnimatedBalls {

    public static void main(String[] args) {
        new AnimatedBalls();
    }

    public AnimatedBalls() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new Balls());
                frame.setSize(400, 400);
                frame.setVisible(true);
            }
        });
    }

    public class Balls extends JPanel {
        private static final long serialVersionUID = 1L;

        public Balls() {
            setLayout(null);
            // Randomize the speed and direction...
            add(new Ball(Color.red, 10 - (int) Math.round((Math.random() * 20)),
                    10 - (int) Math.round((Math.random() * 20))));
            add(new Ball(Color.blue, 10 - (int) Math.round((Math.random() * 20)),
                    10 - (int) Math.round((Math.random() * 20))));
            add(new Ball(Color.cyan, 10 - (int) Math.round((Math.random() * 20)),
                    10 - (int) Math.round((Math.random() * 20))));
            add(new Ball(Color.gray, 10 - (int) Math.round((Math.random() * 20)),
                    10 - (int) Math.round((Math.random() * 20))));
            add(new Ball(Color.green, 10 - (int) Math.round((Math.random() * 20)),
                    10 - (int) Math.round((Math.random() * 20))));
        }
    }

    public class Ball extends JPanel implements Runnable {
        private static final long serialVersionUID = 1L;
        int diameter = 30;
        long delay = 50;
        Color color;
        private int vx;
        private int vy;

        public Ball(Color color, int xvelocity, int yvelocity) {
            this.color = color;
            this.vx = xvelocity;
            this.vy = yvelocity;

            new Thread(this).start();

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(color);
            g.fillOval(0, 0, diameter, diameter); // adds color to circle
            g.setColor(Color.black);
            g2.drawOval(0, 0, diameter, diameter); // draws circle
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(diameter, diameter);
        }

        public void run() {

            try {
                // Randomize the location...
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        int x = (int) (Math.round(Math.random() * getParent().getWidth()));
                        int y = (int) (Math.round(Math.random() * getParent().getHeight()));

                        setLocation(x, y);
                    }
                });
            } catch (InterruptedException exp) {
                exp.printStackTrace();
            } catch (InvocationTargetException exp) {
                exp.printStackTrace();
            }

            while (isVisible()) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }

                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            move();
                            repaint();
                        }
                    });
                } catch (InterruptedException exp) {
                    exp.printStackTrace();
                } catch (InvocationTargetException exp) {
                    exp.printStackTrace();
                }
            }
        }

        public void move() {

            int x = getX();
            int y = getY();

            if (x + vx < 0 || x + diameter + vx > getParent().getWidth()) {
                vx *= -1;
            }
            if (y + vy < 0 || y + diameter + vy > getParent().getHeight()) {
                vy *= -1;
            }
            x += vx;
            y += vy;

            // Update the size and location...
            setSize(getPreferredSize());
            setLocation(x, y);

        }
    }
}
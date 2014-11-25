package ee.test.collision;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * http://code.google.com/p/java-collision-detection-source-code
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Balls");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                final CollisionNBounceTestOnly panel = new CollisionNBounceTestOnly(400, 300);
                frame.setContentPane(panel);
                frame.pack();
                frame.setVisible(true);

                frame.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        panel.processKeyUp(e);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        panel.processKeyDown(e);
                    }
                });
            }
        });
    }
}

package ee.test.swing;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Bubble extends JPanel {
    private static final long serialVersionUID = 1L;

    int width = 400;
    int height = 400;
    private JFrame frame;
    private static Ellipse2D.Double circle;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Bubble window = new Bubble();
                window.createAndDisplayGUI(window);
            }
        });
    }

    public void createAndDisplayGUI(Bubble window) {
        frame = new JFrame();
        Container container = frame.getContentPane();
        container.add(window);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        window.requestFocusInWindow();
        
        circle = new Ellipse2D.Double(0.1 * width, 0.1 * height, 0.1 * width, 0.1 * height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D brush = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0, 0, width, height);
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.draw(circle);
    }

}

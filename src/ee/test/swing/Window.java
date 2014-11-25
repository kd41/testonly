package ee.test.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Window extends JPanel {

    private static final long serialVersionUID = 1L;

    private static Ellipse2D.Double circle;
    private static Ellipse2D.Double helpCircle;
    private JFrame frame;
    private boolean showHelpCircle;
    private JLabel label;

    public Window() {
        super();
        int width = 400;
        int height = 400;
        circle = new Ellipse2D.Double(0.5 * width, 0.9 * height, 0.1 * width, 0.1 * height);
        helpCircle = new Ellipse2D.Double(0.5 * width, 0.9 * height, 0.2 * width, 0.2 * height);
    }

    public Dimension getPreferredSize() {
        return (new Dimension(frame.getWidth(), frame.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D brush = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        g.clearRect(111, 111, width, height);
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.draw(circle);
        if (showHelpCircle) {
            brush.draw(helpCircle);
        }
//        frame.add(label);
    }

    public class MoveCircle implements KeyListener, MouseListener, MouseMotionListener {

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Working on top!");
            if (e.getKeyCode() == Event.ENTER) {
                System.out.println("Working on bottom!");
                double newX = circle.x - 1;
                circle.x = newX;
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            // TODO Auto-generated method stub
            int k = 9;
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
            showHelpCircle = true;
            repaint();

        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            showHelpCircle = false;
            repaint();

        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseDragged(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {
//            frame.remove(label);
            label.setText("Mouse position: x=" + arg0.getXOnScreen() + " y=" + arg0.getYOnScreen());
            System.out.println(arg0.getXOnScreen() + ":" + arg0.getYOnScreen());
        }
    }

    private void createAndDisplayGUI(Window window) {
        frame = new JFrame();
        label = new JLabel();
        label.setText("Mouse position: ");
        label.setLocation(54, 38);
        frame.add(label);
        Container container = frame.getContentPane();
        container.add(window);
        MoveCircle mc = new MoveCircle();
        window.addKeyListener(mc);
        window.addMouseListener(mc);
        window.addMouseMotionListener(mc);
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        window.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window window = new Window();
                window.createAndDisplayGUI(window);
            }
        });
    }
}
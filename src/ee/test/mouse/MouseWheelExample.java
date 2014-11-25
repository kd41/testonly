package ee.test.mouse;

import java.awt.GridBagLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class MouseWheelExample implements Runnable {
    private JLabel wheelLabel;
    private AtomicInteger count = new AtomicInteger(0);
    private ScheduledExecutorService statisticsExecutor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String... args) throws Exception {
        new MouseWheelExample().init();
    }

    @Override
    public void run() {
        // change GUI in gui-thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                wheelLabel.setText(count + " count/sec");
                count.set(0);
            }
        });
    }

    public void init() {
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setLocation(100, 200);
        frame.setTitle("Mouse Wheel Count Frame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        wheelLabel = new JLabel();
        wheelLabel.setText("0 count/sec");
        frame.add(wheelLabel);
        panel.add(wheelLabel);

        frame.add(panel);

        // end statistics
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                statisticsExecutor.shutdown();
            }
        });

        // mouse wheel
        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                count.addAndGet(e.getScrollAmount());
            }
        });

        frame.setVisible(true);

        startStatiscs();
    }

    private void startStatiscs() {
        statisticsExecutor.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
    }
}

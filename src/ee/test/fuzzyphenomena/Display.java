package ee.test.fuzzyphenomena;

// Display.java

import java.awt.*;

class Display extends Canvas {
    private Dimension dimension;

    private double wvxm, wvxa, wvym, wvya;

    public double wl, wb, wr, wt;

    private Image imBuffer;

    private Graphics gImBuffer;

    Display(double wl, double wb, double wr, double wt, int width, int height) {
        // Create a Dimension object to return from getPreferredSize() so that
        // the entire viewport is visible.

        dimension = new Dimension(width, height);

        // Calculate viewport coordinate.

        double vl = 0;
        double vr = width - 1;
        double vb = 0;
        double vt = height - 1;

        // Calculate windowing transform multipliers and adders so that world
        // coordinates transform to screen coordinates.

        wvxm = (vr - vl) / (wr - wl);
        wvxa = vl - wl * wvxm;
        wvym = (vt - vb) / (wt - wb);
        wvya = vb - wb * wvym;

        // Save window coordinates for access from particle system. The particle
        // system examines each particle to see if it has left the window. If so,
        // the particle is killed off.

        this.wl = wl;
        this.wb = wb;
        this.wr = wr;
        this.wt = wt;

        setBackground(Color.black);
    }

    public void addNotify() {
        super.addNotify();

        // A background buffer for this canvas component can be created only
        // after the canvas is displayable. This happens after the
        // super.addNotify() call, which connects the canvas to a native screen
        // resource.

        imBuffer = createImage(dimension.width, dimension.height);

        // All drawing into the background buffer occurs through a graphics
        // context.

        gImBuffer = imBuffer.getGraphics();
    }

    void clear() {
        // I've established a convention where all drawing takes place on a
        // black background. To change this convention, you would need to
        // change the color in the following setColor() method call, pass a
        // different color to setBackground (Color.black); in the constructor,
        // and modify the PS class's notion of fading a color to black.

        gImBuffer.setColor(Color.black);
        gImBuffer.fillRect(0, 0, getWidth(), getHeight());
    }

    void drawLine(double x1, double y1, double x2, double y2, Color c) {
        gImBuffer.setColor(c);

        // Convert from world to screen coordinates.

        int sx1 = (int) (wvxm * x1 + wvxa);
        int sy1 = (int) (wvym * y1 + wvya);

        int sx2 = (int) (wvxm * x2 + wvxa);
        int sy2 = (int) (wvym * y2 + wvya);

        // Because window coordinates are such that y increases towards the top
        // of the window, the same convention must be observed at the viewport
        // level.

        gImBuffer.drawLine(sx1, dimension.height - sy1, sx2, dimension.height - sy2);
    }

    public Dimension getPreferredSize() {
        return dimension;
    }

    public void paint(Graphics g) {
        g.drawImage(imBuffer, 0, 0, this);
    }

    void plot(double x, double y, Color c) {
        gImBuffer.setColor(c);

        int sx = (int) (wvxm * x + wvxa);
        int sy = (int) (wvym * y + wvya);

        gImBuffer.drawLine(sx, dimension.height - sy, sx, dimension.height - sy);
    }

    public void update(Graphics g) {
        paint(g);
    }
}

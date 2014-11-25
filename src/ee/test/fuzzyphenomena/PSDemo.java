package ee.test.fuzzyphenomena;

// PSDemo.java

import java.awt.*;
import java.awt.event.*;

public class PSDemo extends java.applet.Applet {
    final static int DELAY_EXPLOSION_RING = 25;
    final static int DELAY_FIREWORKS_EXPLOSION = 5;
    final static int DELAY_VAPOR_TRAIL = 25;

    Display display;

    Button btnFireworksExplosion, btnExplosionRing, btnVaporTrail;

    PS ps;

    Thread thd;

    public void init() {
        // Create a display on which to render particles. Specify a window with
        // lower-right corner at (-100.0, -100.0) and upper-right corner at
        // (100.0, 100.0). Specify the applet's 50 pixels less than the applet's
        // width and 50 pixels less than its height (to accommodate the buttons
        // panel) as the viewport's extents.

        display = new Display(-100.0, -100.0, 100.0, 100.0, getWidth() - 50, getHeight() - 50);

        add(display, BorderLayout.NORTH);

        // Build a panel with three buttons whose listeners are responsible for
        // the explosion ring, fireworks explosion, and vapor trail simulations.

        Panel pnl = new Panel();

        btnExplosionRing = new Button("Explosion Ring");
        ActionListener al;
        al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                explosionRing();
            }
        };
        btnExplosionRing.addActionListener(al);
        pnl.add(btnExplosionRing);

        btnFireworksExplosion = new Button("Fireworks Explosion");
        al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireworksExplosion();
            }
        };
        btnFireworksExplosion.addActionListener(al);
        pnl.add(btnFireworksExplosion);

        btnVaporTrail = new Button("Vapor Trail");
        al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vaporTrail();
            }
        };
        btnVaporTrail.addActionListener(al);
        pnl.add(btnVaporTrail);

        add(pnl, BorderLayout.SOUTH);

        // Create the particle system that interacts with the specified display
        // to output colored particles.

        ps = new PS(display);
    }

    void explosionRing() {
        // I want to disable all three buttons when a button is clicked. (These
        // buttons are enabled once all particles in the particle system have
        // died.) I do this to prevent any potential synchronization problems
        // that might arise because of a simulation thread running at the same
        // time as the AWT's event-dispatching thread.

        btnFireworksExplosion.setEnabled(false);
        btnVaporTrail.setEnabled(false);
        btnExplosionRing.setEnabled(false);

        // Reset the particle system.

        ps.reset();

        // Generate 1000 particles that are positioned at the center of the
        // outer explosion ring and share the same range of velocities. Give the
        // outer explosion ring a higher acceleration than the inner explosion
        // ring so that the outer explosion ring pulls away from the inner
        // explosion ring.

        final double x;
        final double y;
        startExplosionRing(PS.PARTICLE_TYPE_FLICKERFADE, PS.PARTICLE_COLOR_TYPE_GREEN, 300, 0, x = rnd(-75.0, 75.0),
                y = rnd(-75.0, 75.0), 0, 0, 1.6, 3000);

        Runnable r = new Runnable() {
            public void run() {
                int counter = 0;

                Thread thdCurrent = Thread.currentThread();

                while (thdCurrent == thd && !ps.isFinished()) {
                    // Before particles can be drawn in their current
                    // positions, the display must be cleared to
                    // remove the particles drawn in their previous
                    // positions.

                    display.clear();

                    // Update the particle system. All particles are
                    // transitioned to a new state, and are rendered
                    // to the display.

                    ps.update();

                    // Because particles are actually rendered to a
                    // background buffer (to prevent screen flicker),
                    // the display is told to paint the background
                    // buffer onto its surface.

                    display.repaint();

                    try {
                        Thread.sleep(DELAY_EXPLOSION_RING);
                    } catch (InterruptedException e) {
                    }

                    // After a small delay, generate a secondary
                    // explosion ring consisting of 500 particles.
                    // These particles are positioned at the center
                    // of the explosion ring and share the same range
                    // of velocities.

                    if (++counter == 8)
                        startExplosionRing(PS.PARTICLE_TYPE_FADE, PS.PARTICLE_COLOR_TYPE_YELLOW, 300, 0, x, y, 0, 0,
                                1.0, 500);

                }

                // ps.isFinished() returns true when all particles
                // have died. At this point, buttons are re-enabled
                // to let the user choose another simulation.

                btnFireworksExplosion.setEnabled(true);
                btnVaporTrail.setEnabled(true);
                btnExplosionRing.setEnabled(true);
            }
        };

        // Prepare new simulation thread and begin the simulation.

        thd = new Thread(r);
        thd.start();
    }

    void fireworksExplosion() {
        btnVaporTrail.setEnabled(false);
        btnExplosionRing.setEnabled(false);
        btnFireworksExplosion.setEnabled(false);

        ps.reset();

        // Generate 4000 particles that are positioned at the center of the
        // fireworks explosion and have unique velocities.

        double x;
        double y;
        startFireworksExplosion(PS.PARTICLE_TYPE_FLICKERFADE, PS.PARTICLE_COLOR_TYPE_YELLOW, 800, PS.rnd(50, 100),
                x = rnd(-75.0, 75.0), y = rnd(-75.0, 75.0), 0, 0, 4000);

        // Generate 500 particles of a different color that are positioned at
        // the center of the fireworks explosion and have unique velocities among
        // themselves. This adds a more colorful effect to the explosion.

        startFireworksExplosion(PS.PARTICLE_TYPE_FADE, PS.PARTICLE_COLOR_TYPE_RED, 800, PS.rnd(50, 100), x, y, 0, 0,
                500);

        // Generate 250 particles of a different color that are positioned at
        // the center of the fireworks explosion and have unique velocities among
        // themselves. This adds an even more colorful effect to the explosion.

        startFireworksExplosion(PS.PARTICLE_TYPE_FADE, PS.PARTICLE_COLOR_TYPE_WHITE, 800, PS.rnd(50, 100), x, y, 0, 0,
                250);

        // Because the fireworks explosion happens in the atmosphere of a planet,
        // particles will be pulled toward the ground via the planet's
        // gravitational force. Because of the window transform, a negative
        // value must be used to pull particles toward the bottom of the window.

        ps.setGravity(-0.01);

        Runnable r = new Runnable() {
            public void run() {
                Thread thdCurrent = Thread.currentThread();

                while (thdCurrent == thd && !ps.isFinished()) {
                    display.clear();

                    ps.update();

                    display.repaint();

                    try {
                        Thread.sleep(DELAY_FIREWORKS_EXPLOSION);
                    } catch (InterruptedException e) {
                    }
                }

                btnVaporTrail.setEnabled(true);
                btnExplosionRing.setEnabled(true);
                btnFireworksExplosion.setEnabled(true);
            }
        };

        thd = new Thread(r);
        thd.start();
    }

    public double rnd(double min, double max) {
        // Return a new double-precision random number that ranges from min
        // inclusive to max exclusive.

        return min + Math.random() * (max - min);
    }

    public void stop() {
        // Stop current simulation thread when user moves away from Web page or
        // when applet is about to be destroyed.

        thd = null;
    }

    void startExplosionRing(int type, int color_type, int lifetime, int age, double x, double y, double xv, double yv,
            double accel, int num_particles) {
        while (--num_particles >= 0) {
            // Calculate particle's departure angle in terms of degrees.

            int ang = PS.rnd(0, 359);

            // All particles must leave the explosion's center within a limited
            // range of velocities.

            double vel = 1.5 + rnd(0.0, 0.5);

            // Accelerate or decelerate the explosion ring if accel is not 1.0.

            vel *= accel;

            // Generate the particle so that it leaves the explosion ring in the
            // appropriate direction.

            ps.generateParticle(type, color_type, lifetime, age, x, y, xv + Math.cos(Math.toRadians(ang)) * vel, yv
                    + Math.sin(Math.toRadians(ang)) * vel);
        }
    }

    void startFireworksExplosion(int type, int color_type, int lifetime, int age, double x, double y, double xv,
            double yv, int num_particles) {
        // Each particle must be given a unique velocity for the firworks
        // explosion to look realistic. This is accomplished first by calculating
        // a velocity increment based on the number of particles, and then by
        // specifying num_particles velocities, where each velocity is a unique
        // multiple of the increment.

        double incr = 1.0 / num_particles;
        double vel = 0.0;

        while (--num_particles >= 0) {
            // Calculate particle's departure angle in terms of degrees.

            int ang = PS.rnd(0, 359);

            // Establish appropriate departure velocity.

            vel += incr;

            // Generate the particle so that it leaves the fireworks explosion's
            // center in the appropriate direction and at the appropriate
            // velocity.

            ps.generateParticle(type, color_type, lifetime, age, x, y, xv + Math.cos(Math.toRadians(ang)) * vel, yv
                    + Math.sin(Math.toRadians(ang)) * vel);
        }
    }

    void vaporTrail() {
        btnExplosionRing.setEnabled(false);
        btnFireworksExplosion.setEnabled(false);
        btnVaporTrail.setEnabled(false);

        ps.reset();

        Runnable r;
        r = new Runnable() {
            public void run() {
                // Vapor trail particles emerge from an emitter -- the nozzle
                // of a rocket ship, for example. Establish the emitter's
                // initial location in the lower-right quadrant of the window.

                double emit_x = 75.0;
                double emit_y = -75.0;

                // Generate an initial particle. At least one particle must
                // exist. Otherwise, ps.isFinished() returns false (because
                // there are no alive particles) and the while loop is never
                // entered.

                ps.generateParticle(PS.PARTICLE_TYPE_FADE, PS.PARTICLE_COLOR_TYPE_WHITE, 300, PS.rnd(90, 150), emit_x,
                        emit_y, 1.0, -1.0);

                Thread thdCurrent = Thread.currentThread();

                while (thdCurrent == thd && !ps.isFinished()) {
                    // Create a group of particles that blast forth in various
                    // directions from the emitter. Directions are constrained
                    // so that the vapor trail moves in the opposite direction
                    // to the emitter.

                    int limit = PS.rnd(30, 130);
                    for (int i = 0; i < limit; i++) {
                        double xv = rnd(1.0, 7.0);
                        double yv = rnd(-1.0, -7.0);

                        ps.generateParticle(PS.PARTICLE_TYPE_FADE, PS.PARTICLE_COLOR_TYPE_WHITE, 300, PS.rnd(90, 150),
                                emit_x, emit_y, xv, yv);
                    }

                    display.clear();

                    ps.update();

                    // Draw a rocket. (As an exercise, make the rocket look
                    // more realistic than its current arrow shape.)

                    display.drawLine(emit_x - 8, emit_y + 8, emit_x - 8, emit_y - 2, Color.magenta);
                    display.drawLine(emit_x - 8, emit_y + 8, emit_x + 2, emit_y + 8, Color.magenta);
                    display.drawLine(emit_x - 8, emit_y + 8, emit_x + 2, emit_y - 2, Color.magenta);

                    display.repaint();

                    try {
                        Thread.sleep(DELAY_VAPOR_TRAIL);
                    } catch (InterruptedException e) {
                    }

                    // Move the emitter (and rocket) in a diagonal direction
                    // towards the upper-left corner of the window.

                    emit_x -= 1.0;
                    emit_y += 1.0;
                }

                btnExplosionRing.setEnabled(true);
                btnFireworksExplosion.setEnabled(true);
                btnVaporTrail.setEnabled(true);
            }
        };

        thd = new Thread(r);
        thd.start();
    }
}

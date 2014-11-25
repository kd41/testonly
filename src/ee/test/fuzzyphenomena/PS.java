package ee.test.fuzzyphenomena;

// PS.java

import java.awt.*;

class PS {
    final static int PARTICLE_TYPE_CONSTANT = 0;
    final static int PARTICLE_TYPE_FADE = 1;
    final static int PARTICLE_TYPE_FLICKERFADE = 2;

    final static int PARTICLE_COLOR_TYPE_GREEN = 0;
    final static int PARTICLE_COLOR_TYPE_RED = 1;
    final static int PARTICLE_COLOR_TYPE_WHITE = 2;
    final static int PARTICLE_COLOR_TYPE_YELLOW = 3;

    final static int MAX_PARTICLES = 5000;

    private final static int PARTICLE_STATE_DEAD = 0;
    private final static int PARTICLE_STATE_ALIVE = 1;

    private Display display;

    private Color[] grns, reds, whis, yels;

    private Particle[] particles;

    double particle_wind = 0.0;

    double particle_gravity = 0.0;

    PS(Display display) {
        // Save display for drawing purposes (during the update() method).

        this.display = display;

        // Create color tables for shades of green, red, white, and yellow. The
        // use of tables improves performance and eliminates the creation of
        // unnecessary Color objects during update().

        grns = new Color[256];
        for (int i = 0; i < 256; i++)
            grns[i] = new Color(0, i, 0);

        reds = new Color[256];
        for (int i = 0; i < 256; i++)
            reds[i] = new Color(i, 0, 0);

        whis = new Color[256];
        for (int i = 0; i < 256; i++)
            whis[i] = new Color(i, i, i);

        yels = new Color[256];
        for (int i = 0; i < 256; i++)
            yels[i] = new Color(i, i, 0);

        // Establish an array of Particle objects. Each Particle is initialized
        // to default values.

        particles = new Particle[MAX_PARTICLES];
        for (int i = 0; i < MAX_PARTICLES; i++)
            particles[i] = new Particle();
    }

    void generateParticle(int type, int color_type, int lifetime, int age, double x, double y, double xv, double yv) {
        // Validate lifetime and age arguments.

        if (lifetime <= 0)
            throw new IllegalArgumentException("lifetime <= 0");
        else if (age < 0)
            throw new IllegalArgumentException("age < 0");
        else if (age >= lifetime)
            throw new IllegalArgumentException("age >= lifetime");

        // Search for an available Particle object.

        int pindex = -1;
        for (int i = 0; i < MAX_PARTICLES; i++)
            if (particles[i].state == PARTICLE_STATE_DEAD) {
                pindex = i;
                break;
            }

        if (pindex == -1)
            return;

        // If Particle object found, mark it as alive and establish its startup
        // values.

        particles[pindex].state = PARTICLE_STATE_ALIVE;
        particles[pindex].type = type;
        particles[pindex].color_type = color_type;
        particles[pindex].lifetime = lifetime;
        particles[pindex].age = age;
        particles[pindex].x = x;
        particles[pindex].y = y;
        particles[pindex].xv = xv;
        particles[pindex].yv = yv;
        particles[pindex].curr_color = 255;

        if (type == PARTICLE_TYPE_FLICKERFADE) {
            // Establish initial color bounds for flickering particles.

            particles[pindex].up_bound = 255;
            particles[pindex].lo_bound = 127;
        }
    }

    boolean isFinished() {
        // The particle system is finished when all particles are dead.

        for (int i = 0; i < MAX_PARTICLES; i++)
            if (particles[i].state == PARTICLE_STATE_ALIVE)
                return false;

        return true;
    }

    void reset() {
        for (int i = 0; i < MAX_PARTICLES; i++)
            particles[i].reset();

        particle_wind = 0.0;
        particle_gravity = 0.0;
    }

    static int rnd(int min, int max) {
        // Return an integer that ranges from min inclusive to max inclusive.

        return (int) (min + Math.random() * (max - min + 1));
    }

    void setGravity(double g) {
        particle_gravity = g;
    }

    void setWind(double w) {
        particle_wind = w;
    }

    void update() {
        for (int i = 0; i < MAX_PARTICLES; i++)
            if (particles[i].state == PARTICLE_STATE_ALIVE) {
                // Update particle position based on its horizontal and
                // vertical velocities, and directions (the sign of a velocity
                // determines direction).

                particles[i].x += particles[i].xv;
                particles[i].y += particles[i].yv;

                // When a particle moves beyond the window's boundaries, the
                // particle is killed off.

                if (particles[i].x < display.wl || particles[i].x > display.wr || particles[i].y < display.wb
                        || particles[i].y > display.wt) {
                    particles[i].state = PARTICLE_STATE_DEAD;
                    continue;
                }

                // Adjust particle velocities to account for wind and gravity.
                // For simplicity, wind affects only the horizontal velocity.
                // (As an exercise, think about supporting downward wind gusts,
                // which would also need to take the vertical velocity into
                // account.)

                particles[i].xv += particle_wind;
                particles[i].yv += particle_gravity;

                // If the particle has a constant type (it never changes its
                // color -- this is suitable for fountain-based particle
                // systems, for example), the particle maintains its initial
                // shade of color. The particle is not killed off until its
                // current age reaches its lifetime. If the particle has a
                // flicker-fade or fade type, the particle ages by flickering-
                // fading or just fading to black. When the particle reaches
                // black, it is killed off. However, a flicker-fade or fade
                // particle also ages in the same manner as a constant
                // particle. If a flicker-fade or fade particle's current age
                // reaches its lifetime before its color fades to black, the
                // particle is killed off.

                if (particles[i].type == PARTICLE_TYPE_FLICKERFADE) {
                    particles[i].curr_color = rnd(particles[i].lo_bound, particles[i].up_bound);

                    if (particles[i].curr_color == 0) {
                        particles[i].state = PARTICLE_STATE_DEAD;
                        continue;
                    }

                    // It's not necessary to kill off the particle when either
                    // the lower bound or upper bound reaches 0 because the
                    // next iteration will automatically kill off the particle
                    // (because black will be chosen) -- unless the particle
                    // is killed off first when its current age reaches its
                    // lifetime limit (later in the code).

                    if (--particles[i].lo_bound < 0)
                        particles[i].lo_bound = 0;

                    if (--particles[i].up_bound < 0)
                        particles[i].up_bound = 0;
                } else if (particles[i].type == PARTICLE_TYPE_FADE) {
                    if (--particles[i].curr_color == 0) {
                        particles[i].state = PARTICLE_STATE_DEAD;
                        continue;
                    }
                }

                // Tell the display to plot the particle at its new position
                // using its color.

                switch (particles[i].color_type) {
                case PARTICLE_COLOR_TYPE_GREEN:
                    display.plot(particles[i].x, particles[i].y, grns[particles[i].curr_color]);
                    break;

                case PARTICLE_COLOR_TYPE_RED:
                    display.plot(particles[i].x, particles[i].y, reds[particles[i].curr_color]);
                    break;

                case PARTICLE_COLOR_TYPE_WHITE:
                    display.plot(particles[i].x, particles[i].y, whis[particles[i].curr_color]);
                    break;

                case PARTICLE_COLOR_TYPE_YELLOW:
                    display.plot(particles[i].x, particles[i].y, yels[particles[i].curr_color]);
                }

                // Age the particle. When the particle's age reaches its
                // lifetime limit, the particle is killed off.

                if (++particles[i].age == particles[i].lifetime)
                    particles[i].state = PARTICLE_STATE_DEAD;
            }
    }

    private class Particle {
        int state; // particle is alive or dead
        int type; // particle is constant, flicker-fades to black or fades
                  // to black
        int color_type; // particle is green, red, whiten, or yellow
        int lifetime; // particle dies when it reaches this value
        int age; // particle's current age
        double x, y; // particle's current position
        double xv, yv; // particle's current horizontal and vertical velocities
        int curr_color; // particle's current shade of its color type
        int up_bound; // particle's current upper color bound (for
                      // flicker-fade)
        int lo_bound; // particle's current lower color bound (for
                      // flicker-fade)

        Particle() {
            reset();
        }

        void reset() {
            state = PARTICLE_STATE_DEAD;
            type = PARTICLE_TYPE_CONSTANT;
            color_type = -1;
            lifetime = 0;
            age = 0;
            x = 0.0;
            y = 0.0;
            xv = 0.0;
            yv = 0.0;
            curr_color = 0;
            up_bound = 0;
            lo_bound = 0;
        }
    }
}

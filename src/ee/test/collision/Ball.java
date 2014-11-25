package ee.test.collision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Ball {
    float x, y; // Ball's center x and y
    float speedX, speedY; // Ball's speed per step in x and y
    float radius; // Ball's radius
    Color color; // Ball's color
    float mass = -1;
    int collisionCount = 0;
    Font countFont;

    public Ball(float x, float y, float radius, float speed, float angleInDegree, Color color) {
        this.x = x;
        this.y = y;
        this.speedX = (float) (speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float) (-speed * (float) Math.sin(Math.toRadians(angleInDegree)));
        this.radius = radius;
        this.color = color;
        this.countFont = new Font("Collision count", Font.BOLD, (int) radius);
    }

    public void update() {
        this.x += this.speedX;
        this.y += this.speedY;
    }

    /** Draw itself using the given graphics context. */
    public void draw(Graphics g) {
        g.setColor(color);
        int _x = (int) (x - radius);
        int _y = (int) (y - radius);
        g.fillOval(_x, _y, (int) (2 * radius), (int) (2 * radius));
        int fontSize = (int) radius;
        int countX = _x + (int) radius - fontSize / 2;
        int countY = _y + (int) radius + fontSize / 2 - 2;
        g.setFont(countFont);
        g.setColor(Color.RED);
        g.drawString("" + collisionCount, countX, countY);
    }

    /** Return mass */
    public float getMass() {
        if (mass < 0) {
            mass = 2 * radius * radius * radius / 1000f;
        }
        return mass;
    }

    public int getCollisionCount() {
        return collisionCount;
    }

    public void increaseCollisionCount() {
        collisionCount++;
    }
}

package ee.test.collision;

import java.awt.Color;

public class MoveBall extends Ball {
    private float moveDelta;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    public MoveBall(float x, float y, float radius, float speed, float angleInDegree, Color color, float moveDelta) {
        super(x, y, radius, speed, angleInDegree, color);
        this.moveDelta = moveDelta;
    }

    public void moveVertical(float delta) {
        y += delta;
    }

    public void moveHorisontal(float delta) {
        x += delta;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    @Override
    public void update() {
        super.update();

        if (moveLeft) {
            moveHorisontal(-moveDelta);
            this.speedX = -moveDelta;
        } else if (moveRight) {
            moveHorisontal(moveDelta);
            this.speedX = moveDelta;
        } else {
            this.speedX = 0f;
        }
        if (moveUp) {
            moveVertical(-moveDelta);
            this.speedY = -moveDelta;
        } else if (moveDown) {
            moveVertical(moveDelta);
            this.speedY = moveDelta;
        } else {
            this.speedY = 0f;
        }
    }
}

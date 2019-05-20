package ch.heigvd.pro.a03.simulation;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * An animator representing a bullet
 */
public class Bullet extends Animator {

    private float radius;
    private boolean didHit;

    /**
     * Creates a new bullet
     * @param position starting position
     * @param target destination
     */
    public Bullet(Vector2 position, Vector2 target) {
        super(position, target, 512);
        this.radius = 4;
        this.didHit = false;
    }

    @Override
    public void update(float deltaTime) {
        if (!didHit()) {
            super.update(deltaTime);
        }
    }

    /**
     * draws the bullets
     * @param shapeRenderer shape renderer
     */
    public void draw(ShapeRenderer shapeRenderer) {
        if (!didHit()) {
            shapeRenderer.circle(position.x, position.y, radius);
        }
    }

    /**
     * Check if the bullet hit the target
     * @return true if hit
     */
    private boolean didHit() {
        didHit |= super.atDestination(32);
        return didHit;
    }
}

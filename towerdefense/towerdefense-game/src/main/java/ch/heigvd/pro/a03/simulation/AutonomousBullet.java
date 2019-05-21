package ch.heigvd.pro.a03.simulation;

import com.badlogic.gdx.math.Vector2;

/**
 * An autonomous bullet that follows an Animator
 */
public class AutonomousBullet extends Bullet {

    private Animator target;

    /**
     * Creates a new autonomous bullet
     * @param position starting position
     * @param target animator to follow
     */
    public AutonomousBullet(Vector2 position, Animator target) {
        super(position, target.getPosition());
        this.target = target;
    }

    @Override
    public void update(float deltaTime) {
        setDestination(target.getPosition());
        super.update(deltaTime);
    }
}

package ch.heigvd.pro.a03.simulation;

import com.badlogic.gdx.math.Vector2;

public class AutonomousBullet extends Bullet {

    private Animator target;

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

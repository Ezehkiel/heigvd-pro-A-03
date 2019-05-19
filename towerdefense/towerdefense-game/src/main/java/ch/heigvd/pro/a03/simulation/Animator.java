package ch.heigvd.pro.a03.simulation;

import com.badlogic.gdx.math.Vector2;

public class Animator {

    private final static float DEAD_ZONE = 0.25f;
    protected Vector2 position;
    private Vector2 destination;
    private float speed;

    public Animator(Vector2 position, Vector2 destination, float speed) {
        this.position = position;
        this.destination = destination;
        this.speed = speed;
    }

    public boolean atDestination(float radius) {
        return position.dst(destination) <= radius;
    }

    public void update(float deltaTime) {

        if (position.dst(destination) <= speed * deltaTime) {
            return;
        }

        Vector2 direction = new Vector2(destination).sub(position);
        direction.setLength(speed * deltaTime);

        position.add(direction);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setDestination(Vector2 destination) {
        this.destination = destination;
    }

    public float getDirectionAngle() {
        return new Vector2(destination).sub(position).angle();
    }
}

package ch.heigvd.pro.a03.simulation;

import com.badlogic.gdx.math.Vector2;

/**
 * Moves a vector2 towards a destination at a given speed
 */
public class Animator {

    protected Vector2 position;
    private Vector2 destination;
    private float speed;

    /**
     * Create the animator
     * @param position starting position
     * @param destination destination
     * @param speed speed
     */
    public Animator(Vector2 position, Vector2 destination, float speed) {
        this.position = position;
        this.destination = destination;
        this.speed = speed;
    }

    /**
     * Check if in a radius of the destination
     * @param radius radius
     * @return true if in the radius
     */
    public boolean atDestination(float radius) {
        return position.dst(destination) <= radius;
    }

    /**
     * Moves the vector toward the destination
     * @param deltaTime delta time since last update
     */
    public void update(float deltaTime) {

        if (atDestination(speed * deltaTime)) {
            return;
        }

        Vector2 direction = new Vector2(destination).sub(position);
        direction.setLength(speed * deltaTime);

        position.add(direction);
    }

    /**
     * Geets the current vector's position
     * @return
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Changes the destination
     * @param destination new destination
     */
    public void setDestination(Vector2 destination) {
        this.destination = destination;
    }

    /**
     * Gets the angle in degrees of the direction
     * @return angle in degrees
     */
    public float getDirectionAngle() {
        return new Vector2(destination).sub(position).angle();
    }
}

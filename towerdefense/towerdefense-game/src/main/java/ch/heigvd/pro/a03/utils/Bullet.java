package ch.heigvd.pro.a03.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    private  float speed; // per seconds;
    private Vector2 end;
    private Vector2 position;
    private float radius;

    public Bullet(Vector2 start, Vector2 end) {
        this.position = start;
        this.end = end;
        this.speed = 256;
        this.radius = 4;
    }

    public boolean hasArrived() {
        return position.dst(end) <= radius / 2;
    }

    public void update(float deltaTime) {
        Vector2 direction = new Vector2(end);
        direction.sub(position);
        direction.setLength(speed * deltaTime);

        position.add(direction);
        System.out.println(position);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        if (!hasArrived()) {
            shapeRenderer.circle(position.x, position.y, radius);
        }
    }
}

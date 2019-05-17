package ch.heigvd.pro.a03.simulation;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.event.simulation.DeathEvent;
import ch.heigvd.pro.a03.event.simulation.MoveEvent;
import ch.heigvd.pro.a03.event.simulation.SpawnEvent;
import ch.heigvd.pro.a03.states.towerdefense.SimulationState;
import ch.heigvd.pro.a03.utils.TextureManager;
import ch.heigvd.pro.a03.utils.TiledMapManager;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.units.Unit;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Simulator {

    private static final int UNIT_SIZE = 32;
    private static final Random random = new Random();
    private Map map;
    private int playerId;
    private HashMap<Integer, WarEntity> entities;
    private HashMap<Integer, Point> offsets;
    private HashMap<Integer, Animator> animators;
    private ArrayList<Bullet> bullets;

    public Simulator(Map map, int playerId) {

        this.map = map;
        this.playerId = playerId;
        entities = new HashMap<>();
        offsets = new HashMap<>();
        animators = new HashMap<>();
        bullets = new ArrayList<>();

        for (Structure[] structures : map.getStructures()) {
            for (Structure structure : structures) {
                if (structure != null) {
                    entities.put(structure.getId(), structure);
                }
            }
        }
    }

    public void spawn(SpawnEvent event) {

        if (entities.containsKey(event.ENTITY_ID)) {
            return;
        }

        Unit unit = event.UNIT_TYPE.createUnit(event.SPAWN_POINT);
        unit.setId(event.ENTITY_ID);

        entities.put(event.ENTITY_ID, unit);
        offsets.put(event.ENTITY_ID, new Point(randomOffset(), randomOffset()));

        Vector2 position = pointVec(offsetedPosition(unit));
        animators.put(event.ENTITY_ID, new Animator(position, new Vector2(position),
                TiledMapManager.TILE_SIZE / (unit.getSpeed() * SimulationState.TIME_PER_TICK)
        ));
    }

    public void move(MoveEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        if (entity instanceof Unit) {

            entity.setPosition(event.DESTINATION_POINT);
            animators.get(entity.getId()).setDestination(pointVec(offsetedPosition(entity)));
        }
    }

    public void attack(AttackEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        WarEntity target = entities.get(event.TARGET_ID);

        if (entity == null || target == null) {
            return;
        }

        target.dealDamage(event.DAMAGE);

        if (target instanceof Structure) {
            bullets.add(new Bullet(animators.get(entity.getId()).getPosition().cpy(), pointVec(offsetedPosition(target))));
        } else {
            bullets.add(new AutonomousBullet(pointVec(offsetedPosition(entity)), animators.get(target.getId())));
        }
    }

    public void death(DeathEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        entity.kill();
    }

    public void update(float deltaTime) {

        for (Animator animator : animators.values()) {
            animator.update(deltaTime);
        }

        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).update(deltaTime);
        }
    }

    public void drawSprites(SpriteBatch spriteBatch) {
        for (WarEntity entity : entities.values()) {
            if (entity instanceof Unit && !entity.isEntityDestroyed()) {
                Unit unit = (Unit) entity;

                Animator animator = animators.get(unit.getId());
                spriteBatch.draw(
                        new TextureRegion(TextureManager.getUnitTexture(unit.TYPE)),
                        animator.position.x - UNIT_SIZE / 2f, animator.position.y - UNIT_SIZE / 2f,
                        UNIT_SIZE / 2f, UNIT_SIZE / 2f,
                        UNIT_SIZE, UNIT_SIZE,
                        1, 1, animator.getDirectionAngle() - 90
                );
            }
        }
    }

    public void drawShapes(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : bullets) {
            bullet.draw(shapeRenderer);
        }
    }

    private Point offsetedPosition(WarEntity entity) {

        Point position = entity.getPosition();

        int offsetX = TowerDefense.MAP_WIDTH * map.ID + map.ID;

        int displayX = position.x + offsetX;
        int displayY = map.ID == playerId ? position.y :
                TowerDefense.MAP_HEIGHT - position.y - 1;

        if (entity instanceof Unit) {

            return new Point(
                    displayX * TiledMapManager.TILE_SIZE + offsets.get(entity.getId()).x + UNIT_SIZE / 2,
                    displayY * TiledMapManager.TILE_SIZE + offsets.get(entity.getId()).y + UNIT_SIZE / 2
            );
        }

        return new Point(
                displayX * TiledMapManager.TILE_SIZE + UNIT_SIZE,
                displayY * TiledMapManager.TILE_SIZE + UNIT_SIZE
        );
    }

    private int randomOffset() {
        return random.nextInt(32);
    }

    private Vector2 pointVec(Point point) {
        return new Vector2(point.x, point.y);
    }
}

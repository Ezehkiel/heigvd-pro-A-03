package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.event.simulation.DeathEvent;
import ch.heigvd.pro.a03.event.simulation.MoveEvent;
import ch.heigvd.pro.a03.event.simulation.SpawnEvent;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.units.Unit;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Simulator {

    private static final Random random = new Random();
    private Map map;
    private int playerId;
    private HashMap<Integer, WarEntity> entities;
    private HashMap<Integer, Point> offsets;
    private ArrayList<Bullet> bullets;

    public Simulator(Map map, int playerId) {

        this.map = map;
        this.playerId = playerId;
        entities = new HashMap<>();
        offsets = new HashMap<>();
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
    }

    public void move(MoveEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        if (entity instanceof Unit) {

            Unit unit = (Unit) entity;
            unit.setPosition(event.DESTINATION_POINT);
        }
    }

    public void attack(AttackEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        WarEntity target = entities.get(event.TARGET_ID);

        if (entity == null || target == null) {
            return;
        }

        target.dealDamage(event.DAMAGE);

        Point start = offsetedPosition(entity);
        Point end = offsetedPosition(target);
        bullets.add(new Bullet(new Vector2(start.x, start.y), new Vector2(end.x, end.y)));
    }

    public void death(DeathEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        entity.kill();
    }

    public void update(float deltaTime) {

        LinkedList<Integer> toDelete = new LinkedList<>();

        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).update(deltaTime);
            if (bullets.get(i).hasArrived()) {
                toDelete.add(i);
            }
        }
    }

    public void drawSprites(SpriteBatch spriteBatch) {


        for (WarEntity entity : entities.values()) {
            if (entity instanceof Unit && !entity.isEntityDestroyed()) {
                Unit unit = (Unit) entity;

                Point position = offsetedPosition(entity);
                spriteBatch.draw(
                        TextureManager.getUnitTexture(unit.TYPE),
                        position.x, position.y,
                        32, 32
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
                    displayX * TiledMapManager.TILE_SIZE + offsets.get(entity.getId()).x,
                    displayY * TiledMapManager.TILE_SIZE + offsets.get(entity.getId()).y
            );

        }

        return new Point(
                displayX * TiledMapManager.TILE_SIZE + 32,
                displayY * TiledMapManager.TILE_SIZE + 32
        );
    }

    private int randomOffset() {
        return random.nextInt(32);
    }
}

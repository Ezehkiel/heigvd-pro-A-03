package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.event.simulation.DeathEvent;
import ch.heigvd.pro.a03.event.simulation.MoveEvent;
import ch.heigvd.pro.a03.event.simulation.SpawnEvent;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.util.HashMap;

public class Simulator {

    private Map map;
    private HashMap<Integer, WarEntity> entities;

    public Simulator(Map map) {

        this.map = map;
        entities = new HashMap<>();

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
    }

    public void death(DeathEvent event) {

        WarEntity entity = entities.get(event.ENTITY_ID);
        entity.dealDamage(entity.getTotalHealth());
    }
}

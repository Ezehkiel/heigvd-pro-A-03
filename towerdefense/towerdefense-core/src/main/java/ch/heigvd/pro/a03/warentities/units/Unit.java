package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.EventManager;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.algorithm.Astar;
import ch.heigvd.pro.a03.algorithm.Position;
import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.event.simulation.DeathEvent;
import ch.heigvd.pro.a03.event.simulation.MoveEvent;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;
import java.util.Iterator;
import java.util.List;


public abstract class Unit extends WarEntity {

    private Astar pathFinding;
    private List<Point> path;
    private Iterator<Point> it;
    private boolean hasPath;
    private int displacementTicks;
    private int attackTicks;

    public final WarEntityType.UnitType TYPE;

    public Unit(String name, Point position, int totalHealth,
                int defPoint, int attackCoolDown, int speed,
                int attackPoints, int range, int price, WarEntityType.UnitType type) {
        super(name, position, totalHealth, defPoint, attackCoolDown);
        super.setAttackPoints(attackPoints);
        super.setRange(range);
        super.setSpeed(speed);
        super.setPrice(price);
        hasPath = false;
        displacementTicks = 0;
        attackTicks = 0;

        TYPE = type;
    }


    @Override
    public void update(int tickId, Map map) {

        pathUnit(map);


        if (!super.isEntityDestroyed()) {

            displacementTicks++;
            attackTicks++;

            if (displacementTicks == this.getSpeed()) {

                if (displacement(map.getBase().getPosition())) {
                    EventManager.getInstance().addEvent(new MoveEvent(tickId, getId(), getPosition(), map.ID));
                }

                displacementTicks = 0;
            }

            if (attackTicks == this.getAttackCoolDown()) {

                //If the enemy base is in range, the Unit will focus only on the base

                if (isInRange(map.getBase()) && !map.getBase().isEntityDestroyed()) {

                    EventManager.getInstance().addEvent(new AttackEvent(tickId,getId(),map.getBase().getId(),attack(map.getBase()),map.ID));

                    if(map.getBase().isEntityDestroyed()){
                        EventManager.getInstance().addEvent(new DeathEvent(tickId,map.getBase().getId(),map.ID));
                    }

                } else { //attacks the closest turret

                    Structure closeTarget = null;
                    int chosenOneDistance = Integer.MAX_VALUE;

                    for (int i = 0; i < map.getRow(); ++i) {
                        for (int j = 0; j < map.getCol(); ++j) {

                            if (((map.getStructures()[i][j] != null) && !map.getStructures()[i][j].isEntityDestroyed())
                                    && isInRange(map.getStructures()[i][j])
                                    && distance(this, map.getStructures()[i][j]) < chosenOneDistance) {

                                closeTarget = map.getStructures()[i][j];
                                chosenOneDistance = distance(this, closeTarget);
                            }
                        }

                    }

                    //attack the chosen one if their is any
                    if (closeTarget != null) {

                        EventManager.getInstance().addEvent(new AttackEvent(tickId,getId(),closeTarget.getId(),attack(closeTarget),map.ID));

                        if(closeTarget.isEntityDestroyed()){
                            EventManager.getInstance().addEvent(new DeathEvent(tickId,closeTarget.getId(),map.ID));
                        }
                    }
                }
                attackTicks = 0;
            }
        }


    }

    /**
     * @param map the map of the current game
     * @brief this method will find a path to the target if there is non already defined.
     */
    public void pathUnit(Map map) {

        if (!hasPath) {

            hasPath = true;

            pathFinding = new Astar(map.getRow(), map.getCol(),
                    new Position(this.getPosition().y, this.getPosition().x),
                    new Position(map.getBase().getPosition().y,
                            map.getBase().getPosition().x));

            Structure[][] blockage = map.getStructures();

            /*sets the blockage*/
            for (int i = 0; i < blockage.length; ++i) {
                for (int j = 0; j < blockage[i].length; ++j) {
                    if (blockage[i][j] != null) {
                        if (blockage[i][j] != map.getBase()) {
                            pathFinding.setBlockPos(blockage[i][j].getPosition().y,
                                    blockage[i][j].getPosition().x);
                        }
                    }
                }
            }

            path = pathFinding.findPath();
            it = path.iterator();
        }

    }

    /**
     * @param basePosition the base position
     * @breif moves the unit to the next position and checks that is not the base
     */
    public boolean displacement(Point basePosition) {
        if (it.hasNext()) {
            Point end = it.next();
            if (!end.equals(basePosition)) {
                super.setPosition(end);
                return true;
            }
        }
        return false;
    }


    public void setEndSimulation() {
        hasPath = false;
    }

    public List<Point> getPath() {
        return path;
    }
}

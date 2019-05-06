package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.algorithm.Astar;
import ch.heigvd.pro.a03.algorithm.Position;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntity;

import java.awt.*;
import java.util.Iterator;
import java.util.List;


public class Unit extends WarEntity {

    private Astar pathFinding;
    private List<Point> path;
    private Iterator<Point> it;
    private boolean hasPath;
    private int displacementTicks;
    private int attackTicks;



    public Unit(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int speed, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown);
        super.setAttackPoints(attackPoints);
        super.setRange(range);
        super.setSpeed(speed);
        super.setPrice(price);
        hasPath=false;
        displacementTicks=0;
        attackTicks=0;

    }


    @Override
    public void update(Map map) {

        pathUnit(map);


        if (!super.isEntityDestroyed()) {

            if (displacementTicks == this.getSpeed()) {

                displacement(map.getBasePosition().getPosition());
                displacementTicks = 0;
            }

            if (attackTicks == this.getAttackCoolDown()) {

                attack(map.getBasePosition());
                attackTicks = 0;
            }
        }

        displacementTicks++;
        attackTicks++;

    }

    /**
     * @brief this method will find a path to the target if there is non already defined.
     * @param map the map of the current game
     */
    public void pathUnit(Map map){

        if(!hasPath) {

            hasPath=true;

            pathFinding = new Astar(map.getRow(), map.getCol(),
                    new Position(this.getPosition().y, this.getPosition().x),
                    new Position(map.getBasePosition().getPosition().y,
                            map.getBasePosition().getPosition().x));

            Structure[][] blockage= map.getStructures();

            /*sets the blockage*/
            for(int i =0; i<blockage.length;++i){
                for(int j=0; j<blockage[i].length;++j){
                    if(blockage[i][j]!=null){
                        pathFinding.setBlockPos(blockage[i][j].getPosition().y,
                                blockage[i][j].getPosition().x);
                    }
                }
            }

            path=pathFinding.findPath();
            it=path.iterator();
        }

    }

    /**
     * @breif moves the unit to the next position and checks that is not the base
     * @param basePosition the base position
     */
    public void displacement(Point basePosition){

        if(it.hasNext()){
            Point end=it.next();
            if(end != basePosition) {
                super.setPosition(end);
            }
        }


    }

    /**
     * @brief deals damage to the target
     * @param baseEnemy the target
     */
    public void attack(Base baseEnemy){
        super.attack(baseEnemy);

    }

    public void setEndSimulation() {
       hasPath=false;
    }
}

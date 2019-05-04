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
    private boolean endSimulation;


    public Unit(String name,Point position,int totalHealth, int defPoint, int speed, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint);
        super.setAttackPoints(attackPoints);
        super.setRange(range);
        super.setSpeed(speed);
        super.setPrice(price);
        endSimulation=false;


    }


    @Override
    public void update(Map map) {

        pathFinding= new Astar(map.getRow(),map.getCol(),
                new Position(this.getPosition().y,this.getPosition().x),
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

        while (!endSimulation){

            displacement(map.getBasePosition().getPosition());
            if(!super.isEntityDestroyed()) {
                attack(map.getBasePosition());
            }else{
                this.endSimulation=true;
            }
        }


    }

    public void displacement(Point basePosition){

        if(it.hasNext()){
            Point end=it.next();
            if(end != basePosition) {
                super.setPosition(end);
            }
        }


    }

    public void attack(Base baseEnemy){
        super.attack(baseEnemy);

    }

    public void setEndSimulation(boolean endSimulation) {
        this.endSimulation = endSimulation;
    }
}

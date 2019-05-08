package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public class Structure extends WarEntity {

    public Structure(String name,Point position, int totalHealth, int defPoint,int attackCoolDown) {
        super(name,position,totalHealth,defPoint, attackCoolDown);
    }

    @Override
    public void update(Map map) {

        //we'll attack the nearest Unit which is in range if their is any (called choseOne here)

        //find the chosen one if any (the chosenOne is the nearest unit in range of this Turret)
        Unit chosenOne = null;
        int chosenOneDistance = Integer.MAX_VALUE;
        for(Unit u: map.getUnits()){
            if(isInRange(u) && distance(this, u) < chosenOneDistance){
                chosenOne = u;
                chosenOneDistance = distance(this, u);
            }
        }

        //attack the chosen one if their is any
        if(chosenOne != null) attack(chosenOne);


        //The following code is probably so faulty that I would suggest to rewrite it from scratch
        //It would be easier to modify NearestTarget to use it

        /*
        //Compute potential targets
        List<Vec2> targets = new LinkedList<>();
        Structure[][] structures = map.getStructures();
        for (int i = 0; i < structures.length; ++i)
            for (int j = 0; j < structures[0].length; ++j) {
                WarEntity e = structures[i][j];
                if (e instanceof Unit) targets.add(new Vec2(i, j));
            }

        //compute target
        NearestTarget nt = new NearestTarget(map.getRow(), map.getCol(), targets);
        Vec2 target = nt.getNearestTarget(new Vec2(getPosition().x, getPosition().y), (int) getRange());

        //attack target if any
        map.get
        if(target != null) { attack(structures[target.getRow()][target.getCol()]); }

        */
    }

}

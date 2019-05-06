package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.Map;

import java.awt.*;

public class Structure extends WarEntity {

    public Structure(String name,Point position, int totalHealth, int defPoint,int attackCoolDown) {
        super(name,position,totalHealth,defPoint, attackCoolDown);
    }

    @Override
    public void update(Map map) {
    }

}

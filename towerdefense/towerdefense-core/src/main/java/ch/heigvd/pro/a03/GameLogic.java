package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.util.LinkedList;

public class GameLogic {

    Player p1,p2;
    LinkedList<Map> maps;
    private boolean endMatch;
    private boolean endRound;

    public GameLogic(Player player1, Player player2, Map map1, Map map2) {
        p1=player1;
        p2=player2;
        maps.add(map1);
        maps.add(map2);
        endMatch = false;
        endRound = false;

    }

    public GameLogic(){
        this(new Player("player1"),new Player("player2"),
                new Map(9,12,new Base(new Point(0,4))),
                new Map(9,12,new Base(new Point(0,4))));
    }

    public void playGame() {

        while (!endMatch) {
            playRound();
        }

    }

    public void playRound() {

        while (!endRound) {
            for (Map m : maps) {
                m.update();
                endRound = m.isEndSimulation();
                endMatch |= m.isEndMatch();
            }
        }

        p1.addMoney(1000);
        p2.addMoney(1000);

        endRound = false;
        LinkedList<Unit> units;

        units = maps.get(0).getUnits();
        units.addAll(maps.get(1).getUnits());

        for (Unit u : units) {
            u.setEndSimulation();
        }


    }


    public void setEndMatch(boolean match) {
        this.endMatch = match;
    }


}

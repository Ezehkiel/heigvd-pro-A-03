package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.util.LinkedList;

public class GameLogic {

    private int entityCount;

    LinkedList<Player> players;
    LinkedList<Map> maps;
    private boolean endMatch;
    private boolean endRound;

    public GameLogic(Player player1, Player player2, Map map1, Map map2) {
        players=new LinkedList<>();
        maps=new LinkedList<>();
        players.add(player1);
        players.add(player2);
        maps.add(map1);
        maps.add(map2);
        endMatch = false;
        endRound = false;
        entityCount = 0;

    }

    public GameLogic() {
        this(new Player("player0"), new Player("player1"),
                new Map(9, 12, new Base(new Point(0, 4))),
                new Map(9, 12, new Base(new Point(0, 4))));
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

        for (Player p : players) {
            p.addMoney(1000);
        }

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

    public Map getPlayerMap(int index) {

        return maps.get(index);
    }

    public Player getPlayer(int index) {

        return players.get(index);
    }

    public int getNextEntityId() {
        return entityCount++;
    }
}

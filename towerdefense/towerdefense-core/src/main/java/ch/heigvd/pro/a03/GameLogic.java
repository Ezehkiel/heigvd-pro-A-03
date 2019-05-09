package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.util.LinkedList;

public class GameLogic {

    private int entityCount;

    Player[] players;
    Map[] maps;
    private boolean endMatch;
    private boolean endRound;

    public GameLogic(Player[] players, Map[] maps) {
        this.players = players;
        this.maps = maps;
        endMatch = false;
        endRound = false;
        entityCount = 0;

    }

    public GameLogic(Player[] players) {
        this(players, new Map[]{
                        new Map(9, 12, new Base(new Point(0, 4))),
                        new Map(9, 12, new Base(new Point(0, 4)))
                }
        );
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

        units = maps[0].getUnits();
        units.addAll(maps[1].getUnits());

        for (Unit u : units) {
            u.setEndSimulation();
        }
    }


    public void setEndMatch(boolean match) {
        this.endMatch = match;
    }

    public Map getPlayerMap(int index) {

        return maps[index];
    }

    public Map[] getMaps() {
        return maps;
    }

    public Player getPlayer(int index) {

        return players[index];
    }

    public int getNextEntityId() {
        return entityCount++;
    }
}

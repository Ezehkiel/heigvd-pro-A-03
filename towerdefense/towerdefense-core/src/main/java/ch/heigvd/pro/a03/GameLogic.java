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
    private int nbTick;


    public GameLogic(Player[] players, Map[] maps) {
        this.players = players;
        this.maps = maps;

        endMatch = false;
        endRound = false;
        entityCount = 0;
        nbTick = 0;
    }

    public GameLogic(Player[] players) {
        this(players, new Map[players.length]);

        for (int i = 0; i < maps.length; ++i) {
            maps[i] = new Map(9, 12, new Base(new Point(0, 4)), i);
        }
    }



    public void playRound() {


        while (!endRound && !endMatch) {

            nbTick++;
            EventManager.getInstance().clearEvents();

            endRound = isEndRound(maps);

            for (Map m : maps) {
                m.update(nbTick);
                endMatch = m.isEndMatch();
            }
        }

        for (Player p : players) {
            p.addMoney(1000);
        }

        endRound = false;

        for(Map m: maps){
            m.getUnits().clear();
        }
    }

    private boolean isEndRound(Map[] maps) {

        for (Map map : maps) {
            if (!map.unitsAreDead()) {
                return false;
            }
        }

        return true;
    }

    public int getNbTick() {
        return nbTick;
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

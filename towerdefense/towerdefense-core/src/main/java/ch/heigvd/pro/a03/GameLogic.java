package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.event.simulation.SpawnEvent;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.units.Unit;
import org.json.JSONArray;
import org.json.JSONObject;

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
            maps[i] = new Map(12, 9, new Base(new Point(4, 0)),new Point(4,11) , i);
            maps[i].getBase().setId(getNextEntityId());
        }
    }

    public void playRound() {

        EventManager.getInstance().clearEvents();
        nbTick = 0;

        for(Map m :  maps){
            for(Unit u : m.getUnits()){
                EventManager.getInstance().addEvent(new SpawnEvent(nbTick,u.getId(),u.TYPE,m.getSpawnPoint(),m.ID));
            }
        }

        while (!endRound && !endMatch) {

            nbTick++;

            endRound = isEndRound(maps);

            for (Map m : maps) {
                m.update(nbTick);
                endMatch = m.isEndMatch();
            }
        }

        giveMoneyToPlayers();

        endRound = false;

        for(Map m: maps){
            m.getUnits().clear();
        }
    }

    public void giveMoneyToPlayers() {
        for (Player p : players) {
            p.addMoney(1000);
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

    public Player getLoser() {
        Player loser = null;

        //Search if there is a base destroyed
        for(Player p: players){
           if(this.getPlayerMap(p.ID).getBase().isEntityDestroyed()){
               loser = p;
               break;
           }
        }
        return loser;
    }
    public int getNextEntityId() {
        return entityCount++;
    }

    public String getMapsJson() {

        StringBuilder json = new StringBuilder("[");

        if (maps.length > 0) {
            json.append(maps[0].toJson());
            for (int i = 1; i < maps.length; ++i) {
                json.append(",").append(maps[i].toJson());
            }
        }

        return json.append("]").toString();
    }
}

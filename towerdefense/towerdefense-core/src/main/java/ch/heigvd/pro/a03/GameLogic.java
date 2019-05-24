package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.simulation.SpawnEvent;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.units.Unit;
import java.awt.*;

/***
 * Class containing the game logic. (How a round is determined, etc)
 *
 * @author Andres Moreno, Nicodeme Stalder
 */

public class GameLogic {

    private int entityCount;
    private Player[] players;
    private Map[] maps;
    private boolean endMatch;
    private boolean endRound;
    private int nbTick;


    /**
     * Constructor
     * @param players the players of the match
     * @param maps the map for each player
     */
    public GameLogic(Player[] players, Map[] maps) {
        this.players = players;
        this.maps = maps;
        endMatch = false;
        endRound = false;
        entityCount = 0;
        nbTick = 0;
    }

    /***
     * Constructor
     * @param players the players of the match
     */
    public GameLogic(Player[] players) {
        this(players, new Map[players.length]);

        for (int i = 0; i < maps.length; ++i) {
            maps[i] = new Map(12, 9, new Base(new Point(4, 0)),new Point(4,11) , i);
            maps[i].getBase().setId(getNextEntityId());
        }
    }

    /***
     *
     * @param index
     * @return the map of the player related to the index number
     */
    public Map getPlayerMap(int index) {
        return maps[index];
    }


    /***
     *
     * @param index
     * @return the player related to the given index
     */
    public Player getPlayer(int index) {

        return players[index];
    }

    /***
     *
     * @return the player who lost the match
     */
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

    /***
     * Executes a round of the match.
     */
    public void playRound() {

        EventManager.getInstance().clearEvents();
        nbTick = 0;


        while (!endRound && !endMatch) {

            nbTick++;

            endRound = isEndRound(maps);

            for (Map m : maps) {
                m.update(nbTick);
                endMatch = m.isEndMatch();

                if (endMatch) {
                    break;
                }
            }
        }

        giveMoneyToPlayers();

        endRound = false;

        for(Map m: maps){
            m.clearUnits();
        }
    }

    /***
     * Add's currency to each player of the game
     */
    public void giveMoneyToPlayers() {
        for (Player p : players) {
            p.addMoney(1000);
        }
    }

    /***
     *
     * @param maps
     * @return true if all units form both players are dead.
     */
    private boolean isEndRound(Map[] maps) {

        for (Map map : maps) {
            if (!map.unitsAreDead()) {
                return false;
            }
        }

        return true;
    }


}

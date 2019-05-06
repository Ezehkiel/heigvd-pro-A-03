package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.users.User;
import java.util.LinkedList;

public class GameLogic {

    LinkedList<User> players;
    LinkedList<Map> maps;
    private boolean endMatch;//final de una simulacion si todas las unidades estan muertas o si la base destruida
    private boolean endRound;

    public GameLogic(User player1, User player2, Map map1, Map map2) {
        players.add(player1);
        players.add(player2);
        maps.add(map1);
        maps.add(map2);
        endMatch = false;
        endRound = false;

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
                endMatch=m.isEndMatch();
            }
        }

        endRound=false;
    }


    public void setEndMatch(boolean match) {
        this.endMatch = match;
    }
}

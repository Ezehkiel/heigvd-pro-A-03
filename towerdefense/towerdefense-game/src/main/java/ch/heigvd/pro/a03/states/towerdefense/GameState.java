package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.State;
import ch.heigvd.pro.a03.states.StateMachine;

/**
 * Represente a tower defense game state
 */
public abstract class GameState extends State {

    private TowerDefense game;

    /**
     * Creates a new game state
     * @param stateMachine state machine
     * @param game tower defanse game
     */
    public GameState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine);

        this.game = game;
    }

    /**
     * Gets the game
     * @return tower defense game
     */
    public TowerDefense getGame() {
        return game;
    }
}

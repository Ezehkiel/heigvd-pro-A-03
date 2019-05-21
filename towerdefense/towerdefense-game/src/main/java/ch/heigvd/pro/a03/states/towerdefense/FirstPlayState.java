package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

/**
 * State of the first round
 */
public class FirstPlayState extends PlayState {

    /**
     * Creates the state
     * @param stateMachine state machine
     * @param game tower defense game
     */
    public FirstPlayState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("First round!");

        getGame().getScene().getGameMenu().getPlayingMenu().hideTurretSelectionMenu();
    }
}

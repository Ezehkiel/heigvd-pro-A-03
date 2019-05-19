package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

/**
 * State when waiting for server informations
 */
public class WaitState extends GameState {

    /**
     * Creates the state
     * @param stateMachine state machine
     * @param game tower defense game
     */
    public WaitState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        getGame().getScene().getGameMenu().showInfo("Waiting for other players!");
        System.out.println("Now waiting!");
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == FirstPlayState.class ||
                stateClass == PlayState.class ||
                stateClass == OpponentPlayState.class ||
                stateClass == SimulationState.class;
    }
}

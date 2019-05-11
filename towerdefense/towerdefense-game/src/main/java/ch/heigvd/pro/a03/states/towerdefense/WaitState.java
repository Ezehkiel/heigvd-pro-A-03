package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class WaitState extends GameState {

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

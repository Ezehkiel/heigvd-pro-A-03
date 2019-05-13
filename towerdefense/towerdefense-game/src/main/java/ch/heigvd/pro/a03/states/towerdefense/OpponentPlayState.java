package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class OpponentPlayState extends GameState {

    public OpponentPlayState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        getGame().getScene().getGameMenu().showInfo("Opponent's turn.");
        System.out.println("Opponents turn");
    }

    @Override
    public void leave() {
        super.leave();

        System.out.println("Turn ended");
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitState.class;
    }
}

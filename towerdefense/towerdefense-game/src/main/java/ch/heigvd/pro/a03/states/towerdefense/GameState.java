package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.State;
import ch.heigvd.pro.a03.states.StateMachine;

public abstract class GameState extends State {

    private TowerDefense game;

    public GameState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine);

        this.game = game;
    }

    public TowerDefense getGame() {
        return game;
    }

    public void changeState(TowerDefense.GameStateType stateType) {
        getStateMachine().changeState(game.getState(stateType));
    }
}

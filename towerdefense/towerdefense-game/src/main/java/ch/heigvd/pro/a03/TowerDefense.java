package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.states.StateMachine;
import ch.heigvd.pro.a03.states.towerdefense.*;

public class TowerDefense {

    private GameScene scene;

    // States variables
    public enum GameStateType {
        PLAY, SIMULATION, WAIT
    }

    private StateMachine stateMachine;
    private GameState[] states;

    public TowerDefense(GameScene scene) {

        this.scene = scene;
        stateMachine = new StateMachine();

        states = new GameState[] {
                new PlayState(stateMachine, this),
                new SimulationState(stateMachine, this),
                new WaitingState(stateMachine, this)
        };

        stateMachine.changeState(getState(GameStateType.WAIT));
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public GameState getState(GameStateType stateType) {
        return states[stateType.ordinal()];
    }
}

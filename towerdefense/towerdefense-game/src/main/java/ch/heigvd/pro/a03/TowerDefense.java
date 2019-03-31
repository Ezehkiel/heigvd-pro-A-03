package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.states.StateMachine;
import ch.heigvd.pro.a03.states.towerdefense.*;
import ch.heigvd.pro.a03.warentities.turrets.Turret;

public class TowerDefense {

    private GameScene scene;

    private Map map;

    // States variables
    private StateMachine stateMachine;
    private GameState[] states;

    public enum GameStateType {
        PLAY, OPPONENT_PLAY, SIMULATION, WAIT
    }

    public TowerDefense(GameScene scene) {

        this.scene = scene;

        map = new Map(20, 12);

        stateMachine = new StateMachine();

        states = new GameState[] {
                new PlayState(stateMachine, this),
                new OpponentPlayState(stateMachine, this),
                new SimulationState(stateMachine, this),
                new WaitState(stateMachine, this)
        };

        stateMachine.changeState(getState(GameStateType.WAIT));
    }

    public boolean placeTurret(Turret turret) {

        if (!isInState(GameStateType.PLAY) || map.getStructureAt(turret.getPosition().x, turret.getPosition().y) != null) {
            return false;
        }

        map.setStructureAt(turret, turret.getPosition().x, turret.getPosition().y);
        scene.updateMap(map);

        return true;
    }

    private boolean isInState(GameStateType stateType) {
        return stateMachine.getState() == getState(stateType);
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public GameState getState(GameStateType stateType) {
        return states[stateType.ordinal()];
    }
}

package ch.heigvd.pro.a03.states.gamescene;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.states.State;
import ch.heigvd.pro.a03.states.StateMachine;

public abstract class GameSceneState extends State {

    private GameScene scene;

    public GameSceneState(StateMachine stateMachine, GameScene scene) {
        super(stateMachine);

        this.scene = scene;
    }

    public GameScene getScene() {
        return scene;
    }
}

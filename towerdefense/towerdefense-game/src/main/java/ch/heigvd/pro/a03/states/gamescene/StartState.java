package ch.heigvd.pro.a03.states.gamescene;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.states.StateMachine;

public class StartState extends GameSceneState {

    public StartState(StateMachine stateMachine, GameScene scene) {
        super(stateMachine, scene);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("Initializing game!");
        getStateMachine().changeState(new PlayState(getStateMachine(), getScene()));
    }

    @Override
    public void leave() {
        super.leave();

        System.out.println("GameLauncher initialized!");
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == PlayState.class;
    }
}

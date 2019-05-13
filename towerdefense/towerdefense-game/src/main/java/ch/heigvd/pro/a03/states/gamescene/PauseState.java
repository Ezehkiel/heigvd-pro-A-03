package ch.heigvd.pro.a03.states.gamescene;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.states.StateMachine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PauseState extends GameSceneState {

    public PauseState(StateMachine stateMachine, GameScene scene) {
        super(stateMachine, scene);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("GAME PAUSED");
    }

    @Override
    public void leave() {
        super.leave();

        System.out.println("GAME RESUMING");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            getStateMachine().changeState(new PlayState(getStateMachine(), getScene()));
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == PlayState.class;
    }
}

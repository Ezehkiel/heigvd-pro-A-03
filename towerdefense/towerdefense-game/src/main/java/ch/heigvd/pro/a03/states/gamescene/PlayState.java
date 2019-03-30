package ch.heigvd.pro.a03.states.gamescene;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.states.StateMachine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayState extends GameSceneState {

    private float timer = 0f;

    public PlayState(StateMachine stateMachine, GameScene scene) {
        super(stateMachine, scene);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("GAME STARTED PLAYING");
    }

    @Override
    public void leave() {
        super.leave();

        System.out.println("GAME STOPPED PLAYING");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        getScene().updateCamera();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            getStateMachine().changeState(new PauseState(getStateMachine(), getScene()));
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == PauseState.class;
    }
}

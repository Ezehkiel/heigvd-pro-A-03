package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;
import com.badlogic.gdx.Gdx;

public class PlayState extends GameState {

    public PlayState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("Turn started");
    }

    @Override
    public void leave() {
        super.leave();

        System.out.println("Turn ended");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (Gdx.input.justTouched()) {
            changeState(TowerDefense.GameStateType.WAIT);
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitingState.class;
    }
}

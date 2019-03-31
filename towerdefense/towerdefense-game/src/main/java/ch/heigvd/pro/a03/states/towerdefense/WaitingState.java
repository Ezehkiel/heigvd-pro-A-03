package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class WaitingState extends GameState {

    int i = 0;

    public WaitingState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        i++;
        System.out.println("Now waiting!");
    }

    @Override
    public void leave() {
        super.leave();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (i == 3) {
            i = 0;
            changeState(TowerDefense.GameStateType.SIMULATION);
        } else {
            changeState(TowerDefense.GameStateType.PLAY);
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == PlayState.class ||
                stateClass == SimulationState.class;
    }
}

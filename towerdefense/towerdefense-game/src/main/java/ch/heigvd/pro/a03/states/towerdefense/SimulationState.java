package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class SimulationState extends GameState {

    private float timer = 0f;
    private int counter = 0;

    public SimulationState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("Let's simulate!");
    }

    @Override
    public void leave() {
        super.leave();

        timer = 0f;
        counter = 0;

        System.out.println("\nDone simaling!");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        timer += deltaTime;

        if (timer >= 1f) {
            counter++;
            timer = 0f;

            System.out.print(".");
        }

        if (counter == 10) {
            changeState(TowerDefense.GameStateType.WAIT);
        }

    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitingState.class;
    }
}

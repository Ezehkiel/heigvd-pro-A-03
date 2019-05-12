package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class SimulationState extends GameState {

    public SimulationState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    private boolean ended = false;
    private final float TIME_PER_TICK = 1f; // Ticks per seconds
    private float timer = 0f;
    private int currentTick = 0;

    @Override
    public void enter() {
        super.enter();

        ended = false;
        timer = 0f;

        System.out.println("Simulation starts.");
    }

    @Override
    public void leave() {
        super.leave();

        System.out.println("Simulation ends.");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (ended) { return; }

        timer += deltaTime;
        while (timer >= TIME_PER_TICK) {

            if (getGame().simEvents == null || getGame().simEvents.isEmpty()) {
                ended = true;
                getGame().endSimulation();
                return;
            }

            System.out.println(getGame().simEvents.pop().toString());

            timer -= TIME_PER_TICK;
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitState.class;
    }
}

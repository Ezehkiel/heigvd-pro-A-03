package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class SimulationState extends GameState {

    public SimulationState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    private boolean ended = false;
    private final float TIME_PER_TICK = 1f/50; // Ticks per seconds
    private float timer = 0f;
    private int currentTick = 0;

    @Override
    public void enter() {
        super.enter();

        ended = false;
        timer = 0f;
        currentTick = 0;

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

            while (!getGame().simEvents.isEmpty()) {

                if (getGame().simEvents.peek().TICK_ID != currentTick) {
                    break;
                }

                getGame().processSimEvent(getGame().simEvents.pop());
            }

            if (getGame().simEvents.isEmpty()) {
                ended = true;
                getGame().endSimulation();
                return;
            }

            currentTick++;
            timer -= TIME_PER_TICK;
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitState.class;
    }
}

package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class SimulationState extends GameState {

    public SimulationState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    private boolean ended = false;
    public static final float TIME_PER_TICK = 0.05f; // 20 per seconds
    private float timer = 0f;
    private int currentTick = 0;

    @Override
    public void enter() {
        super.enter();

        ended = false;
        timer = 0f;
        currentTick = 0;

        System.out.println("Simulation starts.");
        System.out.println(getGame().simEvents.size() + " events.");
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

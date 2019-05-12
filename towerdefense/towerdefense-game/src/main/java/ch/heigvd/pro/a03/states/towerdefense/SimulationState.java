package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.event.simulation.*;
import ch.heigvd.pro.a03.states.StateMachine;

public class SimulationState extends GameState {

    public SimulationState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    private boolean ended = false;

    @Override
    public void enter() {
        super.enter();

        ended = false;
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

        if (!ended && getGame().simEvents != null) {

            if (getGame().simEvents.isEmpty()) {
                ended = true;
                getGame().endSimulation();
                return;
            }

            System.out.println(getGame().simEvents.pop().toString());
        }
    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitState.class;
    }
}

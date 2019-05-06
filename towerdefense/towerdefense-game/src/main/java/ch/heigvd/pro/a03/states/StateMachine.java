package ch.heigvd.pro.a03.states;

public class StateMachine {

    private State state = null;

    public StateMachine() { }

    public void update(float deltaTime) {
        if (state != null) {
            state.update(deltaTime);
        }
    }

    public boolean changeState(State newState) {

        if (state != null) {

            if (!state.canEnterState(newState.getClass())) {
                return false;
            }

            state.leave();
        }

        state = newState;
        state.enter();

        return true;
    }

    public State getState() {
        return state;
    }
}

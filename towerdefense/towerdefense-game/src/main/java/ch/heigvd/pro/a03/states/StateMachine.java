package ch.heigvd.pro.a03.states;

/**
 * Manages states
 */
public class StateMachine {

    private State state = null;

    public StateMachine() { }

    /**
     * Updates the current state
     * @param deltaTime delta time since last update
     */
    public void update(float deltaTime) {
        if (state != null) {
            state.update(deltaTime);
        }
    }

    /**
     * Change the current state to the given one if possible
     * @param newState next state
     * @return true is the change occurred
     */
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

    /**
     * Gets the current state
     * @return state
     */
    public State getState() {
        return state;
    }
}

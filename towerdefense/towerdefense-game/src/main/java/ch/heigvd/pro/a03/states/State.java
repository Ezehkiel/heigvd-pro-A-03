package ch.heigvd.pro.a03.states;

/**
 * Represents a state.
 */
public abstract class State {

    private StateMachine stateMachine;

    /**
     * Create a new state with its state machine.
     * @param stateMachine state machine
     */
    public State(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    /**
     * Called when entering the state
     */
    public void enter() { }

    /**
     * Called when leaving the state
     */
    public void leave() { }

    /**
     * Update the state.
     * @param deltaTime delta time since last
     */
    public void update(float deltaTime) { }

    /**
     * Check if the given state class can be entered.
     * @param stateClass next state class
     * @return true if possible to enter
     */
    public boolean canEnterState(Class<?> stateClass) {
        return false;
    }

    /**
     * Gets the statemachine
     * @return state machiness
     */
    public StateMachine getStateMachine() {
        return stateMachine;
    }
}

package ch.heigvd.pro.a03.states;

public abstract class State {

    private StateMachine stateMachine;

    public State(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    // TODO: ADD from and to in changeState() and leave() if needed

    public void enter() { }

    public void leave() { }

    public void update(float deltaTime) { }

    public boolean canEnterState(Class<?> stateClass) {
        return false;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }
}

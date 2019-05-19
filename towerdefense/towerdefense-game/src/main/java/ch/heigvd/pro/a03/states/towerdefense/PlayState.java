package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;
import com.badlogic.gdx.Gdx;

/**
 * State when the player is player
 */
public class PlayState extends GameState {

    /**
     * Creates the state
     * @param stateMachine state machine
     * @param game tower defense game
     */
    public PlayState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        getGame().getScene().getGameMenu().showInfo("Your turn!");
        getGame().clearPlayerEvents();
        getGame().getScene().getGameMenu().updateMoney(getGame().getGameClient().getPlayer().getMoney());
        getGame().getScene().getGameMenu().resetUnitSelectionMenu();
        getGame().getScene().getGameMenu().getPlayingMenu().showTurretSelectionMenu();
        getGame().getScene().getGameMenu().getPlayingMenu().showUnitsSelectionButton();
        getGame().getScene().getGameMenu().getPlayingMenu().showEndTurnButton();
        System.out.println("My turn");
    }

    @Override
    public void leave() {
        super.leave();
        System.out.println("Turn ended");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


    }

    @Override
    public boolean canEnterState(Class<?> stateClass) {
        return stateClass == WaitState.class;
    }
}

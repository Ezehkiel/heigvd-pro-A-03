package ch.heigvd.pro.a03.states.towerdefense;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.states.StateMachine;

public class FirstPlayState extends PlayState {

    public FirstPlayState(StateMachine stateMachine, TowerDefense game) {
        super(stateMachine, game);
    }

    @Override
    public void enter() {
        super.enter();

        System.out.println("First turn!");

        getGame().getScene().getGameMenu().getPlayingMenu().hideTurretSelectionMenu();
    }
}

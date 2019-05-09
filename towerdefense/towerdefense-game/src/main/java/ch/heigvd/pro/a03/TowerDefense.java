package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.states.StateMachine;
import ch.heigvd.pro.a03.states.towerdefense.*;
import ch.heigvd.pro.a03.warentities.Base;

import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.Game;

import java.awt.*;
import java.util.ArrayList;

public class TowerDefense {

    private GameScene scene;
    private GameClient gameClient;

    public static final int MAP_WIDTH = 9;
    public static final int MAP_HEIGHT = 12;

    private Map[] maps;

    // States variables
    private StateMachine stateMachine;
    private GameState[] states;

    public enum GameStateType {
        PLAY, OPPONENT_PLAY, SIMULATION, WAIT
    }

    public TowerDefense(GameScene scene, GameClient gameClient) {

        this.scene = scene;
        this.gameClient = gameClient;
        maps = new Map[gameClient.PLAYERS_COUNT];
        for (int i = 0; i < maps.length; ++i) {
            maps[i] = new Map(MAP_HEIGHT, MAP_WIDTH, new Base(new Point(4,11)), i);
        }

        stateMachine = new StateMachine();

        states = new GameState[] {
                new PlayState(stateMachine, this),
                new OpponentPlayState(stateMachine, this),
                new SimulationState(stateMachine, this),
                new WaitState(stateMachine, this)
        };

        stateMachine.changeState(getState(GameStateType.WAIT));
    }



    /* ----- Turret Management -----*/

    public boolean isCellOccupied(int mapId, int x, int y) {
        return maps[mapId].getStructureAt(y, x) != null;
    }

    public Turret getTurretAt(int mapId, int x, int y) {

        Structure structure = maps[mapId].getStructureAt(y, x);
        if (structure instanceof Turret) {
            return (Turret) structure;
        }

        return null;
    }

    public boolean placeTurret(int mapId, Turret turret) {

        if (!isInState(GameStateType.PLAY) || maps[mapId].getStructureAt(turret.getPosition().y, turret.getPosition().x) != null) {
            return false;
        }

        try {
            maps[mapId].setStructureAt(turret, turret.getPosition().y, turret.getPosition().x);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        scene.updateMaps();

        return true;
    }

    public boolean destroyTurret(int mapId, Turret turret) {

        if (!isInState(GameStateType.PLAY) || maps[mapId].getStructureAt(turret.getPosition().y, turret.getPosition().x) == null) {
            return false;
        }

        try {
            maps[mapId].setStructureAt(null, turret.getPosition().y, turret.getPosition().x);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        scene.updateMaps();

        return true;
    }

    public Map[] getMaps() {
        return maps;
    }

    private boolean isInState(GameStateType stateType) {
        return stateMachine.getState() == getState(stateType);
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public GameState getState(GameStateType stateType) {
        return states[stateType.ordinal()];
    }

    public GameScene getScene() {
        return scene;
    }
}

package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.TurretEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.states.StateMachine;
import ch.heigvd.pro.a03.states.towerdefense.*;
import ch.heigvd.pro.a03.utils.Waiter;
import ch.heigvd.pro.a03.warentities.Base;

import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.Turret;

import java.awt.*;

public class TowerDefense {

    private GameScene scene;
    private GameClient gameClient;

    public static final int MAP_WIDTH = 9;
    public static final int MAP_HEIGHT = 12;

    private Map[] maps;

    // States variables
    private StateMachine stateMachine;
    private GameState[] states;

    private Executable roundEnd;
    private Executable playerTurnStart;
    private Executable playerTurnEnd;
    private Executable showMap;

    private PlayerEvent playerEvent;
    private Waiter<PlayerEvent> playerEventWaiter;

    public enum GameStateType {
        FIRST_PLAY, PLAY, OPPONENT_PLAY, SIMULATION, WAIT
    }

    public TowerDefense(GameScene scene, GameClient gameClient) {

        this.scene = scene;
        this.gameClient = gameClient;
        maps = new Map[gameClient.PLAYERS_COUNT];
        for (int i = 0; i < maps.length; ++i) {
            maps[i] = new Map(MAP_HEIGHT, MAP_WIDTH, new Base(new Point(4,11)),new Point(11,4), i);
        }

        stateMachine = new StateMachine();

        states = new GameState[] {
                new FirstPlayState(stateMachine, this),
                new PlayState(stateMachine, this),
                new OpponentPlayState(stateMachine, this),
                new SimulationState(stateMachine, this),
                new WaitState(stateMachine, this)
        };

        changeState(GameStateType.WAIT);

        playerEvent = new PlayerEvent();
        playerEventWaiter = new Waiter<>();

        // Setup commands
        playerTurnStart = args -> changeState(
                gameClient.getPlayer().ID == (Integer) args[0] ?
                        GameStateType.PLAY : GameStateType.OPPONENT_PLAY
        );

        playerTurnEnd = args -> changeState(GameStateType.WAIT);

        roundEnd = args -> changeState(GameStateType.SIMULATION);
        showMap = args -> {
            Map map = (Map) args[0];
            maps[map.ID] = map;
            scene.updateMaps();
        };

        gameClient.firstRound(args -> changeState(
            gameClient.getPlayer().ID == (Integer) args[0] ?
                    GameStateType.FIRST_PLAY : GameStateType.OPPONENT_PLAY
        ), playerTurnEnd, args -> {
            changeState(GameStateType.WAIT);
            gameClient.round(playerTurnStart, playerTurnEnd, roundEnd, showMap, playerEventWaiter);

        }, showMap, playerEventWaiter);
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

        if (!isInState(GameStateType.PLAY) ||
                maps[mapId].getStructureAt(turret.getPosition().y, turret.getPosition().x) != null) {
            return false;
        }

        try {
            maps[mapId].setStructureAt(turret, turret.getPosition().y, turret.getPosition().x);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        // TODO add turret event

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

    public boolean changeState(GameStateType stateType) {
        return stateMachine.changeState(getState(stateType));
    }

    public void addTurretEvent(TurretEvent event) {
        playerEvent.addTurretEvent(event);
    }

    public void sendEvents() {
        playerEventWaiter.send(playerEvent);
    }

    public void clearPlayerEvents() {
        playerEvent.clear();
    }

    public boolean sendUnits(WarEntityType.UnitType[] types, int[] quantities) {

        if (types.length != quantities.length) {
            return false;
        }

        Point dummyPoint = new Point();
        int totalPrice = 0;

        for (int i = 0; i < types.length; ++i) {
            totalPrice += types[i].createUnit(dummyPoint).getPrice() * quantities[i];
        }

        Player player = gameClient.getPlayer();

        if (totalPrice > player.getMoney()) {
            return false;
        }

        player.removeMoney(totalPrice);
        scene.getGameMenu().updateMoney(player.getMoney());

        for (int i = 0; i < types.length; ++i) {
            for (int id : gameClient.getOpponentsIds()) {
                if (quantities[i] > 0) {
                    playerEvent.addUnitEvent(new SendUnitEvent(id, types[i], quantities[i]));
                }
            }
        }

        return true;
    }

    public GameClient getGameClient() {
        return gameClient;
    }
}

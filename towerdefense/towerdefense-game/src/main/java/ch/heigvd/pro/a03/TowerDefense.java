package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.algorithm.Astar;
import ch.heigvd.pro.a03.algorithm.Position;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.commands.game.ShowMapsCommand;
import ch.heigvd.pro.a03.event.player.*;
import ch.heigvd.pro.a03.event.simulation.*;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.states.StateMachine;
import ch.heigvd.pro.a03.states.towerdefense.*;
import ch.heigvd.pro.a03.simulation.Simulator;
import ch.heigvd.pro.a03.utils.Waiter;
import ch.heigvd.pro.a03.warentities.Base;

import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.LinkedList;

/**
 * Manages the game and everything needed to display and interact with it.
 */
public class TowerDefense {

    private GameScene scene;
    private GameClient gameClient;

    public static final int MAP_WIDTH = 9;
    public static final int MAP_HEIGHT = 12;

    private Map[] maps;

    // States variables
    private StateMachine stateMachine;
    private GameState[] states;

    private Executable roundStart;
    private Executable roundEnd;
    private Executable playerTurnStart;
    private Executable playerTurnEnd;
    private Executable showMaps;

    private PlayerEvent playerEvent;
    private Waiter<PlayerEvent> playerEventWaiter;
    public LinkedList<SimEvent> simEvents = null;

    private Simulator[] simulators;

    /**
     * Types of possible game states
     */
    public enum GameStateType {
        FIRST_PLAY, PLAY, OPPONENT_PLAY, SIMULATION, WAIT
    }

    /**
     * Creates a new tower defense game manager.
     * @param scene game scene
     * @param gameClient game client
     */
    public TowerDefense(GameScene scene, GameClient gameClient) {

        this.scene = scene;
        this.gameClient = gameClient;
        simulators = new Simulator[gameClient.PLAYERS_COUNT];
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

        roundStart = args -> gameClient.round(playerTurnStart, playerTurnEnd, roundEnd, showMaps, playerEventWaiter);
        roundEnd = a -> gameClient.startSimulation(args -> {
            simEvents = (LinkedList<SimEvent>) args[0];
            setupSimulators();
            changeState(GameStateType.SIMULATION);
        });

        showMaps = new ShowMapsCommand(this);

        gameClient.setGame(this);
        gameClient.firstRound(args -> changeState(
            gameClient.getPlayer().ID == (Integer) args[0] ?
                    GameStateType.FIRST_PLAY : GameStateType.OPPONENT_PLAY
        ), playerTurnEnd, roundStart, showMaps, playerEventWaiter);
    }

    /**
     * Manages the end of the simulation
     */
    public void endSimulation() {
        changeState(GameStateType.WAIT);
        gameClient.endSimulation(roundStart,
                args -> getScene().getGameMenu().showEndMenu(gameClient.getPlayer().ID, (Player) args[0]));
    }

    /**
     * Setup the simulators
     */
    public void setupSimulators() {
        simulators = new Simulator[gameClient.PLAYERS_COUNT];
        for (Map map : maps) {
            simulators[map.ID] = new Simulator(this, map, gameClient.getPlayer().ID);
        }
    }

    /**
     * Updates the simulators of the simulation.
     * @param deltaTime delta time since last updates
     */
    public void updateSimulation(float deltaTime) {
        if (stateMachine.getState() instanceof SimulationState) {
            for (Simulator simulator : simulators) {
                simulator.update(deltaTime);
            }
        }
    }

    /**
     * Draws the simulators
     * @param spriteBatch sprite batch
     * @param shapeRenderer shape renderer
     */
    public void drawSimulation(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

        if (stateMachine.getState() instanceof SimulationState) {
            spriteBatch.begin();
            for (Simulator simulator : simulators) {
                simulator.drawSprites(spriteBatch);
            }
            spriteBatch.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.ORANGE);
            for (Simulator simulator : simulators) {
                simulator.drawShapes(shapeRenderer);
            }
            shapeRenderer.end();
        }
    }

    /**
     * Processes the sim event and send send to the corresponding simulator
     * @param event simulation event
     */
    public void processSimEvent(SimEvent event) {

        GameClient.LOG.info(event.toString());

        switch (event.TYPE) {
            case SPAWN:
                simulators[event.MAP_ID].spawn((SpawnEvent) event);
                break;
            case MOVE:
                simulators[event.MAP_ID].move((MoveEvent) event);
                break;
            case ATTACK:
                simulators[event.MAP_ID].attack((AttackEvent) event);
                break;
            case DEATH:
                simulators[event.MAP_ID].death((DeathEvent) event);
                break;
        }
    }

    /**
     * Check if a cell is occupied with a strucure
     * @param mapId map id
     * @param x x-axis coordinate
     * @param y y-axis coordinate
     * @return true if cell is occupied
     */
    public boolean isCellOccupied(int mapId, int x, int y) {
        return maps[mapId].getStructureAt(y, x) != null;
    }

    /**
     * Gets a turret at a given position in the selected map
     * @param mapId map id
     * @param x x-axis coordinate
     * @param y y-axis coordinate
     * @return the turret or null
     */
    public Turret getTurretAt(int mapId, int x, int y) {

        Structure structure = maps[mapId].getStructureAt(y, x);
        if (structure instanceof Turret) {
            return (Turret) structure;
        }

        return null;
    }

    /**
     * Places a turret iat the given position in the selected map
     * @param mapId map id
     * @param turret turret to playe
     * @return true is the turret has been placed
     */
    public boolean placeTurret(int mapId, Turret turret) {

        if (!iAmMapOwner(mapId) || !isInState(GameStateType.PLAY) ||
                maps[mapId].getStructureAt(turret.getPosition().y, turret.getPosition().x) != null) {
            return false;
        }

        if (turret.getPosition().x == maps[mapId].getSpawnPoint().x &&
                turret.getPosition().y == maps[mapId].getSpawnPoint().y) {
            return false;
        }

        if (gameClient.getPlayer().getMoney() < turret.getPrice()) {
            return false;
        }

        try {
            maps[mapId].setStructureAt(turret, turret.getPosition().y, turret.getPosition().x);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        // Check for path
        Map map = maps[mapId];
        Astar pathFinding = new Astar(map.getRow(), map.getCol(),
                new Position(map.getSpawnPoint().y, map.getSpawnPoint().x),
                new Position(map.getBase().getPosition().y, map.getBase().getPosition().x));

        Structure[][] blockage = map.getStructures();

        // Sets the blockage
        for (int i = 0; i < blockage.length; ++i) {
            for (int j = 0; j < blockage[i].length; ++j) {
                if (blockage[i][j] != null) {
                    if (blockage[i][j] != map.getBase()) {
                        pathFinding.setBlockPos(blockage[i][j].getPosition().y,
                                blockage[i][j].getPosition().x);
                    }
                }
            }
        }

        if (pathFinding.findPath().isEmpty()) {
            try {
                maps[mapId].setStructureAt(null, turret.getPosition().y, turret.getPosition().x);
            } catch (IndexOutOfBoundsException e) {
                return false;
            }

            return false;
        }

        playerEvent.addTurretEvent(new TurretEvent(
                TurretEventType.ADD, turret.getPosition(), turret.TYPE
        ));

        gameClient.getPlayer().removeMoney(turret.getPrice());
        scene.getGameMenu().updateMoney(gameClient.getPlayer().getMoney());

        scene.updateMaps();

        return true;
    }

    /**
     * Destroys a turret
     * @param mapId map id
     * @param turret turret
     * @return true if destroyed
     */
    public boolean destroyTurret(int mapId, Turret turret) {

        if (!iAmMapOwner(mapId) || !isInState(GameStateType.PLAY) ||
                maps[mapId].getStructureAt(turret.getPosition().y, turret.getPosition().x) == null) {
            return false;
        }

        try {
            maps[mapId].setStructureAt(null, turret.getPosition().y, turret.getPosition().x);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        playerEvent.addTurretEvent(new TurretEvent(
                TurretEventType.DESTROY, turret.getPosition(), turret.TYPE
        ));

        gameClient.getPlayer().removeMoney(turret.getPrice() / 2);
        scene.getGameMenu().updateMoney(gameClient.getPlayer().getMoney());

        scene.updateMaps();

        return true;
    }

    /**
     * Check is the selected map is the player's map
     * @param mapId map id
     * @return true if player's map
     */
    public boolean iAmMapOwner(int mapId) {
        return mapId == gameClient.getPlayer().ID;
    }

    /**
     * Get all the maps
     * @return array of maps
     */
    public Map[] getMaps() {
        return maps;
    }

    /**
     * Check if the game is in a state of the given type
     * @param stateType state type
     * @return true if in state type
     */
    private boolean isInState(GameStateType stateType) {
        return stateMachine.getState() == getState(stateType);
    }

    /**
     * Gets the state machine
     * @return state machine
     */
    public StateMachine getStateMachine() {
        return stateMachine;
    }

    /**
     * Get the state of the given type
     * @param stateType state type
     * @return state of type
     */
    public GameState getState(GameStateType stateType) {
        return states[stateType.ordinal()];
    }

    /**
     * Gets the game scene
     * @return game scene
     */
    public GameScene getScene() {
        return scene;
    }

    /**
     * Changes the current state to the state of the given type
     * @param stateType type of the next state
     * @return true is the change occurred
     */
    public boolean changeState(GameStateType stateType) {
        return stateMachine.changeState(getState(stateType));
    }

    /**
     * Send the player events to the game client
     */
    public void sendEvents() {
        playerEventWaiter.send(playerEvent);
    }

    /**
     * Clear the player eents
     */
    public void clearPlayerEvents() {
        playerEvent = new PlayerEvent();
    }

    /**
     * Send the unit to the player event
     * @param types types of the units
     * @param quantities quantities
     * @return true if the units were sent
     */
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

    /**
     * Gets the game client
     * @return game client
     */
    public GameClient getGameClient() {
        return gameClient;
    }

    /**
     * Sets the map of the given id
     * @param i map id
     * @param map map
     */
    public void setMap(int i, Map map) {
        maps[i] = map;
    }

    public void quit() {
        scene.getGameMenu().showEndMenu(0, null);
    }
}

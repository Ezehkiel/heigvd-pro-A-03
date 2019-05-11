package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.menus.game.GameMenu;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.utils.TiledMapManager;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MortarTurret;
import ch.heigvd.pro.a03.warentities.turrets.LaserGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;
import org.lwjgl.Sys;

import java.awt.*;

public class GameScene extends Scene {

    private static final float CAMERA_SPEED = 5f;

    private OrthographicCamera gameCamera;
    private Viewport gameViewport;

    private TiledMapManager tiledMapManager;

    private GameClient gameClient;

    private TowerDefense game;
    private WarEntityType.TurretType selectedTurretType;

    private GameMenu gameMenu;

    public GameScene(GameClient gameClient) {

        this.gameClient = gameClient;
        gameCamera = new OrthographicCamera();
        gameViewport = new ScreenViewport(gameCamera);
        gameViewport.update(GameLauncher.WIDTH, GameLauncher.HEIGHT, true);
        gameMenu = new GameMenu(getSkin(), this);

        // Create GameLauncher Menu
        setViewport(new ScreenViewport());
        setStage(new Stage(getViewport()));

        getStage().addActor(gameMenu.getMenu());

        game = new TowerDefense(this, gameClient);

        tiledMapManager = new TiledMapManager(
                TowerDefense.MAP_WIDTH, TowerDefense.MAP_HEIGHT, gameClient.PLAYERS_COUNT
        );
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //game.getStateMachine().update(deltaTime);
        updateCamera();

        tiledMapManager.getRenderer().setView(gameCamera);
    }

    @Override
    public void draw() {
        gameViewport.apply();
        tiledMapManager.getRenderer().render();

        // Sow menu
        super.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        gameViewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        super.dispose();

        tiledMapManager.dispose();
    }

    public void updateCamera() {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameCamera.position.y += CAMERA_SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameCamera.position.y -= CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameCamera.position.x -= CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameCamera.position.x += CAMERA_SPEED;
        }

        // Zoom gameCamera
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            gameCamera.zoom += CAMERA_SPEED / 100;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            gameCamera.zoom -= CAMERA_SPEED / 100;
        }

        gameCamera.update();
    }

    public void clickMap(float mouseX, float mouseY) {

        Vector3 mousePosition = new Vector3(mouseX, mouseY, 0);
        mousePosition = gameCamera.unproject(getViewport().getCamera().unproject(mousePosition));

        int tmpX = (int) Math.floor(mousePosition.x / TiledMapManager.TILE_SIZE);

        int mapId = tmpX / (TowerDefense.MAP_WIDTH + 1);
        int x = tmpX % (TowerDefense.MAP_WIDTH + 1);
        int y = (int) Math.floor(mousePosition.y / TiledMapManager.TILE_SIZE);

        System.out.println(mapId + ", " + x + ", " + y);

        Turret turret = null;

        if (game.isCellOccupied(mapId, x, y)) {

            turret = game.getTurretAt(mapId, x, y);
            if (turret != null) {
                gameMenu.showTurretMenu(game, mapId, turret);
            }

        } else if (selectedTurretType != null) {

            switch (selectedTurretType) {

                case MACHINE_GUN:
                    turret = new MachineGunTurret(new Point(x,y));

                    break;

                case MORTAR:
                    turret = new MortarTurret(new Point(x, y));
                    break;

                case LASER_GUN:
                    turret = new LaserGunTurret(new Point(x, y));
                    break;
            }

            game.placeTurret(mapId, turret);
        }
    }

    public void updateMaps() {
        tiledMapManager.update(game.getMaps());
    }

    public void selectTurret(WarEntityType.TurretType turretType) {
        selectedTurretType = turretType;
    }

    public void clearSelectedTurret() {
        selectedTurretType = null;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public TowerDefense getGame() {
        return game;
    }
}

package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.menus.game.GameMenu;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MortarTurret;
import ch.heigvd.pro.a03.warentities.turrets.LaserGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;

import java.awt.*;

public class GameScene extends Scene {

    private static final float CAMERA_SPEED = 5f;

    private static final int TILE_MAP_WIDTH = 20;
    private static final int TILE_MAP_HEIGHT = 12;
    private static final int TILE_SIZE = 64;

    private static final int TURRET_LAYER = 1;

    private OrthographicCamera gameCamera;
    private Viewport gameViewport;

    private Texture notFoundTexture;
    private Texture machineGunTexture;
    private Texture mortarTexture;
    private Texture slowerTexture;
    private Texture tileTexture;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private TowerDefense game;
    private TurretType selectedTurretType;

    private GameMenu gameMenu;

    public enum TurretType {
        MACHINE_GUN, MORTAR, SLOWER
    }

    public GameScene() {

        gameCamera = new OrthographicCamera();
        gameViewport = new ScreenViewport(gameCamera);
        gameViewport.update(GameLauncher.WIDTH, GameLauncher.HEIGHT, true);
        gameMenu = new GameMenu(getSkin(), this);

        // Create GameLauncher Menu
        setViewport(new ScreenViewport());
        setStage(new Stage(getViewport()));

        getStage().addActor(gameMenu.getMenu());

        notFoundTexture = new Texture(Gdx.files.internal("assets/NotFound.png"));
        machineGunTexture = new Texture(Gdx.files.internal("assets/MachineGun.png"));
        mortarTexture = new Texture(Gdx.files.internal("assets/Mortar.png"));
        slowerTexture = new Texture(Gdx.files.internal("assets/Slower.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));

        tiledMap = new TiledMap();
        TiledMapTileLayer backgroundLayer = new TiledMapTileLayer(TILE_MAP_WIDTH, TILE_MAP_HEIGHT, TILE_SIZE, TILE_SIZE);
        TiledMapTileLayer turretLayer = new TiledMapTileLayer(TILE_MAP_WIDTH, TILE_MAP_HEIGHT, TILE_SIZE, TILE_SIZE);

        for (int i = 0; i < TILE_MAP_WIDTH; ++i) {
            for (int j = 0; j < TILE_MAP_HEIGHT; ++j) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new TextureRegion(tileTexture)));
                backgroundLayer.setCell(i, j, cell);

                turretLayer.setCell(i, j, new TiledMapTileLayer.Cell());
            }
        }
        tiledMap.getLayers().add(backgroundLayer); // Background Layer
        tiledMap.getLayers().add(turretLayer); // Turret Layer

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        game = new TowerDefense(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        game.getStateMachine().update(deltaTime);
        updateCamera();

        tiledMapRenderer.setView(gameCamera);
    }

    @Override
    public void draw() {
        gameViewport.apply();
        tiledMapRenderer.render();

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

        tiledMap.dispose();
        machineGunTexture.dispose();
        tileTexture.dispose();
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

        int x = (int) Math.floor(mousePosition.x / TILE_SIZE);
        int y = (int) Math.floor(mousePosition.y / TILE_SIZE);

        Turret turret = null;

        if (game.isCellOccupied(x, y)) {

            turret = game.getTurretAt(x, y);
            if (turret != null) {
                gameMenu.showTurretMenu(game, turret);
            }

        } else if (selectedTurretType != null) {

            switch (selectedTurretType) {

                case MACHINE_GUN:
                    turret = new MachineGunTurret(new Point(x,y));

                    break;

                case MORTAR:
                    turret = new MortarTurret(new Point(x, y));
                    break;

                case SLOWER:
                    turret = new LaserGunTurret(new Point(x, y));
                    break;
            }

            game.placeTurret(turret);
        }
    }

    public void updateMap(Map map) {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(TURRET_LAYER);

        for (int x = 0; x < TILE_MAP_WIDTH; ++x) {
            for (int y = 0; y < TILE_MAP_HEIGHT; ++y) {

                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                Structure structure = map.getStructureAt(x, y);
                if (structure != null) {

                    Texture texture = notFoundTexture;

                    if (structure instanceof MachineGunTurret) {
                        texture = machineGunTexture;

                    } else if (structure instanceof MortarTurret) {
                        texture = mortarTexture;

                    } else if (structure instanceof LaserGunTurret) {
                        texture = slowerTexture;

                    }

                    cell.setTile(new StaticTiledMapTile(new TextureRegion(texture)));
                } else {

                    cell.setTile(null);
                }
            }
        }
    }

    public void selectTurret(TurretType turretType) {
        selectedTurretType = turretType;
    }

    public void clearSelectedTurret() {
        selectedTurretType = null;
    }
}

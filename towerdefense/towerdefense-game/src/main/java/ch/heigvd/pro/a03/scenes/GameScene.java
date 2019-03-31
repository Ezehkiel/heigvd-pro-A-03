package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.menus.game.GameMenu;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MortarTurret;
import ch.heigvd.pro.a03.warentities.turrets.SlowerTurret;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.*;

import java.awt.*;

public class GameScene extends Scene {

    private static final float CAMERA_SPEED = 5f;

    private static final int TILE_MAP_WIDTH = 20;
    private static final int TILE_MAP_HEIGHT = 12;
    private static final int TILE_SIZE = 64;

    private static final int BACKGROUND_LAYER = 0;
    private static final int TURRET_LAYER = 1;

    private OrthographicCamera camera;
    private Viewport gameViewport;

    private Skin menuSkin;
    private Stage menuStage;
    private Viewport menuViewport;

    private Texture turretTexture;
    private Texture tileTexture;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private TowerDefense game;
    private TurretType selectedTurretType;

    public enum TurretType {
        MACHINE_GUN, MORTAR, SLOWER;
    }

    public GameScene() {

        camera = new OrthographicCamera();
        gameViewport = new FillViewport(GameLauncher.WIDTH, GameLauncher.HEIGHT, camera);
        gameViewport.update(GameLauncher.WIDTH, GameLauncher.HEIGHT, true);

        // Create GameLauncher Menu
        menuViewport = new ScreenViewport();

        menuSkin = new Skin(Gdx.files.internal("uiskin.json"));
        menuStage = new Stage(menuViewport);

        menuStage.addActor(new GameMenu(menuSkin, this).getMenu());

        turretTexture = new Texture(Gdx.files.internal("assets/Turret.png"));
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
    public void enter() {
        Gdx.input.setInputProcessor(menuStage);
        System.out.println("Entering Game Scene");
    }

    @Override
    public void leave() {
        Gdx.input.setInputProcessor(null);
        System.out.println("Leaving Game Scene");
    }

    @Override
    public void update(float deltaTime) {

        game.getStateMachine().update(deltaTime);

        tiledMapRenderer.setView(camera);
        menuStage.act(deltaTime);
    }

    @Override
    public void draw() {
        gameViewport.apply();
        tiledMapRenderer.render();

        menuViewport.apply();
        menuStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        menuViewport.update(width, height, true);
    }

    @Override
    public void dispose() {

        tiledMap.dispose();
        turretTexture.dispose();
        tileTexture.dispose();
    }

    public void updateCamera() {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += CAMERA_SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += CAMERA_SPEED;
        }

        // Zoom camera
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += CAMERA_SPEED / 100;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= CAMERA_SPEED / 100;
        }

        camera.update();
    }

    public void clickMap(float mouseX, float mouseY) {

        Vector3 mousePosition = camera.unproject(new Vector3(mouseX, mouseY, 0));
        int x = (int) Math.floor(mousePosition.x / TILE_SIZE);
        int y = (int) Math.floor(mousePosition.y / TILE_SIZE);

        if (selectedTurretType != null) {

            Turret turret = null;
            switch (selectedTurretType) {

                case MACHINE_GUN:
                    turret = new MachineGunTurret(new Point(x, y));
                    break;

                case MORTAR:
                    turret = new MortarTurret(new Point(x, y));
                    break;

                case SLOWER:
                    turret = new SlowerTurret(new Point(x, y));
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
                if (map.getStructureAt(x, y) != null) {
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(turretTexture)));
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

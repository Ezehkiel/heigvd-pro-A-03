package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.Game;
import ch.heigvd.pro.a03.menus.GameMenu;
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

public class GameScene extends Scene {

    private final float CAMERA_SPEED = 5f;
    private final int TURRET_LAYER = 1;
    private final int TILE_MAP_WIDTH = 20;
    private final int TILE_MAP_HEIGHT = 12;
    private final int TILE_SIZE = 64;

    private OrthographicCamera camera;
    private Viewport gameViewport;

    private Skin menuSkin;
    private Stage menuStage;
    private Viewport menuViewport;

    private Texture turretTexture;
    private Texture tileTexture;

    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    public GameScene() {

        camera = new OrthographicCamera();
        gameViewport = new FillViewport(Game.WIDTH, Game.HEIGHT, camera);
        gameViewport.update(Game.WIDTH, Game.HEIGHT, true);

        // Create Game Menu
        menuViewport = new ScreenViewport();

        menuSkin = new Skin(Gdx.files.internal("uiskin.json"));
        menuStage = new Stage(menuViewport);

        menuStage.addActor(new GameMenu(menuSkin, this).getMenu());

        turretTexture = new Texture(Gdx.files.internal("assets/Turret.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));

        map = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(TILE_MAP_WIDTH, TILE_MAP_HEIGHT, TILE_SIZE, TILE_SIZE);
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 12; ++j) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new TextureRegion(tileTexture)));
                layer.setCell(i, j, cell);
            }
        }
        map.getLayers().add(layer); // Background Layer
        map.getLayers().add(new TiledMapTileLayer(20, 12, 64, 64)); // Turret Layer

        mapRenderer = new OrthogonalTiledMapRenderer(map);
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

        // Return to main menu if "ESCAPE" is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.getInstance().getSceneManager().pop();
        }

        // Move camera
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
        mapRenderer.setView(camera);

        menuStage.act(deltaTime);
    }

    @Override
    public void draw() {
        gameViewport.apply();
        mapRenderer.render();

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

        map.dispose();
        turretTexture.dispose();
        tileTexture.dispose();
    }

    public void click(float mouseX, float mouseY) {

        Vector3 mousePosition = camera.unproject(new Vector3(mouseX, mouseY, 0));
        int x = (int) Math.floor(mousePosition.x / TILE_SIZE);
        int y = (int) Math.floor(mousePosition.y / TILE_SIZE);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(TURRET_LAYER);
        TiledMapTileLayer.Cell cell = layer.getCell(x, y);
        if (cell == null) {
            cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(new TextureRegion(turretTexture)));
            cell.setRotation(TiledMapTileLayer.Cell.ROTATE_90);
            layer.setCell(x, y, cell);

        } else {
            cell.setRotation((cell.getRotation() + 1) % 4);
        }
    }
}

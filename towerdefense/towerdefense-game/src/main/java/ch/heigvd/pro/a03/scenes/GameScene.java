package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.Game;
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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScene extends Scene {

    private final float CAMERA_SPEED = 5f;
    private final int TURRET_LAYER = 1;
    private final int TILE_MAP_WIDTH = 20;
    private final int TILE_MAP_HEIGHT = 12;
    private final int TILE_SIZE = 64;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture turretTexture;
    private Texture tileTexture;

    private TiledMap map;
    private TiledMapRenderer mapRenderer;


    public GameScene() {

        camera = new OrthographicCamera();
        viewport = new FillViewport(Game.WIDTH, Game.HEIGHT, camera);
        viewport.update(Game.WIDTH, Game.HEIGHT, true);

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
        System.out.println("Entering Game Scene");
    }

    @Override
    public void leave() {
        System.out.println("Leaving Game Scene");
    }

    @Override
    public void update(float deltaTime) {

        // Return to main menu if "ESCAPE" is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.getInstance().popScene();
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


        if (Gdx.input.justTouched()) {
            Vector3 mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            int x = (int) Math.floor(mousePosition.x / TILE_SIZE);
            int y = (int) Math.floor(mousePosition.y / TILE_SIZE);
            System.out.println("(" + x + ", " + y + ")");

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

        camera.update();
        mapRenderer.setView(camera);
    }

    @Override
    public void draw() {
        mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {

        map.dispose();
        turretTexture.dispose();
        tileTexture.dispose();
    }
}

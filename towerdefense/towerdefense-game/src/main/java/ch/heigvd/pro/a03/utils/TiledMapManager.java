package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.turrets.LaserGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MortarTurret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TiledMapManager {

    public static final int TILE_SIZE = 64;
    private static final int TURRET_LAYER = 1;

    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;
    private final int MAP_COUNT;

    private final int FULL_WIDTH;
    private final int FULL_HEIGHT;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private Texture notFoundTexture;
    private Texture machineGunTexture;
    private Texture mortarTexture;
    private Texture slowerTexture;
    private Texture tileTexture;
    private final int PLAYER_ID;

    public TiledMapManager(int width, int height, int mapCount, int playerId) {

        notFoundTexture = new Texture(Gdx.files.internal("assets/NotFound.png"));
        machineGunTexture = new Texture(Gdx.files.internal("assets/MachineGun.png"));
        mortarTexture = new Texture(Gdx.files.internal("assets/Mortar.png"));
        slowerTexture = new Texture(Gdx.files.internal("assets/Slower.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));

        MAP_WIDTH = width;
        MAP_HEIGHT = height;
        MAP_COUNT = mapCount;
        PLAYER_ID = playerId;

        FULL_WIDTH = MAP_WIDTH * mapCount + mapCount - 1;
        FULL_HEIGHT = MAP_HEIGHT;

        tiledMap = new TiledMap();
        TiledMapTileLayer backgroundLayer = new TiledMapTileLayer(FULL_WIDTH, FULL_HEIGHT, TILE_SIZE, TILE_SIZE);
        TiledMapTileLayer turretLayer = new TiledMapTileLayer(FULL_WIDTH, FULL_HEIGHT, TILE_SIZE, TILE_SIZE);

        for (int i = 0; i < MAP_COUNT; ++i) {

            int offsetX = MAP_WIDTH * i + i;

            for (int x = 0; x < MAP_WIDTH; ++x) {
                for (int y = 0; y < MAP_HEIGHT; ++y) {

                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(tileTexture)));
                    backgroundLayer.setCell(x + offsetX, y, cell);

                    turretLayer.setCell(x + offsetX, y, new TiledMapTileLayer.Cell());
                }
            }
        }

        tiledMap.getLayers().add(backgroundLayer); // Background Layer
        tiledMap.getLayers().add(turretLayer); // Turret Layer

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void update(Map[] maps) {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(TURRET_LAYER);
        for (int i = 0; i < MAP_COUNT; ++i) {

            int offsetX = MAP_WIDTH * i + i;

            for (int x = 0; x < MAP_WIDTH; ++x) {
                for (int y = 0; y < MAP_HEIGHT; ++y) {

                    int displayX = x + offsetX;
                    int displayY = i == PLAYER_ID ? y : MAP_HEIGHT - y - 1;

                    TiledMapTileLayer.Cell cell = layer.getCell(displayX, displayY);
                    Structure structure = maps[i].getStructureAt(y, x);
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
    }

    public TiledMapRenderer getRenderer() {
        return tiledMapRenderer;
    }

    public void dispose() {
        tiledMap.dispose();
    }
}

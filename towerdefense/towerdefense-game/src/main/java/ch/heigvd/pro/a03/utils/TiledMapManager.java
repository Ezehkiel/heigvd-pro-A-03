package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.turrets.LaserGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MortarTurret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TiledMapManager {

    public static final int TILE_SIZE = 64;
    private static final int TURRET_LAYER = 1;
    private static final int EFFECT_LAYER = 2;

    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;
    private final int MAP_COUNT;

    private final int FULL_WIDTH;
    private final int FULL_HEIGHT;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private Texture notFoundTexture;
    private Texture baseTexture;
    private Texture spawnTexture;
    private Texture machineGunTexture;
    private Texture mortarTexture;
    private Texture laserGunTexture;
    private Texture tileTexture;
    private Texture destroyedTexture;
    private final int PLAYER_ID;

    public TiledMapManager(int width, int height, int mapCount, int playerId) {

        notFoundTexture = new Texture(Gdx.files.internal("assets/NotFound.png"));
        baseTexture = new Texture(Gdx.files.internal("assets/Base.png"));
        spawnTexture = new Texture(Gdx.files.internal("assets/Spawn.png"));
        machineGunTexture = new Texture(Gdx.files.internal("assets/turrets/MachineGun.png"));
        mortarTexture = new Texture(Gdx.files.internal("assets/turrets/Mortar.png"));
        laserGunTexture = new Texture(Gdx.files.internal("assets/turrets/LaserGun.png"));
        tileTexture = new Texture(Gdx.files.internal("assets/Tile.png"));
        destroyedTexture = new Texture(Gdx.files.internal("assets/Destroyed.png"));

        MAP_WIDTH = width;
        MAP_HEIGHT = height;
        MAP_COUNT = mapCount;
        PLAYER_ID = playerId;

        FULL_WIDTH = MAP_WIDTH * mapCount + mapCount - 1;
        FULL_HEIGHT = MAP_HEIGHT;

        tiledMap = new TiledMap();
        TiledMapTileLayer backgroundLayer = new TiledMapTileLayer(FULL_WIDTH, FULL_HEIGHT, TILE_SIZE, TILE_SIZE);
        TiledMapTileLayer turretLayer = new TiledMapTileLayer(FULL_WIDTH, FULL_HEIGHT, TILE_SIZE, TILE_SIZE);
        TiledMapTileLayer effectLayer = new TiledMapTileLayer(FULL_WIDTH, FULL_HEIGHT, TILE_SIZE, TILE_SIZE);

        for (int i = 0; i < MAP_COUNT; ++i) {

            int offsetX = MAP_WIDTH * i + i;

            for (int x = 0; x < MAP_WIDTH; ++x) {
                for (int y = 0; y < MAP_HEIGHT; ++y) {

                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(tileTexture)));
                    backgroundLayer.setCell(x + offsetX, y, cell);

                    turretLayer.setCell(x + offsetX, y, new TiledMapTileLayer.Cell());
                    effectLayer.setCell(x + offsetX, y, new TiledMapTileLayer.Cell());
                }
            }
        }

        tiledMap.getLayers().add(backgroundLayer); // Background Layer
        tiledMap.getLayers().add(turretLayer); // Turret Layer
        tiledMap.getLayers().add(effectLayer); // Effect Layer

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void update(Map[] maps) {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(TURRET_LAYER);
        TiledMapTileLayer effectLayer = (TiledMapTileLayer) tiledMap.getLayers().get(EFFECT_LAYER);
        for (int i = 0; i < MAP_COUNT; ++i) {

            int mapPosition = mapPosition(i);
            int offsetX = xOffset(mapPosition);

            for (int x = 0; x < MAP_WIDTH; ++x) {
                for (int y = 0; y < MAP_HEIGHT; ++y) {

                    int displayX = xDisplay(x, offsetX);
                    int displayY = yDisplay(y, mapPosition);

                    TiledMapTileLayer.Cell cell = layer.getCell(displayX, displayY);
                    TiledMapTileLayer.Cell effectCell = effectLayer.getCell(displayX, displayY);
                    Structure structure = maps[i].getStructureAt(y, x);
                    if (structure != null) {

                        Texture texture = notFoundTexture;

                        if (structure instanceof MachineGunTurret) {
                            texture = machineGunTexture;

                        } else if (structure instanceof MortarTurret) {
                            texture = mortarTexture;

                        } else if (structure instanceof LaserGunTurret) {
                            texture = laserGunTexture;

                        } else if (structure instanceof Base) {
                            texture = baseTexture;
                        }

                        cell.setTile(new StaticTiledMapTile(new TextureRegion(texture)));

                        if (structure.isEntityDestroyed()) {
                            effectCell.setTile(new StaticTiledMapTile(new TextureRegion(destroyedTexture)));
                        } else {
                            effectCell.setTile(null);
                        }

                    } else {

                        cell.setTile(null);
                        effectCell.setTile(null);
                    }
                }
            }

            int displayX = xDisplay(maps[i].getSpawnPoint().x, offsetX);
            int displayY = yDisplay(maps[i].getSpawnPoint().y, mapPosition);
            layer.getCell(displayX, displayY).setTile(new StaticTiledMapTile( new TextureRegion(spawnTexture)));
        }
    }

    public void addEffect(int mapId, int x, int y) {
        int mapPosition = mapPosition(mapId);
        int displayX = xDisplay(x, xOffset(mapPosition));
        int displayY = yDisplay(y, mapPosition);

        TiledMapTileLayer effectLayer = (TiledMapTileLayer) tiledMap.getLayers().get(EFFECT_LAYER);
        effectLayer.getCell(displayX, displayY).setTile(new StaticTiledMapTile( new TextureRegion(destroyedTexture)));
    }

    public TiledMapRenderer getRenderer() {
        return tiledMapRenderer;
    }

    public void dispose() {
        tiledMap.dispose();
    }

    public int mapId(int mapPosition) {
        if (mapPosition == 0) {
            return PLAYER_ID;
        }

        if (mapPosition - 1 < PLAYER_ID) {
            return mapPosition - 1;
        }

        return mapPosition;
    }

    public int mapPosition(int mapId) {

        if (mapId == PLAYER_ID) {
            return 0;
        }

        if (mapId < PLAYER_ID) {
            return mapId + 1;
        }

        return mapId;
    }

    public int xOffset(int mapPosition) {
        return MAP_WIDTH * mapPosition + mapPosition;
    }

    public int xDisplay(int x, int xOffset) {
        return x + xOffset;
    }

    public int yDisplay(int y, int mapPosition) {
        return mapPosition == 0 ? y : MAP_HEIGHT - y - 1;
    }
}

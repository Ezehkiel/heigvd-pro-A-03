package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

/**
 * The Game launcher starts the game and creates the scene manager.
 */
public class GameLauncher implements ApplicationListener {

    static private GameLauncher instance = null;

    static public final String TITLE = "Tower Defense";
    static public final int WIDTH = 1280;
    static public final int HEIGHT = 768;

    private SceneManager sceneManager;

    /**
     * Default constructor, called by getInstance() if needed
     */
    private GameLauncher() {
        super();

        sceneManager = new SceneManager();
    }

    /**
     * Get the instance of the Singleton
     * @return the instance of GameLauncher
     */
    static public GameLauncher getInstance() {

        if (instance == null) {
            instance = new GameLauncher();
        }

        return instance;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public void create () {
        sceneManager.add(new MainMenuScene());
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (sceneManager.hasScene()) {
            sceneManager.peek().update(Gdx.graphics.getDeltaTime());
            sceneManager.peek().draw();
        }
    }

    @Override
    public void dispose () {

        // remove all scenes
        while (sceneManager.hasScene()) {
            sceneManager.pop();
        }
    }

    @Override
    public void resize(int width, int height) {

        if (sceneManager.hasScene()) {
            sceneManager.peek().resize(width, height);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * Application entry point
     * @param args arguments
     */
    public static void main(String[] args) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = GameLauncher.TITLE;
        config.width = GameLauncher.WIDTH;
        config.height = GameLauncher.HEIGHT;

        new LwjglApplication(GameLauncher.getInstance(), config);
    }
}

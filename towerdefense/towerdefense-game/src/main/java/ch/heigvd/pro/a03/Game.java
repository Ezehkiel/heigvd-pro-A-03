package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

public class Game implements ApplicationListener {

    static private Game instance = null;

    static public final String TITLE = "Tower Defense";
    static public final int WIDTH = 1280;
    static public final int HEIGHT = 768;

    private SceneManager sceneManager;

    /**
     * Default constructor, called by getInstance() if needed
     */
    private Game() {
        super();
    }

    /**
     * Get the instance of the Singleton
     * @return the instance of Game
     */
    static public Game getInstance() {

        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public void create () {

        sceneManager = new SceneManager();
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

    public static void main(String[] args) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Game.TITLE;
        config.width = Game.WIDTH;
        config.height = Game.HEIGHT;

        new LwjglApplication(Game.getInstance(), config);
    }
}

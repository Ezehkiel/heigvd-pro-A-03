package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import java.util.Stack;

public class Game implements ApplicationListener {

    static private Game instance = null;

    static public final String TITLE = "Tower Defense";
    static public final int WIDTH = 1280;
    static public final int HEIGHT = 768;

    Stack<Scene> scenes;

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

    @Override
    public void create () {

        scenes = new Stack<>();
        scenes.add(new MainMenu());
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scenes.peek().update(Gdx.graphics.getDeltaTime());

        scenes.peek().draw();
    }

    @Override
    public void dispose () {

    }

    @Override
    public void resize(int i, int i1) {

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

    // Scene managment functions

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    /**
     *
     * @return
     */
    public void popScene() {

        if (!scenes.empty()) {

            scenes.peek().dispose();
            scenes.pop();
        }
    }
}

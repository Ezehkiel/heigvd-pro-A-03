package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.scenes.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

public class Game implements ApplicationListener {

    static public final String TITLE = "Tower Defense";
    static public final int WIDTH = 1280;
    static public final int HEIGHT = 768;

    Scene scene;

    @Override
    public void create () {

        scene = new MainMenu();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scene.update(Gdx.graphics.getDeltaTime());

        scene.draw();
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

        new LwjglApplication(new Game(), config);
    }
}

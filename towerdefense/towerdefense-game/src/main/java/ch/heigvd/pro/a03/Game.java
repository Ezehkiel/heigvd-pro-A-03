package ch.heigvd.pro.a03;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {

    static public final String TITLE = "Tower Defense";
    static public final int WIDTH = 800;
    static public final int HEIGHT = 480;

    SpriteBatch spriteBatch;
    Texture helloWorldTexture;

    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        helloWorldTexture = new Texture(Gdx.files.internal("./assets/HelloWorld.png"));

        System.out.println(HelloWorld.greet("Jack"));
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        spriteBatch.draw(
                helloWorldTexture,
                WIDTH / 2f - helloWorldTexture.getWidth() / 2f ,
                HEIGHT / 2f - helloWorldTexture.getHeight() / 2f
        );

        spriteBatch.end();
    }

    @Override
    public void dispose () {
        spriteBatch.dispose();
        helloWorldTexture.dispose();
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

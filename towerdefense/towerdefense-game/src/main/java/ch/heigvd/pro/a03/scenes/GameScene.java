package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScene extends Scene {

    SpriteBatch spriteBatch;
    Texture helloWorld;

    public GameScene() {

        spriteBatch = new SpriteBatch();

        helloWorld = new Texture(Gdx.files.internal("assets/HelloWorld.png"));
    }

    @Override
    public void update(float deltaTime) {

        // Return to main menu if "Q" is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Game.getInstance().popScene();
        }
    }

    @Override
    public void draw() {

        spriteBatch.begin();

        spriteBatch.draw(helloWorld, Game.WIDTH / 2f - helloWorld.getWidth() / 2f, Game.HEIGHT / 2f - helloWorld.getHeight() / 2f);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        helloWorld.dispose();
    }
}

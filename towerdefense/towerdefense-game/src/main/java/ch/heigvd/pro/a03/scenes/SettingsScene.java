package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SettingsScene extends Scene {

    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    public SettingsScene() {

        viewport = new FitViewport(GameLauncher.WIDTH, GameLauncher.HEIGHT);

        // Create Scene2D skin and stage
        skin = UI.getSkin();
        stage = new Stage(viewport);

        Table table = new Table();
        table.setFillParent(true);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameLauncher.getInstance().getSceneManager().pop();
            }
        });

        table.add(backButton).prefWidth(250).prefHeight(50);

        stage.addActor(table);
    }

    @Override
    public void enter() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void leave() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
    }

    @Override
    public void draw() {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}

package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MatchMakingScene extends Scene {

    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    @Override
    public void enter() {

        viewport = new FitViewport(GameLauncher.WIDTH, GameLauncher.HEIGHT);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        Label title = new Label("Searching for players", skin);
        title.setAlignment(Align.center);

        Table menuTable = new Table();
        menuTable.defaults().expandX().bottom();

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        rootTable.defaults().grow();
        rootTable.add(title);
        rootTable.row();
        rootTable.add(menuTable);

        stage.addActor(rootTable);

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
        stage.dispose();
        skin.dispose();
    }
}

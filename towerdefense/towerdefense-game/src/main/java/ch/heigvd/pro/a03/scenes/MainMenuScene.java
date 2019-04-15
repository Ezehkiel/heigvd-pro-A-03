package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.menus.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScene extends Scene {

    private final boolean DEBUG = false;

    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    public MainMenuScene() {

        viewport = new FitViewport(GameLauncher.WIDTH, GameLauncher.HEIGHT);

        // Create Scene2D skin and stage
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(viewport);

        // Title
        Label title = new Label("GameLauncher Titled", skin);
        title.setFontScale(1f);
        title.setAlignment(Align.center);
        title.setColor(Color.GOLDENROD);

        Table menuTable = new Table();
        menuTable.setDebug(DEBUG);

        menuTable.defaults().expand().bottom();
        menuTable.add(new MainMenu(skin).getMenu());
        menuTable.add(new AuthSelectionMenu(skin).getMenu());

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setDebug(DEBUG);
        rootTable.padBottom(50);

        rootTable.defaults().grow();
        rootTable.add(title);
        rootTable.row();
        rootTable.add(menuTable).spaceBottom(50);

        stage.addActor(rootTable);
    }

    @Override
    public void enter() {
        Gdx.input.setInputProcessor(stage);
        System.out.println("Entering Main Menu");
    }

    @Override
    public void leave() {
        Gdx.input.setInputProcessor(null);
        System.out.println("Leaving Main Menu");
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

    private void showRegistrationMenu() {

    }
}

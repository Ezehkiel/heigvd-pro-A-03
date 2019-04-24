package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.menus.*;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.Gdx;
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
        skin = UI.getSkin();
        stage = new Stage(viewport);

        // Title
        Label title = new Label("Tower Defense", skin, "title");
        title.setAlignment(Align.center);

        Table menuTable = new Table();

        menuTable.defaults().expand().bottom();
        menuTable.add(new MainMenu(skin).getMenu());
        menuTable.add(new AuthSelectionMenu(skin).getMenu());

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.padBottom(UI.SPACING);

        rootTable.defaults().grow();
        rootTable.add(title);
        rootTable.row();
        rootTable.add(menuTable);

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
}

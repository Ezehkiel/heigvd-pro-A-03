package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu extends Scene {

    private Skin skin;
    private Stage stage;

    public MainMenu() {

        // Create Scene2D skin and stage
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table menuTable = new Table();
        menuTable.setPosition(0, 0);
        menuTable.setSize(Game.WIDTH / 2, Game.HEIGHT);

        //menuTable.setDebug(true);
        stage.addActor(menuTable);

        // Title
        Label title = new Label("Game Title", skin);
        title.setFontScale(1.5f);
        title.setAlignment(Align.center);
        title.setColor(Color.GOLDENROD);

        // Buttons
        TextButton onlineButton = new TextButton("Play Online", skin, "default");
        TextButton offlineButton = new TextButton("Play Offline", skin, "default");
        TextButton settingsButton = new TextButton("Settings", skin, "default");

        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        menuTable.defaults().prefWidth(250).prefHeight(50);
        menuTable.add(title).expandY();
        menuTable.row();
        menuTable.add(onlineButton).spaceBottom(50);
        menuTable.row();
        menuTable.add(offlineButton).spaceBottom(50);
        menuTable.row();
        menuTable.add(settingsButton).spaceBottom(50);
        menuTable.row();
        menuTable.add(exitButton).spaceBottom(50);
        menuTable.row();
        menuTable.add();// cell for spacing
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
    }

    @Override
    public void draw() {
        stage.draw();
    }
}

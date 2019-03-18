package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

        setupMenu();
        setupConnectionMenu();
    }

    /**
     * Create the connection form
     */
    private void setupConnectionMenu() {

        Table menu = new Table();
        menu.setPosition(Game.WIDTH / 2f, 0);
        menu.setSize(Game.WIDTH / 2f, Game.HEIGHT);
        menu.bottom();
        menu.setDebug(true);

        // Label
        Label label = new Label("Connection", skin);

        // Tex fields
        TextField usernameField = new TextField("", skin);
        TextField passwordField = new TextField("", skin);

        // Buttons
        TextButton connectButton = new TextButton("Log in", skin);

        menu.defaults().prefWidth(250).prefHeight(50).spaceBottom(50);
        menu.add(label);
        menu.row();
        menu.add(usernameField);
        menu.row();
        menu.add(passwordField);
        menu.row();
        menu.add(connectButton);
        menu.row();
        menu.add();

        stage.addActor(menu);
    }

    /**
     * Create the main menu buttons and their behavior
     */
    private void setupMenu() {

        Table menu = new Table();
        menu.setPosition(0, 0);
        menu.setSize(Game.WIDTH / 2f, Game.HEIGHT);

        //menuTable.setDebug(true);
        stage.addActor(menu);

        // Title
        Label title = new Label("Game Title", skin);
        title.setFontScale(1.5f);
        title.setAlignment(Align.center);
        title.setColor(Color.GOLDENROD);

        // Buttons
        TextButton onlineButton = new TextButton("Play Online", skin, "default");
        TextButton offlineButton = new TextButton("Play Offline", skin, "default");
        offlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                Game.getInstance().addScene(new GameScene());
            }
        });

        TextButton settingsButton = new TextButton("Settings", skin, "default");

        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        menu.defaults().prefWidth(250).prefHeight(50);
        menu.add(title).expandY();
        menu.row();
        menu.add(onlineButton).spaceBottom(50);
        menu.row();
        menu.add(offlineButton).spaceBottom(50);
        menu.row();
        menu.add(settingsButton).spaceBottom(50);
        menu.row();
        menu.add(exitButton).spaceBottom(50);
        menu.row();
        menu.add();// cell for spacing
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

    }

    @Override
    public void dispose() {

        stage.dispose();
        skin.dispose();
    }
}

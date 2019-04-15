package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.utils.Communication;
import ch.heigvd.pro.a03.utils.Protocole;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static ch.heigvd.pro.a03.utils.Communication.readProtocol;
import static ch.heigvd.pro.a03.utils.Protocole.YOURAREPLAYERONE;

public class MatchMakingScene extends Scene {

    private Viewport viewport;
    private Skin skin;
    private Stage stage;
    private boolean waitingForServer = true;

    public MatchMakingScene() {
        viewport = new FitViewport(GameLauncher.WIDTH, GameLauncher.HEIGHT);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        Label title = new Label("Searching for players", skin);
        title.setAlignment(Align.center);

        TextButton readyButton = new TextButton("Ready!", skin);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameClient.getInstance().ready();
                System.out.printf("You are player %d", GameClient.getInstance().getPlayerNumber());

                GameLauncher.getInstance().getSceneManager().add(new GameScene());
            }
        });

        // Setup menu table
        Table menuTable = new Table();
        menuTable.defaults().expandX().bottom();

        // Setup root Table
        Table rootTable = new Table();
        rootTable.setFillParent(true);

        rootTable.defaults().grow();
        rootTable.add(title);
        rootTable.row();
        rootTable.add(menuTable);

        stage.addActor(rootTable);

        // Connect to server
        new Thread() {

            @Override
            public void run() {
                super.run();

                if (GameClient.getInstance().connect()) {
                    System.out.println("Server is ready");
                    title.setText("Player Found! Are you ready?");
                    menuTable.add(readyButton);
                }
            }
        }.start();
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
        stage.dispose();
        skin.dispose();
    }
}

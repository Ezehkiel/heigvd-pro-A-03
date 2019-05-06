package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.utils.UI;
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

public class MatchMakingScene extends Scene {


    public MatchMakingScene() {

        Label title = new Label("Searching for players", getSkin());
        title.setAlignment(Align.center);

        TextButton readyButton = new TextButton("Ready!", getSkin());
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

        getStage().addActor(rootTable);

        // Connect to server
        new Thread() {

            @Override
            public void run() {
                super.run();

                if (GameClient.getInstance().connect()) {
                    System.out.println("Server is ready");
                    title.setText("PlayerEvent Found! Are you ready?");
                    menuTable.add(readyButton);
                }
            }
        }.start();
    }
}

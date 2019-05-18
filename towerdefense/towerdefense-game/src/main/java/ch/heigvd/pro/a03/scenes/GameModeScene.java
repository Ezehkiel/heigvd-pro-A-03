package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.scenes.SetSceneCommand;
import ch.heigvd.pro.a03.server.GameClient;
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

public class GameModeScene extends Scene {

    public GameModeScene() {

        Table table = new Table();
        table.setFillParent(true);

        TextButton playButton = new TextButton("Play!", getSkin());
        playButton.addListener(new ButtonCommand(new SetSceneCommand(GameLauncher.getInstance())) {
            @Override
            public void executeCommand() {
                getCommand().execute(new GameScene(new GameClient(2, false)));
            }
        });

        table.add(playButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);

        getStage().addActor(table);
    }
}

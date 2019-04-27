package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.scenes.PopSceneCommand;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SettingsScene extends Scene {

    public SettingsScene() {

        Table table = new Table();
        table.setFillParent(true);

        TextButton backButton = new TextButton("Back", getSkin());
        backButton.addListener(new ButtonCommand(new PopSceneCommand(GameLauncher.getInstance())));

        table.add(backButton).prefWidth(250).prefHeight(50);

        getStage().addActor(table);
    }
}

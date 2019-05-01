package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GamePauseMenu extends Menu {

    public GamePauseMenu(GameMenu menu, GameScene scene, Skin skin) {

        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ButtonCommand(new Command<GameMenu>(menu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showPlayingMenu();
            }
        }));

        TextButton leaveButton = new TextButton("Leave game", skin);
        leaveButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public void execute(Object... args) {
                getReceiver().getSceneManager().pop();
            }
        }));

        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT).spaceBottom(UI.SPACING);
        getMenu().add(continueButton);
        getMenu().row();
        getMenu().add(leaveButton);
    }
}

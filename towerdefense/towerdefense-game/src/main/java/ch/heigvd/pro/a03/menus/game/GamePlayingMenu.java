package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GamePlayingMenu extends Menu {

    public GamePlayingMenu(GameMenu menu, GameScene scene, Skin skin) {

        TextButton selectUnitButton = new TextButton("Units", skin);
        selectUnitButton.addListener(new ButtonCommand(new Command<GameMenu>(menu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showUnitSelectionMenu();
            }
        }));

        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.addListener(new ButtonCommand(new Command<GameMenu>(menu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showPauseMenu();
            }
        }));

        getMenu().pad(UI.SPACING / 2f);

        // Top menu
        getMenu().add(pauseButton).prefWidth(UI.BUTTON_SMALL_WIDTH).prefHeight(UI.BUTTON_HEIGHT).left();

        // Separate top and bottom
        getMenu().row();
        getMenu().add().expandY();
        getMenu().row();

        // Bottom menu
        getMenu().add(selectUnitButton).prefWidth(UI.BUTTON_SMALL_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
        getMenu().add().expandX();
        getMenu().add(new TurretSelectionMenu(skin, scene).getMenu());

        getMenu().setTouchable(Touchable.enabled);
        getMenu().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (event.getTarget() == getMenu()) {
                    scene.clickMap(x, y);
                }
            }
        });
    }
}

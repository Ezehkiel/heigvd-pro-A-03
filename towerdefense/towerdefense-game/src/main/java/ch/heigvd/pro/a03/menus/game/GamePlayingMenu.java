package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.game.GameSceneCommand;
import ch.heigvd.pro.a03.commands.game.TowerDefenseCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import javax.swing.plaf.basic.BasicButtonUI;

public class GamePlayingMenu extends Menu {

    private Label infoLabel;
    private Label moneyLabel;
    private TextButton endTurnButton;

    public GamePlayingMenu(GameMenu menu, GameScene scene, Skin skin) {

        infoLabel = new Label("Please Wait", skin);
        moneyLabel = new Label("0", skin);
        moneyLabel.setAlignment(Align.right);

        endTurnButton = new TextButton("End turn", skin);
        hideEndTurnButton();
        endTurnButton.addListener(new ButtonCommand(new GameSceneCommand(scene) {
            @Override
            public void execute(Object... args) {
                getReceiver().getGame().sendEvents();
                hideEndTurnButton();
            }
        }));

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
        getMenu().add(infoLabel).expandX();
        getMenu().add(moneyLabel).right();

        // Separate top and bottom
        getMenu().row();
        getMenu().add().expandY();
        getMenu().row();

        // Bottom menu
        getMenu().add(selectUnitButton)
                .prefWidth(UI.BUTTON_SMALL_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT);

        getMenu().add(endTurnButton)
                .prefWidth(UI.BUTTON_SMALL_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT)
                .spaceLeft(UI.SPACING);

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

    public void updateInfo(String text) {
        infoLabel.setText(text);
    }

    public void updateMoney(int money) {
        moneyLabel.setText(money);
    }

    public void showEndTurnButton() {
        endTurnButton.setVisible(true);
    }

    public void hideEndTurnButton() {
        endTurnButton.setVisible(false);
    }
}

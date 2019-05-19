package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.game.GameSceneCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * The game playing menu
 */
public class GamePlayingMenu extends Menu {

    private Label infoLabel;
    private Label moneyLabel;
    private TextButton endTurnButton;
    private TextButton selectUnitButton;
    private TextButton incomingUnitsButton;
    private Table turretSelectionMenu;

    /**
     * Creates the menu
     * @param menu game menu
     * @param scene game scene
     * @param skin skin used
     */
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
                hideTurretSelectionMenu();
                hideUnitsSelectionButton();
                hideEndTurnButton();
            }
        }));

        selectUnitButton = new TextButton("Units", skin);
        hideUnitsSelectionButton();
        selectUnitButton.addListener(new ButtonCommand(new Command<GameMenu>(menu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showUnitSelectionMenu();
            }
        }));

        incomingUnitsButton = new TextButton("Incoming", skin);
        incomingUnitsButton.addListener(new ButtonCommand(new Command<GameMenu>(menu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showIncomingUnitsMenu();
            }
        }));

        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.addListener(new ButtonCommand(new Command<GameMenu>(menu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showPauseMenu();
            }
        }));

        turretSelectionMenu = new TurretSelectionMenu(skin, scene).getMenu();
        hideTurretSelectionMenu();

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

        getMenu().add(incomingUnitsButton)
                .prefWidth(UI.BUTTON_SMALL_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT);

        getMenu().add(endTurnButton)
                .prefWidth(UI.BUTTON_SMALL_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT)
                .spaceLeft(UI.SPACING);

        getMenu().add().expandX();
        getMenu().add(turretSelectionMenu);

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

    /**
     * Updates the message info
     * @param text message
     */
    public void updateInfo(String text) {
        infoLabel.setText(text);
    }

    /**
     * Update the money label
     * @param money money
     */
    public void updateMoney(int money) {
        moneyLabel.setText(money);
    }

    /**
     * show the end turn button
     */
    public void showEndTurnButton() {
        endTurnButton.setVisible(true);
    }

    /**
     * Hide the end turn button
     */
    public void hideEndTurnButton() {
        endTurnButton.setVisible(false);
    }

    /**
     * Show the unit selection button
     */
    public void showUnitsSelectionButton() {
        selectUnitButton.setVisible(true);
    }

    /**
     * Hide the unit selection button
     */
    public void hideUnitsSelectionButton() {
        selectUnitButton.setVisible(false);
    }

    /**
     * Show the turret selection and incoming units buttons
     */
    public void showTurretSelectionMenu() {
        turretSelectionMenu.setVisible(true);
        incomingUnitsButton.setVisible(true);
    }

    /**
     * Hide the turret selection and incoming units buttons
     */
    public void hideTurretSelectionMenu() {
        turretSelectionMenu.setVisible(false);
        incomingUnitsButton.setVisible(false);
    }
}

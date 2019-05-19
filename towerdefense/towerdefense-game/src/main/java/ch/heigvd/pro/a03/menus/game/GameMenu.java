package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.utils.UI;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.json.JSONArray;

/**
 * The game menu
 */
public class GameMenu extends Menu {

    private Skin skin;
    private GameScene scene;

    private GamePlayingMenu playingMenu;
    private GamePauseMenu pauseMenu;
    private UnitSelectionMenu unitSelectionMenu;
    private IncomingUnitsMenu incomingUnitsMenu;

    /**
     * Creates the game menu
     * @param skin skin used
     * @param scene scene of the menu
     */
    public GameMenu(Skin skin, GameScene scene) {

        this.scene = scene;
        this.skin = skin;

        getMenu().setFillParent(true);

        playingMenu = new GamePlayingMenu(this, scene, skin);
        pauseMenu = new GamePauseMenu(this, scene, skin);
        unitSelectionMenu = new UnitSelectionMenu(this, skin);
        incomingUnitsMenu = new IncomingUnitsMenu(this, skin);

        showPlayingMenu();
    }

    /**
     * Shows the playing menu
     */
    public void showPlayingMenu() {

        getMenu().clear();

        getMenu().add(playingMenu.getMenu()).expand().grow();
    }

    /**
     * Shows the pause menu
     */
    public void showPauseMenu() {

        getMenu().clear();

        getMenu().add(pauseMenu.getMenu()).expand().grow();
    }

    /**
     * Show the unit selection menu
     */
    public void showUnitSelectionMenu() {

        getMenu().clear();

        getMenu().add(unitSelectionMenu.getMenu());
    }

    /**
     * Shows the incoming units
     */
    public void showIncomingUnitsMenu() {

        getMenu().clear();

        getMenu().add(incomingUnitsMenu.getMenu());
    }

    /**
     * Updates the incoming units menus
     * @param units
     */
    public void updateIncomingUnitsMenu(JSONArray units) {
        incomingUnitsMenu.update(units);
    }

    /**
     * Resets the unit selection menu
     */
    public void resetUnitSelectionMenu() {
        unitSelectionMenu.reset();
    }

    /**
     * Shows the turret menu
     * @param towerDefense tower defense game
     * @param mapId palyer's map id
     * @param turret the menu of the turret
     */
    public void showTurretMenu(TowerDefense towerDefense, int mapId, Turret turret) {

        System.out.println("Not implemented yet!");
        return;

//        getMenu().clear();
//
//        getMenu().add(new TurretMenu(this, towerDefense, mapId, turret, skin).getMenu());
    }

    /**
     * Show message info
     * @param text message
     */
    public void showInfo(String text) {
        showPlayingMenu();
        playingMenu.updateInfo(text);
    }

    /**
     * Update the money labels
     * @param money money
     */
    public void updateMoney(int money) {
        playingMenu.updateMoney(money);
        unitSelectionMenu.updateMoney(money);
    }

    /**
     * Send the selected units
     * @param types array of unit types
     * @param quantities array of quantities
     * @return true if the units were sent
     */
    public boolean sendUnits(WarEntityType.UnitType[] types, int[] quantities) {
        return scene.getGame().sendUnits(types, quantities);
    }

    /**
     * Get the playing menu
     * @return game playing menu
     */
    public GamePlayingMenu getPlayingMenu() {
        return playingMenu;
    }

    /**
     * Shows the end menu
     * @param id if of the current player
     * @param player the loser
     */
    public void showEndMenu(int id, Player player) {

        String text = id == player.ID ? "You lost!" : "You won!";

        TextButton closeButton = new TextButton("Leave game", skin);
        closeButton.addListener(new ButtonCommand(args -> GameLauncher.getInstance().getSceneManager().pop()));

        getMenu().clear();

        getMenu().add(new Label(text, skin, "title")).spaceBottom(50);
        getMenu().row();
        getMenu().add(closeButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
    }
}

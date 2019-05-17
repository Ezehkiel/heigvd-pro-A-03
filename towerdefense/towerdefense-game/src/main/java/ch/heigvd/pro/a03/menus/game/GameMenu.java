package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class GameMenu extends Menu {

    private Skin skin;
    private GameScene scene;

    private GamePlayingMenu playingMenu;
    private GamePauseMenu pauseMenu;
    private UnitSelectionMenu unitSelectionMenu;

    public GameMenu(Skin skin, GameScene scene) {

        this.scene = scene;
        this.skin = skin;

        getMenu().setFillParent(true);

        playingMenu = new GamePlayingMenu(this, scene, skin);
        pauseMenu = new GamePauseMenu(this, scene, skin);
        unitSelectionMenu = new UnitSelectionMenu(this, skin);

        showPlayingMenu();
    }

    public void showPlayingMenu() {

        getMenu().clear();

        getMenu().add(playingMenu.getMenu()).expand().grow();
    }

    public void showPauseMenu() {

        getMenu().clear();

        getMenu().add(pauseMenu.getMenu()).expand().grow();
    }

    public void showUnitSelectionMenu() {

        getMenu().clear();

        getMenu().add(unitSelectionMenu.getMenu());
    }

    public void resetUnitSelectionMenu() {
        unitSelectionMenu.reset();
    }

    public void showTurretMenu(TowerDefense towerDefense, int mapId, Turret turret) {

        getMenu().clear();

        getMenu().add(new TurretMenu(this, towerDefense, mapId, turret, skin).getMenu());
    }

    public void showInfo(String text) {
        showPlayingMenu();
        playingMenu.updateInfo(text);
    }

    public void updateMoney(int money) {
        playingMenu.updateMoney(money);
    }

    public boolean sendUnits(WarEntityType.UnitType[] types, int[] quantities) {
        return scene.getGame().sendUnits(types, quantities);
    }

    public GamePlayingMenu getPlayingMenu() {
        return playingMenu;
    }
}

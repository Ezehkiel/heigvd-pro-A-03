package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameMenu extends Menu {

    private GamePlayingMenu playingMenu;
    private GamePauseMenu pauseMenu;

    public GameMenu(Skin skin, GameScene scene) {

        getMenu().setFillParent(true);

        playingMenu = new GamePlayingMenu(this, scene, skin);
        pauseMenu = new GamePauseMenu(this, scene, skin);

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
}

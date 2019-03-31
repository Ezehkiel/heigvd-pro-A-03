package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameMenu extends Menu {

    public GameMenu(Skin skin, GameScene scene) {

        getMenu().setFillParent(true);
        getMenu().pad(25);

        TextButton unitButton = new TextButton("U", skin);

        Table turretSelectionMenu = new TurretSelectionMenu(skin, scene).getMenu();

        // Top menu
        getMenu().add();

        // Separate top and bottom
        getMenu().row();
        getMenu().add().expandY();
        getMenu().row();

        // Bottom menu
        getMenu().add().expandX();
        getMenu().add(turretSelectionMenu);

        getMenu().setTouchable(Touchable.enabled);
        getMenu().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (event.getTarget() == getMenu()) {
                    scene.clickMap(x, GameLauncher.HEIGHT - y);
                }
            }
        });
    }
}

package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.Game;
import ch.heigvd.pro.a03.scenes.GameScene;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameMenu extends Menu {

    public GameMenu(Skin skin, GameScene scene) {

        getMenu().setFillParent(true);
        getMenu().pad(25);

        TextButton unitButton = new TextButton("U", skin);

        TextButton turretButton = new TextButton("T", skin);
        turretButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                System.out.println("Turret Button");
            }
        });

        getMenu().defaults().prefWidth(64).prefHeight(64);

        // Top menu
        getMenu().add();

        // Separate top and bottom
        getMenu().row();
        getMenu().add().expandY();
        getMenu().row();

        // Bottom menu
        getMenu().add(unitButton);
        getMenu().add().expandX();
        getMenu().add(turretButton);

        getMenu().setTouchable(Touchable.enabled);
        getMenu().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (event.getTarget() == getMenu()) {
                    scene.click(x, Game.HEIGHT - y);
                }
            }
        });
    }
}

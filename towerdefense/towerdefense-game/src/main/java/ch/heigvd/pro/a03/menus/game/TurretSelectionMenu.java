package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.game.GameSceneCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.*;

/**
 * Turret selection menu
 */
public class TurretSelectionMenu extends Menu {

    /**
     * Create the menu
     * @param skin skin used
     * @param scene game scene
     */
    public TurretSelectionMenu(Skin skin, GameScene scene) {

        getMenu().defaults().prefWidth(64).prefHeight(64).spaceLeft(16);

        for (WarEntityType.TurretType type : WarEntityType.TurretType.values()) {

            TextButton button = new TextButton(type.getName(), skin);
            button.addListener(new ButtonCommand(new GameSceneCommand(scene) {
                @Override
                public void execute(Object... args) {
                    getReceiver().selectTurret(type);
                }
            }));

            getMenu().add(button);
        }
    }
}

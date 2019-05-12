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

public class TurretSelectionMenu extends Menu {

    public TurretSelectionMenu(Skin skin, GameScene scene) {

        TextButton clearSelectionButton = new TextButton("X", skin);
        clearSelectionButton.addListener(new ButtonCommand(new GameSceneCommand(scene) {
            @Override
            public void execute(Object... args) {
                getReceiver().clearSelectedTurret();
            }
        }));

        TextButton machineGun = new TextButton("MG", skin);
        machineGun.addListener(new ButtonCommand(new GameSceneCommand(scene) {
            @Override
            public void execute(Object... args) {
                getReceiver().selectTurret(WarEntityType.TurretType.MACHINE_GUN);
            }
        }));

        TextButton mortar = new TextButton("M", skin);
        mortar.addListener(new ButtonCommand(new GameSceneCommand(scene) {
            @Override
            public void execute(Object... args) {
                getReceiver().selectTurret(WarEntityType.TurretType.MORTAR);
            }
        }));

        TextButton slower = new TextButton("S", skin);
        slower.addListener(new ButtonCommand(new GameSceneCommand(scene) {
            @Override
            public void execute(Object... args) {
                getReceiver().selectTurret(WarEntityType.TurretType.LASER_GUN);
            }
        }));

        getMenu().defaults().prefWidth(64).prefHeight(64).spaceLeft(16);
        getMenu().add(clearSelectionButton);
        getMenu().add(machineGun);
        getMenu().add(mortar);
        getMenu().add(slower);
    }
}

package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.game.GameSceneCommand;
import ch.heigvd.pro.a03.commands.game.ShowPlayingMenuCommand;
import ch.heigvd.pro.a03.commands.scenes.MacroCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.utils.UI;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.awt.*;

public class TurretMenu extends Menu {

    public TurretMenu(GameMenu menu, TowerDefense towerDefense, int mapId, Turret turret, Skin skin) {

        ShowPlayingMenuCommand showPlayingMenuCommand = new ShowPlayingMenuCommand(menu);

        TextButton repairButton = new TextButton(String.format("Repair: %d", turret.getPrice() / 2), skin);
        repairButton.addListener(new ButtonCommand(new MacroCommand(
                new Command<Turret>(turret) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().heal(100000);
                    }
                },
                showPlayingMenuCommand
        )));

        TextButton destroyButton = new TextButton(String.format("Destroy: %d", turret.getPrice() / 2), skin);
        destroyButton.addListener(new ButtonCommand(new MacroCommand(
                new Command<TowerDefense>(towerDefense) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().destroyTurret(mapId, turret);
                    }
                },
                showPlayingMenuCommand
        )));

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ButtonCommand(showPlayingMenuCommand));

        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT).spaceBottom(UI.SPACING);
        getMenu().add(repairButton);
        getMenu().row();
        getMenu().add(destroyButton);
        getMenu().row();
        getMenu().add(closeButton);
        getMenu().row();
    }
}

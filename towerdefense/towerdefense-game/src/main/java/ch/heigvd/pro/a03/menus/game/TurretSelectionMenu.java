package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TurretSelectionMenu extends Menu {

    public TurretSelectionMenu(Skin skin, GameScene scene) {

        TextButton machineGun = new TextButton("MG", skin);
        machineGun.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                scene.selectTurret(GameScene.TurretType.MACHINE_GUN);
            }
        });

        TextButton mortar = new TextButton("M", skin);
        mortar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                scene.selectTurret(GameScene.TurretType.MORTAR);
            }
        });

        TextButton slower = new TextButton("S", skin);
        slower.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                scene.selectTurret(GameScene.TurretType.SLOWER);
            }
        });

        getMenu().setDebug(true);
        getMenu().defaults().prefWidth(64).prefHeight(64).spaceLeft(16);
        getMenu().add(machineGun);
        getMenu().add(mortar);
        getMenu().add(slower);
    }
}

package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.menus.*;
import ch.heigvd.pro.a03.menus.auth.AuthMenu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class MainMenuScene extends Scene {

    public MainMenuScene() {

        // Title
        Label title = new Label("Tower Defense", getSkin(), "title");
        title.setAlignment(Align.center);

        Table menuTable = new Table();

        menuTable.defaults().expand().grow();
        menuTable.add(new MainMenu(getSkin()).getMenu());
        menuTable.add(new AuthMenu(getSkin()).getMenu());

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(UI.SPACING);

        rootTable.defaults();
        rootTable.add(title).pad(UI.SPACING);
        rootTable.row();
        rootTable.add(menuTable).expand().grow();

        getStage().addActor(rootTable);
    }
}

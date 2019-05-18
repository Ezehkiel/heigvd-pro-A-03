package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.menus.WindowMenu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import org.json.JSONArray;
import org.json.JSONObject;

public class IncomingUnitsMenu extends Menu {

    private Skin skin;
    private TextButton closeButton;

    public IncomingUnitsMenu(GameMenu gameMenu ,Skin skin) {
        this.skin = skin;

        closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ButtonCommand(args -> gameMenu.showPlayingMenu()));
    }

    public void update(JSONArray units) {

        getMenu().clear();

        Window window = new WindowMenu(skin).getWindow();
        for (int i = 0; i < units.length(); ++i) {

            JSONObject unit = units.getJSONObject(i);

            String type = unit.getString("type");
            int count = unit.getInt("quantity");

            window.add(new Label(type + ": " + count, skin));
            window.row().spaceBottom(10);
        }

        if (units.isEmpty()) {
                window.add(new Label("No incoming units.", skin));
        }

        getMenu().add(window).prefWidth(UI.BUTTON_WIDTH).spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(closeButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
    }
}

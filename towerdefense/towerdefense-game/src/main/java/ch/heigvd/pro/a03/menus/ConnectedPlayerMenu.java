package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.Player;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ConnectedPlayerMenu extends Menu {

    Label usernameLabel;

    public ConnectedPlayerMenu(Player player, Skin skin) {

        usernameLabel = new Label(player.getUsername(), skin);

        getMenu().add(usernameLabel).expandY().growY().top();
    }
}

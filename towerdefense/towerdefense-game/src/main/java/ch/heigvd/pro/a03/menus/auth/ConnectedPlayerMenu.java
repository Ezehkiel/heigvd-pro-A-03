package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.LogoutCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ConnectedPlayerMenu extends Menu {

    Label usernameLabel;

    public ConnectedPlayerMenu(Player player, AuthMenu authMenu, Skin skin) {

        usernameLabel = new Label(player.getUsername(), skin);

        TextButton logoutButton = new TextButton("Log out", skin);
        logoutButton.addListener(new ButtonCommand(new LogoutCommand(authMenu)));

        getMenu().setDebug(true);
        getMenu().add(usernameLabel).expand().spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(logoutButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);

    }
}

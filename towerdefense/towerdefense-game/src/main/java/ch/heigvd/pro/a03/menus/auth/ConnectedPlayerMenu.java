package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.LogoutCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ConnectedPlayerMenu extends Menu {

    public ConnectedPlayerMenu(User player, AuthMenu authMenu, Skin skin) {

        Label usernameLabel = new Label(player.getUsername(), skin);
        Label winGameLabel = new Label("Nombre de parties gagnées: " + player.getGetNbPartieGagne(), skin);
        Label playedGameLabel = new Label("Nombre de partie jouées: " + player.getNbPartieJoue(), skin);

        TextButton logoutButton = new TextButton("Log out", skin);
        logoutButton.addListener(new ButtonCommand(new LogoutCommand(authMenu)));

        getMenu().add(usernameLabel).expand().spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(winGameLabel).expand().spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(playedGameLabel).expand().spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(logoutButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);

    }
}

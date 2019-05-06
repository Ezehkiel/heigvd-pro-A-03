package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.matchmaking.PlayerMenu;
import ch.heigvd.pro.a03.server.GameClient;

public class MatchMakingScene extends Scene {

    private PlayerMenu playerOneMenu;
    private PlayerMenu playerTwoMenu;

    private GameClient gameClient;

    public MatchMakingScene() {

        gameClient = new GameClient();

        playerOneMenu = new PlayerMenu(GameLauncher.getInstance().getConnectedPlayer(), null, getSkin());

        getStage().addActor(playerOneMenu.getMenu());

        gameClient.connect(new Command<PlayerMenu>(playerOneMenu) {
            @Override
            public void execute(Object... args) {
                getReceiver().updateMenu(true);
            }
        });
    }
}

package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.matchmaking.PlayerMenu;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.utils.RandomPlayer;

public class MatchMakingScene extends Scene {

    private PlayerMenu playerMenu;
    private GameClient gameClient;

    public MatchMakingScene(boolean online) {

        gameClient = new GameClient(2, online);
        gameClient.connect(new Command<MatchMakingScene>(this) {
            @Override
            public void execute(Object... args) {
                getReceiver().showPlayerMenu();
            }
        });
    }

    public void showPlayerMenu() {

        playerMenu = new PlayerMenu(
                gameClient.ONLINE ? GameLauncher.getInstance().getConnectedPlayer()
                        : RandomPlayer.USER,
                this, gameClient, getSkin());
        playerMenu.getMenu().setFillParent(true);

        getStage().addActor(playerMenu.getMenu());

        gameClient.getPlayers(
                new Command<MatchMakingScene>(this) {
                    @Override
                    public void execute(Object... args) {
                        System.out.println(((Player) args[0]).getName() + " has arrived.");
                    }
                },
                new Command<MatchMakingScene>(this) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().playerMenu.updateMenu(true);
                    }
                }
        );
    }

    public void startGame() {
        GameLauncher.getInstance().getSceneManager().set(new GameScene(gameClient));
    }
}

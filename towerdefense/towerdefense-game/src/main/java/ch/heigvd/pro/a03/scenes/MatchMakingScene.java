package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.menus.matchmaking.MatchMakingMenu;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.utils.RandomPlayer;

/**
 * Match making scene
 */
public class MatchMakingScene extends Scene {

    private MatchMakingMenu menu;
    private GameClient gameClient;

    /**
     * Creates the scene
     * @param online true if connect to online server
     */
    public MatchMakingScene(boolean online) {

        gameClient = new GameClient(2, online);

        menu = new MatchMakingMenu(
                gameClient.ONLINE ? GameLauncher.getInstance().getConnectedPlayer() : RandomPlayer.USER,
                gameClient, this, getSkin()
        );

        getStage().addActor(menu.getMenu());

        gameClient.setMatchMakingScene(this);
        gameClient.connect(args -> connected());
    }

    private void connected() {
        menu.setTitle("Waiting for players");
        gameClient.getPlayers(
                args -> System.out.println("Player has arrived"),
                args -> ready()
        );
    }

    private void ready() {
        menu.setTitle("Are your ready?");
        menu.showReadyButton();
    }

    /**
     * Starts the game
     */
    public void startGame() {
        GameLauncher.getInstance().getSceneManager().set(new GameScene(gameClient));
    }

    public void failed() {
        menu.setTitle("Connection failed!");
        menu.hideReadyButton();
    }

    public void quit() {
        gameClient.close();
        GameLauncher.getInstance().getSceneManager().pop();
    }
}

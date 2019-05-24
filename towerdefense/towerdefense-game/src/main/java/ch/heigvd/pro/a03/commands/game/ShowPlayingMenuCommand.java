package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.game.GameMenu;

/**
 * A command that shows the game playing menu
 */
public class ShowPlayingMenuCommand extends Command<GameMenu> {

    /**
     * Creates a new command
     * @param receiver the receiver
     */
    public ShowPlayingMenuCommand(GameMenu receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {
        getReceiver().showPlayingMenu();
    }
}

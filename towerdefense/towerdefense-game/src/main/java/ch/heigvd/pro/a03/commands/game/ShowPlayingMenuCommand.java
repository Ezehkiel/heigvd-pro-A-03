package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.game.GameMenu;

public class ShowPlayingMenuCommand extends Command<GameMenu> {

    public ShowPlayingMenuCommand(GameMenu receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {
        getReceiver().showPlayingMenu();
    }
}

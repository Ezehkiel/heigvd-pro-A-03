package ch.heigvd.pro.a03.commands.gameclient;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.server.GameClient;

public abstract class GameClientCommand extends Command<GameClient> {

    public GameClientCommand(GameClient receiver) {
        super(receiver);
    }
}

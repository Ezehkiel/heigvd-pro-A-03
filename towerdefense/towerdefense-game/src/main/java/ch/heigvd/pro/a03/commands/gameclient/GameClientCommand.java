package ch.heigvd.pro.a03.commands.gameclient;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.server.GameClient;

/**
 * Represents a command with a GameClient as the receiver.
 */
public abstract class GameClientCommand extends Command<GameClient> {

    /**
     * Create a new command.
     * @param receiver game client
     */
    public GameClientCommand(GameClient receiver) {
        super(receiver);
    }
}

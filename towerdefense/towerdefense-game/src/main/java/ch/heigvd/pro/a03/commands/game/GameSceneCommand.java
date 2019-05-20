package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.scenes.GameScene;

/**
 * A coomand execute with a game scene as the receiver.
 */
public abstract class GameSceneCommand extends Command<GameScene> {

    /**
     * Creates a new command.
     * @param receiver game scene
     */
    public GameSceneCommand(GameScene receiver) {
        super(receiver);
    }
}

package ch.heigvd.pro.a03.commands.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;

/**
 * A command that pops the current scene
 */
public class PopSceneCommand extends Command<GameLauncher> {

    /**
     * Creates a new pop scene command
     * @param receiver the game launcher
     */
    public PopSceneCommand(GameLauncher receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {
        getReceiver().getSceneManager().pop();
    }
}

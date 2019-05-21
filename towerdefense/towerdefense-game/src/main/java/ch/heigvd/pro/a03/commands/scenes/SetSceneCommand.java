package ch.heigvd.pro.a03.commands.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.scenes.Scene;

/**
 * A command that pops the current scene
 */
public class SetSceneCommand extends Command<GameLauncher> {

    /**
     * Creates a new set scene command
     * @param receiver the game launcher
     */
    public SetSceneCommand(GameLauncher receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {
        getReceiver().getSceneManager().set((Scene) args[0]);
    }
}

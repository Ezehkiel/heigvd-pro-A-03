package ch.heigvd.pro.a03.commands.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.scenes.Scene;

public class SetSceneCommand extends Command<GameLauncher> {

    public SetSceneCommand(GameLauncher receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(Object... args) {
        getReceiver().getSceneManager().set((Scene) args[0]);
        return true;
    }
}

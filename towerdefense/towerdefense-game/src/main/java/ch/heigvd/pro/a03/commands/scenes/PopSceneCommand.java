package ch.heigvd.pro.a03.commands.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;

public class PopSceneCommand extends Command<GameLauncher> {

    public PopSceneCommand(GameLauncher receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(Object... args) {
        getReceiver().getSceneManager().pop();
        return true;
    }
}

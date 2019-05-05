package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.scenes.GameScene;

public abstract class GameSceneCommand extends Command<GameScene> {

    public GameSceneCommand(GameScene receiver) {
        super(receiver);
    }
}

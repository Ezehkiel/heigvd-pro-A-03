package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.commands.Command;

/**
 * Represents a command with a TowerDefense a sa receiver
 */
public abstract class TowerDefenseCommand extends Command<TowerDefense> {

    /**
     * Creates a new command
     * @param receiver the receiver
     */
    public TowerDefenseCommand(TowerDefense receiver) {
        super(receiver);
    }
}

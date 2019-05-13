package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.commands.Command;

public abstract class TowerDefenseCommand extends Command<TowerDefense> {

    public TowerDefenseCommand(TowerDefense receiver) {
        super(receiver);
    }
}

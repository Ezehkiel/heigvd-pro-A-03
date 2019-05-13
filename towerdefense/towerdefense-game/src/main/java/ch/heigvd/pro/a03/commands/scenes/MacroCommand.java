package ch.heigvd.pro.a03.commands.scenes;

import ch.heigvd.pro.a03.commands.Executable;

import java.util.Arrays;
import java.util.LinkedList;

public class MacroCommand implements Executable {

    LinkedList<Executable> commands;

    public MacroCommand(Executable... commands) {
        this.commands = new LinkedList<>();
        this.commands.addAll(Arrays.asList(commands));
    }

    @Override
    public void execute(Object... args) {
        for (Executable command : commands) {
            command.execute(args);
        }
    }
}

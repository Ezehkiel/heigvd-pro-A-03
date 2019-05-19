package ch.heigvd.pro.a03.commands;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Represents a composite of commands. When executed, every commands are executed one after another.
 */
public class MacroCommand implements Executable {

    LinkedList<Executable> commands;

    /**
     * Creates a new macro command.
     * @param commands the commands of the composite
     */
    public MacroCommand(Executable... commands) {
        this.commands = new LinkedList<>();
        this.commands.addAll(Arrays.asList(commands));
    }

    /**
     * Executes all the commands one after an other
     * @param args arguments
     */
    @Override
    public void execute(Object... args) {
        for (Executable command : commands) {
            command.execute(args);
        }
    }
}

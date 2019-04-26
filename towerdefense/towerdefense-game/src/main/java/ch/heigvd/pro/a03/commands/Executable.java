package ch.heigvd.pro.a03.commands;

/**
 * Represents somthing that can be executed such as a Command.
 */
public interface Executable {

    /**
     * Executes something.
     * @return true if the execution worked
     */
    boolean execute(Object... args);
}

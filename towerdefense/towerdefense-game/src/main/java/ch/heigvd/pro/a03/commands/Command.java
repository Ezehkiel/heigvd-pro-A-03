package ch.heigvd.pro.a03.commands;

/**
 * Generic class representing a command with a receiver
 * @param <Receiver>
 */
public abstract class Command<Receiver> implements Executable {

    private Receiver receiver;

    /**
     * Create a new command with it's receiver.
     * @param receiver The receiver of the command.
     */
    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the command
     */
    public abstract void execute();

    /**
     * Gets the command's receiver.
     * @return the receiver
     */
    public Receiver getReceiver() {
        return receiver;
    }
}
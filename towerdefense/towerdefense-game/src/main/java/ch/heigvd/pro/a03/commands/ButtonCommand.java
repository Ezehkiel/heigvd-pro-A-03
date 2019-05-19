package ch.heigvd.pro.a03.commands;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Runs a command without parameters when the button is clicked.
 */
public class ButtonCommand extends ClickListener {

    private Executable command;

    /**
     * Creates a new button command.
     * @param command the executable
     */
    public ButtonCommand(Executable command) {
        this.command = command;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        executeCommand();
    }

    /**
     * Executes the command called whene a button is clicked
     */
    public void executeCommand() {
        command.execute();
    }

    /**
     * Gets the executable.
     * @return exeutable
     */
    public Executable getCommand() {
        return command;
    }
}

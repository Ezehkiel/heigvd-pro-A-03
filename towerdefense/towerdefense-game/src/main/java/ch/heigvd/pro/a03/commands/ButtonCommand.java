package ch.heigvd.pro.a03.commands;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Runs a command without parameters when the button is clicked.
 */
public class ButtonCommand extends ClickListener {

    private Executable command;

    public ButtonCommand(Executable command) {
        this.command = command;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        executeCommand();
    }

    public void executeCommand() {
        command.execute();
    }

    public Executable getCommand() {
        return command;
    }
}

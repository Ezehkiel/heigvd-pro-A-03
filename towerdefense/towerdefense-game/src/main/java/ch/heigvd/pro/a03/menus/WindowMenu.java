package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * A scene 2d menu with a background.
 */
public class WindowMenu {

    private Window window;

    /**
     * Creates a new windowed menu
     * @param skin skin used for the window
     */
    public WindowMenu(Skin skin) {
        window = new Window("", skin);
        window.pad(UI.WINDOW_PAD);
    }

    /**
     * Gets the window.
     * @return window
     */
    public Window getWindow() {
        return window;
    }
}

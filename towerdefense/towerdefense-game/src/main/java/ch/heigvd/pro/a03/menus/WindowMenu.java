package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class WindowMenu {

    private Window window;

    public WindowMenu(Skin skin) {
        window = new Window("", skin);
        window.pad(UI.WINDOW_PAD);
    }

    public Window getWindow() {
        return window;
    }
}

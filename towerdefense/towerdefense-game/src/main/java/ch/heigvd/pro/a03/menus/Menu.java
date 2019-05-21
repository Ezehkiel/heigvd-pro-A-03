package ch.heigvd.pro.a03.menus;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A simple scene 2d menu containing a table.
 */
public class Menu {

    private Table menu;

    /**
     * Create an empty menu
     */
    public Menu() {
        menu = new Table();
    }

    /**
     * Gets the table tha represents the menu
     * @return a table
     */
    public Table getMenu() {
        return menu;
    }
}

package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.menus.WindowMenu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import java.util.LinkedList;

public class UnitSelectionMenu extends Menu {

    private int totalPrice;
    private Label totalPriceLabel;
    private LinkedList<UnitMenu> unitMenus;

    public UnitSelectionMenu(GameMenu gameMenu, Skin skin) {

        totalPrice = 0;
        totalPriceLabel = new Label("0", skin);
        totalPriceLabel.setAlignment(Align.right);

        unitMenus = new LinkedList<>();

        unitMenus.add(new UnitMenu(this, "Soldier", skin));
        unitMenus.add(new UnitMenu(this, "Tank", skin));
        unitMenus.add(new UnitMenu(this, "Scout", skin));

        Window priceWindow = new WindowMenu("", skin).getWindow();

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ButtonCommand(new Command<GameMenu>(gameMenu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showPlayingMenu();
            }
        }));

        priceWindow.defaults().prefHeight(UI.BUTTON_HEIGHT);
        priceWindow.add(new Label("Total price", skin)).prefWidth(UI.BUTTON_SMALL_WIDTH);
        priceWindow.add(totalPriceLabel).expandX().grow();

        getMenu().defaults().spaceBottom(UI.SPACING).prefWidth(UI.BUTTON_WIDTH);
        for (UnitMenu unitMenu : unitMenus) {
            getMenu().add(unitMenu.getWindow());
            getMenu().row();
        }

        getMenu().add(priceWindow);
        getMenu().row();
        getMenu().add(closeButton).prefHeight(UI.BUTTON_HEIGHT);
    }

    public void updatePrice() {

        totalPrice = 0;
        for (UnitMenu unitMenu : unitMenus) {
            totalPrice += unitMenu.getCount(); // TODO: add unit price
        }

        totalPriceLabel.setText(totalPrice);
    }
}

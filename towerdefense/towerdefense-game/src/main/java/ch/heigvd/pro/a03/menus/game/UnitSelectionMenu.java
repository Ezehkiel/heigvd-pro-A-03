package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.menus.WindowMenu;
import ch.heigvd.pro.a03.utils.UI;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.sun.tools.javac.util.Pair;

import java.util.LinkedList;

public class UnitSelectionMenu extends Menu {

    private int totalPrice;
    private Label totalPriceLabel;
    private UnitMenu[] unitMenus;
    private TextButton sendButton;

    public UnitSelectionMenu(GameMenu gameMenu, Skin skin) {

        totalPrice = 0;
        totalPriceLabel = new Label("0", skin);
        totalPriceLabel.setAlignment(Align.right);

        unitMenus = new UnitMenu[] {
                new UnitMenu(this, "Soldier", WarEntityType.UnitType.SOLIDER, skin),
                new UnitMenu(this, "Tank", WarEntityType.UnitType.TANK, skin),
                new UnitMenu(this, "Scout", WarEntityType.UnitType.SCOOT, skin)
        };

        Window priceWindow = new WindowMenu(skin).getWindow();

        sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ButtonCommand(new Command<GameMenu>(gameMenu) {
            @Override
            public void execute(Object... args) {

                System.out.println("Clicked!");
                WarEntityType.UnitType[] types = new WarEntityType.UnitType[unitMenus.length];
                int[] quantities = new int[unitMenus.length];

                for (int i = 0; i < types.length; ++i) {
                    types[i] = unitMenus[i].UNIT_TYPE;
                    quantities[i] = unitMenus[i].getCount();
                }

                if (!gameMenu.sendUnits(types, quantities)) {
                    System.out.println("Not enough money.");
                    return;
                }
                System.out.println("Units sent.");

                for (UnitMenu unitMenu: unitMenus) {
                    unitMenu.disable();
                }

                sendButton.setVisible(false);

                getReceiver().showPlayingMenu();
            }
        }));

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
        getMenu().add(sendButton).prefHeight(UI.BUTTON_HEIGHT);
        getMenu().row();
        getMenu().add(closeButton).prefHeight(UI.BUTTON_HEIGHT);
    }

    public void updatePrice() {

        totalPrice = 0;
        for (UnitMenu unitMenu : unitMenus) {
            totalPrice += unitMenu.getCount() * unitMenu.PRICE;
        }

        totalPriceLabel.setText(totalPrice);
    }

    public void reset() {
        for (UnitMenu unitMenu : unitMenus) {
            unitMenu.reset();
        }
        updatePrice();
        sendButton.setVisible(true);
    }
}

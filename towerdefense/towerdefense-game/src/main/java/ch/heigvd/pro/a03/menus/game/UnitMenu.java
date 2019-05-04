package ch.heigvd.pro.a03.menus.game;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.scenes.MacroCommand;
import ch.heigvd.pro.a03.menus.WindowMenu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class UnitMenu extends WindowMenu {

    private int count;
    private Label countLabel;

    public UnitMenu(UnitSelectionMenu parent, String name, Skin skin) {
        super("", skin);

        Label nameLabel = new Label(name, skin);

        Command<UnitSelectionMenu> updateParentCommand = new Command<UnitSelectionMenu>(parent) {
            @Override
            public void execute(Object... args) {
                getReceiver().updatePrice();
            }
        };

        Button plusButton = new Button(skin, "arrow-up");
        plusButton.addListener(new ButtonCommand(new MacroCommand(
                new Command<UnitMenu>(this) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().addUnit();
                    }
                },
                updateParentCommand
        )));

        Button minusButton = new Button(skin, "arrow-down");
        minusButton.addListener(new ButtonCommand(new MacroCommand(
                new Command<UnitMenu>(this) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().removeUnit();
                    }
                },
                new Command<UnitSelectionMenu>(parent) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().updatePrice();
                    }
                }
        )));

        count = 0;
        countLabel = new Label("0", skin);
        countLabel.setAlignment(Align.right);

        VerticalGroup group = new VerticalGroup();
        group.align(Align.center);
        group.addActor(plusButton);
        group.addActor(minusButton);

        getWindow().defaults().prefHeight(UI.BUTTON_HEIGHT);
        getWindow().add(nameLabel).prefWidth(UI.BUTTON_SMALL_WIDTH);
        getWindow().add(group);
        getWindow().add(countLabel).expandY().grow();
    }

    private void updateCountLabel() {
        countLabel.setText(count);
    }

    public void addUnit() {
        count++;
        updateCountLabel();
    }

    public void removeUnit() {

        if (count <= 0) {
            return;
        }

        count--;
        updateCountLabel();
    }

    public int getCount() {
        return count;
    }
}

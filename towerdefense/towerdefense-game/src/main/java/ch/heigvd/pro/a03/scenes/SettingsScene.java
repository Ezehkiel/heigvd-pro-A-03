package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.utils.Config;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * The settings scene
 */
public class SettingsScene extends Scene {

    private TextField offlineIpField;
    private TextField offlinePortField;
    private UI.Theme theme;

    /**
     * Creates the scene
     */
    public SettingsScene() {

        Table table = new Table();
        table.setFillParent(true);

        // Game server ip + port
        offlineIpField = new TextField(Config.getOfflineIp(), getSkin());
        offlinePortField = new TextField(String.valueOf(Config.getOfflinePort()), getSkin());

        TextButton musicToggle = new TextButton(Config.getMusicOn() ? "Music on" : "Music off", getSkin());
        musicToggle.addListener(new ButtonCommand(args -> {
            boolean on = !Config.getMusicOn();
            Config.setMusicOn(on);
            musicToggle.setText(on ? "Music on" : "Music off");
        }));

        theme = UI.Theme.fromName(Config.getTheme());
        TextButton themeToggle = new TextButton("Theme: " + theme.NAME, getSkin());
        themeToggle.addListener(new ButtonCommand(args -> {

            theme = theme.next();
            Config.setProperty(Config.THEME_PATH, theme.NAME);
            themeToggle.setText("Theme: " + theme.NAME);
        }));

        TextButton restoreDefaultsButton = new TextButton("Restore defaults", getSkin());
        restoreDefaultsButton.addListener(new ButtonCommand(args -> {
            Config.restoreDefaults();
            GameLauncher.getInstance().getSceneManager().set(new MainMenuScene());
        }));

        TextButton saveButton = new TextButton("Save", getSkin());
        saveButton.addListener(new ButtonCommand(args -> {
            if (saveSettings()) {
                GameLauncher.getInstance().getSceneManager().set(new MainMenuScene());
            }
        }));

        table.defaults();
        table.add(new Label("Offline game server address", getSkin())).prefWidth(UI.BUTTON_WIDTH);
        table.row();
        table.add(offlineIpField).prefWidth(UI.BUTTON_WIDTH)
                .prefHeight(UI.TEXT_FIELD_HEIGHT)
                .spaceBottom(UI.SPACING);
        table.row();
        table.add(new Label("Offline game server port", getSkin())).prefWidth(UI.BUTTON_WIDTH);
        table.row();
        table.add(offlinePortField).prefWidth(UI.BUTTON_WIDTH)
                .prefHeight(UI.TEXT_FIELD_HEIGHT)
                .spaceBottom(UI.SPACING);
        table.row();
        table.add(musicToggle).prefWidth(UI.BUTTON_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT)
                .spaceBottom(UI.SPACING);
        table.row();
        table.add(themeToggle).prefWidth(UI.BUTTON_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT)
                .spaceBottom(UI.SPACING);
        table.row();
        table.add(restoreDefaultsButton).prefWidth(UI.BUTTON_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT)
                .spaceBottom(UI.SPACING);
        table.row();
        table.add(saveButton).prefWidth(UI.BUTTON_WIDTH)
                .prefHeight(UI.BUTTON_HEIGHT)
                .spaceBottom(UI.SPACING);
        table.row();

        getStage().addActor(table);
    }

    @Override
    public void leave() {
        super.leave();
        GameLauncher.getInstance().updateMusic();
    }

    /**
     * Saves and stores the settings
     * @return true if saved
     */
    private boolean saveSettings() {
        try {
            Config.setProperty(Config.OFFLINE_IP_PATH, offlineIpField.getText());
            Config.setProperty(Config.OFFLINE_PORT_PATH, String.valueOf(Integer.parseInt(offlinePortField.getText())));
            Config.store();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}

package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.GameLauncher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.lwjgl.Sys;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Manages the applications configurations
 */
public class Config {

    private static final String defaultFileName = "configs/default-config.properties";
    private static final String fileName = "configs/config.properties";

    public static final String SERVER_IP_PATH = "server-ip";
    public static final String SERVER_PORT_PATH = "server-port";

    public static final String OFFLINE_IP_PATH = "offline-ip";
    public static final String OFFLINE_PORT_PATH = "offline-port";

    public static final String HTTP_IP_PATH = "http-ip";
    public static final String HTTP_PORT_PATH = "http-port";

    public static final String MUSIC_ON_PATH = "music-on";

    public static final String THEME_PATH = "theme";

    private static Config instance;

    private Properties properties;

    /**
     * Creates the config manager.
     */
    private Config() {

        try {
            Properties defaults = new Properties();

            FileHandle defaultFileHandle = getDefaultsFileHandle();
            defaults.load(defaultFileHandle.reader());

            properties = new Properties(defaults);

            FileHandle fileHandle = getFileHandle();
            if (fileHandle.exists()) {
                properties.load(fileHandle.reader());
            }

        } catch (IOException e) {
            e.printStackTrace();
            GameLauncher.getInstance().exit();
        }
    }

    /**
     * Gets the instance of the singleton.
     * @return config manager
     */
    private static Config getInstance() {

        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Gets a config property
     * @param path path of the property
     * @return value of the property
     */
    public static String getProperty(String path) {
        return getInstance().properties.getProperty(path);
    }

    /**
     * Gets the game server's address ip
     * @return string with the address ip
     */
    public static String getServerIp() {
        return getProperty(SERVER_IP_PATH);
    }

    /**
     * Gets the game server's port
     * @return server's port
     */
    public static int getServerPort() {
        return Integer.valueOf(getProperty(SERVER_PORT_PATH));
    }

    /**
     * Gets the offline game server's address ip
     * @return string with the address ip
     */
    public static String getOfflineIp() {
        return getProperty(OFFLINE_IP_PATH);
    }

    /**
     * Gets the offline game server's port
     * @return server's port
     */
    public static int getOfflinePort() {
        return Integer.valueOf(getProperty(OFFLINE_PORT_PATH));
    }

    /**
     * Gets the url of the http server
     * @return string with the address ip
     */
    public static String getHttpUrl() {
        return getProperty(HTTP_IP_PATH) + ":" + getProperty(HTTP_PORT_PATH);
    }

    /**
     * Gets the music on property
     * @return true if the music is on
     */
    public static boolean getMusicOn() {
        return Boolean.valueOf(getProperty(MUSIC_ON_PATH));
    }

    /**
     * Sets the stat of the music
     * @param on ture if music should turn on.
     */
    public static void setMusicOn(boolean on) {
        setProperty(MUSIC_ON_PATH, String.valueOf(on));
    }

    /**
     * Gets the theme name
     * @return theme name
     */
    public static String getTheme() {
        return getProperty(THEME_PATH);
    }

    /**
     * Sets a value of a property
     * @param path the path of the property
     * @param value the value of the property
     */
    public static void setProperty(String path, String value) {
        getInstance().properties.setProperty(path, value);
    }

    /**
     * Restores to the default values.
     */
    public static void restoreDefaults() {
        try {
            getInstance().properties.load(getInstance().getDefaultsFileHandle().reader());
            store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the modification in the user config file.
     */
    public static void store() {
        try {
            FileHandle fileHandle = getInstance().getFileHandle();
            if (!fileHandle.exists()
                    && !fileHandle.file().getParentFile().mkdirs()
                    && fileHandle.file().createNewFile()) {
                return;
            }

            getInstance().properties.store(
                    fileHandle.writer(false),
                    null
            );

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets the default file.
     * @return file handle
     */
    private FileHandle getDefaultsFileHandle() {
        return Gdx.files.internal(defaultFileName);
    }

    /**
     * Gets the user file.
     * @return file handle
     */
    private FileHandle getFileHandle() {
        return Gdx.files.local(fileName);
    }
}

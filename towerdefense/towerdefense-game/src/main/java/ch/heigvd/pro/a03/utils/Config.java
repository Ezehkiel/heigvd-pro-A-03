package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.GameLauncher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

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

    private static Config getInstance() {

        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static String getProperty(String path) {
        return getInstance().properties.getProperty(path);
    }

    public static String getServerIp() {
        return getProperty(SERVER_IP_PATH);
    }

    public static int getServerPort() {
        return Integer.valueOf(getProperty(SERVER_PORT_PATH));
    }

    public static String getOfflineIp() {
        return getProperty(OFFLINE_IP_PATH);
    }

    public static int getOfflinePort() {
        return Integer.valueOf(getProperty(OFFLINE_PORT_PATH));
    }

    public static String getHttpUrl() {
        return getProperty(HTTP_IP_PATH) + ":" + getProperty(HTTP_PORT_PATH);
    }

    public static boolean getMusicOn() {
        return Boolean.valueOf(getProperty(MUSIC_ON_PATH));
    }

    public static void setMusicOn(boolean on) {
        setProperty(MUSIC_ON_PATH, String.valueOf(on));
    }

    public static String getTheme() {
        return getProperty(THEME_PATH);
    }

    public static void setProperty(String path, String value) {
        getInstance().properties.setProperty(path, value);
    }

    public static void restoreDefaults() {
        try {
            getInstance().properties.load(getInstance().getDefaultsFileHandle().reader());
            store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void store() {
        try {
            FileHandle fileHandle = getInstance().getFileHandle();
            if (!fileHandle.exists() || fileHandle.file().createNewFile()) {
                getInstance().properties.store(
                        fileHandle.writer(false),
                        null
                );
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private FileHandle getDefaultsFileHandle() {
        return Gdx.files.internal(defaultFileName);
    }

    private FileHandle getFileHandle() {
        return Gdx.files.local(fileName);
    }
}

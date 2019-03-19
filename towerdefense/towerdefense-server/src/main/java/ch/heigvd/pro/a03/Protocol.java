package ch.heigvd.pro.a03;

public class Protocol {
    public final static String CMD_CREATE = "CREATE";
    public final static String CMD_START = "START";
    public final static String CMD_COMMANDS = "COMMANDS";

    public final static String CMD_HIGH_SCORE="HIGH_SCORE";
    public final static String CMD_CONNECT = "CONNECT";
    public final static String CMD_BYE="BYE";
    public final static String[] SUPPORTED_COMMANDS = new String[]{CMD_BYE,CMD_CREATE,
                                    CMD_CONNECT,CMD_HIGH_SCORE,CMD_START,CMD_COMMANDS};
}

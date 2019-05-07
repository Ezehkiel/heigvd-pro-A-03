package ch.heigvd.pro.a03.utils;

import java.io.*;

public class Protocole {

    private int id;
    private String data;

    public Protocole(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public static Protocole receive(BufferedReader in) throws IOException {
        String response = in.readLine();

        String[]responseArray = response.split("-");

        return new Protocole(Integer.parseInt(responseArray[0]),responseArray[1]);
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public static void sendProtocol(BufferedWriter out, int protocolId, String data) {

        try {
            out.write(String.format("%03d-%s\r\n", protocolId * 100, data));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

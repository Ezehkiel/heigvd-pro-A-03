package ch.heigvd.pro.a03.utils;

import java.io.BufferedReader;
import java.io.IOException;

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
}

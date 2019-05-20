package ch.heigvd.pro.a03.utils;

import java.io.*;
import java.net.SocketException;

public class Protocole {

    private int id;
    private String data;

    public Protocole(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public static Protocole receive(BufferedReader in) throws IOException {
        String response = in.readLine();

        if (response == null) {
            throw new SocketException("Client disconnected");
        }

        String[]responseArray = response.split("-");

        Protocole p = new Protocole(Integer.parseInt(responseArray[0]),responseArray[1]);
        if(p.data.equals("CLIENTDISCONNECTED")){
            throw new SocketException("Client disconnected");
        }

        return p;
    }

    public static String receiveJson(BufferedReader in) throws SocketException {
        try {
            return in.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new SocketException("Client disconnected");
        }
    }

    public static void sendJson(String json, BufferedWriter out) throws SocketException {
        try {
            out.write(json + "\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SocketException("Client disconnected");

        }
    }

    public static void sendProtocol(BufferedWriter out, int protocolId, String data) throws SocketException {

        try {
            out.write(String.format("%03d-%s\r\n", protocolId * 100, data));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            throw new SocketException("Socket Disconnected");
        }
    }

    /**
     * Send a specific object to spesific user
     * @param ous
     * @param object
     */
    public static void sendObject(ObjectOutputStream ous, Object object) throws SocketException {
        try {
            ous.writeObject(object);
            ous.flush();
//            ous.reset();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SocketException("Client disconnected");

        }
    }


    public static Object readObject(ObjectInputStream ois) throws SocketException {
        Object o = null;
        try {
            o =ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SocketException("Client disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }
}

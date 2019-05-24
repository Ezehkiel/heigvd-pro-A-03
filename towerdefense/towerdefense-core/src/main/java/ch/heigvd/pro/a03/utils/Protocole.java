package ch.heigvd.pro.a03.utils;

import java.io.*;
import java.net.SocketException;

/**
 * The class that provide all the usseful function to transfert data to the client or server
 * @Author Didier Page
 */
public class Protocole {

    private int id;
    private String data;

    /**
     * Constructor
     * @param id the state id
     * @param data the content of the message
     */
    public Protocole(int id, String data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Getter for the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the data
     * @return get the centent
     */
    public String getData() {
        return data;
    }

    /**
     * Function that recieve a protocole message through a buffered reader
     * @param in the inputstream
     * @return the protocol
     * @throws IOException
     */
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

    /**
     * Function used to read a json (string) in the input stream
     * @param in inputstream
     * @return the json as string
     * @throws SocketException
     */
    public static String receiveJson(BufferedReader in) throws SocketException {
        try {
            return in.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new SocketException("Client disconnected");
        }
    }

    /**
     * Function to send a json as a string through a BufferedWriter
     * @param json the json to send
     * @param out the outputStream
     * @throws SocketException
     */
    public static void sendJson(String json, BufferedWriter out) throws SocketException {
        try {
            out.write(json + "\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SocketException("Client disconnected");

        }
    }

    /**
     * Function to send a protocol trough the outputstream
     * @param out the bufferdWritter
     * @param protocolId the id of the current state
     * @param data the data to send
     * @throws SocketException
     */
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
     * Send a specific object through ObjectOutputstream
     * @param ous the outputstream
     * @param object the object to send
     */
    public static void sendObject(ObjectOutputStream ous, Object object) throws SocketException {
        try {
            ous.writeObject(object);
            ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SocketException("Client disconnected");

        }
    }

    /**
     * Read an object in the ObjectInputstream
     * @param ois the Objectputstream
     * @return the object
     * @throws SocketException
     */
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

package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.player.PlayerEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;

public class Test {

    public static void main(String ... args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 4567);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        LinkedList<PlayerEvent> a = (LinkedList<PlayerEvent>) ois.readObject();
        System.out.println(a);

    }
}

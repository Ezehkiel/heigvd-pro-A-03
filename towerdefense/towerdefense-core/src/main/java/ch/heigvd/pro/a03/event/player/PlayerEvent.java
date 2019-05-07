package ch.heigvd.pro.a03.event.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public abstract class PlayerEvent implements Serializable {
    int entityId;

    public PlayerEvent(int entityId) {
        this.entityId = entityId;
    }

    public static LinkedList<PlayerEvent> getEvents(ObjectInputStream in){
        LinkedList<PlayerEvent> events =null;
        try{
            events = (LinkedList<PlayerEvent>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  events;
    }
    public static PlayerEvent getEvent(ObjectInputStream in) {
        PlayerEvent event = null;
        try {
            event = (PlayerEvent) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return event;
    }
    public static void sendEvent(PlayerEvent playerEvent, ObjectOutputStream out) {
        try {
            out.writeObject(playerEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendEvents(LinkedList<PlayerEvent> playerEvents, ObjectOutputStream out) {
        for (PlayerEvent playerEvent : playerEvents)
            sendEvent(playerEvent,out);
    }

    @Override
    public String toString() {
        return "PlayerEvent{" +
                "entityId=" + entityId +
                '}';
    }
}

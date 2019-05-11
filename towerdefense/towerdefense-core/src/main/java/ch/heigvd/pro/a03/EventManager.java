package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.Event;

import java.util.LinkedList;

public class EventManager {

    private static EventManager em;
    private LinkedList<Event> events;

    private EventManager() {

        events=new LinkedList<>();
    }

    public static EventManager getInstance() {
        if (em == null) {
            em = new EventManager();
        }

        return em;
    }

    public void addEvent(Event e){
        events.add(e);
    }

    public LinkedList<Event> getEvents() {
        return events;
    }

    public void clearEvents(){
        events.clear();
    }
}

package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.simulation.SimEvent;

import java.util.LinkedList;

public class EventManager {

    private static EventManager em;
    private LinkedList<SimEvent> events;

    private EventManager() {

        events=new LinkedList<>();
    }

    public static EventManager getInstance() {
        if (em == null) {
            em = new EventManager();
        }

        return em;
    }

    public void addEvent(SimEvent e){
        events.add(e);
    }

    public LinkedList<SimEvent> getEvents() {
        return events;
    }

    public void clearEvents(){
        events = new LinkedList<>();
    }
}

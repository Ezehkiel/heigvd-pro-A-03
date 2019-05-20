package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.simulation.SimEvent;
import java.util.LinkedList;

/***
 * Class adopting the Singleton patter in order to manage the events of a match
 * @author Andres Moreno
 */

public class EventManager {

    private static EventManager em;
    private LinkedList<SimEvent> events;

    /***
     * private constructor
     */
    private EventManager() {

        events=new LinkedList<>();
    }

    /***
     * Creates a instance of the class if there isn't one already
     * @return the instance
     */
    public static EventManager getInstance() {
        if (em == null) {
            em = new EventManager();
        }

        return em;
    }

    /***
     *
     * @return a list with the simulation events.
     */
    public LinkedList<SimEvent> getEvents() {
        return events;
    }

    /***
     * Add's a event to the simulation list
     * @param e the event
     */
    public void addEvent(SimEvent e){
        events.add(e);
    }



    /***
     * clears the list with the events
     */
    public void clearEvents(){
        events = new LinkedList<>();
    }
}

package de.jooarye.test;

import de.jooarye.eventmanager.EventManager;
import de.jooarye.eventmanager.event.Event;
import de.jooarye.eventmanager.types.EventTarget;
import de.jooarye.eventmanager.types.Priority;

public class Main {

    private int maxAmount;

    public static void main(String[] args) {
        EventManager.register(new Main());
        EventManager.call(new EventTest());
    }

    @EventTarget(priority = Priority.HIGH)
    public void onTest(EventTest event) {
        System.out.println("HIGH");
    }

    @EventTarget(priority = Priority.MIDDLE)
    public void onTest2(EventTest event) {
        System.out.println("MIDDLE");
    }

    @EventTarget(priority = Priority.LOW)
    public void onTest3(EventTest event) {
        System.out.println("LOW");
    }

    static class EventTest implements Event {
    }

}

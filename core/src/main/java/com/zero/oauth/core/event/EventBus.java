package com.zero.oauth.core.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private static volatile EventBus instance;
    private final Map<Class<?>, List<Class<?>>> a = new HashMap<>();

    public static EventBus instance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    /**
     * Registers the given subscriber to receive events.
     *
     * @param subscriber Subscriber
     */
    public void register(Object subscriber) {
    }

    /**
     * Unregisters the given subscriber from all event classes.
     *
     * @param subscriber Subscriber
     */
    public void unregister(Object subscriber) {

    }

    /**
     * Posts the given event to the event bus.
     *
     * @param event Event
     */
    public void post(Object event) {

    }

}

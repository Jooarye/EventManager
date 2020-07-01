package de.jooarye.eventmanager;

import de.jooarye.eventmanager.event.Event;
import de.jooarye.eventmanager.event.EventStoppable;
import de.jooarye.eventmanager.types.EventTarget;
import de.jooarye.eventmanager.types.MethodData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

    private static final HashMap<Class<? extends Event>, List<MethodData>> registryMap = new HashMap<>();

    public static Event call(final Event event) {
        List<MethodData> dataList = registryMap.get(event.getClass());

        if (dataList != null) {
            if (event instanceof EventStoppable) {
                EventStoppable stoppable = (EventStoppable) event;

                for (final MethodData data : dataList) {
                    invoke(data, event);

                    if (stoppable.isStopped())
                        break;
                }
            } else {
                for (final MethodData data : dataList) {
                    invoke(data, event);
                }
            }
        }

        return event;
    }

    private static void invoke(MethodData data, Event event) {
        try {
            data.getTarget().invoke(data.getSource(), event);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    public static void register(Object obj) {
        for (final Method method : obj.getClass().getDeclaredMethods()) {
            if (!isValidMethod(method))
                continue;

            register(method, obj);
        }
    }

    public static void unregister(Object obj) {
        for (final List<MethodData> dataList : registryMap.values()) {
            for (final MethodData data : dataList) {
                if (data.getSource().equals(obj))
                    dataList.remove(data);
            }
        }
    }

    public static void unregister(Method method, Object obj) {
        for (final List<MethodData> dataList : registryMap.values()) {
            for (final MethodData data : dataList) {
                if (data.getSource().equals(obj) && data.getTarget().equals(method))
                    dataList.remove(data);
            }
        }
    }

    public static void register(Method method, Object object) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];

        final MethodData data = new MethodData(object, method, method.getAnnotation(EventTarget.class).priority());

        if (!data.getTarget().isAccessible())
            data.getTarget().setAccessible(true);

        if (registryMap.containsKey(indexClass)) {
            if (!registryMap.get(indexClass).contains(data)) {
                registryMap.get(indexClass).add(data);
            }
        } else {
            registryMap.put(indexClass, new CopyOnWriteArrayList<MethodData>());
            registryMap.get(indexClass).add(data);
        }

        registryMap.get(indexClass).sort((a, b) -> b.getPriority().id - a.getPriority().id);
    }

    private static boolean isValidMethod(Method method) {
        return method.isAnnotationPresent(EventTarget.class) && method.getParameterTypes().length == 1;
    }

}

package de.jooarye.eventmanager.types;

import de.jooarye.eventmanager.types.Priority;

import java.lang.reflect.Method;

public class MethodData {

    private final Object source;
    private final Method target;
    private final Priority priority;

    public MethodData(Object source, Method target, Priority priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }

    public Object getSource() {
        return source;
    }

    public Method getTarget() {
        return target;
    }

    public Priority getPriority() {
        return priority;
    }
}

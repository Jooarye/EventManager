package de.jooarye.eventmanager.types;

public enum Priority {
    HIGH(3), MIDDLE(2), LOW(1);

    public int id;

    Priority(int id) {
        this.id = id;
    }

}

package de.jooarye.eventmanager;

public enum Priority {
    HIGH(3), MIDDLE(2), LOW(1);

    int id;

    public Priority(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

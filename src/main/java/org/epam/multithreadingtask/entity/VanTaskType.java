package org.epam.multithreadingtask.entity;

public enum VanTaskType {
    LOAD(1), UNLOAD(2);

    private int value;

    private VanTaskType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

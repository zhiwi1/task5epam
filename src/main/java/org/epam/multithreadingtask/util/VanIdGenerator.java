package org.epam.multithreadingtask.util;


public class VanIdGenerator {
    private static long count;

    private VanIdGenerator() {

    }

    public static long generateId() {
        return ++count;
    }
}

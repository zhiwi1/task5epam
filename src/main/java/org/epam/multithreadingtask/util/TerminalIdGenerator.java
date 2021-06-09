package org.epam.multithreadingtask.util;

public class TerminalIdGenerator {
    private static long count;

    private TerminalIdGenerator() {

    }

    public static long generateId() {
        return ++count;
    }
}

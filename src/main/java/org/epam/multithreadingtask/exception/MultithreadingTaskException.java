package org.epam.multithreadingtask.exception;

public class MultithreadingTaskException extends Exception {
    public MultithreadingTaskException() {
        super();
    }

    public MultithreadingTaskException(String message) {
        super(message);
    }

    public MultithreadingTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultithreadingTaskException(Throwable cause) {
        super(cause);
    }
}


package org.epam.multithreadingtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.multithreadingtask.util.TerminalIdGenerator;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Terminal {
    private final static Lock lock = new ReentrantLock();
    private final static Logger logger = LogManager.getLogger();
    private final static int TIME_TO_SLEEP = 3;
    private long id;
    private Condition condition = lock.newCondition();

    public Terminal() {
        this.id = TerminalIdGenerator.generateId();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Van process(Van van) {

        lock.lock();
        int countOfContainers = van.getCountOfContainers();
        van.setState(VanState.PROCESSING);
        long vanId = van.getVanId();
        logger.info("Terminal " + id + " started processing van " + vanId);
        LogisticBase base = LogisticBase.getInstance();
        if (van.getVanTaskType() == VanTaskType.LOAD) {
            base.addContainersInBase(countOfContainers);
        } else base.removeContainersInBase(countOfContainers);
        try {
            TimeUnit.MILLISECONDS.sleep(TIME_TO_SLEEP);
        } catch (InterruptedException e) {
            logger.error("Caught an exception: ", e);
            Thread.currentThread().interrupt();
        } finally {
            van.setState(VanState.FINISHED);
            logger.info("Terminal " + id + " finished processing van " + vanId);
            lock.unlock();
        }
        return van;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal terminal = (Terminal) o;
        return id == terminal.id && Objects.equals(condition, terminal.condition);
    }

    @Override
    public int hashCode() {
        int result = (int) (this.id ^ (this.id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Terminal{");
        sb.append("id=").append(id);
        sb.append(", condition=").append(condition);
        sb.append('}');
        return sb.toString();
    }
}

package org.epam.multithreadingtask.entity;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.multithreadingtask.exception.MultithreadingTaskException;
import org.epam.multithreadingtask.parser.DataParser;
import org.epam.multithreadingtask.parser.impl.DataParserImpl;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private final static DataParser parser = new DataParserImpl();
    private final static String RELATIVE_PATH_TO_PROPERTIES = "\\base";
    private static ResourceBundle logisticsBaseProperties;
    private final static Logger logger = LogManager.getLogger();
    private Queue<Terminal> freeTerminals = new ArrayDeque<>();
    private Queue<Terminal> occupiedTerminals = new ArrayDeque<>();
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int maxValueOfTerminals;
    private AtomicInteger containersInBase;

    {
        try {
            logisticsBaseProperties = ResourceBundle.getBundle(RELATIVE_PATH_TO_PROPERTIES);
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, "Can't find resource" + RELATIVE_PATH_TO_PROPERTIES + " " + "is not correct");
            logger.log(Level.ERROR, e.getStackTrace());
        }
    }


    private LogisticBase() {
        try {
            int[] properties = parser.parseResources(logisticsBaseProperties);
            this.maxValueOfTerminals = properties[1];
            this.containersInBase=new AtomicInteger(0);
            for (int i = 0; i < properties[1]; i++) {
                freeTerminals.add(new Terminal());
            }
        } catch (MultithreadingTaskException e) {
            logger.error(e.getMessage());
        }
    }


    private static class LazyBaseHolder {
        public static LogisticBase singletonInstance = new LogisticBase();
    }

    public static LogisticBase getInstance() {
        return LazyBaseHolder.singletonInstance;
    }

    public AtomicInteger getContainersInBase() {
        return containersInBase;
    }

    public void addContainersInBase(int countOfContainers) {
        containersInBase.addAndGet(countOfContainers);
    }

    public void removeContainersInBase(int countOfContainers){containersInBase.addAndGet(-countOfContainers);}

    public int getMaxValueOfTerminals() {
        return maxValueOfTerminals;
    }

    public void setMaxValueOfTerminals(int maxValueOfTerminals) {
        this.maxValueOfTerminals = maxValueOfTerminals;
    }

    public int getCountOfFreeTerminals() {
        return freeTerminals.size();
    }

    public Terminal getFreeTerminal() {
        Terminal terminal;
        lock.lock();
        while (freeTerminals.isEmpty()) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                logger.log(Level.DEBUG, "Thread was interrupted: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        try {
            terminal = freeTerminals.poll();
        } finally {
            lock.unlock();
        }
        lock.lock();
        try {
            occupiedTerminals.add(terminal);
        } finally {
            lock.unlock();
        }
        return terminal;
    }

    public void releaseOccupiedTerminal(Terminal terminal) {
        lock.lock();
        try {
            occupiedTerminals.remove(terminal);
        } finally {
            lock.unlock();
        }
        try {
            lock.lock();
            freeTerminals.add(terminal);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticBase base = (LogisticBase) o;
        return maxValueOfTerminals == base.maxValueOfTerminals && Objects.equals(freeTerminals, base.freeTerminals) && Objects.equals(occupiedTerminals, base.occupiedTerminals) && Objects.equals(condition, base.condition) && Objects.equals(containersInBase, base.containersInBase);
    }

    @Override
    public int hashCode() {
        int result=1;
        result=17*result+freeTerminals.hashCode();
        result=17*result+occupiedTerminals.hashCode();
        result=17*result+maxValueOfTerminals;
        result=17*result+containersInBase.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogisticBase{");
        sb.append("freeTerminals=").append(freeTerminals);
        sb.append(", occupiedTerminals=").append(occupiedTerminals);
        sb.append(", condition=").append(condition);
        sb.append(", maxValueOfTerminals=").append(maxValueOfTerminals);
        sb.append(", containersInBase=").append(containersInBase);
        sb.append('}');
        return sb.toString();
    }
}

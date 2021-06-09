package org.epam.multithreadingtask.entity;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.multithreadingtask.util.VanIdGenerator;

import java.util.concurrent.Callable;


public class Van implements Callable<Van> {
    private final static Logger logger = LogManager.getLogger();
    private long vanId;
    private boolean isPerishable;
    private int countOfContainers;
    private VanState state;
    private VanTaskType vanTaskType;

    public Van(boolean isPerishable, int countOfContainers, VanTaskType vanTaskType) {
        this.vanId = VanIdGenerator.generateId();
        this.isPerishable = isPerishable;
        this.countOfContainers = countOfContainers;
        this.state = VanState.NEW;
        this.vanTaskType=vanTaskType;
    }

    public long getVanId() {
        return vanId;
    }

    public void setVanId(long vanId) {
        this.vanId = vanId;
    }

    public boolean isPerishable() {
        return isPerishable;
    }

    public void setPerishable(boolean perishable) {
        isPerishable = perishable;
    }

    public int getCountOfContainers() {
        return countOfContainers;
    }

    public void setCountOfContainers(int countOfContainers) {
        this.countOfContainers = countOfContainers;
    }

    public VanState getState() {
        return state;
    }

    public void setState(VanState state) {
        this.state = state;
    }

    public VanTaskType getVanTaskType() {
        return vanTaskType;
    }

    public void setVanTaskType(VanTaskType vanTaskType) {
        this.vanTaskType = vanTaskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Van van = (Van) o;
        return vanId == van.vanId && isPerishable == van.isPerishable && countOfContainers == van.countOfContainers && state == van.state;
    }

    @Override
    public int hashCode() {

        int result = 13;
        result = 31 * result + (int) (this.vanId ^ (this.vanId >>> 32));
        result = 31 * result + countOfContainers;
        result = 31 * result + (isPerishable ? 17 : 19);
        result = 31 * result + (state == null ? 0 : state.hashCode());

        return result;


    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Van{");
        sb.append("vanId=").append(vanId);
        sb.append(", isPerishable=").append(isPerishable);
        sb.append(", countOfBoxes=").append(countOfContainers);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Van call() {
        LogisticBase base = LogisticBase.getInstance();
        Terminal terminal = base.getFreeTerminal();
        Van van = terminal.process(this);
        base.releaseOccupiedTerminal(terminal);
        return van;
    }
}

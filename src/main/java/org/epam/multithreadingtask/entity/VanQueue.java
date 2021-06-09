package org.epam.multithreadingtask.entity;

import org.epam.multithreadingtask.comparator.QueueVanComparator;

import java.util.*;

public class VanQueue {

    private Queue<Van> vansOutsideBase;

    public VanQueue(List<Van> deliveryVans) {
        vansOutsideBase = new PriorityQueue<>(new QueueVanComparator());
        vansOutsideBase.addAll(deliveryVans);
    }

    public void add(Van deliveryVan) {
        vansOutsideBase.add(deliveryVan);
    }

    public void addAll(Collection<Van> collection) {
        collection.forEach(this::add);
    }

    public Van pool() {
        return vansOutsideBase.poll();
    }

    public boolean isEmpty() {
        return vansOutsideBase.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VanQueue queue = (VanQueue) o;
        return vansOutsideBase.equals( queue.vansOutsideBase);
    }

    @Override
    public int hashCode() {
        return vansOutsideBase.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VanQueue{");
        sb.append("vansOutsideBase=").append(vansOutsideBase);
        sb.append('}');
        return sb.toString();
    }
}
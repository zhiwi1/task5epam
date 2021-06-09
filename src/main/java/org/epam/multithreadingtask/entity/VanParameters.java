package org.epam.multithreadingtask.entity;

import java.util.Objects;

public class VanParameters {
    private boolean isPerishable;
    private int countOfContainers;
    private int type;

    public VanParameters(boolean isPerishable, int countOfContainers, int type) {
        this.isPerishable = isPerishable;
        this.countOfContainers = countOfContainers;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VanParameters that = (VanParameters) o;
        return isPerishable == that.isPerishable && countOfContainers == that.countOfContainers && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 13 + (isPerishable ? 1 : 0);
        result = result * 13 + countOfContainers;
        result = result * 13 + type;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VanParameters{");
        sb.append("isPerishable=").append(isPerishable);
        sb.append(", countOfContainers=").append(countOfContainers);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}

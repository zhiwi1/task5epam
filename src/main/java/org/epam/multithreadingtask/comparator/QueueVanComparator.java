package org.epam.multithreadingtask.comparator;


import org.epam.multithreadingtask.entity.Van;

import java.util.Comparator;

public class QueueVanComparator implements Comparator<Van> {
    @Override
    public int compare(Van firstVan, Van secondVan) {
        int result = 0;
        boolean type1 = firstVan.isPerishable();
        boolean type2 = secondVan.isPerishable();
        if (type1 && !type2) {
            result = -1;
        } else if (!type1 && type2) {
            result = 1;
        }
        return result;
    }
}

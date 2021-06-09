package org.epam.multithreadingtask.factory;


import org.epam.multithreadingtask.entity.Van;
import org.epam.multithreadingtask.entity.VanParameters;
import org.epam.multithreadingtask.entity.VanTaskType;

import java.util.List;

import java.util.stream.Collectors;


public class VanFactory {

    public Van createVan(VanParameters parameters) {
        if (parameters.getType() == 1) {
            return new Van(parameters.isPerishable(), parameters.getCountOfContainers(), VanTaskType.LOAD);
        } else {
            return new Van(parameters.isPerishable(), parameters.getCountOfContainers(), VanTaskType.UNLOAD);
        }
    }

    public List<Van> createListOfVans(List<VanParameters> parametersList) {
        return parametersList.stream().map(this::createVan).collect(Collectors.toList());
    }

}

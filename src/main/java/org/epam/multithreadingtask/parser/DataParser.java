package org.epam.multithreadingtask.parser;

import org.epam.multithreadingtask.entity.VanParameters;
import org.epam.multithreadingtask.exception.MultithreadingTaskException;

import java.util.List;
import java.util.ResourceBundle;

public interface DataParser {
    int[] parseResources(ResourceBundle resourceBundle) throws MultithreadingTaskException;
    List<VanParameters> parseListOfVanParams(List<String> list) throws MultithreadingTaskException;
    public VanParameters parseVanParams(String dataString) throws MultithreadingTaskException;
}

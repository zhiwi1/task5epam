package org.epam.multithreadingtask.parser.impl;

import org.epam.multithreadingtask.entity.VanParameters;
import org.epam.multithreadingtask.exception.MultithreadingTaskException;
import org.epam.multithreadingtask.parser.DataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DataParserImpl implements DataParser {
    private final static String WHITE_SPACE_DELIMITER_REGEXP = "\\s+";
    private final static String MAX_COUNT_OF_CONTAINERS_NAME = "COUNT_OF_CONTAINERS";
    private final static String COUNT_OF_TERMINALS_NAME = "COUNT_OF_TERMINALS";
    private final static String CURRENT_COUNT_OF_CONTAINERS_NAME = "CURRENT_COUNT_OF_CONTAINERS";

    @Override
    public List<VanParameters> parseListOfVanParams(List<String> list) throws MultithreadingTaskException {
        List<VanParameters> vanList = new ArrayList<>();
        for (String s : list) {
            vanList.add(parseVanParams(s));
        }
        return vanList;
    }

    @Override
    public VanParameters parseVanParams(String dataString) throws MultithreadingTaskException {
        String[] params = dataString.split(WHITE_SPACE_DELIMITER_REGEXP);
        boolean perishable;
        int countOfBoxes;
        int type;
        try {
            perishable = Boolean.parseBoolean(params[0]);
            countOfBoxes = Integer.parseInt(params[1]);
            type = Integer.parseInt(params[2]);
        } catch (NumberFormatException e) {
            throw new MultithreadingTaskException("Can't parse resources, check file base.properties");
        }
        return new VanParameters(perishable, countOfBoxes,type);
    }
    @Override
    public int[] parseResources(ResourceBundle resourceBundle) throws MultithreadingTaskException {
        int[] properties = new int[3];
        try {
            properties[0] = Integer.parseInt(resourceBundle.getString(MAX_COUNT_OF_CONTAINERS_NAME));
            properties[1] = Integer.parseInt(resourceBundle.getString(COUNT_OF_TERMINALS_NAME));
            properties[2] = Integer.parseInt(resourceBundle.getString(CURRENT_COUNT_OF_CONTAINERS_NAME));
        } catch (NumberFormatException e) {
            throw new MultithreadingTaskException("Can't parse resources, check file base.properties");
        }
        return properties;
    }
}

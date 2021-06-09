package org.epam.multithreadingtask.reader.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.multithreadingtask.reader.DataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReaderImpl implements DataReader {
    private final static Logger logger = LogManager.getLogger();
    private static DataReaderImpl instance;

    private DataReaderImpl() {
    }

    public static DataReaderImpl getInstance() {
        if (instance == null) {
            instance = new DataReaderImpl();
        }
        return instance;
    }

    @Override
    public List<String> readFromFile(String relativePath) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(relativePath))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            logger.info("Error in reading");
        }
        return list;
    }
}

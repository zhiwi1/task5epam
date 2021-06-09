package org.epam.multithreadingtask.reader;

import java.util.List;

public interface DataReader {
    List<String> readFromFile(String relativePath);
}

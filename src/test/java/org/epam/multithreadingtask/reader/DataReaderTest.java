package org.epam.multithreadingtask.reader;

import org.epam.multithreadingtask.reader.impl.DataReaderImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DataReaderTest {
    DataReader reader;
    private final static String RELATIVE_PATH_TO_DATA = "src/test/resources/data/data.txt";

    @BeforeTest
    public void initialize() {
        reader = DataReaderImpl.getInstance();
    }

    @Test
    public void readTest() {
        String expected = "[false 12 2, false 300 1, false 2 2, false 1 1, true 4 1, false 1 1]";
        String actual = reader.readFromFile(RELATIVE_PATH_TO_DATA).toString();
        Assert.assertEquals(actual,expected);
    }
}

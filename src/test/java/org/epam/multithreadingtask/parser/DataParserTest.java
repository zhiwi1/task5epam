package org.epam.multithreadingtask.parser;

import org.epam.multithreadingtask.entity.LogisticBase;
import org.epam.multithreadingtask.entity.VanParameters;
import org.epam.multithreadingtask.exception.MultithreadingTaskException;
import org.epam.multithreadingtask.parser.impl.DataParserImpl;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DataParserTest {
    DataParser parser = new DataParserImpl();

    @Test
    public void parseTest() throws MultithreadingTaskException {
        VanParameters actual = parser.parseVanParams("false 12 2");
        VanParameters expected = new VanParameters(false, 12, 2);
        Assert.assertEquals(actual, expected);
    }
}

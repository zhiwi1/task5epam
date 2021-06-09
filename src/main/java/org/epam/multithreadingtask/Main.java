package org.epam.multithreadingtask;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.multithreadingtask.entity.LogisticBase;
import org.epam.multithreadingtask.entity.Van;
import org.epam.multithreadingtask.entity.VanParameters;
import org.epam.multithreadingtask.entity.VanQueue;
import org.epam.multithreadingtask.exception.MultithreadingTaskException;
import org.epam.multithreadingtask.factory.VanFactory;
import org.epam.multithreadingtask.parser.DataParser;
import org.epam.multithreadingtask.parser.impl.DataParserImpl;
import org.epam.multithreadingtask.reader.DataReader;
import org.epam.multithreadingtask.reader.impl.DataReaderImpl;
import org.epam.multithreadingtask.service.VanTimerTaskService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private final static Logger logger = LogManager.getLogger();
    private final static int TIME_TO_SLEEP = 5;
    private final static int DELAY = 5;
    private final static int PERIOD = 5;
    private final static String RELATIVE_PATH_TO_DATA = "src/main/resources/data/data.txt";

    public static void main(String[] args) throws MultithreadingTaskException {
        DataReader reader = DataReaderImpl.getInstance();
        DataParser parser = new DataParserImpl();
        VanFactory factory = new VanFactory();
        List<String> lines = reader.readFromFile(RELATIVE_PATH_TO_DATA);
        List<VanParameters> parametersList = parser.parseListOfVanParams(lines);
        List<Van> vans = factory.createListOfVans(parametersList);
        VanQueue queue = new VanQueue(vans);
        LogisticBase logisticsBaseSingleton = LogisticBase.getInstance();
        logger.log(Level.INFO, logisticsBaseSingleton.getMaxValueOfTerminals());
        ExecutorService executorService = Executors.newFixedThreadPool(logisticsBaseSingleton.getMaxValueOfTerminals());
        while (logisticsBaseSingleton.getCountOfFreeTerminals() > 0 && !queue.isEmpty()) {
            executorService.submit(queue.pool());
        }
        executorService.shutdown();
        TimerTask timerTask = new VanTimerTaskService();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, DELAY, PERIOD);
        try {
            TimeUnit.SECONDS.sleep(TIME_TO_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
    }
}

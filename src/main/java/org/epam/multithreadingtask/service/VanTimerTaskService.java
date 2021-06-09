package org.epam.multithreadingtask.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.multithreadingtask.entity.LogisticBase;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class VanTimerTaskService extends TimerTask {
    private final static Logger logger = LogManager.getLogger();
    private final static int TIME_TO_VIEW_COUNT_OF_CONTAINERS = 2;
    private final LogisticBase base = LogisticBase.getInstance();

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(TIME_TO_VIEW_COUNT_OF_CONTAINERS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(base.getContainersInBase());
    }


}

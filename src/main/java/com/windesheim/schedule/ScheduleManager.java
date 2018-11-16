package com.windesheim.schedule;

import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Schedule management class
 *
 * @author Lucas Ouwens
 */
public class ScheduleManager {

    private ArrayList<Schedulable> scheduledFutures;

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static ScheduleManager schedulerManagerInstance = null;

    private ScheduleManager() {
        scheduledFutures = new ArrayList<>();
    }

    /**
     * ScheduleManager instance
     *
     * @return ScheduleManager
     */
    public static ScheduleManager getManagerInstance() {
        if (schedulerManagerInstance == null) {
            schedulerManagerInstance = new ScheduleManager();
        }

        return schedulerManagerInstance;
    }

    /**
     * Get the scheduled actions
     *
     * @return ArrayList containing Schedulable objects (implements the interface)
     */
    public synchronized ArrayList<Schedulable> getScheduled() {
        return scheduledFutures;
    }


}

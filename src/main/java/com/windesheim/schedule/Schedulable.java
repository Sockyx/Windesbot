package com.windesheim.schedule;

/**
 * Interface for registering schedulable data.
 * @see ScheduleManager for uses.
 *
 * @author Lucas Ouwens
 */
public interface Schedulable {

    /**
     * Blueprint method for the scheduling of actions.
     */
    public void schedule();

}

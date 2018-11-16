package com.windesheim.schedule;

/**
 * Interface for registering schedulable data.
 *
 * @author Lucas Ouwens
 * @see ScheduleManager for uses.
 */
public interface Schedulable {

    /**
     * Blueprint method for the scheduling of actions.
     */
    public void schedule();

}

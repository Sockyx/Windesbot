package com.windesheim.command;

/**
 * Interface for command execution
 *
 * @author Lucas Ouwens
 */
public interface CommandExecutionTemplate {

    /**
     * Method for the command execution of a command
     *
     * @param botCommand Command object
     * @return boolean
     */
    public boolean execute(Command botCommand);
}

package com.windesheim.command;

import java.util.HashMap;

/**
 * A registry of all the commands
 *
 * @author Lucas Ouwens
 */
public class CommandRegister {

    private static CommandRegister commandRegisterInstance = null;
    private HashMap<String, CommandExecutionTemplate> botCommands = null;

    /**
     * Private constructor to initialize the hashmap
     */
    private CommandRegister() {
        botCommands = new HashMap<>();
    }


    /**
     * Get access to the register through an instance
     *
     * @return CommandRegister
     */
    public static CommandRegister getRegister() {
        if (commandRegisterInstance == null) {
            commandRegisterInstance = new CommandRegister();
        }

        return commandRegisterInstance;
    }

    /**
     * Get the execution template for a specific command
     *
     * @param command String the command to be searched for
     * @return CommandExecutionTemplate
     */
    public CommandExecutionTemplate getCommandExecutionTemplate(String command) {
        return botCommands.get(command);
    }

    /**
     * Register a new Command Execution Template to register a new command.
     *
     * @param command           String
     * @param executionTemplate CommandExecutionTemplate
     */
    public void registerCommandExecutionTemplate(String command, CommandExecutionTemplate executionTemplate) {
        botCommands.put(command, executionTemplate);
    }

}

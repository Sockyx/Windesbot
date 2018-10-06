package com.windesheim.command;

import net.dv8tion.jda.core.entities.Message;

public class Command {

    private Message commandMessage;
    private String command;
    private String[] arguments;

    public Command(Message sentMessage, String command, String[] arguments) {
        this.commandMessage = sentMessage;
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * The message object, usable for things like checking reactions.
     * @return Message
     */
    public Message getCommandMessage() {
        return commandMessage;
    }

    /**
     * The command the user is trying to execute
     * @return String
     */
    public String getCommandName() {
        return command;
    }

    /**
     * The arguments the user has specified
     * @return String[]
     */
    public String[] getArguments() {
        return arguments;
    }
}

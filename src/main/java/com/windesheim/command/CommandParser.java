package com.windesheim.command;

import com.windesheim.constant.BotConstant;
import net.dv8tion.jda.core.entities.Message;

import java.util.ArrayList;

/**
 * Class for parsing the given "command" and checking its validity
 *
 * @author Lucas Ouwens
 */
public class CommandParser {

    private static CommandParser botCommandParser = null;

    private CommandParser() {
    }

    /**
     * Command parser instance
     *
     * @return CommandParser
     */
    public static CommandParser getBotCommandParser() {
        if (botCommandParser == null) {
            botCommandParser = new CommandParser();
        }

        return botCommandParser;
    }

    /**
     * Parse the command, execute it.
     *
     * @param receivedMessage Message object containing all message data
     * @return boolean
     */
    public boolean parseCommand(Message receivedMessage) {
        String command = receivedMessage.getContentRaw().toLowerCase().split(" ")[0].replace(BotConstant.windesbotCommandPrefix, "");
        String[] arguments = this.getArguments(receivedMessage.getContentRaw());
        if (arguments != null) {
            CommandExecutionTemplate botCommandExecutor = CommandRegister.getRegister().getCommandExecutionTemplate(command);
            if (botCommandExecutor != null) {
                if (botCommandExecutor.execute(new Command(receivedMessage, command, arguments))) {
                    receivedMessage.addReaction("\u2705").queue();
                    return true;
                }
            }
        }
        receivedMessage.addReaction("\u26D4").queue();
        return false;
    }

    /**
     * Split the raw message to get the arguments for the command
     *
     * @param message String
     * @return String[] of arguments
     */
    private String[] getArguments(String message) {
        ArrayList<String> argumentContainer = new ArrayList<String>();
        if (!(message.length() == 0)) {
            for (String argument : message.split(" ")) {
                if (message.split(" ")[0].equalsIgnoreCase(argument)) continue;
                argumentContainer.add(argument);
            }

            return argumentContainer.toArray(new String[0]);
        }

        return null;
    }

}

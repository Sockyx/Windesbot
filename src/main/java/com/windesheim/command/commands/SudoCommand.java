package com.windesheim.command.commands;

import com.windesheim.command.Command;
import com.windesheim.command.CommandExecutionTemplate;
import net.dv8tion.jda.core.MessageBuilder;

/**
 * Temporary discord command for system tests.
 *
 * @author Lucas Ouwens
 */
public class SudoCommand implements CommandExecutionTemplate {

    public boolean execute(Command botCommand) {
        if(!(botCommand.getArguments().length == 0)) {
            StringBuilder messageToSend = new StringBuilder();
            for(String argument : botCommand.getArguments()) {
                messageToSend.append(argument).append(" ");
            }
            messageToSend.trimToSize();
            botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append(messageToSend.toString()).build()).submit();
            return true;
        } else {
            botCommand.getCommandMessage().getTextChannel().sendMessage("Please provide arguments, " + botCommand.getCommandMessage().getAuthor().getName() + "!").submit();
        }

        return false;
    }
}

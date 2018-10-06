package com.windesheim.listener;

import com.windesheim.command.CommandParser;
import com.windesheim.constant.BotConstant;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * A listener for commands in the text channel.
 *
 * @author Lucas Ouwens
 */
public class CommandListener extends ListenerAdapter {

    /**
     * Handle messages received with the command prefix.
     * @param event MessageReceivedEvent
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().toLowerCase().startsWith(BotConstant.windesbotCommandPrefix.toLowerCase())) {
            if(event.getChannelType() == ChannelType.TEXT) {
                CommandParser.getBotCommandParser().parseCommand(event.getMessage());
            } else {
                event.getJDA().getPrivateChannelById(event.getPrivateChannel().getId())
                        .sendMessage(new MessageBuilder("Hello, please contact me through a discord server instead!").build())
                        .submit();
            }
        }
    }
}

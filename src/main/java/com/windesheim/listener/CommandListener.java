package com.windesheim.listener;

import com.windesheim.command.CommandParser;
import com.windesheim.constant.BotConstant;
import com.windesheim.database.Database;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                if(!(event.getMember().isOwner())) {
                    try {
                        PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("SELECT role FROM Authorisation WHERE server_id = ? AND command = ?");
                        ps.setLong(1, Long.parseLong(event.getGuild().getId()));
                        ps.setString(2, event.getMessage().getContentStripped().split(" ")[0]);

                        ResultSet rs = ps.executeQuery();
                        boolean isAuthorised = false;

                        while (rs.next()) {
                            String role = rs.getString("role");
                            for (Role memberRole : event.getMember().getRoles()) {
                                if (memberRole.getName().trim().toLowerCase().equalsIgnoreCase(role)) {
                                    isAuthorised = true;
                                    break;
                                }
                            }
                        }

                        if (isAuthorised) {
                            CommandParser.getBotCommandParser().parseCommand(event.getMessage());
                        } else {
                            event.getJDA().getTextChannelById(event.getTextChannel().getId())
                                    .sendMessage(new MessageBuilder("I am sorry, you are not authorised to use this command.").build())
                                    .queue();
                        }
                    } catch (SQLException e) {
                        Logger.log(e.getMessage(), MessageType.ERROR);
                    }
                } else {
                    CommandParser.getBotCommandParser().parseCommand(event.getMessage());
                }
            } else {
                event.getJDA().getPrivateChannelById(event.getPrivateChannel().getId())
                        .sendMessage(new MessageBuilder("Hello, please contact me through a discord server instead!").build())
                        .queue();
            }
        }
    }
}

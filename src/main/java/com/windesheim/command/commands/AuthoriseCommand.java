package com.windesheim.command.commands;

import com.windesheim.command.Command;
import com.windesheim.command.CommandExecutionTemplate;
import com.windesheim.database.Database;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import net.dv8tion.jda.core.MessageBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Command for authorisation
 * Example: w!authorise <role> <command>
 *
 * @author Lucas Ouwens
 */
public class AuthoriseCommand implements CommandExecutionTemplate {

    @Override
    public boolean execute(Command botCommand) {
        String[] args = botCommand.getArguments();
        if (args.length >= 2) {
            String role = args[0];
            String command = args[1];
            if (botCommand.getCommandMessage().getGuild().getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(role))) {
                try (Connection con = Database.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO Authorisation (server_id, role, command) VALUES (?, ?, ?)")) {
                    ps.setLong(1, Long.parseLong(botCommand.getCommandMessage().getGuild().getId()));
                    ps.setString(2, role);
                    ps.setString(3, command);

                    // executeUpdate returns rows affected.
                    int executed = ps.executeUpdate();
                    if (executed > 0) {
                        botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append(String.format("The role %s is now authorised to use the command %s", role, command)).build()).queue();
                        return true;
                    } else {
                        botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append("Please contact the developer, querying the database has failed.").build()).queue();
                    }
                } catch (SQLException e) {
                    Logger.log(e.getMessage(), MessageType.ERROR);
                }
            } else {
                botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append(String.format("Could not find a role with the name %s", role)).build()).queue();
            }
        } else {
            botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append("Please use the command as follows: w!authorise <role> <command>").build()).queue();
        }

        return false;
    }

}

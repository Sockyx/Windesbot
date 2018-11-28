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
 * Command for deauthorisation
 * Example: w!unauthorise <role> <command>
 *
 * @author Lucas Ouwens
 */
public class UnauthoriseCommand implements CommandExecutionTemplate {

    @Override
    public boolean execute(Command botCommand) {
        String[] args = botCommand.getArguments();
        if (args.length >= 2) {
            String role = args[0];
            String command = args[1];
            if (botCommand.getCommandMessage().getGuild().getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(role))) {
                try (Connection con = Database.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("DELETE FROM Authorisation WHERE server_id = ? AND role = ? AND command = ?")) {
                    ps.setLong(1, Long.parseLong(botCommand.getCommandMessage().getGuild().getId()));
                    ps.setString(2, role);
                    ps.setString(3, command);

                    // executeUpdate returns rows affected.
                    int executed = ps.executeUpdate();
                    if (executed > 0) {
                        botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append(String.format("De rol %s is nu niet meer geautoriseerd om de volgende commando te gebruiken: %s", role, command)).build()).queue();
                        return true;
                    } else {
                        botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append("Er is geen autorisatie voor deze rol aanwezig.").build()).queue();
                    }
                } catch (SQLException e) {
                    Logger.log(e.getMessage(), MessageType.ERROR);
                }
            } else {
                botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append(String.format("Kon de rol met de naam %s niet vinden.", role)).build()).queue();
            }
        } else {
            botCommand.getCommandMessage().getTextChannel().sendMessage(new MessageBuilder().append("Gebruik het commando als volgt: w!unauthorise <rol> <commando>").build()).queue();
        }

        return false;
    }
}

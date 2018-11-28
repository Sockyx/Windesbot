package com.windesheim.command.commands;

import com.windesheim.command.Command;
import com.windesheim.command.CommandExecutionTemplate;
import com.windesheim.database.Database;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UbindCommand implements CommandExecutionTemplate {

    @Override
    public boolean execute(Command botCommand) {
        String[] args = botCommand.getArguments();
        if (args.length >= 2) {
            String user = args[0];
            String binding = args[1];
            if (botCommand.getCommandMessage().getGuild().getMembers().stream().anyMatch(u -> u.getEffectiveName().equalsIgnoreCase(user))) {
                try (Connection con = Database.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO user_bindings (server_id, user, untis_group) VALUES (?, ?, ?);")) {
                    ps.setLong(1, Long.parseLong(botCommand.getCommandMessage().getGuild().getId()));
                    ps.setString(2, user);
                    ps.setString(3, binding);

                    int rowsAffected = ps.executeUpdate();

                    return rowsAffected > 0;

                } catch (SQLException e) {
                    Logger.log(e.getMessage(), MessageType.ERROR);
                }
            }
        }
        return false;
    }
}

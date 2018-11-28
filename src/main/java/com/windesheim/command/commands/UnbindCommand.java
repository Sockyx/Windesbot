package com.windesheim.command.commands;

import com.windesheim.command.Command;
import com.windesheim.command.CommandExecutionTemplate;
import com.windesheim.database.Database;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Command for unbinding the groups which the role is bound to.
 *
 * @author Lucas Ouwens
 */
public class UnbindCommand implements CommandExecutionTemplate {

    @Override
    public boolean execute(Command botCommand) {
        String[] args = botCommand.getArguments();
        if (args.length >= 1) {
            String role = args[0];
            if (botCommand.getCommandMessage().getGuild().getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(role))) {
                try (Connection con = Database.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("DELETE FROM role_bindings WHERE server_id = ? AND role = ?;")) {
                    ps.setLong(1, Long.parseLong(botCommand.getCommandMessage().getGuild().getId()));
                    ps.setString(2, role);

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

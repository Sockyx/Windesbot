package com.windesheim.command.commands;

import com.windesheim.command.Command;
import com.windesheim.command.CommandExecutionTemplate;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

/**
 * Show all the commands in the bot
 *
 * @author Lucas Ouwens
 */
public class HelpCommand implements CommandExecutionTemplate {

    @Override
    public boolean execute(Command botCommand) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("Algemeen", "w!help - Dit commando", false);
        builder.addField("Authorisatie", "w!authorise <rol> <commando> - Authoriseer het gebruik van een commando" +
                "\r\nw!unauthorise <role> <command> - Deauthoriseer het gebruik van een commando", false);

        builder.addField("Binden", "w!bind <role> <untis_group> - Verbindt een rol met een untis groep.\r\nVoorbeeld: w!bind ICTm1s3 ICTM1s", false);

        builder.addField("Binnenkort..", "w!unbind <role> - Haal alle bindingen van een rol af." +
                "\r\nw!UbindCommand <user> <webuntis_group> - Verbind een gebruiker aan een webuntis groep.\r\n" +
                "w!unubind <user> - Ontbind een gebruiker van een untis groep", false);

        builder.setThumbnail("https://www.captise.nl/Portals/1/EasyDNNNews/151/600600p509EDNmain151windesheim_300x300.png");
        builder.setColor(Color.ORANGE);

        User target = botCommand.getCommandMessage().getMember().getUser();
        target.openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage(builder.build()).queue()));
        return true;
    }
}

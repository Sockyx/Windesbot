package com.windesheim.listener;

import com.windesheim.command.CommandRegister;
import com.windesheim.command.commands.AuthoriseCommand;
import com.windesheim.command.commands.SudoCommand;
import com.windesheim.command.commands.UnauthoriseCommand;

import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import com.windesheim.schedule.Schedulable;
import com.windesheim.schedule.ScheduleManager;
import com.windesheim.schedule.scheduled.WebUntisDataRetrievalSchedule;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Initialise every class here
 *
 * This class listens to the bot being ready for usage
 *
 * @author Lucas Ouwens
 */
public class BotStartupListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        // Register the commands for the bot
        CommandRegister.getRegister().registerCommandExecutionTemplate("sudo", new SudoCommand());
        CommandRegister.getRegister().registerCommandExecutionTemplate("authorise", new AuthoriseCommand());
        CommandRegister.getRegister().registerCommandExecutionTemplate("unauthorise", new UnauthoriseCommand());

        Logger.log("Commands have been registered", MessageType.INFO);

        // Register the scheduled data retrieval from webuntis.
        ScheduleManager.getManagerInstance().getScheduled().add(new WebUntisDataRetrievalSchedule());

        // Start the schedules
        ScheduleManager.getManagerInstance().getScheduled().forEach(Schedulable::schedule);

        Logger.log("Schedulers have been enabled.", MessageType.INFO);

    }
}

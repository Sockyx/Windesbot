package com.windesheim.main;

import com.windesheim.listener.BotStartupListener;
import com.windesheim.listener.CommandListener;
import com.windesheim.schedule.Schedulable;
import com.windesheim.schedule.ScheduleManager;
import com.windesheim.schedule.scheduled.WebUntisDataRetrievalSchedule;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

/**
 * The main bot class, to be constructed in the Main class.
 *
 * @author Lucas Ouwens
 */
public class Windesbot {

    /**
     * Direct instance to the bot
     */
    private JDA windesBot = null;

    /**
     * Wrapper built around the windesbot.
     */
    private static Windesbot windesbotObjectInstance = null;

    /**
     * Constructor for the Windesbot, no public constructor to dissuade people from creating an instance outside of this class.
     * @param token String the discord bot token.
     * @throws LoginException thrown upon failing to login.
     */
    private Windesbot(String token) throws LoginException, InterruptedException {
        this.windesBot = new JDABuilder(token)
                .addEventListener(new CommandListener())
                .addEventListener(new BotStartupListener())
                .setStatus(OnlineStatus.ONLINE)
                .setGame(Game.of(Game.GameType.WATCHING, " dank memes"))
                .build();

        // wait for the bot to be ready.
        windesBot.awaitReady();
    }

    /**
     * Try creating an instance to the Windesbot.
     * @param token String the given token for the bot
     * @return Windesbot the bot wrapper around the JDA object.
     */
    public static Windesbot createBotInstance(String token) {
        try {
            if(windesbotObjectInstance == null) {
                windesbotObjectInstance = new Windesbot(token);
            }

            return windesbotObjectInstance;
        } catch(LoginException | InterruptedException e) {
            // TODO logger
        }
        return null;
    }

    /**
     * Access the instance.
     * @throws NoBotInstanceException thrown upon trying to access an instance before there being one.
     * @return Windesbot The bot object wrapper.
     */
    public static Windesbot getBotInstance() throws NoBotInstanceException {
        if(windesbotObjectInstance == null) throw new NoBotInstanceException("Unable to access bot, instance was not created. See Windesbot.createBotInstance(String token)");
        return windesbotObjectInstance;
    }

    /**
     * @return JDA instance to the bot
     */
    public JDA getWindesBot() {
        return this.windesBot;
    }

    /**
     * Anonymous inner class for throwing an exception if the bot instance wasn't created, yet being accessed.
     */
    public static class NoBotInstanceException extends Exception {
        NoBotInstanceException(String message) {
            super(message);
        }
    }

}

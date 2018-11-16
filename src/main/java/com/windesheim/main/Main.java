package com.windesheim.main;

import com.windesheim.constant.BotConstant;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;

/**
 * Main class for initializing the bot.
 *
 * @author Lucas Ouwens
 */
public class Main {

    private static Windesbot windesbotWrapper = null;

    /**
     * Main method, initialises the application
     *
     * @param args
     */
    public static void main(String[] args) {
        Logger.log("Starting up the bot..", MessageType.ALL);
        windesbotWrapper = Windesbot.createBotInstance(BotConstant.discordBotToken);
        Logger.log("Bot has been initialised.", MessageType.ALL);
    }


    /**
     * Instance to the windesbot wrapper
     *
     * @return Windesbot
     */
    public static Windesbot getWindesbotWrapper() {
        return windesbotWrapper;
    }

}

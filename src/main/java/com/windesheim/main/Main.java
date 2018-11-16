package com.windesheim.main;

import com.windesheim.constant.BotConstant;

/**
 * Main class for initializing the bot.
 *
 * @author Lucas Ouwens
 */
public class Main {

    private static Windesbot windesbotWrapper = null;

    /**
     * Main method, initialises the application
     * @param args
     */
    public static void main(String[] args) {
        windesbotWrapper = Windesbot.createBotInstance(BotConstant.discordBotToken);
    }


    /**
     * Instance to the windesbot wrapper
     * @return Windesbot
     */
    public static Windesbot getWindesbotWrapper() {
        return windesbotWrapper;
    }

}

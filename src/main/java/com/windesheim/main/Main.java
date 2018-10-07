package com.windesheim.main;

import com.windesheim.command.CommandRegister;
import com.windesheim.command.commands.AuthoriseCommand;
import com.windesheim.command.commands.SudoCommand;
import com.windesheim.command.commands.UnauthoriseCommand;
import com.windesheim.constant.BotConstant;
import com.windesheim.database.Database;

import java.sql.Connection;
import java.sql.SQLException;

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
        CommandRegister.getRegister().registerCommandExecutionTemplate("sudo", new SudoCommand());
        CommandRegister.getRegister().registerCommandExecutionTemplate("authorise", new AuthoriseCommand());
        CommandRegister.getRegister().registerCommandExecutionTemplate("unauthorise", new UnauthoriseCommand());
    }


    /**
     * Instance to the windesbot wrapper
     * @return Windesbot
     */
    public static Windesbot getWindesbotWrapper() {
        return windesbotWrapper;
    }

}

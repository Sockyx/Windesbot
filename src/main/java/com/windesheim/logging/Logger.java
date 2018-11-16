package com.windesheim.logging;

/**
 * Basic logger for messages.
 *
 * @author Lucas Ouwens
 */
public class Logger {

    private static MessageType minimalLogLevel = MessageType.ALL;
    private static final String PREFIX = "[Windesbot]";


    public static void log(String message, MessageType type) {
        if (minimalLogLevel.ID >= type.ID) {
            System.out.println(PREFIX + " " + type.TEXT + ": " + message);
        }
    }

    public static void log(String message) {
        log(message, MessageType.ALL);
    }

}

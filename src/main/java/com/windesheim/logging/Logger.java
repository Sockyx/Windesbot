package com.windesheim.logging;

/**
 *
 * Basic logger for messages.
 *
 * @author Lucas Ouwens
 */
public class Logger {

    public static MessageType minimalLogLevel = MessageType.ALL;
    public static final String PREFIX = "[Windesbot]";


    public static void log(String message, MessageType type) {
        System.out.println(PREFIX + " " + minimalLogLevel.TEXT + ": " + message);
    }

    public static void log(String message) {
        log(message, MessageType.ALL);
    }

}

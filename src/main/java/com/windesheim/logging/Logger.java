package com.windesheim.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Basic logger for messages.
 *
 * @author Lucas Ouwens
 */
public class Logger {

    private static MessageType minimalLogLevel = MessageType.ALL;
    private static final String PREFIX = "[Windesbot]";


    public static void log(String message, MessageType type) {
        if (minimalLogLevel.ID <= type.ID) {
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("d-M-u H:m:s")) + "]" + PREFIX + " " + type.TEXT + ": " + message);
        }
    }

    public static void log(String message) {
        log(message, MessageType.ALL);
    }

}

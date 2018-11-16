package com.windesheim.logging;

public enum MessageType {
    ALL(0, "All"),
    INFO(1, "Info"),
    DEPRECATED(2, "Deprecated"),
    WARNING(3, "Warning"),
    ERROR(4, "Error");

    String TEXT;
    int ID;

    MessageType(int ID, String TEXT) {
        this.ID = ID;
        this.TEXT = TEXT;
    }
}

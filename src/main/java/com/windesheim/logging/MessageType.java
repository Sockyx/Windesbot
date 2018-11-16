package com.windesheim.logging;

public enum MessageType {
    ALL(0, "All"),
    ERROR(1, "Error"),
    WARNING(2, "Warning"),
    INFO(3, "Info"),
    DEPRECATED(4, "Deprecated");

    String TEXT;
    int ID;
    MessageType(int ID, String TEXT) {
        this.ID = ID;
        this.TEXT = TEXT;
    }
}

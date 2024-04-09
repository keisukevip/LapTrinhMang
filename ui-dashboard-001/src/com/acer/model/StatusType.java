package com.acer.model;

public enum StatusType {
    PENDING, APPROVED, REJECT;
    public static StatusType fromString(String str) {
        for (StatusType status : values()) {
            if (status.name().equalsIgnoreCase(str)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + str);
    }
}

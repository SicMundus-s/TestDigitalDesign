package com.digdes.school;

enum ActionType {
    INSERT("INSERT VALUES"),
    UPDATE("UPDATE VALUES"),
    DELETE("DELETE"),
    SELECT("SELECT");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public static ActionType fromString(String value) {
        if (value != null) {
            for (ActionType pt : ActionType.values()) {
                if (value.equalsIgnoreCase(pt.value)) {
                    return pt;
                }
            }
        }
        throw new IllegalArgumentException("No such value action");
    }
}
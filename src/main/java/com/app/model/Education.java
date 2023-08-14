package com.app.model;

public enum Education {

    WYŻSZE("WYŻSZE"),
    ŚREDNIE("ŚREDNIE"),
    PODSTAWOWE("PODSTAWOWE");
    private String description;

    Education(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package com.app.model;

public enum Gender {
    MĘŻCZYZNA("MĘŻCZYZNA"),
    KOBIETA("KOBIETA");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

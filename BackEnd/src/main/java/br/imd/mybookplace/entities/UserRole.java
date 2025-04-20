package br.imd.mybookplace.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    // @JsonCreator
    // public static UserRole fromString(String value) {
    //     for (UserRole role : UserRole.values()) {
    //         if (role.getRole().equalsIgnoreCase(value)) {
    //             return role;
    //         }
    //     }
    //     throw new IllegalArgumentException("Invalid role: " + value);
    // }
}
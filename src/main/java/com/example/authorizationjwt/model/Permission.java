package com.example.authorizationjwt.model;

public enum Permission {

    ADMIN_ROOT("root:admin"),
    STUDENT_ROOT("root:student"),
    TEACHER_ROOT("root:teacher");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
